//package yin.style.baselib.net.adapter;
//
//
//import java.io.File;
//import java.util.Map;
//
//import yin.style.baselib.net.processor.BInterceptor;
//
//public abstract class IBaseObserver<T> extends IObserver<T> implements BInterceptor {
//
//    @Override
//    public Map<String, String> header(Map<String, String> headerMap) {
//        return headerMap;
//    }
//
//    @Override
//    public Map<String, String> post(Map<String, String> postMap) {
//        return postMap;
//    }
//
//    @Override
//    public Map<String, String> get(Map<String, String> getMap) {
//        return getMap;
//    }
//
//    @Override
//    public Map<String, File> upload(Map<String, File> uploadMap) {
//        return uploadMap;
//    }
//}
