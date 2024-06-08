package com.lamnguyen.ticket_movie_nlu.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeFormat {
    static String patternDate = "dd/MM/yyyy";
    static String patternTime = "HH:mm";
    static DateTimeFormatter formatDateTime = DateTimeFormatter.ofPattern(patternDate + " " + patternTime);
    static DateTimeFormatter formatDate = DateTimeFormatter.ofPattern(patternDate);
    static DateTimeFormatter formatTime = DateTimeFormatter.ofPattern(patternTime);

    public static String getDate(LocalDateTime localDateTime) {
        return formatDate.format(localDateTime.toLocalDate());
    }

    public static String getTime(LocalDateTime localDateTime) {
        return formatTime.format(localDateTime.toLocalTime());
    }

    public static String getDateTime(LocalDateTime localDateTime) {
        return formatDateTime.format(localDateTime);
    }
}
