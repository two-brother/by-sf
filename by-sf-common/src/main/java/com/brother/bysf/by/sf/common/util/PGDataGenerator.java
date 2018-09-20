package com.brother.bysf.by.sf.common.util;

import com.alibaba.fastjson.JSONObject;
import com.brother.bysf.by.sf.common.model.DB;
import com.brother.bysf.by.sf.common.model.Field;
import com.brother.bysf.by.sf.common.model.JDBC;
import com.brother.bysf.by.sf.common.model.Table;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import static com.brother.bysf.by.sf.common.util.DataFaker.generateDataByFieldNameAndFieldType;


/**
 * @author sk-shifanwen
 * @date 2018/9/18
 */
public class PGDataGenerator {
    private static String DRIVER = "org.postgresql.Driver";
    private static String JDBC_URL = "jdbc:postgresql://10.10.21.96:5432/";
    private static String DB_NAME = "pm_etl_test";
    private static String DB_USER_NAME = "pmbi";
    private static String DB_USER_PASSWORD = "111111";

    private static JDBC jdbc = new JDBC(DRIVER, JDBC_URL.concat(DB_NAME), DB_USER_NAME, DB_USER_PASSWORD);

     public static DB getDB(){
        DB db = new DB();
        List<Table> tables = new ArrayList<>();
        db.setName(DB_NAME);
        db.setTables(tables);
        List<String> tableNames = getTable();
        for (String tableName : tableNames) {
            String sql = "SELECT * FROM " + tableName;
            List<Field> fields = new ArrayList<>();
            String[][] rows = getFieldAndType(sql);
            for (String[] row : rows != null ? rows : new String[0][]) {
                Field field = new Field();
                field.setName(row[0]);
                field.setType(row[1]);
                fields.add(field);
            }
            Table table = new Table();
            table.setName(tableName);
            table.setFields(fields);
            tables.add(table);
        }
        return db;
    }

    public static List<String> getTable() {
        List<String> tables = new ArrayList<>();
        try (
                Connection connection = getConnection();
        ) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            String[] types = {"TABLE"};
            ResultSet resultSet = databaseMetaData.getTables(null, null, null, types);
            while (resultSet.next()){
                tables.add(resultSet.getString("TABLE_NAME"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return tables;
    }

    public static String[][] getFieldAndType(String sql) {
        try (
                Connection connection = getConnection();
                Statement stat = connection.createStatement()
        ) {
            ResultSet rs = stat.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[][] row = new String[columnCount][2];
            for (int i = 0; i < columnCount; i++) {
                row[i][0] = metaData.getColumnLabel(i + 1);
                row[i][1] = metaData.getColumnTypeName(i + 1);
            }
            return row;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        String driver = jdbc.getDriver();
        String jdbcUrl = jdbc.getJdbcUrl();
        String user = jdbc.getJdbcUserName();
        String password = jdbc.getJdbcPassword();

        Class.forName(driver);

        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);

        return DriverManager.getConnection(jdbcUrl, props);
    }

    public static Map<String, Object[][]> tableFieldAndTypeAndValueMap() throws Exception {
        Map<String, Object[][]> tableFieldAndTypeAndValueMap = new HashMap<>();
        DB db = getDB();
        List<Table> tables = db.getTables();
        for (Table table : tables) {
            List<Field> fields = table.getFields();
            Object[][] fieldAndTypeAndValue = new Object[fields.size()][3];
            for (int i = 0; i < fields.size(); i++) {
                fieldAndTypeAndValue[i][0] = "\"" + fields.get(i).getName() + "\"";
                fieldAndTypeAndValue[i][1] = fields.get(i).getType();
                fieldAndTypeAndValue[i][2] = generateDataByFieldNameAndFieldType(fields.get(i).getName(), fields.get(i).getType());
                tableFieldAndTypeAndValueMap.put(table.getName(), fieldAndTypeAndValue);
            }
        }
        return tableFieldAndTypeAndValueMap;
    }

    public static List<String> generateSql() throws Exception {
        List<String> insertSqlList = new ArrayList<>();
        Map<String, Object[][]> tableFieldAndTypeAndValueMap = tableFieldAndTypeAndValueMap();
        tableFieldAndTypeAndValueMap.forEach((tableName,fieldAndTypeAndValues) ->{
            StringBuilder fieldNameBuilder = new StringBuilder();
            StringBuilder valueBuilder = new StringBuilder();
            for (int i = 0; i < fieldAndTypeAndValues.length; i++) {
                if (i == 0){
                    fieldNameBuilder.append(fieldAndTypeAndValues[i][0]);
                    valueBuilder.append(fieldAndTypeAndValues[i][2]);
                } else {
                    fieldNameBuilder.append(",").append(fieldAndTypeAndValues[i][0]);
                    valueBuilder.append(",").append(fieldAndTypeAndValues[i][2]);
                }
            }
            String insert = "INSERT INTO " + tableName + "(" + fieldNameBuilder.toString() + ") VALUES ( " + valueBuilder.toString() + ");";
            insertSqlList.add(insert);
        });
        return insertSqlList;
    }

    public static void groupByType(){
        DB db = getDB();
        List<Table> tables = db.getTables();
        List<Field> fields = new ArrayList<>();
        for (Table table : tables) {
            fields.addAll(table.getFields());
        }
        Map<String, Set<String>> map = fields.stream().collect(Collectors.groupingBy(Field::getType, Collectors.mapping(Field::getName, Collectors.toSet())));
        System.out.println(JSONObject.toJSONString(map, true));
    }

    public static void insertToDB(String sql) {
        try (
                Connection connection = getConnection();
                Statement stat = connection.createStatement()
        ) {
            stat.execute(sql);
            System.out.println(sql);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("error sql: " + sql);
        }
    }

    public static void main(String[] args) throws Exception {
        generateSql().forEach(System.out::println);
        /*ScheduledThreadPoolExecutor  scheduled = new ScheduledThreadPoolExecutor(2);
        scheduled.scheduleAtFixedRate(() -> {
            try {
                long startTime = System.currentTimeMillis();
                System.out.println("startTime: " + LocalDateTime.now().toString());
                List<String> sqlList = generateSql();
                sqlList.forEach(PGDataGenerator::insertToDB);
                long endTime = System.currentTimeMillis();
                System.out.println("endTime: " + LocalDateTime.now().toString());
                System.out.println("generateSql and insertToDB cost time[" + (endTime - startTime) + "]ms");
                hourNum.incrementAndGet();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 3, TimeUnit.SECONDS);*/
    }
}
