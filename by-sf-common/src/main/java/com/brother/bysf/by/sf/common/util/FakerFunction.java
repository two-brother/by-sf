package com.brother.bysf.by.sf.common.util;

import com.github.javafaker.Faker;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * @author sk-shifanwen
 * @date 2018/9/19
 */
public class FakerFunction {
    public final ConcurrentHashMap<String, Supplier> FIELD_NAME_TYPE_FUNCTION = new ConcurrentHashMap<>();
    public  final LocalDateTime localDateTime = LocalDateTime.now();
    public static AtomicInteger hourNum = new AtomicInteger(0);

    private Faker faker = new Faker();

    public FakerFunction() {
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

    }

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
        return Integer.parseInt(dtfHour.format(localDateTime.minusHours(hourNum.get())));
    };
    Supplier int8_day = () -> {
        DateTimeFormatter dtfDay = DateTimeFormatter.ofPattern("yyyyMMdd");
        return Integer.parseInt(dtfDay.format(localDateTime.minusHours(hourNum.get())));
    };
    Supplier int8_weekstart = () -> Integer.parseInt(getDayOfMonday());
    Supplier int8_weekend = () -> Integer.parseInt(getDayOfSunday());;
    Supplier int8_month = () -> {
        DateTimeFormatter dtfMonth = DateTimeFormatter.ofPattern("yyyyMM");
        return Integer.parseInt(dtfMonth.format(localDateTime.minusHours(hourNum.get())));
    };
    Supplier int8_year = () -> {
        DateTimeFormatter dtfYear = DateTimeFormatter.ofPattern("yyyy");
        return Integer.parseInt(dtfYear.format(localDateTime.minusHours(hourNum.get())));
    };
    Supplier int8_statoap_tx_data_bytes = () -> faker.number().randomNumber();
    Supplier aptosta_tx_data_bytes = () -> faker.number().randomNumber();
    Supplier statoap_rx_data_bytes = () -> faker.number().randomNumber();

    Supplier text_acmacs = () -> "'" + faker.internet().macAddress() + "'";

    public static void main(String[] args) {
        Faker faker = new Faker();
        System.out.println(faker.number().randomDouble(3, 0, 1));
    }

}
