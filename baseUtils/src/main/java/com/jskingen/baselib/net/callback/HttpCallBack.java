package com.jskingen.baselib.net.callback;

import com.google.gson.Gson;
import com.jskingen.baselib.net.exception.NetException;
import com.jskingen.baselib.net.inter.ICallBack;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by ChneY on 2017/6/22.
 */

public abstract class HttpCallBack<T> implements ICallBack {

    @Override
    public void onSuccess(String result) {
        Class<?> clz = null;
        try {
            clz = analysisClassInfo(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        T t;
        if (clz.equals(String.class) || clz.equals(null)) {
            t = (T) result;
        } else
            t = (T) new Gson().fromJson(result, clz);

        try {
            onSuccess(t);
        } catch (Exception e) {
            onError(e.getMessage());
        }
    }

    @Override
    public void onError(String result) {
        onError(new NetException(result));
    }

    public abstract void onSuccess(T t);

    public abstract void onError(NetException exception);

    private Class<T> analysisClassInfo(Object object) {
        Type genType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        return (Class<T>) params[0];
    }

}