package yin.style.baselib.utils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;


import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by chenY on 2016/10/10
 * <p>
 * RxJava and RxAndroid 结合的RxBus
 * <p>
 * 直接用EventBus 更方便 与Activity 绑定
 * 使用过度会对代码解读造成压力，建议少量使用，
 * EventBus 可与AS插件配合解读 ，而RxBus无法操作，所以抛弃
 */
@Deprecated
public class RxBus {

    private final FlowableProcessor<Object> mBus;

    private RxBus() {
        // toSerialized method made bus thread safe
        mBus = PublishProcessor.create().toSerialized();
    }

    public static RxBus get() {
        return Holder.BUS;
    }

    public void post(Object obj) {
        mBus.onNext(obj);
    }

    public <T> Flowable<T> toFlowable(Class<T> tClass) {
        return mBus.ofType(tClass);
    }

    public Flowable<Object> toFlowable() {
        return mBus;
    }

    public boolean hasSubscribers() {
        return mBus.hasSubscribers();
    }

    private static class Holder {
        private static final RxBus BUS = new RxBus();
    }


    /**
     * RxBus
     */
    private void operateBus() {
        RxBus.get().toFlowable(String.class)
                .subscribeOn(Schedulers.io())//
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {

                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                    }

                    @Override
                    public void onNext(String s) {

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
