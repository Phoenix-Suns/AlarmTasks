package com.windyroad.nghia.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.windyroad.nghia.common.R;

public class CustomButton extends RelativeLayout {

    // child view
    TextView mTextView;

    public CustomButton(Context context) {
        super(context);
        init(context, null);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context, attrs);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        // lấy Value qua "Text" của Android: android:text="nghia"
        //mTitle = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "text");

        // Lấy Value qua Custom Attribute
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.attrsCustomButton, 0, 0);
        // Lấy thông tin trong Attr
        String title = ta.getString(R.styleable.attrsCustomButton_title);
        ta.recycle();


        // dùng Layout Inflater thêm Layout vào CustomButton
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            inflater.inflate(R.layout.view_custom_button, this);

            // find Views
            this.mTextView = (TextView) findViewById(R.id.customButton_text);

            //thiết lập cho TextView trên CustomButton hiện tại
            this.mTextView.setText(title);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Wrap_Content

        int height = 0;
        int width = 0;
        for(int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

            int h = child.getMeasuredHeight();
            int w = child.getMeasuredWidth();

            if(h > height) height = h;
            if(w > width) width = w;
        }

        if (height != 0) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }
        if (width != 0) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public String getTitle() {
        return mTextView.getText().toString();
    }

    public void setTitle(String title) {
        this.mTextView.setText(title);
    }
}
