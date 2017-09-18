package com.jskingen.baselib.update.dailog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jskingen.baselib.R;
import com.jskingen.baselib.update.inter.DialogListener;
import com.jskingen.baselib.view.NumberProgressBar;

/**
 * Created by ChneY on 2016/10/13.
 */
public class NumberProgressDialog extends Dialog {

    DialogListener callback;
    private NumberProgressBar numberProgressBar;
    private TextView cancelBtn;

    public NumberProgressDialog(Context context, DialogListener callback) {
        super(context, R.style.CustomDialog);
        this.callback = callback;
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_number_progress, null);
        ((TextView) mView.findViewById(R.id.dialog_title)).setText("正在下载");
        numberProgressBar = (NumberProgressBar) mView.findViewById(R.id.numberProgressBar);
        numberProgressBar.setMax(100);
        numberProgressBar.setProgress(0);
        cancelBtn = (TextView) mView.findViewById(R.id.dialog_confirm_cancle);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onclick(0);
                cancel();
            }
        });
        super.setContentView(mView);
    }

    public void setNumberProgress(int progress) {
        if (numberProgressBar != null)
            numberProgressBar.setProgress(progress);
    }
}
