package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter IN_DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static String format(LocalDateTime date) {
        return date.format(DATE_FORMATTER);
    }

    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
    }

    public static LocalDateTime parse(String date) {
        return LocalDateTime.parse(date, IN_DATE_FORMATTER);
    }
}
