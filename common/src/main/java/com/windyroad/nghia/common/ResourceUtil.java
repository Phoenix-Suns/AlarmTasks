package com.windyroad.nghia.common;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.content.res.AppCompatResources;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Nghia-PC on 7/29/2015.
 * Xử lý Resource
 */
public class ResourceUtil {

    public static Bitmap getBitmap(Context context, String defType, String fileName) {
        int flagId = context.getResources().getIdentifier(fileName, defType, context.getPackageName());
        InputStream imageStream = context.getResources().openRawResource(flagId);
        return BitmapFactory.decodeStream(imageStream);
    }

    public static void setTextSize(TextView textView, Context context, @DimenRes int dimenResId) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) context.getResources().getDimensionPixelSize(dimenResId));
    }

    public static void setTint(ImageView imageView, Context context, @ColorRes int colorRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // https://stackoverflow.com/questions/20121938/how-to-set-tint-for-an-image-view-programmatically-in-android
            imageView.setColorFilter(context.getColor(colorRes), PorterDuff.Mode.MULTIPLY);
        } else {
            // https://stackoverflow.com/questions/39437254/how-to-use-setimagetintlist-on-android-api-21
            ColorStateList csl = AppCompatResources.getColorStateList(context, colorRes);
            ImageViewCompat.setImageTintList(imageView, csl);
        }
    }
}
