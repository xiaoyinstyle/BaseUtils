package com.jskingen.baselib.network.inter;

import com.jskingen.baselib.network.exception.MyException;

import retrofit2.Call;

/**
 * Created by ChneY on 2017/4/6.
 */

public interface IDjCallback<T> {
    void onStart(Call<T> call);

    void onFinish();

    void onSuccess(T t);

    void onError(MyException e);

    void onRequestFailure(Throwable t);
}
