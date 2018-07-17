//package yin.style.baselib.net;
//
//import yin.style.baselib.net.adapter.IObserver;
//import yin.style.baselib.net.inter.ICallBack;
//import yin.style.baselib.net.processor.IHttpProcessor;
//import yin.style.baselib.net.processor.okgo.OkgoProcessor;
//
//import java.util.Map;
//
///**
// * Created by ChneY on 2017/6/22.
// */
//
//public class HttpHelper implements IHttpProcessor {
//    private static IHttpProcessor mIHttpProcessor = null;
//    private static HttpHelper _instance;
//
//    public static void _init(IHttpProcessor httpProcessor) {
//        mIHttpProcessor = httpProcessor;
//    }
//
//    private HttpHelper() {
//
//    }
//    public static HttpHelper init(String url) {
//        if (_instance == null) {
//            _instance = new HttpHelper();
//        }
//        if (mIHttpProcessor == null) {
//            _init(new OkgoProcessor(url));
//        }
//        return _instance;
//    }
////    public static IHttpProcessor init() {
////        mIHttpProcessor = new OkgoProcessor(null);
////        return mIHttpProcessor;
////    }
//
////    public static IHttpProcessor init(String url) {
////        if (mIHttpProcessor == null)
////            mIHttpProcessor = new OkgoProcessor(url);
////        return mIHttpProcessor;
////    }
//
//    @Override
//    public IHttpProcessor header(Map<String, String> params) {
//        mIHttpProcessor.header(params);
//        return this;
//    }
//
//    @Override
//    public IHttpProcessor header(String key, String value) {
//        mIHttpProcessor.header(key, value);
//        return this;
//    }
//
//    @Override
//    public IHttpProcessor post(Map<String, String> params) {
//        mIHttpProcessor.post(params);
//        return this;
//    }
//
//    @Override
//    public IHttpProcessor post(String key, String value) {
//        mIHttpProcessor.header(key, value);
//        return this;
//    }
//
//    @Override
//    public IHttpProcessor get(Map<String, String> params) {
//        mIHttpProcessor.get(params);
//        return this;
//    }
//
//    @Override
//    public IHttpProcessor get(String key, String value) {
//        mIHttpProcessor.get(key, value);
//        return this;
//    }
//
//    @Override
//    public IHttpProcessor upload(Map<String, Object> params) {
//        mIHttpProcessor.upload(params);
//        return this;
//    }
//
//    @Override
//    public IHttpProcessor upload(String key, Object value) {
//        mIHttpProcessor.upload(key, value);
//        return this;
//    }
//
//    @Override
//    public void cancel(Object tag) {
//        mIHttpProcessor.cancel(tag);
//    }
//
//    @Override
//    public void callBack(ICallBack iCallBack) {
//        mIHttpProcessor.callBack(iCallBack);
//    }
//
//    @Override
//    public <T> void subscribe(IObserver<T> observer) {
//        mIHttpProcessor.subscribe(observer);
//    }
//}
