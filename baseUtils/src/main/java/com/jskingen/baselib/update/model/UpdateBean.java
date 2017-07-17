package com.jskingen.baselib.update.model;

/**
 * Created by ChneY on 2017/4/13.
 */

public class UpdateBean {

    /**
     * CODE : 1.0
     * success : true
     * MSG : 欢迎下载使用!<br/>您是否更新?
     */

    private String CODE;
    private boolean success;
    private String MSG;

    public String getCODE() {
        return CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMSG() {
        return MSG;
    }

    public void setMSG(String MSG) {
        this.MSG = MSG;
    }
}
