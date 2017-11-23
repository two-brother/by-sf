package com.brother.bysf.by.sf.algorithm.util;

import java.time.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.util.TimeZone.getTimeZone;

/**
 * 日期工具类
 * @author sf
 * @date 2017/11/23
 */
public class DateTimeUtils {
    /**
     * 获取所有区域时区
     * @return 所有时区列表
     */
    public static List<TimeZone> getTimeZones(){
        List<TimeZone> timeZones = new ArrayList<>();
        for (String zoneId : ZoneId.getAvailableZoneIds()) {
            timeZones.add(getTimeZone(zoneId));
        }
        return timeZones;
    }

    /**
     * 获取所有时区相对UTC以秒计的原始偏移时间
     * @return 所有时区
     */
    public static Set<Long> getTimeZoneRawOffsets(){
        Set<Long> zoneOffsets = new HashSet<>();
        for (TimeZone timeZone : getTimeZones()){
            zoneOffsets.add(TimeUnit.MILLISECONDS.toSeconds(timeZone.getRawOffset()));
        }
        return zoneOffsets;
    }

    /**
     * 根据UTC时间获取相对该UTC时间为整点的时区的以秒计的原始偏移时间
     * @param utcDateTime UTC时间
     * @return 整点时区偏移时间
     */
    public static Set<Long> getHourTimeZoneRawOffsetsByUtcTime(ZonedDateTime utcDateTime){
        Set<Long> hourZoneOffsets = new HashSet<>();
        for (long rawOffset : getTimeZoneRawOffsets()){
            ZonedDateTime zonedDateTime = utcDateTime.withZoneSameInstant(ZoneOffset.ofTotalSeconds((int) rawOffset));
            if (zonedDateTime.getMinute() == 0){
                hourZoneOffsets.add(rawOffset);
            }
        }
        return hourZoneOffsets;
    }

    public static void main(String[] args) {
        for (TimeZone timeZone : getTimeZones()){
            System.out.println(timeZone.getDisplayName() + " == " + timeZone.getRawOffset());
        }

        List<Long> zoneOffsets = new ArrayList<>(getTimeZoneRawOffsets());
        Collections.sort(zoneOffsets);
        for (long zoneOffset : zoneOffsets){
            System.out.println("zoneOffset==" + zoneOffset);
        }

        ZonedDateTime utcDateTime = Instant.now().atZone(ZoneId.of("UTC")).withSecond(0).withNano(0);
        System.out.println("utc time:" + utcDateTime);
        for (long hourZoneOffset : getHourTimeZoneRawOffsetsByUtcTime(utcDateTime)){
            System.out.println("hourZoneOffset==" + hourZoneOffset);
        }
    }
}
