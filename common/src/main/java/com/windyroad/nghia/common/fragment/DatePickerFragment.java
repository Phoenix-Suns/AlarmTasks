package com.windyroad.nghia.common.fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by Nghia-PC on 8/19/2015.
 * Dialog chọn ngày
 */
public class DatePickerFragment extends DialogFragment {

    private static final String KEY_YEAR = "year";
    private static final String KEY_MONTH_OF_YEAR = "month";
    private static final String KEY_DAY_OF_MONTH = "day";
    private IListener mListener;

    /** Khởi tạo Dialog */
    public static DatePickerFragment newInstance(
            @Nullable int year, @Nullable int monthOfYear, @Nullable int dayOfMonth) {

        Bundle args = new Bundle();
        args.putInt(KEY_YEAR, year);
        args.putInt(KEY_MONTH_OF_YEAR, monthOfYear);
        args.putInt(KEY_DAY_OF_MONTH, dayOfMonth);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /** Khởi tạo Dialog */
    public static DatePickerFragment newInstance(Calendar calendar) {

        Bundle args = new Bundle();
        args.putInt(KEY_YEAR, calendar.get(Calendar.YEAR));
        args.putInt(KEY_MONTH_OF_YEAR, calendar.get(Calendar.MONTH));
        args.putInt(KEY_DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /** Set sự kiện */
    public void setListener(IListener listener){
        this.mListener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // lấy giá trị mặc định truyền vào,
        // không có => giờ mặc định là hiện tại
        final Calendar calendar = Calendar.getInstance();

        Bundle bundle = this.getArguments();
        int year = bundle.getInt(KEY_YEAR, calendar.get(Calendar.YEAR));
        int monthOfYear = bundle.getInt(KEY_MONTH_OF_YEAR, calendar.get(Calendar.MONTH));
        int dayOfMonth = bundle.getInt(KEY_DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));

        //--- Tạo DatePicker lấy giá trị trả về
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Trả về Listener khi chọn xong
                mListener.onDateSet(view, year, monthOfYear, dayOfMonth);
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(), listener, year, monthOfYear, dayOfMonth );

        return datePickerDialog;
    }

    /** Interface trung gian */
    public interface IListener {
        /** Đặt thời gian */
        void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth);
    }
}
