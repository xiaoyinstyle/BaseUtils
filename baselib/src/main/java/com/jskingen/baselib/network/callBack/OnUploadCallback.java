package com.jskingen.baselib.network.callBack;

import com.jskingen.baselib.utils.LogUtils;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ChneY on 2017/5/3.
 */

public abstract class OnUploadCallback<T> implements Callback<T> {
    public OnUploadCallback() {

    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        try {
            onSuccess(response.body());
        } catch (Exception e) {
            e.printStackTrace();
            onError(new Throwable(response.message()));
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onError(t);
    }

    public void onLoading(long total, long progress) {
        LogUtils.e(progress + "_onLoading");
    }

    public abstract void onSuccess(T t);

    public abstract void onError(Throwable e);
}