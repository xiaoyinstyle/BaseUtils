package com.jskingen.baseutils.http.api;

import com.jskingen.baselib.network.MyCall;
import com.jskingen.baselib.network.model.HttpResult;
import com.jskingen.baseutils.http.model.User;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by ChneY on 2017/5/3.
 */

public interface DemoService {
    //http://192.168.0.234:8081/meters/gpsdata/login?username=admin&password=123456
    //@POST("gpsdata/login")
    @POST("gpsdata/login")
    MyCall<HttpResult<User>> login(@QueryMap Map<String, String> map);

    //    @POST("gpsdata/login")
    @GET("gpsdata/login")
    MyCall<HttpResult<User>> login2(@Query("username") String name, @Query("password") String password);

    //图片上传 http://192.168.0.234:8081/meters/gpsdata/uploadphoto?userid=3&project=1&photo=
    @POST("gpsdata/uploadphoto")
    MyCall<HttpResult> upload(@Body RequestBody body);
}
