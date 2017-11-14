package com.jskingen.baselib.update.dailog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jskingen.baselib.R;
import com.jskingen.baselib.update.inter.DialogListener;

/**
 * Created by ChneY on 2016/10/13.
 */
public class ConfirmDialog extends Dialog {

    DialogListener callback;
    private TextView content;
    private TextView sureBtn;
    private TextView cancelBtn;

    public ConfirmDialog(Context context, DialogListener callback) {
        super(context, R.style.CustomDialog);
        this.callback = callback;
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_confirm, null);
        sureBtn = (TextView) mView.findViewById(R.id.dialog_confirm_sure);
        cancelBtn = (TextView) mView.findViewById(R.id.dialog_confirm_cancle);
        content = (TextView) mView.findViewById(R.id.dialog_confirm_title);


        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onclick(1);
                ConfirmDialog.this.cancel();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onclick(0);
                ConfirmDialog.this.cancel();
            }
        });
        super.setContentView(mView);
    }


    public ConfirmDialog setContent(String s) {
        content.setText(s);
        return this;
    }


}