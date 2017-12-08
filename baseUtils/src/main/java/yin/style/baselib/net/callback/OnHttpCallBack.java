package yin.style.baselib.net.callback;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;

import yin.style.baselib.R;
import yin.style.baselib.utils.AppManager;
import yin.style.baselib.view.LoadingDialog;

import okhttp3.Call;

/**
 * Created by ChneY on 2017/6/27.
 */

public abstract class OnHttpCallBack<T> extends HttpCallBack<T> {
    private boolean showDialog;
    private Context mContext;
    private LoadingDialog mDialog;

    public OnHttpCallBack() {

    }

    public OnHttpCallBack(boolean showDialog) {
        this.showDialog = showDialog;
        if (showDialog) {
            mContext = AppManager.getInstance().currentActivity();
            mDialog = new LoadingDialog.Builder(mContext)
                    .setBackground(R.drawable.progress_custom_bg_black)
                    .setTextColor(Color.WHITE)
                    .create();
        }
    }

    public OnHttpCallBack(Context context, boolean showDialog) {
        this.showDialog = showDialog;
        if (showDialog) {
            mContext = context;
            mDialog = new LoadingDialog.Builder(mContext)
                    .setBackground(R.drawable.progress_custom_bg_black)
                    .setTextColor(Color.WHITE)
                    .create();
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
                if (call != null) {
                    call.cancel();
                }
            }
        });
    }
}