package com.windyroad.nghia.common.fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Nghia-PC on 8/19/2015.
 * Dialog chọn Giờ
 */
public class TimePickerFragment extends DialogFragment {

    private static final String KEY_HOUR_OF_DAY = "hour_of_day";
    private static final String KEY_MINUTE = "minute";
    IListener mListener;

    // khởi tạo dữ liệu ban đầu
    public static TimePickerFragment newInstance(int hourOfDay, int minute){
        TimePickerFragment fragment = new TimePickerFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(KEY_HOUR_OF_DAY, hourOfDay);
        bundle.putInt(KEY_MINUTE, minute);
        fragment.setArguments(bundle);

        return fragment;
    }

    public static TimePickerFragment newInstance(Calendar calendar) {
        Bundle args = new Bundle();
        args.putInt(KEY_HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
        args.putInt(KEY_MINUTE, calendar.get(Calendar.MINUTE));

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // khởi tạo IListener
    public void setListener(IListener listener){
        this.mListener = listener;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // lấy giá trị mặc định truyền vào,
        // không có => giờ mặc định là hiện tại
        final Calendar calendar = Calendar.getInstance();

        Bundle bundle = this.getArguments();
        int hour = bundle.getInt(KEY_HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
        int minute = bundle.getInt(KEY_MINUTE, calendar.get(Calendar.MINUTE));

        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                // Làm gì đó khi chọn xong thời gian
                // trả về listener
                mListener.onTimeSet(timePicker, hourOfDay, minute);
            }
        };

        // Tạo khởi tạo TimePickerDialog trả về
        TimePickerDialog timeDialog = new TimePickerDialog(
                getActivity(),
                listener,
                hour, minute,
                DateFormat.is24HourFormat(getActivity()));

        return timeDialog;
    }




    public interface IListener {
        /**
         * Đặt thời gian
         * @param timePicker
         * @param hourOfDay
         * @param minute
         */
        void onTimeSet(TimePicker timePicker, int hourOfDay, int minute);
    }
}
