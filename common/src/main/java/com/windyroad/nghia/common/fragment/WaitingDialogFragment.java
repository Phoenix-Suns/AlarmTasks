package com.windyroad.nghia.common.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.windyroad.nghia.common.R;

public class WaitingDialogFragment extends DialogFragment {

    // --- Variables ---
    ProgressDialogFragment.IListener mListener;

    //--- Constructors ---
    // khởi tạo Fragment với Title
    public static WaitingDialogFragment newInstance(boolean allowCancel) {
        // khởi tạo Listener truyền dữ liệu
        WaitingDialogFragment frag = new WaitingDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putBoolean("cancel", allowCancel);

        frag.setArguments(bundle);

        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.fragment_waiting_dialog, null);

        // Khởi tạo giao diện Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(getArguments().getString("title", ""))
                .setView(rootView);
        if (getArguments().getBoolean("cancel", false))
            builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (mListener != null) mListener.onCancelClick(dialog, which);
                    dialog.dismiss();
                }
            });

        AlertDialog dialog = builder.create();

        return dialog;
    }


    // khởi tạo listener - do không cho tạo Constructor
    public void setListener(ProgressDialogFragment.IListener listener){
        mListener = listener;
    }


    /**
     * Interface trung gian
     */
    public interface IListener {
        void onCancelClick(DialogInterface dialog, int which);
    }
}
