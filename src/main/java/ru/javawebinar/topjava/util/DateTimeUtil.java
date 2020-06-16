package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
    }

    public static <T extends Comparable<? super T>> boolean isBetweenInclusive(T time, T startTime, T endTime) {
        return time.compareTo(startTime) >= 0 && time.compareTo(endTime) <= 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static LocalTime parseToLocalTime(String s) {
        if (!s.isEmpty()) {
            return LocalTime.parse(s);
        } else {
            return null;
        }
    }

    public static LocalDate parseToLocalDate(String s) {
        if (!s.isEmpty()) {
            return LocalDate.parse(s);
        } else {
            return null;
        }
    }
}

