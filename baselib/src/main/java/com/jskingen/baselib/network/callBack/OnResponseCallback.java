package com.jskingen.baselib.network.callBack;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;

import com.jskingen.baselib.network.exception.MyException;
import com.jskingen.baselib.network.inter.IDjCallback;
import com.jskingen.baselib.network.model.HttpResult;
import com.jskingen.baselib.utils.AppManager;
import com.jskingen.baselib.utils.ToastUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ChneY on 2017/4/6.
 */

public abstract class OnResponseCallback<T> implements Callback<T>, IDjCallback<T> {
    private boolean showDialog;
    private Context mContext;
    private ProgressDialog mDialog;

    public OnResponseCallback() {

    }

    public OnResponseCallback(boolean showDialog) {
        if (mContext != null) {
            mContext = AppManager.getInstance().currentActivity().getApplicationContext();
            this.showDialog = showDialog;
            mDialog = new ProgressDialog(mContext);
        }
    }

    @Override
    public void onResponse(Call<T> call, Response<T> t) {
        if (t.isSuccessful()) {
            T result = t.body();
            if (result instanceof HttpResult) {
                try {
                    if (((HttpResult) result).isSuccess())
                        onSuccess(result);
                    else
                        onError(new MyException((HttpResult) result));
                } catch (Exception e) {
                    onError(new MyException(e.getMessage()));
                }
            } else {
                try {
                    onSuccess(t.body());
                } catch (Exception e) {
                    onError(new MyException(e.getMessage()));
                }
            }
        } else {
            onError(new MyException(t.message()));
        }
    }


    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onRequestFailure(t);
    }

    @Override
    public void onStart(final Call<T> call) {
        if (showDialog && mDialog != null) {
            initDialog(call);
            mDialog.show();
        }
    }

    @Override
    public void onRequestFailure(Throwable t) {
        if (t instanceof ConnectException) {
            ToastUtils.show("网络未连接");
        } else if (t instanceof SocketTimeoutException) {
            ToastUtils.show("连接超时");
        } else
            onError(new MyException(t.getMessage()));
        t.printStackTrace();
    }

    @Override
    public void onFinish() {
        if (mDialog != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mDialog.dismiss();
                }
            }, 250);

        }
    }

    private void initDialog(final Call<T> call) {
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
