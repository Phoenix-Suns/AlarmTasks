package com.windyroad.nghia.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

/**
 * Created by Nghia-PC on 7/29/2015.
 * Xử lý Resource
 */
public class ResUtil {

    public static Bitmap getBitmap(Context context, String defType, String fileName) {
        int flagId = context.getResources().getIdentifier(fileName, defType, context.getPackageName());
        InputStream imageStream = context.getResources().openRawResource(flagId);
        return BitmapFactory.decodeStream(imageStream);
    }
}
