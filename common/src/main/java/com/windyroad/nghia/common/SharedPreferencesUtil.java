package com.windyroad.nghia.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Nghia-PC on 7/7/2015.
 */
public class SharedPreferencesUtil {
    /**
     * Save Preferences
     */
    public static boolean put(Context context, String prefName, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();  // Bắt đầu chỉnh sửa
        editor.putString(prefName, value);  // lưu dữ liệu
        return editor.commit();  // kết thúc chỉnh sửa
    }

    public static String get(Context context, String prefName, String defaultValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(prefName, defaultValue);
    }

    /**
     * Xóa thông tin
     */
    public static void clear(Context context, String prefName) {
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE).edit().clear().commit();
    }
}
