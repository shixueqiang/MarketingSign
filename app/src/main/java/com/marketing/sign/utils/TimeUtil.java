package com.marketing.sign.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by shixq on 2017/7/2.
 */

public class TimeUtil {
    public static String getNowTimeByFormat(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date(System.currentTimeMillis());
        return dateFormat.format(date);
    }
}
