package com.windyroad.nghia.common.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Nghia-PC on 9/26/2015.
 * List view Với Height Wrap Content
 * Chưa test
 */
public class ListViewMaxHeight extends ListView {

    public ListViewMaxHeight(Context context) {
        super(context);
    }

    public ListViewMaxHeight(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewMaxHeight(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ListViewMaxHeight(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    //Cách 1
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Đo child Item
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

        /*//? add = code = chưa test
        if(adapter.getCount() > 5){
        View item = adapter.getView(0, null, listView);
        item.measure(0, 0);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, (int) (5.5 * item.getMeasuredHeight()));
        listView.setLayoutParams(params);
        }*/
    }

}
