package yin.style.baselib.net.utils;

import android.text.TextUtils;

import java.net.URLEncoder;
import java.util.Map;

public class BHUtils {
    public static String appendParams(String url, Map<String, String> params) {
        if (params == null || params.isEmpty())
            return url;

        StringBuilder sb = new StringBuilder(url);
        if (sb.indexOf("?") <= 0) {
            sb.append("?");
        } else {
            if (!sb.toString().endsWith("&")) {
                sb.append("&");
            }
        }

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (!TextUtils.isEmpty(entry.getKey()) && !TextUtils.isEmpty(entry.getValue()))
                sb.append(entry.getKey()).append("=")
                        .append(encode(entry.getValue().toString()))
                        .append("&");
        }
        return sb.toString();
    }

    public static String encode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 把手拼的unicode字符串(\u0000\u1111)转化为可被识别的字符
     */
    public static String unicodeStringDecode(String unicodeStr) {
        if (unicodeStr == null) {
            return null;
        }
        StringBuilder retBuf = new StringBuilder();
        int maxLoop = unicodeStr.length();
        for (int i = 0; i < maxLoop; i++) {
            if (unicodeStr.charAt(i) == '\\') {
                if ((i < maxLoop - 5) &&
                        ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr.charAt(i + 1) == 'U')))
                    try {
                        // 关键一句, 转化为16进制, 再转化为char
                        retBuf.append((char) Integer.parseInt(unicodeStr.substring(i + 2, i + 6), 16));
                        i += 5;
                    } catch (NumberFormatException localNumberFormatException) {
                        retBuf.append(unicodeStr.charAt(i));
                    }
                else
                    retBuf.append(unicodeStr.charAt(i));
            } else {
                retBuf.append(unicodeStr.charAt(i));
            }
        }
        return retBuf.toString();
    }
}
