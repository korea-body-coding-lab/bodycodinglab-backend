package com.project.bcl_back.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DateUtils {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter YEAR_MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");

    // yyyy-MM-dd HH:mm:ss 포맷 형식
    public static String format(LocalDateTime dateTime) {
        return (dateTime != null) ? dateTime.format(FORMATTER) : null;
    }

    public static LocalDateTime parse(String dateString) {
        return (dateString != null && !dateString.isEmpty()) ? LocalDateTime.parse(dateString, FORMATTER) : null;
    }

    // yyyy-MM 포맷 형식
    public static String yearDateFormat(LocalDateTime dateTime) {
        return (dateTime != null) ? dateTime.format(YEAR_MONTH_FORMATTER) : null;
    }

    public static LocalDateTime yearDateParse(String dateString) {
            if (dateString == null || dateString.isEmpty()) return null;

        String fixed = dateString + "-01T00:00:00";  // ex) "2016-03" → "2016-03-01T00:00:00"
        return LocalDateTime.parse(fixed);
    }


    public static String nowFormated() {
        return format(LocalDateTime.now());
    }
}
