package yin.style.baselib.net.exception;


import yin.style.baselib.net.model.HttpResult;

/**
 * Created by ChneY on 2017/4/7.
 * 通用异常
 */
public class NetException extends Exception {

    private int code = 0;

    public NetException(HttpResult httpResult) {
        super(httpResult.getMsg());
        this.code = httpResult.getCode();
    }

    public NetException(Exception message) {
        super(message);
    }

    public NetException(String message) {
        super(message);
    }

    public NetException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
