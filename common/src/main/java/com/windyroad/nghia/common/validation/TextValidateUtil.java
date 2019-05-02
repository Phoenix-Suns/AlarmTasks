package com.windyroad.nghia.common.validation;

//import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.regex.Pattern;

/**
 * Created by Nghia-PC on 8/25/2015.
 * Lớp kiểm tra View hợp lệ
 * Tự hiện lỗi
 */
public class TextValidateUtil {

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

    public static boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isPhoneValid(String phone) {
        return Pattern.matches(REGEX_PHONE, phone);
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
