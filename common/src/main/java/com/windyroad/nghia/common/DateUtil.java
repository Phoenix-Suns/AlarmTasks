package com.windyroad.nghia.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Nghia-PC on 7/14/2015.
 */
public class DateUtil {

    public static final String DATE_FORMAT_COMMON = "yyyy/MM/dd HH:mm:ss";
    public static final String DATE_FORMAT_SYNC_CODE = "yyMMddHHmmssSSS";

    /**
     * get current datetime
     * */
    public static String getNowDateTime(String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());
        return sdf.format(new Date());
    }
}
