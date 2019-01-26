package com.brother.bysf.by.sf.common.tool;

import com.github.javafaker.Faker;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

/**
 * @author sk-shifanwen
 * @date 2018/9/19
 */
public class FakerFunction {
    public final ConcurrentHashMap<String, Supplier> FIELD_NAME_TYPE_FUNCTION = new ConcurrentHashMap<>();
    public  final LocalDateTime localDateTime = LocalDateTime.now();
    public static AtomicLong counter = new AtomicLong(1);

    private Faker faker = new Faker();

    public FakerFunction() {
        // ==== pgsql start ====
        FIELD_NAME_TYPE_FUNCTION.put("int4", int4);
        FIELD_NAME_TYPE_FUNCTION.put("int8", int8);
        FIELD_NAME_TYPE_FUNCTION.put("float8", float8);
        FIELD_NAME_TYPE_FUNCTION.put("varchar", varchar);
        FIELD_NAME_TYPE_FUNCTION.put("text", text);
        FIELD_NAME_TYPE_FUNCTION.put("inet", inet);
        FIELD_NAME_TYPE_FUNCTION.put("macaddr", macaddr);

        FIELD_NAME_TYPE_FUNCTION.put("int4_datatype", int4_datatype);
        FIELD_NAME_TYPE_FUNCTION.put("int4_arealevel", int4_arealevel);
        FIELD_NAME_TYPE_FUNCTION.put("int8_hour", int8_hour);
        FIELD_NAME_TYPE_FUNCTION.put("int8_day", int8_day);
        FIELD_NAME_TYPE_FUNCTION.put("int8_weekstart", int8_weekstart);
        FIELD_NAME_TYPE_FUNCTION.put("int8_weekend", int8_weekend);
        FIELD_NAME_TYPE_FUNCTION.put("int8_month", int8_month);
        FIELD_NAME_TYPE_FUNCTION.put("int8_year", int8_year);
        FIELD_NAME_TYPE_FUNCTION.put("int8_statoap_tx_data_bytes", int8_statoap_tx_data_bytes);
        FIELD_NAME_TYPE_FUNCTION.put("aptosta_tx_data_bytes", aptosta_tx_data_bytes);
        FIELD_NAME_TYPE_FUNCTION.put("statoap_rx_data_bytes", statoap_rx_data_bytes);
        FIELD_NAME_TYPE_FUNCTION.put("text_acmacs", text_acmacs);
       // ==== pgsql end ====

        // ==== mysql start ====
        FIELD_NAME_TYPE_FUNCTION.put("DATETIME", int8_hour);
        FIELD_NAME_TYPE_FUNCTION.put("TIMESTAMP", int8_hour);
        FIELD_NAME_TYPE_FUNCTION.put("VARCHAR", varchar);
        FIELD_NAME_TYPE_FUNCTION.put("DECIMAL", float8);
        FIELD_NAME_TYPE_FUNCTION.put("CHAR", varchar);
        FIELD_NAME_TYPE_FUNCTION.put("BIGINT", BIGINT);
        FIELD_NAME_TYPE_FUNCTION.put("DOUBLE", float8);
        FIELD_NAME_TYPE_FUNCTION.put("INT", int4);
        FIELD_NAME_TYPE_FUNCTION.put("DECIMAL UNSIGNED", float8);
        FIELD_NAME_TYPE_FUNCTION.put("INT UNSIGNED", int4);
        FIELD_NAME_TYPE_FUNCTION.put("BIGINT UNSIGNED", BIGINT);
        FIELD_NAME_TYPE_FUNCTION.put("TINYINT", TINYINT);
        FIELD_NAME_TYPE_FUNCTION.put("SMALLINT UNSIGNED", TINYINT);
        FIELD_NAME_TYPE_FUNCTION.put("DATE", DATETIME_curr_date);


        FIELD_NAME_TYPE_FUNCTION.put("CHAR_ip", CHAR_ip);
        FIELD_NAME_TYPE_FUNCTION.put("CHAR_ac_ip", CHAR_ac_ip);
        FIELD_NAME_TYPE_FUNCTION.put("BIGINT_mac", BIGINT_mac);
        FIELD_NAME_TYPE_FUNCTION.put("INT_clock", INT_clock);

        FIELD_NAME_TYPE_FUNCTION.put("DATETIME_curr_date", DATETIME_curr_date);
        FIELD_NAME_TYPE_FUNCTION.put("DATETIME_occur_time", DATETIME_occur_time);
        FIELD_NAME_TYPE_FUNCTION.put("DATETIME_clock", DATETIME_clock);
        FIELD_NAME_TYPE_FUNCTION.put("DATETIME_User_time", DATETIME_User_time);
        FIELD_NAME_TYPE_FUNCTION.put("DATETIME_start_time", DATETIME_start_time);
        FIELD_NAME_TYPE_FUNCTION.put("DATETIME_end_time", DATETIME_end_time);
        FIELD_NAME_TYPE_FUNCTION.put("DATETIME_sta_uplinetime", DATETIME_sta_uplinetime);

        FIELD_NAME_TYPE_FUNCTION.put("TIMESTAMP_curr_time", TIMESTAMP_curr_time);
        FIELD_NAME_TYPE_FUNCTION.put("TIMESTAMP_createtime", TIMESTAMP_createtime);
        FIELD_NAME_TYPE_FUNCTION.put("TIMESTAMP_first_time", TIMESTAMP_first_time);
        FIELD_NAME_TYPE_FUNCTION.put("TIMESTAMP_cur_time", TIMESTAMP_cur_time);
        FIELD_NAME_TYPE_FUNCTION.put("TIMESTAMP_clock", TIMESTAMP_clock);
        FIELD_NAME_TYPE_FUNCTION.put("TIMESTAMP_time", TIMESTAMP_time);
        FIELD_NAME_TYPE_FUNCTION.put("TIMESTAMP_pre_time", TIMESTAMP_pre_time);
        FIELD_NAME_TYPE_FUNCTION.put("TIMESTAMP_finish_time", TIMESTAMP_finish_time);

        // ==== mysql end ====

    }

    // ==== pgsql start ====

    public String getDayOfMonday() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance(Locale.CHINA);
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return format.format(c.getTime());
    }

    public String getDayOfSunday() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance(Locale.CHINA);
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return format.format(c.getTime());
    }

    Supplier int4 = () -> faker.number().randomDigitNotZero();
    Supplier int8 = () -> faker.number().numberBetween(10, 95);
    Supplier float8 = () -> faker.number().randomDouble(3, 0, 1);
    Supplier varchar = () -> "'" + faker.internet().password() + "'";
    Supplier text = () -> "'" + faker.harryPotter().quote() + "'";
    Supplier inet = () -> "'" + faker.internet().ipV4Address() + "'";
    Supplier macaddr = () -> "'" + faker.internet().macAddress() + "'";

    Supplier int4_datatype = () -> 1;
    Supplier int4_arealevel = () -> faker.number().numberBetween(1, 5);

    Supplier int8_hour = () -> {
        DateTimeFormatter dtfHour = DateTimeFormatter.ofPattern("yyyyMMddHH");
        return Integer.parseInt(dtfHour.format(localDateTime.minusHours(counter.get())));
    };
    Supplier int8_day = () -> {
        DateTimeFormatter dtfDay = DateTimeFormatter.ofPattern("yyyyMMdd");
        return Integer.parseInt(dtfDay.format(localDateTime.minusHours(counter.get())));
    };
    Supplier int8_weekstart = () -> Integer.parseInt(getDayOfMonday());
    Supplier int8_weekend = () -> Integer.parseInt(getDayOfSunday());;
    Supplier int8_month = () -> {
        DateTimeFormatter dtfMonth = DateTimeFormatter.ofPattern("yyyyMM");
        return Integer.parseInt(dtfMonth.format(localDateTime.minusHours(counter.get())));
    };
    Supplier int8_year = () -> {
        DateTimeFormatter dtfYear = DateTimeFormatter.ofPattern("yyyy");
        return Integer.parseInt(dtfYear.format(localDateTime.minusHours(counter.get())));
    };
    Supplier int8_statoap_tx_data_bytes = () -> faker.number().randomNumber();
    Supplier aptosta_tx_data_bytes = () -> faker.number().randomNumber();
    Supplier statoap_rx_data_bytes = () -> faker.number().randomNumber();

    Supplier text_acmacs = () -> "'" + faker.internet().macAddress() + "'";

    // ==== pgsql end ====



    // ==== mysql end ====
    Supplier TINYINT = () -> faker.number().numberBetween(0, 2);
    Supplier BIGINT = () -> faker.number().randomNumber();
    Supplier BIGINT_mac = () -> counter.incrementAndGet();
    Supplier INT_clock = () -> counter.incrementAndGet();

    Supplier CHAR_ip = () -> "'" + faker.internet().ipV4Address() + "'";
    Supplier CHAR_ac_ip = () -> "'" + faker.internet().ipV4Address() + "'";

    Supplier DATETIME_curr_date = () -> "FROM_UNIXTIME(" + faker.number().numberBetween(1529946061, 1537894861) + ", '%Y-%m-%d %H:%m:%s') ";
    Supplier DATETIME_occur_time = () -> "FROM_UNIXTIME(" + faker.number().numberBetween(1529946061, 1537894861) + ", '%Y-%m-%d %H:%m:%s') ";
    Supplier DATETIME_clock = () -> "FROM_UNIXTIME(" + faker.number().numberBetween(1529946061, 1537894861) + ", '%Y-%m-%d %H:%m:%s') ";
    Supplier DATETIME_User_time = () -> "FROM_UNIXTIME(" + faker.number().numberBetween(1529946061, 1537894861) + ", '%Y-%m-%d %H:%m:%s') ";
    Supplier DATETIME_start_time = () -> "FROM_UNIXTIME(" + faker.number().numberBetween(1529946061, 1537894861) + ", '%Y-%m-%d %H:%m:%s') ";
    Supplier DATETIME_end_time = () -> "FROM_UNIXTIME(" + faker.number().numberBetween(1529946061, 1537894861) + ", '%Y-%m-%d %H:%m:%s') ";
    Supplier DATETIME_sta_uplinetime = () -> "FROM_UNIXTIME(" + faker.number().numberBetween(1529946061, 1537894861) + ", '%Y-%m-%d %H:%m:%s') ";

    Supplier TIMESTAMP_curr_time = () -> "FROM_UNIXTIME(" + faker.number().numberBetween(1529946061, 1537894861) + ", '%Y-%m-%d %H:%m:%s') ";
    Supplier TIMESTAMP_createtime = () -> "FROM_UNIXTIME(" + faker.number().numberBetween(1529946061, 1537894861) + ", '%Y-%m-%d %H:%m:%s') ";
    Supplier TIMESTAMP_first_time = () -> "FROM_UNIXTIME(" + faker.number().numberBetween(1529946061, 1537894861) + ", '%Y-%m-%d %H:%m:%s') ";
    Supplier TIMESTAMP_cur_time = () -> "FROM_UNIXTIME(" + faker.number().numberBetween(1529946061, 1537894861) + ", '%Y-%m-%d %H:%m:%s') ";
    Supplier TIMESTAMP_clock = () -> "FROM_UNIXTIME(" + faker.number().numberBetween(1529946061, 1537894861) + ", '%Y-%m-%d %H:%m:%s') ";
    Supplier TIMESTAMP_time = () -> "FROM_UNIXTIME(" + faker.number().numberBetween(1529946061, 1537894861) + ", '%Y-%m-%d %H:%m:%s') ";
    Supplier TIMESTAMP_pre_time = () -> "FROM_UNIXTIME(" + faker.number().numberBetween(1529946061, 1537894861) + ", '%Y-%m-%d %H:%m:%s') ";
    Supplier TIMESTAMP_finish_time = () -> "FROM_UNIXTIME(" + faker.number().numberBetween(1529946061, 1537894861) + ", '%Y-%m-%d %H:%m:%s') ";
    // ==== mysql end ====

}