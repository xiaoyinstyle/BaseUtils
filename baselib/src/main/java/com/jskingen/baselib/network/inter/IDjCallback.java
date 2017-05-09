package com.jskingen.baselib.network.inter;

import com.jskingen.baselib.network.exception.DjException;

import retrofit2.Call;

/**
 * Created by ChneY on 2017/4/6.
 */

public interface IDjCallback<T> {
    void onStart(Call<T> call);

    void onFinish();

    void onSuccess(T t);

    void onError(DjException e);

    void onRequestFailure(Throwable t);
}
