package com.jskingen.baselib.net.callback;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;

import com.jskingen.baselib.utils.AppManager;

import okhttp3.Call;

/**
 * Created by ChneY on 2017/6/27.
 */

public abstract class OnHttpCallBack<T> extends HttpCallBack<T> {
    private boolean showDialog;
    private Context mContext;
    private ProgressDialog mDialog;

    public OnHttpCallBack() {

    }

    public OnHttpCallBack(boolean showDialog) {
        this.showDialog = showDialog;
        if (showDialog) {
            mContext = AppManager.getInstance().currentActivity();
            mDialog = new ProgressDialog(mContext);
        }
    }

    public OnHttpCallBack(Context context, boolean showDialog) {
        this.showDialog = showDialog;
        if (showDialog) {
            mContext = context;
            mDialog = new ProgressDialog(mContext);
        }
    }

    @Override
    public void onStart(Call call) {
        if (showDialog && mDialog != null) {
            initDialog(call);
            mDialog.show();
        }
    }

    @Override
    public void onFinish() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    private void initDialog(final Call call) {
        mDialog.setMessage("加载中");
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                call.cancel();
            }
        });
    }
}