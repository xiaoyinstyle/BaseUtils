package com.jskingen.baselib.network.utils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by ChneY on 2017/4/10.
 */

public class RequestMultipart {
    private MultipartBody.Builder requestBody;

    public RequestMultipart() {
        requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);

    }

    public RequestBody build() {
        return requestBody.build();
    }


    public RequestMultipart add(String key, File file) {
//        requestBody.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        requestBody.addFormDataPart(key, file.getName(), RequestBody.create(getMediaType(file.getName()), file));
        return this;
    }

    public RequestMultipart add(String key, String text) {
        requestBody.addFormDataPart(key, text);
        return this;
    }

    public RequestMultipart add(String key, int text) {
        requestBody.addFormDataPart(key, "" + text);
        return this;
    }

    private MediaType getMediaType(String path) {
        if (path.toLowerCase().endsWith(".jpg") || path.toLowerCase().endsWith(".png") || (path.toLowerCase().endsWith(".gif")))
            return MediaType.parse("image/*");
        return MediaType.parse("application/octet-stream");  //任意的二进制数据
    }

    private final String[][] MIME_MapTable = {
            //{后缀名，MIME类型}
            {".doc", "application/msword"},
            {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".pdf", "application/pdf"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".jpeg", "image/jpeg"},
            {".jpg", "image/jpeg"},
            {".zip", "application/x-zip-compressed"},
            {".apk", "application/vnd.android.package-archive"},
            {"", "*/*"}
    };

//    public  class Builder {
//        private MultipartBody.Builder requestBody;
//
//        public Builder() {
//            requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
//        }
//
//        public Builder add(String key, File file) {
//            requestBody.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
//            return this;
//        }
//
//        private Builder add(String key, String text) {
//            requestBody.addFormDataPart(key, text);
//            return this;
//        }
//
//        private RequestBody build() {
//            return requestBody.build();
//        }
//    }
}
