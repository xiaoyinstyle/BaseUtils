package yin.style.baselib.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 封装的GSON解析工具类，提供泛型参数
 * <p>
 * Created by chenY on 2017/2/10.
 */
public class GsonUtils {
    //    private static Gson gson = new Gson();
    private static Gson gson = new GsonBuilder().serializeNulls().create();

    /**
     * json字符串转对象或容器
     *
     * @author chenyin
     * @date 2017/3/10
     */
    public static <T> T fromJson(String json, TypeToken<T> token) {
        try {
            return gson.fromJson(json, token.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }  public static <T> T fromJson(JsonReader jsonReader, Type type) {
        try {
            return gson.fromJson(jsonReader, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json字符串转对象或容器
     *
     * @author chenyin
     * @date 2017/3/10
     */
    public static <T> T fromJson(String json, Class<T> c) {
        try {
            return gson.fromJson(json, c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T fromJson(JsonReader jsonReader, Class<T> type) {
        try {
            return gson.fromJson(jsonReader, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toJson(Object src) {
        return gson.toJson(src);
    }

    /**
     * 将Json数据解析成相应的映射对象
     *
     * @author chenyin
     * @date 2017/3/10
     */
    public static <T> T getObject(String jsonString, Class<T> cls) {
        T result = gson.fromJson(jsonString, cls);
        return result;
    }

    /**
     * 将Json数组解析成相应的映射对象列表
     *
     * @author chenyin
     * @date 2017/3/10
     */
//    public static <T> List<T> parseJsonArray(String jsonString, Class<T> cls) {
//        List<T> ResultBean = gson.fromJson(jsonString, new TypeToken<ArrayList<T>>() {
//        }.getType());
//        return ResultBean;
//    }
    public static <T> List<T> getObjectList(String jsonString, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            JsonArray arry = new JsonParser().parse(jsonString).getAsJsonArray();
            for (JsonElement jsonElement : arry) {
                list.add(gson.fromJson(jsonElement, cls));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 关键字 获取字符串
     *
     * @author chenyin
     * @date 2017/3/10
     */
    public static String getString(String jsonString, String keyWord) {
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(jsonString).getAsJsonObject();
        //将data节点下的内容转为JsonArray
        JsonElement jsonElement = jsonObject.get(keyWord);
        if (jsonElement != null)
            return jsonElement.toString();
        else
            return null;
    }

    /**
     * 获取HashMap
     *
     * @author chenyin
     * @date 2018/6/8
     */
    public static HashMap getMap(String jsonString) {
        //将data节点下的内容转为JsonArray
        try {
            return gson.fromJson(jsonString, new TypeToken<HashMap<String, String>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关键字 解析
     *
     * @author chenyin
     * @date 2017/3/10
     */
    public static <T> T getObject(String jsonString, String keyWord, Class<T> cls) {
        String jsonArray = getString(jsonString, keyWord);
        return getObject(jsonArray, cls);
    }

    /**
     * 关键字 解析数组
     *
     * @author chenyin
     * @date 2017/3/10
     */
    public static <T> List<T> getObjectList(String jsonString, String keyWord, Class<T> cls) {
        String jsonArray = getString(jsonString, keyWord);
        return getObjectList(jsonArray, cls);
    }
}