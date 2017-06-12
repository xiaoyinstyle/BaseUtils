package com.jskingen.baselib.network.exception;

import com.jskingen.baselib.network.model.HttpResult;

/**
 * Created by ChneY on 2017/4/7.
 * 通用异常
 */
public class MyException extends Exception {

    private int resultCode;
    private String detailMessage = "";

    public MyException(HttpResult httpResult) {
        super(httpResult.getMsg());
        this.detailMessage = httpResult.getMsg();
        this.resultCode = httpResult.getCode();
    }

    public MyException(String detailMessage) {
        super(detailMessage);
        this.detailMessage = detailMessage;
    }

    public int getCode() {
        return resultCode;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

}
