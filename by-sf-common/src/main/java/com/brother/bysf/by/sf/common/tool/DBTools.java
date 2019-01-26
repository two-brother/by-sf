package com.brother.bysf.by.sf.common.tool;

import com.alibaba.fastjson.JSONObject;
import com.brother.bysf.by.sf.common.constant.Constants;
import com.brother.bysf.by.sf.common.constant.DBTypeEnum;
import com.brother.bysf.by.sf.common.model.DB;
import com.brother.bysf.by.sf.common.model.Field;
import com.brother.bysf.by.sf.common.model.JDBC;
import com.brother.bysf.by.sf.common.model.Table;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author sk-shifanwen
 * @date 2018/9/26
 */
public class DBTools {

    private static Connection getConnection(JDBC jdbc) {
        try {
            String driver = jdbc.getDriver();
            String jdbcUrl = jdbc.getJdbcUrl();
            String user = jdbc.getJdbcUserName();
            String password = jdbc.getJdbcPassword();

            Class.forName(driver);

            Properties props = new Properties();
            props.setProperty("user", user);
            props.setProperty("password", password);
            return DriverManager.getConnection(jdbcUrl, props);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public static String[][] getFieldNameAndType(JDBC jdbc, String sql) {
        try (
                Connection connection = getConnection(jdbc);
                Statement stat = connection.createStatement();
        ) {
            ResultSet rs = stat.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[][] fieldNameAndType = new String[columnCount][2];
            for (int i = 0; i < columnCount; i++) {
                fieldNameAndType[i][0] = metaData.getColumnLabel(i + 1);
                fieldNameAndType[i][1] = metaData.getColumnTypeName(i + 1);
            }
            return fieldNameAndType;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getTableNames(JDBC jdbc) {
        List<String> tableNames = new ArrayList<>();
        try (
                Connection connection = getConnection(jdbc);
        ) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            String[] types = {"TABLE"};
            ResultSet resultSet = databaseMetaData.getTables(null, null, null, types);
            while (resultSet.next()) {
                tableNames.add(resultSet.getString("TABLE_NAME"));
            }
            return tableNames;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public static List<String> getTableNamesStatic(List<String> tableNames) {
        return tableNames;
    }

    public static void insertToDB(String sql, JDBC jdbc) {
        try (
                Connection connection = getConnection(jdbc);
                Statement stat = connection.createStatement();
        ) {
            stat.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error sql: " + sql);
        }
    }

    public static void insertBatchToDB(List<String> sqlBatch, JDBC jdbc) {
        try (
                Connection connection = getConnection(jdbc);
                Statement stat = connection.createStatement();
        ) {
            connection.setAutoCommit(false);
            for (String sql : sqlBatch) {
                stat.addBatch(sql);
            }
            stat.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Object[][]> tableFieldAndTypeAndValueMap(List<Table> tables, DBTypeEnum dbType) {
        String quotes;
        if (DBTypeEnum.MYSQL == dbType) {
            quotes = Constants.MYSQL_QUOTES;
        } else {
            quotes = Constants.POSTGRESQL_QUOTES;
        }
        Map<String, Object[][]> tableFieldAndTypeAndValueMap = new HashMap<>();
        try {
            for (Table table : tables) {
                List<Field> fields = table.getFields();
                Object[][] fieldAndTypeAndValue = new Object[fields.size()][3];
                for (int i = 0; i < fields.size(); i++) {
                    fieldAndTypeAndValue[i][0] = quotes + fields.get(i).getName() + quotes;
                    fieldAndTypeAndValue[i][1] = fields.get(i).getType();
                    fieldAndTypeAndValue[i][2] = DataFaker.generateDataByFieldNameAndFieldType(fields.get(i).getName(), fields.get(i).getType());
                    tableFieldAndTypeAndValueMap.put(table.getName(), fieldAndTypeAndValue);
                }
            }
            return tableFieldAndTypeAndValueMap;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return tableFieldAndTypeAndValueMap;
    }

    public static List<String> generateSql(List<Table> tables, DBTypeEnum dbType) {
        List<String> insertSqlList = new ArrayList<>();
        try {
            DBTools.tableFieldAndTypeAndValueMap(tables, dbType).forEach((tableName, fieldAndTypeAndValues) -> {
                StringBuilder fieldNameBuilder = new StringBuilder();
                StringBuilder valueBuilder = new StringBuilder();
                for (int i = 0; i < fieldAndTypeAndValues.length; i++) {
                    if (i == 0) {
                        fieldNameBuilder.append(fieldAndTypeAndValues[i][0]);
                        valueBuilder.append(fieldAndTypeAndValues[i][2]);
                    } else {
                        fieldNameBuilder.append(",").append(fieldAndTypeAndValues[i][0]);
                        valueBuilder.append(",").append(fieldAndTypeAndValues[i][2]);
                    }
                }
                String insert = "INSERT INTO " + tableName + "(" + fieldNameBuilder.toString() + ") VALUES ( " + valueBuilder.toString() + ")";
                insertSqlList.add(insert);
            });
            return insertSqlList;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return insertSqlList;
    }

    public static void groupByType(DB db) throws SQLException {
        List<Table> tables = db.getTables();
        List<Field> fields = new ArrayList<>();
        for (Table table : tables) {
            fields.addAll(table.getFields());
        }
        Map<String, Set<String>> map = fields.stream().collect(Collectors.groupingBy(Field::getType, Collectors.mapping(Field::getName, Collectors.toSet())));
        System.out.println(JSONObject.toJSONString(map, true));
    }

    public static DB getDB(JDBC jdbc, String dbName, List<String> tableNames) {
        try {
            DB db = new DB();
            List<Table> tables = new ArrayList<>();
            db.setName(dbName);
            db.setTables(tables);
            for (String tableName : tableNames) {
                String sql = "SELECT * FROM " + tableName + " limit 1";
                List<Field> fields = new ArrayList<>();
                String[][] fieldNameAndTypes = getFieldNameAndType(jdbc, sql);
                if (null == fieldNameAndTypes) {
                    continue;
                }
                for (String[] fieldNameAndType : fieldNameAndTypes) {
                    Field field = new Field();
                    field.setName(fieldNameAndType[0]);
                    field.setType(fieldNameAndType[1]);
                    fields.add(field);
                }
                Table table = new Table();
                table.setName(tableName);
                table.setFields(fields);
                tables.add(table);
            }
            return db;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public static JDBC getMysqlJDBC(){
        String driver = "com.mysql.jdbc.Driver";
        String jdbcUrl = "jdbc:mysql://localhost:3306/";
        String dbUserName = "root";
        String dbUserPassword = "root";
        String dbName = "data_faker_test";
        return new JDBC(driver, jdbcUrl.concat(dbName).concat("?rewriteBatchedStatements=true"), dbUserName, dbUserPassword, dbName);
    }

    public static JDBC getPostgresQLJDBC(){
        String driver = "org.postgresql.Driver";
        String jdbcUrl = "jdbc:postgresql://10.10.21.96:5432/";
        String dbUserName = "pmbi";
        String dbUserPassword = "111111";
        String dbName = "pm_preview_test";
        return new JDBC(driver, jdbcUrl.concat(dbName).concat("?rewriteBatchedStatements=true"), dbUserName, dbUserPassword, dbName);
    }

    public static DB getDBConfig(JDBC jdbc, List<String> tableNames){
        if (null == tableNames || tableNames.isEmpty()) {
            tableNames = DBTools.getTableNames(jdbc);
        }
        return DBTools.getDB(jdbc, jdbc.getDbName(), tableNames);
    }

    public static JDBC getJDBC(DBTypeEnum dbType){
        switch (dbType) {
            case MYSQL: return getMysqlJDBC();
            case POSTGRESQL: return getPostgresQLJDBC();
            default: System.exit(1);
        }
        return null;
    }

}
