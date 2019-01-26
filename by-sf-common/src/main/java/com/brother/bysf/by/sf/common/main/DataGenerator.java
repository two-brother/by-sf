package com.brother.bysf.by.sf.common.main;

import com.brother.bysf.by.sf.common.constant.DBTypeEnum;
import com.brother.bysf.by.sf.common.model.DB;
import com.brother.bysf.by.sf.common.model.JDBC;
import com.brother.bysf.by.sf.common.model.Table;
import com.brother.bysf.by.sf.common.tool.DBTools;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author sk-shifanwen
 * @date 2018/9/18
 */
public class DataGenerator {

    // sk_view_db
    private static List<String> CUSTOM_TABLES_SK_VIEW_DB = Arrays.asList(
            "tfn_ac_if_total_hourly",
            "tfn_ac_total_hourly",
            "tfn_ap_if_total_hourly",
            "tfn_ap_ssid_total_hourly",
            "tfn_ap_total_hourly",
            "tfn_ap_user_total_hourly");

    // sk_view_report_db
    private static List<String> CUSTOM_TABLES_SK_VIEW_REPORT_DB = Arrays.asList(
            "tfn_ac_details",
            "tfn_ac_if_details",
            "tfn_ap_details",
            "tfn_ap_if_details",
            "tfn_ap_ssid_details",
            "tfn_ap_user_days",
            "tfn_ap_user_details",
            "tfn_hosts_details");


    public static void main(String[] args) throws Exception {
        DBTypeEnum dbType = DBTypeEnum.POSTGRESQL;

        JDBC jdbc = DBTools.getJDBC(dbType);
        DB db = DBTools.getDBConfig(jdbc, null);
        if (null == db) {
            System.exit(1);
        }
        List<Table> tables = db.getTables();
        if (null == tables) {
            System.exit(1);
        }

        Runnable task = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + "-startTime: " + LocalDateTime.now().toString());
                long startTime = System.currentTimeMillis();
                List<String> sqlBatch = new ArrayList<>();
                for (int i = 0; i < 100; i++) {
                    sqlBatch.addAll(DBTools.generateSql(tables, dbType));
                }
                System.out.println(Thread.currentThread().getName() + "-generateSql cost time[" + (System.currentTimeMillis() - startTime) + "]ms");
                startTime = System.currentTimeMillis();
                DBTools.insertBatchToDB(sqlBatch, jdbc);
                System.out.println(Thread.currentThread().getName() + "-insertToDB cost time[" + (System.currentTimeMillis() - startTime) + "]ms");
                System.out.println(Thread.currentThread().getName() + "-endTime: " + LocalDateTime.now().toString());
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        };

        ScheduledThreadPoolExecutor scheduled = new ScheduledThreadPoolExecutor(2);
        scheduled.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
    }
}
