package com.windyroad.nghia.common.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by Nghia-PC on 8/24/2015.
 * Dialog Build Cho Yes/No Question
 */
public class YesNoDialogFragment extends DialogFragment {
    //----- Bước 1: Tạo Interface, để Activity Implement -----

    /* Interface cho Activity Implement
     * Chuyển Activity đó thành Interface này
      * Gọi lại hàm trong Activity Interface đó*/
    public interface IDialogListener {
        void onDialogPositiveClick(DialogFragment dialog);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    private static final String KEY_TITLE = "title";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_POSITIVE_BUTTON_TEXT = "positive button text";
    private static final String KEY_NEGATIVE_BUTTON_TEXT = "negative button text";
    // Use this instance of the interface => deliver action events
    IDialogListener mListener;

    /** Khởi tạo Dialog */
    public static YesNoDialogFragment newInstance(
            String title, String message, String positiveButtonText, String negativeButtonText) {

        Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);
        args.putString(KEY_MESSAGE, message);
        args.putString(KEY_POSITIVE_BUTTON_TEXT, positiveButtonText);
        args.putString(KEY_NEGATIVE_BUTTON_TEXT, negativeButtonText);

        YesNoDialogFragment fragment = new YesNoDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Khởi tạo Listener truyền dữ liệu trung gian
     * @param listener
     */
    public void setListener(IDialogListener listener){
        this.mListener = listener;
    }

    // Override the Fragment.onAttach() method to instantiate the INoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
    //----- end bước 1-----


    //----- bước 3:-----
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Build dialog bắt sụ kiện trả về
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle args = getArguments();

        builder.setTitle(args.getString(KEY_TITLE, ""));
        builder.setMessage(args.getString(KEY_MESSAGE, ""));

        String positiveButtonText = args.getString(KEY_POSITIVE_BUTTON_TEXT, "");
        builder.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Send the positive button event back to the host activity
                mListener.onDialogPositiveClick(YesNoDialogFragment.this);
            }
        });
        String negativeButtonText = args.getString(KEY_NEGATIVE_BUTTON_TEXT, "");
        builder.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Send the negative button event back to the host activity
                mListener.onDialogNegativeClick(YesNoDialogFragment.this);
            }
        });
        return builder.create();
    }
}
