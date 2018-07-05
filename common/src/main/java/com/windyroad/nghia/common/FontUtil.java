package com.windyroad.nghia.common;

import android.content.Context;
import android.graphics.Typeface;

import java.lang.reflect.Field;

/**
 * Created by Nghia on 19-10-15.
 * Hỗ trợ chạy Font
 */
public class FontUtil {

    /**
     * Set font mặc định
     * @param context
     * @param staticTypefaceFieldName
     * @param fontAssetName
     * Ví dụ: FontUtil.setDefaultFont(this, "DEFAULT", "MyFontAsset.ttf");
     * FontUtil.setDefaultFont(this, "MONOSPACE", "MyFontAsset2.ttf");
     * DEFAULT, MONOSPACE, SERIF, SANS_SERIF
     */
    public static void setDefaultFont(Context context, String staticTypefaceFieldName, String fontAssetName) {

        final Typeface regular = Typeface.createFromAsset(context.getAssets(), fontAssetName);
        replaceFont(staticTypefaceFieldName, regular);
    }

    /**
     * Đổi font mặc định
     * @param staticTypefaceFieldName
     * @param newTypeface
     */
    protected static void replaceFont(String staticTypefaceFieldName, final Typeface newTypeface) {
        try {
            final Field staticField = Typeface.class.getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);
            staticField.set(null, newTypeface);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
