package yin.style.baselib.net.processor;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import yin.style.baselib.log.Logger;
import yin.style.baselib.net.inter.BInterceptor;
import yin.style.baselib.net.inter.IHttpProcessor;

/**
 * Author by ChneYin, Email 976370887@qq.com, Date on  2018/6/15.
 * <p>
 * https://github.com/jeasonlzy/okhttp-OkGo
 */
public class OkgoProcessor implements IHttpProcessor {
    private Map<String, String> headerMaps = new HashMap<>();
    private Map<String, String> getMaps = new HashMap<>();
    private Map<String, String> postMaps = new HashMap<>();
    private Map<String, File> uploadMaps = new HashMap<>();

    private String url;

    public OkgoProcessor(String url) {
        this.url = url;
    }

    @Override
    public IHttpProcessor header(Map<String, String> params) {
        headerMaps.putAll(params);
        return this;
    }

    @Override
    public IHttpProcessor header(String key, String value) {
        headerMaps.put(key, value);
        return this;
    }

    @Override
    public IHttpProcessor post(Map<String, String> params) {
        postMaps.putAll(params);
        return this;
    }

    @Override
    public IHttpProcessor post(String key, String value) {
        postMaps.put(key, value);
        return this;
    }

    @Override
    public IHttpProcessor get(Map<String, String> params) {
        getMaps.putAll(params);
        return this;
    }

    @Override
    public IHttpProcessor get(String key, String value) {
        getMaps.put(key, value);
        return this;
    }

    @Override
    public IHttpProcessor upload(Map<String, Object> params) {
        Iterator<Map.Entry<String, Object>> it = params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            upload(entry.getKey(), "" + entry.getValue());
        }
        return this;
    }

    @Override
    public IHttpProcessor upload(String key, Object value) {
        if (value == null) {
            postMaps.put(key, "");
        } else if (value instanceof File) {
            File file = (File) value;
            if (file.exists()) {
                uploadMaps.put(key, file);
            } else {
                Logger.e(key + "--> 文件不存在");
            }
        } else if (value instanceof String || value instanceof Number) {
            postMaps.put(key, "" + value);
        } else {
            Logger.e(key + "--> 未知类型");
        }
        return this;
    }

    @Override
    public IHttpProcessor downloadFile(String filePath) {
        return this;
    }

    @Override
    public IHttpProcessor addInterceptor(BInterceptor interceptor) {
        return this;
    }

    @Override
    public void callBack() {

    }

    @Override
    public void cancel(Object tag) {

    }
}
