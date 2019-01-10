package yin.style.baselib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import yin.style.baselib.BaseHelp;

/**
 * Author by ChneYin, Email 976370887@qq.com, Date on  2018/12/14.
 * 上传并检查当前App的 一些基础信息，并上传到服务器
 */
public class CheckCurrentAppTools {
    private static final String TAG = "CheckCurrentAppTools";

    private static long lastUploadTime = 0;
    private static String resultStr = "";

    public static void runThread(Context context, String remarks, final CheckListener listener) {
        runThread(context, "http://yinstyle.linkpc.net:8080/appmanager/api/check", remarks, listener);
    }

    public static void runThread(Context context, String url, String remarks, final CheckListener listener) {
        if (!TextUtils.isEmpty(resultStr) && System.currentTimeMillis() < lastUploadTime) {
            listener.result(context, resultStr);
            return;
        }

        HashMap<String, String> maps = new HashMap<>();
        maps.put("packag", context.getPackageName());
        maps.put("imei", AppUtil.getIMEI(context));
        maps.put("model", AppUtil.getSystemModel());
        maps.put("remarks", TextUtils.isEmpty(remarks) ? AppUtil.getDeviceBrand() : remarks);
        maps.put("name", AppUtil.getAppName(context));
//        maps.put("remarks", "");
        runThread(context, url, maps, listener);
    }

    public static void runThread(final Context context, final String url, final HashMap<String, String> maps, final CheckListener listener) {
        try {
            if (getSP(context))
                return;

            if (listener == null)
                return;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    resultStr = requestPost(url, maps);
                    if (resultStr == null || resultStr == "") {
                        listener.exception();
                        lastUploadTime = System.currentTimeMillis() + 10 * 60 * 1000;
//                        lastUploadTime = System.currentTimeMillis() + 10 * 1000;
                    } else {
                        if (listener.result(context, resultStr))
//                            lastUploadTime = System.currentTimeMillis() + 60 * 1000;
                            lastUploadTime = System.currentTimeMillis() + 2 * 60 * 60 * 1000;
                        else {
//                            lastUploadTime = System.currentTimeMillis() + 60 * 1000;
                            lastUploadTime = System.currentTimeMillis() + 10 * 60 * 1000;
                        }
                    }
                }
            }).start();
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    /**
     * POST请求实现
     *
     * @param paramsMap
     */
    private static String requestPost(String urlStr, HashMap<String, String> paramsMap) {
        String result = null;
        try {
            if (TextUtils.isEmpty(urlStr))
                return null;
            //合成参数
            StringBuilder tempParams = new StringBuilder();
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                pos++;
            }
            String params = tempParams.toString();
            // 请求的参数转换为byte数组
            byte[] postData = params.getBytes();
            // 新建一个URL对象
            URL url = new URL(urlStr);
            // 打开一个HttpURLConnection连接
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            // 设置连接超时时间
            urlConn.setConnectTimeout(5 * 1000);
            //设置从主机读取数据超时
            urlConn.setReadTimeout(5 * 1000);
            // Post请求必须设置允许输出 默认false
            urlConn.setDoOutput(true);
            //设置请求允许输入 默认是true
            urlConn.setDoInput(true);
            // Post请求不能使用缓存
            urlConn.setUseCaches(false);
            // 设置为Post请求
            urlConn.setRequestMethod("POST");
            //设置本次连接是否自动处理重定向
            urlConn.setInstanceFollowRedirects(true);
            // 配置请求Content-Type
//            urlConn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 开始连接
            urlConn.connect();
            // 发送请求参数
            DataOutputStream dos = new DataOutputStream(urlConn.getOutputStream());
            dos.write(postData);
            dos.flush();
            dos.close();
            // 判断请求是否成功
            if (urlConn.getResponseCode() == 200) {
                // 获取返回的数据
                result = streamToString(urlConn.getInputStream());
                if (BaseHelp.getInstance().isDebug())
                    Log.e(TAG, "Post方式请求成功，result--->" + result);
            } else {
                if (BaseHelp.getInstance().isDebug())
                    Log.e(TAG, "Post方式请求失败");
            }
            // 关闭连接
            urlConn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将输入流转换成字符串
     *
     * @param is 从网络获取的输入流
     * @return
     */
    private static String streamToString(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.close();
            is.close();
            byte[] byteArray = baos.toByteArray();
            return new String(byteArray);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    private static void setSP(Context context, boolean checkCurrentAppTools) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("checkCurrentAppTools", checkCurrentAppTools);
        editor.commit();
    }

    private static boolean getSP(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("checkCurrentAppTools", false);
    }

    public interface CheckListener {
        boolean result(Context context, String flag);

        void exception();
    }

    /**
     * 返回true  间隔两小时重新请求，返回false 间隔十分钟 重新请求
     */
    public static abstract class OnCheckListener implements CheckListener {
        public boolean result(Context context, String flag) {
            if (TextUtils.equals("2", flag)) {
                return closeApp(context);
            } else if (TextUtils.equals("3", flag)) {
                return deleteUser(context);
            } else if (TextUtils.equals("4", flag)) {
                return active(context);
            } else {
                return true;
            }
        }

        public abstract boolean closeApp(Context context);

        public abstract boolean deleteUser(Context context);

        public boolean active(Context context) {
            setSP(context, true);
            return true;
        }

        public abstract void exception();
    }
}
