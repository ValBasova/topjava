package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter inDATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    public static String format(LocalDateTime date) {
        return date.format(DATE_FORMATTER);
    }

    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
    }

    public static String parseto(String date) {
        return LocalDateTime.parse(date, inDATE_FORMATTER).format(DATE_FORMATTER);
    }

    public static LocalDateTime parse(String date) {
        return LocalDateTime.parse(parseto(date), DATE_FORMATTER);
    }
}
