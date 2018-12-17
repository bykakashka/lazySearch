package com.byka.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

    public static String formatDate(final Date date) {
        return formatter.format(date);
    }
}
