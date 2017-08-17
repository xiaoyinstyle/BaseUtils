package yin.style.notes.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Chne on 2017/8/16.
 * <p>
 * MD5 加密
 */

public class MD5 {

    public static String getMD5(String val) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(val.getBytes());
            byte[] m = md5.digest();//加密

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < m.length; i++) {
                sb.append(m[i]);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
