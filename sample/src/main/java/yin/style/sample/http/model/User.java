package yin.style.sample.http.model;

/**
 * Created by ChneY on 2017/4/6.
 */

public class User {
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
