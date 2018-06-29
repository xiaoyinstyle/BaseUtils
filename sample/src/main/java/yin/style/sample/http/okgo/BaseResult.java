package yin.style.sample.http.okgo;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.convert.BitmapConvert;
import com.lzy.okgo.convert.FileConvert;
import com.lzy.okgo.request.base.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;
import yin.style.baselib.utils.GsonUtils;
import yin.style.baselib.utils.ToastUtils;

public abstract class BaseResult<T> extends AbsCallback<T> {
    private Type type;

    public BaseResult() {
    }

    public BaseResult(Type type) {
        this.type = type;
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        // 主要用于在所有请求之前添加公共的请求头或请求参数
        // 例如登录授权的 token
        // 使用的设备信息
        // 可以随意添加,也可以什么都不传
        // 还可以在这里对所有的参数进行加密，均在这里实现
        request.headers("header1", "HeaderValue1")//
                .params("params1", "ParamsValue1")//
                .params("token", "3215sdf13ad1f65asd4f3ads1f");
    }

    /**
     * 该方法是子线程处理，不能做ui相关的工作
     * 主要作用是解析网络返回的 response 对象,生产onSuccess回调中需要的数据对象
     * 这里的解析工作不同的业务逻辑基本都不一样,所以需要自己实现,以下给出的时模板代码,实际使用根据需要修改
     */
    @Override
    public T convertResponse(Response response) throws Throwable {

        // 重要的事情说三遍，不同的业务，这里的代码逻辑都不一样，如果你不修改，那么基本不可用
        // 重要的事情说三遍，不同的业务，这里的代码逻辑都不一样，如果你不修改，那么基本不可用
        // 重要的事情说三遍，不同的业务，这里的代码逻辑都不一样，如果你不修改，那么基本不可用

        //详细自定义的原理和文档，看这里： https://github.com/jeasonlzy/okhttp-OkGo/wiki/JsonCallback

        if (type == null) {
            Type genType = getClass().getGenericSuperclass();
            type = ((ParameterizedType) genType).getActualTypeArguments()[0];
        }

        Class clz = null;
        if (type != null)
            clz = (Class) type;

        if (type == null) {
            T t = (T) response.body().string();
            response.close();
            return t;
        } else if (type instanceof Class && clz != null) {
            if (clz.equals(Bitmap.class)) {
                BitmapCallback(1000, 1000);
                if (bitmapConvert == null)
                    bitmapConvert = new BitmapConvert();
                Bitmap bitmap = bitmapConvert.convertResponse(response);
                response.close();
                return (T) bitmap;
            } else if (clz.equals(File.class)) {
                FileCallback(null, null);
                File file = fileConvert.convertResponse(response);
                response.close();
                return (T) file;
            } else if (clz.equals(JSONObject.class)) {
                Log.e("AAA", "convertResponse: type");
                T t = (T) new JSONObject(response.body().string());
                response.close();
                return t;
            } else if (clz.equals(JSONArray.class)) {
                T t = (T) new JSONArray(response.body().string());
                response.close();
                return t;
            } else if (clz.equals(Map.class) || clz.equals(HashMap.class)) {
                Log.e("AAA", "convertResponse: type");
                HashMap t = GsonUtils.getMap(response.body().string());
                response.close();
                return (T) t;
            } else {
                T t = new Gson().fromJson(response.body().string(), type);
                response.close();
                return t;
            }

        } else {
            T t = new Gson().fromJson(response.body().string(), type);
            response.close();
            return t;
        }
    }

    FileConvert fileConvert;

    protected void FileCallback(String destFileDir, String destFileName) {
        if (TextUtils.isEmpty(destFileName)) {
            ToastUtils.show("fileConvert is null, Execution <FileCallback> method");
            throw new NullPointerException("fileConvert is null, Execution <FileCallback> method");
        }

        fileConvert = new FileConvert(destFileDir, destFileName);
        fileConvert.setCallback((Callback<File>) this);
    }

    private BitmapConvert bitmapConvert;

    protected void BitmapCallback(int maxWidth, int maxHeight) {
        BitmapCallback(maxWidth, maxHeight, Bitmap.Config.ARGB_8888, ImageView.ScaleType.CENTER_INSIDE);
    }

    protected void BitmapCallback(int maxWidth, int maxHeight, Bitmap.Config decodeConfig, ImageView.ScaleType scaleType) {
        bitmapConvert = new BitmapConvert(maxWidth, maxHeight, decodeConfig, scaleType);
    }
}
