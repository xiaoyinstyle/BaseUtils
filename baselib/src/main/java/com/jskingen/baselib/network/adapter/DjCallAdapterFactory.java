package com.jskingen.baselib.network.adapter;

import com.jskingen.baselib.network.MyCall;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * Created by ChneY on 2017/4/7.
 */

public class DjCallAdapterFactory extends CallAdapter.Factory {

    public static DjCallAdapterFactory create() {
        return new DjCallAdapterFactory();
    }

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        // 获取原始类型
        Class<?> rawType = getRawType(returnType);
        // 返回值必须是CustomCall并且带有泛型
        if (rawType == MyCall.class && returnType instanceof ParameterizedType) {
            Type callReturnType = getParameterUpperBound(0, (ParameterizedType) returnType);
            return new DjCallAdapter(callReturnType);
        }
        return null;
    }
}
