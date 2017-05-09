package com.jskingen.baselib.network.api;

import com.jskingen.baselib.network.utils.FileRequestBody;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by ChneY on 2017/4/6.
 */

public interface FileService {
    @Streaming
    @GET
    Call<ResponseBody> download(@Url String fileUrl);

    @POST
    Call upload(@Url String fileUrl, @Body FileRequestBody body);
}
