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
public class NumberProgressDialog extends Dialog {

    DialogListener callback;
    private TextView content;
    private TextView sureBtn;
    private TextView cancleBtn;

    public NumberProgressDialog(Context context, DialogListener callback) {
        super(context, R.style.CustomDialog);
        this.callback = callback;
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_confirm, null);
        sureBtn = (TextView) mView.findViewById(R.id.dialog_confirm_sure);
        cancleBtn = (TextView) mView.findViewById(R.id.dialog_confirm_cancle);
        content = (TextView) mView.findViewById(R.id.dialog_confirm_title);


        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onclick(1);
                cancel();
            }
        });
        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onclick(0);
                cancel();
            }
        });
        super.setContentView(mView);
    }


    public NumberProgressDialog setContent(String s) {
        content.setText(s);
        return this;
    }


}
