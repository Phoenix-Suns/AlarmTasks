package com.windyroad.nghia.common.view;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TimePicker;

import com.windyroad.nghia.common.R;

/**
 * Created by Nghia on 2/19/2018.
 */
public class ViewsUtil {
    public static void changeIconDrawableToGray(Context context, Drawable drawable) {
        if (drawable != null) {
            drawable.mutate();
            drawable.setColorFilter(ContextCompat.getColor(context, R.color.dark_gray), PorterDuff.Mode.SRC_ATOP);
        }
    }

    /**
     * Check Position Inside View
     */
    public static boolean isInsideView(View containView, Float x, Float y) {
        return containView.getLeft() + containView.getTranslationX() <= x
                && x <= containView.getRight() + containView.getTranslationX()
                && containView.getTop() + containView.getTranslationY() <= y
                && y <= containView.getBottom() + containView.getTranslationY();
    }
}
