package com.jskingen.baselib.network;

import android.os.Handler;
import android.os.Looper;

import com.jskingen.baselib.network.callBack.OnResponseCallback;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ChneY on 2017/4/6.
 */

public class MyCall<T> {
    private Handler mHandler =  new Handler(Looper.getMainLooper());
    private Call<T> call;

    public MyCall(Call<T> call) {
        this.call = call;
    }

    public void enqueue(final OnResponseCallback<T> callback) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onStart(call);
            }
        });

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(final Call<T> call, final Response<T> response) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onResponse(call, response);
                        callback.onFinish();
                    }
                });

            }

            @Override
            public void onFailure(final Call<T> call, final Throwable t) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(call, t);
                        callback.onFinish();
                    }
                });
            }
        });
    }

    public T execute() throws IOException {
        return call.execute().body();
    }

    public void cancel() {
        call.cancel();
    }
}
