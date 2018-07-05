package com.windyroad.nghia.common.validation;

//import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.regex.Pattern;

/**
 * Created by Nghia-PC on 8/25/2015.
 * Lớp kiểm tra View hợp lệ
 * Tự hiện lỗi
 */
public class ValidateUtil {

    /* Các lỗi cơ bản:
    requited - không rỗng
    Range - giới hạn (10 => 100) - Kiểu
    Length - độ dài (100 kí tự)
    Regular Expression (rex) - sai định dạng hiển thị
    Equal Value - So với field khác
     */

    public static final String REGEX_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String REGEX_PHONE = "\\d{3}-\\d{7}";
    // ^$|pattern = có thể null
    public static final String REGEX_POSITIVE_NUMBER = "^$|[0-9]{1,10}$";


    //region TextView Validate
    /**
     * Kiểm tra Email, tự set lỗi
     * @param editText
     * @param errorMessage
     * @return true: đúng email
     */
    public static boolean isEmailAddress(TextView editText, String errorMessage) {
        return isRegexValid(editText, REGEX_EMAIL, errorMessage);
    }

    /**
     * Kiểm tra Phone, tự set lỗi
     * @param textView
     * @param errorMessage
     * @return true: đúng phone
     */
    public static boolean isPhoneNumber(TextView textView, String errorMessage) {
        return isRegexValid(textView, REGEX_PHONE, errorMessage);
    }


    /**
     * Kiểu tra Regex, tự set lỗi
     * @param textView
     * @param regex
     * @param errorMessage
     * @return true: trùng regex
     */
    public static boolean isRegexValid(TextView textView, String regex, String errorMessage) {
        textView.setError(null);  // clear error

        String text = textView.getText().toString().trim();

        // pattern doesn't match so returning false
        if (!Pattern.matches(regex, text)) {
            textView.setError(errorMessage);
            return false;
        }

        return true;
    }

    /** kiểm tra có Text, tự set lỗi
     * True: nếu có Text*/
    public static boolean hasText(TextView textView, String errorMessage) {
        textView.setError(null);  // clear error

        String text = textView.getText().toString().trim();

        if (TextUtils.isEmpty(text)) {
            // Không có text
            textView.setError(errorMessage);
            return false;
        }

        return true;
    }


    /**
     * Kiểm tra trùng
     * @param textView
     * @param editText2
     * @param errorMessage
     * @return true: nếu trùng
     */
    public static boolean isTextEquals(TextView textView, EditText editText2, String errorMessage) {
        // clear error
        textView.setError(null);
        editText2.setError(null);

        String text1 = textView.getText().toString().trim();
        String text2 = editText2.getText().toString().trim();

        if (!text1.equals(text2)) {
            // Không có text
            textView.setError(errorMessage);
            editText2.setError(errorMessage);

            return false;
        }

        return true;
    }
    public static boolean isPositiveNumber(TextView textView, String errorMessage) {
        return isRegexValid(textView, REGEX_POSITIVE_NUMBER, errorMessage);
    }

    //endregion


    /*//region TextInputLayout Validate
    *//**
     * Kiểm tra Email, tự set lỗi
     * @param inputLayout
     * @param errorMessage
     * @return true: đúng email
     *//*
    public static boolean isEmailAddress(TextInputLayout inputLayout, String errorMessage) {
        return isRegexValid(inputLayout, REGEX_EMAIL, errorMessage);
    }

    *//**
     * Kiểm tra Phone, tự set lỗi
     * @param inputLayout
     * @param errorMessage
     * @return true: đúng phone
     *//*
    public static boolean isPhoneNumber(TextInputLayout inputLayout, String errorMessage) {
        return isRegexValid(inputLayout, REGEX_PHONE, errorMessage);
    }


    *//**
     * Kiểu tra Regex, tự set lỗi
     * @param inputLayout
     * @param regex
     * @param errorMessage
     * @return true: trùng regex
     *//*
    public static boolean isRegexValid(TextInputLayout inputLayout, String regex, String errorMessage) {
        // clear error
        inputLayout.setError(null);
        inputLayout.setErrorEnabled(false);

        if (!isRegexValid(inputLayout.getEditText(), regex, errorMessage)) {
            // KHông trùng Pattern => lỗi
            inputLayout.setErrorEnabled(true);
            inputLayout.setError(errorMessage);

            return false;
        }

        return true;
    }

    *//** kiểm tra có Text, tự set lỗi
     * True: nếu có Text*//*
    public static boolean hasText(TextInputLayout inputLayout, String errorMessage) {
        // clear error
        inputLayout.setError("");
        inputLayout.setErrorEnabled(false);

        if (!hasText(inputLayout.getEditText(), errorMessage)){
            // Không có text => lỗi
            inputLayout.setErrorEnabled(true);
            inputLayout.setError(errorMessage);

            return false;
        }

        return true;
    }

    *//**
     * Kiểm tra trùng
     * @param inputLayout1
     * @param inputLayout2
     * @param errorMessage
     * @return true: nếu trùng
     *//*
    public static boolean isTextEquals(TextInputLayout inputLayout1, TextInputLayout inputLayout2, String errorMessage) {

        // clear error
        inputLayout1.setError(null);
        inputLayout2.setError(null);
        inputLayout1.setErrorEnabled(false);
        inputLayout2.setErrorEnabled(false);

        if (!isTextEquals(inputLayout1.getEditText(), inputLayout2.getEditText(), errorMessage)) {
            // Không có text
            inputLayout1.setErrorEnabled(true);
            inputLayout2.setErrorEnabled(true);
            inputLayout1.setError(errorMessage);
            inputLayout2.setError(errorMessage);

            return false;
        }

        return true;
    }

    public static boolean isPositiveNumber(TextInputLayout inputLayout, String errorMessage) {
        return isRegexValid(inputLayout, REGEX_POSITIVE_NUMBER, errorMessage);
    }
    //endregion*/


    //region Text Validate
    public static boolean hasText(String text) {
        return !TextUtils.isEmpty(text);
    }

    public static boolean isTextEquals(String... texts){
        for (int i = 1; i < texts.length; i++){
            if (!texts[i].equals(texts[i-1])){
                return false;
            }
        }
        return true;
    }


    /**
     * todo chưa test
     */
    public static boolean inRange(Object value, Object minValue, Object maxValue) {
        if (value instanceof Short) {
            return inRange((Short) value, (Short) minValue, (Short) maxValue);
        }
        if (value instanceof Integer) {
            return inRange((Integer) value, (Integer) minValue, (Integer) maxValue);
        }
        if (value instanceof Long) {
            return inRange((Long) value, (Long) minValue, (Long) maxValue);
        }
        if (value instanceof Float) {
            return inRange((Float) value, (Float) minValue, (Float) maxValue);
        }
        if (value instanceof Double) {
            return inRange((Double) value, (Double) minValue, (Double) maxValue);
        }
        if (value instanceof String) {
            return inRange((String) value, (Integer) minValue, (Integer) maxValue);
        }
        if (value instanceof Calendar) {
            return inRange((Calendar) value, (Calendar) minValue, (Calendar) maxValue);
        }
        return false;
    }

    //region InRange
    public static boolean inRange(Calendar value, Calendar minValue, Calendar maxValue) {
        return (value.after(minValue) && value.before(maxValue)) 
                || value.equals(minValue) || value.equals(maxValue);
    }

    public static boolean inRange(String value, Integer minValue, Integer maxValue) {
        return value.length() >= minValue && value.length() <= maxValue;
    }

    public static boolean inRange(Double value, Double minValue, Double maxValue) {
        return value >= minValue && value <= maxValue;
    }

    public static boolean inRange(Float value, Float minValue, Float maxValue) {
        return value >= minValue && value <= maxValue;
    }

    public static boolean inRange(Long value, Long minValue, Long maxValue) {
        return value >= minValue && value <= maxValue;
    }

    public static boolean inRange(Integer value, Integer minValue, Integer maxValue) {
        return value >= minValue && value <= maxValue;
    }

    public static boolean inRange(Short value, Short minValue, Short maxValue) {
        return value >= minValue && value <= maxValue;
    }
    //endregion

    public static boolean isRegexValid(String text, String regex){
        return Pattern.matches(regex, text);
    }

    public static boolean isPositiveNumber(String text) {
        return isRegexValid(text, REGEX_POSITIVE_NUMBER);
    }
    //endregion
}
