package com.jskingen.baselib.net.processor.okhttp;

import android.os.Handler;

import com.jskingen.baselib.net.inter.IFileCallBack;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;
import okio.Source;

/**
 * Created by ChneY on 2017/6/27.
 */

public class FileRequestBody extends RequestBody {
    /**
     * 实际请求体
     */
    private RequestBody requestBody;
    /**
     * 上传回调接口
     */
    private IFileCallBack httpProcessor;

    /**
     * 包装完成的BufferedSink
     */
    private BufferedSink bufferedSink;

    public FileRequestBody(RequestBody requestBody, IFileCallBack callBack) {
        this.requestBody = requestBody;
        this.httpProcessor = callBack;
    }

    @Override
    public MediaType contentType() {
        return MediaType.parse("application/x-www-form-urlencoded");
    }

    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (bufferedSink == null) {
            //包装
            bufferedSink = Okio.buffer(sink(sink));
        }
        //写入
        requestBody.writeTo(bufferedSink);
        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush();
    }
    /**
     * 写入，回调进度接口
     * @param sink Sink
     * @return Sink
     */
    private Sink sink(Sink sink) {
        return new ForwardingSink(sink) {
            //当前写入字节数
            long current = 0L;
            //总字节长度，避免多次调用contentLength()方法
            long remaining = 0L;
            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (remaining == 0) {
                    //获得contentLength的值，后续不再调用
                    remaining = contentLength();
                }
                //增加当前写入的字节数
                current += byteCount;
                //回调
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        httpProcessor.onProgress((double) current / (double) remaining, current, remaining);
                    }
                });
            }
        };
    }
    Handler handler =new Handler();
}
