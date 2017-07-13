package com.jskingen.baseutils.http.model;

/**
 * Created by ChneY on 2017/4/6.
 */

public class UserBean {
    /**
     * data : {"phone":"18900000000","username":"admin","userid":1}
     * code : 0
     */

    private DataBean data;
    private int code;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class DataBean {
        /**
         * phone : 18900000000
         * username : admin
         * userid : 1
         */

        private String phone;
        private String username;
        private int userid;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }
    }
}
