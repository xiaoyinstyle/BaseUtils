package com.jskingen.baselib.network.callBack;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.jskingen.baselib.utils.AppManager;
import com.jskingen.baselib.utils.GsonUtils;
import com.jskingen.baselib.utils.LogUtils;
import com.jskingen.baselib.view.NumberProgressBar;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.request.RequestCall;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ChneY on 2017/5/3.
 */

public abstract class OnUploadCallback<T> extends Callback<String> {
    private static final String TAG = "MainActivity";
    private RequestCall call;

    private boolean showDialog;
    private Context mContext;
    private AlertDialog mDialog;
    private NumberProgressBar numberProgressBar;

    public OnUploadCallback() {
        RequestCall call;
    }

    public OnUploadCallback(AlertDialog mDialog) {
        if (mContext == null) {
            mContext = AppManager.getInstance().currentActivity();
            this.showDialog = true;
            this.mDialog = mDialog;
        }
    }

    public OnUploadCallback(Context context, boolean showDialog) {
        this.showDialog = showDialog;
        if (showDialog) {
            mContext = context;
        }
    }

    public OnUploadCallback(boolean showDialog) {
        this.showDialog = showDialog;
        if (showDialog) {
            mContext = AppManager.getInstance().currentActivity();
        }
    }

    @Override
    public void onBefore(Request request, int id) {
        if (showDialog)
            showDialog();
    }

    @Override
    public void onAfter(int id) {

    }

    @Override
    public void onError(Call call, Exception e, int id) {
        e.printStackTrace();
        onError(e);
    }

    @Override
    public String parseNetworkResponse(Response response, int id) throws Exception {
        return response.body().toString();
    }

    @Override
    public void onResponse(String string, int id) {
        Log.e(TAG, "onResponse：complete");
//        onSuccess(GsonUtils.getObject(string,T));
        switch (id) {
            case 100:
//                Toast.makeText(MainActivity.this, "http", Toast.LENGTH_SHORT).show();
                break;
            case 101:
//                Toast.makeText(MainActivity.this, "https", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void inProgress(float progress, long total, int id) {
        onLoading(total, (int) (progress * 100));
    }


    /**
     *
     */
    private void showDialog() {
        if (mDialog == null) {
            numberProgressBar = new NumberProgressBar(mContext);

            mDialog = new AlertDialog.Builder(mContext)
                    .setTitle("正在上传")
                    .setView(numberProgressBar, 20, 50, 20, 50)
                    .setCancelable(false)
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mDialog.dismiss();
                            if (call != null)
                                call.cancel();

                        }
                    }).create();
        }
        mDialog.show();
    }

    public void onLoading(long total, int progress) {
        LogUtils.e(progress + "_onLoading");
        if (numberProgressBar != null)
            numberProgressBar.setProgress(progress);
    }

    public abstract void onSuccess(T t);

    public abstract void onError(Throwable e);

    public void setCall(RequestCall call) {
        this.call = call;
    }
}