package com.jskingen.baselib.net.model;

/**
 * Created by ChneY on 2017/4/7.
 */
public class HttpResult<T> extends BaseBean {

    private final int SUCCESS = 0;//返回成功

    private String msg;
    private int code;
    private T data;

    /**
     * msg : 账号或密码不正确
     */

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return code == SUCCESS;
    }
}
