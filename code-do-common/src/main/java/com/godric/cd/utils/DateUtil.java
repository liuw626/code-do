package com.godric.cd.utils;


import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;

public class DateUtil {

    public static LocalDateTime getFirstDayByWeek() {
        return LocalDateTime.now().with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)).plusDays(1).withHour(0).withMinute(0).withSecond(0);
    }

    public static String getTimeDiff(LocalDateTime t1, LocalDateTime t2) {
        // 转秒
        long timeStamp1 = t1.toEpochSecond(ZoneOffset.of("+8"));
        long timeStamp2 = t2.toEpochSecond(ZoneOffset.of("+8"));

        long diff = Math.abs(timeStamp1 - timeStamp2);
        long hours = diff / 60 / 60;
        long days = 0L;
        if (hours >= 24) {
            days = hours / 24;
            hours = hours % 24;
        }

        return days > 0 ? days + "d" + (hours > 0 ? hours + "h" : "") : hours + "h";
    }

    public static LocalDateTime getStartTimeOfQuarter() {
        LocalDateTime now = LocalDateTime.now();
        Month month = now.getMonth();
        Month firstMonthOfQuarter = month.firstMonthOfQuarter();
        return LocalDateTime.of(now.getYear(), firstMonthOfQuarter, 1, 0, 0, 0);
    }

    public static LocalDateTime getEndTimeOfQuarter() {
        LocalDateTime now = LocalDateTime.now();
        Month month = now.getMonth();
        Month firstMonthOfQuarter = month.firstMonthOfQuarter();
        Month endMonthOfQuarter = Month.of(firstMonthOfQuarter.getValue() + 2);
        return LocalDateTime.of(now.getYear(), endMonthOfQuarter, endMonthOfQuarter.length(now.toLocalDate().isLeapYear()), 23, 59, 59);
    }

}
