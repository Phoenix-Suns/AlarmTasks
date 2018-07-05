package com.windyroad.nghia.common.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.windyroad.nghia.common.R;


public class EditTextDialogFragment extends DialogFragment {

    // --- Variables ---
    private EditText mEditText;
    IDialogListener mListener;

    //--- Constructors ---
    // khởi tạo Fragment với Title
    public static EditTextDialogFragment newInstance(String title, String value) {
        // khởi tạo Listener truyền dữ liệu
        EditTextDialogFragment frag = new EditTextDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("value", value);

        frag.setArguments(bundle);

        return frag;
    }

    // khởi tạo listener - do không cho tạo Constructor
    public void setListener(IDialogListener listener){
        mListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // khởi tạo Fragment View Dialog
        // find Views
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_edit_text_dialog, null);
        mEditText = view.findViewById(R.id.editText);
        mEditText.setText(getArguments().getString("value", ""));

        // Khởi tạo giao diện Dialog
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(getArguments().getString("title", "Enter Text"))
                .setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // send success
                        mListener.positiveButton_onClick(mEditText.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();

        return dialog;
    }


    /**
     * Interface trung gian
     */
    public interface IDialogListener {
        void positiveButton_onClick(String value);
    }
}
