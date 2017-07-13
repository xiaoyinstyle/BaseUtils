package com.jskingen.baselib.network.adapter;

import com.jskingen.baselib.network.MyCall;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;

/**
 * Created by ChneY on 2017/4/7.
 */

public class MyCallAdapter<R> implements CallAdapter<R, MyCall<?>> {

    private final Type responseType;

    // 下面的 responseType 方法需要数据的类型
    MyCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public MyCall<R> adapt(Call<R> call) {
        // 由 CustomCall 决定如何使用
        return new MyCall<>(call);
    }


}
