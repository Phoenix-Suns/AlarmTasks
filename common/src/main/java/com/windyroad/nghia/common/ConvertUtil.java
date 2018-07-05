package com.windyroad.nghia.common;

import android.support.annotation.Nullable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Nghia-PC on 7/23/2015.
 * Chuyển Đổi
 */
public class ConvertUtil {

    // blod => Bitmap
    // byte[] byteHinhNho = cursor.getBlob(cursor.getColumnIndex("HinhNho"));
    // Bitmap hinhNho = BitmapFactory.decodeByteArray(byteHinhNho, 0, byteHinhNho.length);


    /**string => calendar
     * @param _strDateTime
     * @param _formatPattern format string
     * @param _locale locale (can null)
     * @param _defaultValue
     * @return
     */
    public static Calendar String2Calendar(String _strDateTime, @Nullable String _formatPattern,
                                           @Nullable Locale _locale, @Nullable Calendar _defaultValue) {

        Calendar calendarTemp = Calendar.getInstance();

        // Gán giá trị null
        if(_formatPattern == null) _formatPattern = CalendarUtil.DATE_TIME_FORMAT_COMMON;
        _locale = _locale == null ? Locale.getDefault() : _locale;

        try {

            SimpleDateFormat sdf = new SimpleDateFormat(_formatPattern, _locale);
            Date date = sdf.parse(_strDateTime);

            calendarTemp.setTime(date);
        }
        catch (Exception ex) {
            ex.printStackTrace();

            if (_defaultValue == null) {
                calendarTemp.setTime(new Date(Long.MIN_VALUE)); // giá tr? nh? nh?t
            }
            else {
                calendarTemp = _defaultValue;
            }
        }
        return calendarTemp;
    }

    /**Calendar => String
     * @param _calendar input calendar
     * @param _formatPattern format string (nullable)
     * @param _locale locale (nullable)
     * @return
     */
    public static String Calendar2String(
            Calendar _calendar, @Nullable String _formatPattern, @Nullable Locale _locale) {

        // Gán giá trị null
        if(_formatPattern == null) _formatPattern = CalendarUtil.DATE_TIME_FORMAT_COMMON;
        _locale = _locale == null ? Locale.getDefault() : _locale;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(_formatPattern, _locale);
            return sdf.format(_calendar.getTime());

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    /**
     * String => Date
     * @param _strValue
     * @param _myFormat
     * @param _locale can null
     * @return
     */
    public static Date String2Date(String _strValue, String _myFormat, Locale _locale) {

        _locale = _locale == null ? Locale.getDefault() : _locale; // locale null
        try {

            SimpleDateFormat sdf = new SimpleDateFormat(_myFormat, _locale);
            return sdf.parse(_strValue);

        } catch (Exception e) {
            return null;
        }
    }

    /** Date => String
     * @param _formatPattern: dateFormat, timeFormat (nullable)
     * @param _locale: nullable
     * */
    public static String Date2String(Date _value, String _formatPattern, Locale _locale) {
        // Gán giá trị null
        if(_formatPattern == null) _formatPattern = CalendarUtil.DATE_TIME_FORMAT_COMMON;
        _locale = _locale == null ? Locale.getDefault() : _locale;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(_formatPattern, _locale);
            return sdf.format(_value);

        } catch (Exception e) {
            return null;
        }
    }


    public static int String2Int(String _stringValue, int _defautValue) {
        try {
            return Integer.parseInt(_stringValue);
        }
        catch (Exception e) {
            return _defautValue;
        }
    }

    public static double toDouble(String _stringValue, double _defaultValue) {
        try {
            return Double.parseDouble(_stringValue);
        }
        catch (Exception e) {
            return _defaultValue;
        }
    }

    public static <T extends Enum<T>> T String2Enum(Class<T> enumType, String name){
        return Enum.valueOf(enumType, name);
    }


    /**
     * Stream => String
     * @param inputStream
     * @return
     */
    public static String Stream2String(InputStream inputStream){
        try {

            InputStreamReader streamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            StringBuilder builder = new StringBuilder();

            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line + "\n");
            }

            bufferedReader.close();
            return builder.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String Object2String(Object _objectValue) {
        return _objectValue + "";
    }


    /** USING: dp => px (set padding, marrgin)
     * _context.getResources().getDisplayMetrics().density;*/
    public static int dp2px(float _scale, int _dpValue) {
        return (int) (_dpValue * _scale + 0.5f);
    }

    public static int object2Int(Object _objectValue, int _defautValue) {
        try {
            return Integer.valueOf((String)_objectValue);
        }
        catch (Exception e) {
            return _defautValue;
        }
    }
}
