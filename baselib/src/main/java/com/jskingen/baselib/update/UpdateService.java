package com.jskingen.baselib.update;

import com.jskingen.baselib.network.MyCall;
import com.jskingen.baselib.update.model.UpdateBean;

import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by ChneY on 2017/4/6.
 */

public interface UpdateService {
    //http://112.124.9.97:8889/meters/down/meters_android_version.txt
//    @POST("app_version.txt")
    @POST
    MyCall<UpdateBean> update(@Url String checkUrl);
}
