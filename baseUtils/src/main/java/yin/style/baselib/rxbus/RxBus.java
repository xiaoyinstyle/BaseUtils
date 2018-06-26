package yin.style.baselib.rxbus;


import io.reactivex.Flowable;
import io.reactivex.internal.subscribers.StrictSubscriber;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * Created by chenY on 2016/10/10
 * <p>
 * RxJava2 and RxAndroid2 结合的RxBus
 * <p>
 * 直接用EventBus 更方便 与Activity 绑定
 * 使用过度会对代码解读造成压力，建议少量使用，
 * EventBus 可与AS插件配合解读 ，而RxBus无法操作，所以抛弃
 */
@Deprecated
public class RxBus {
    //注意这里存在内存泄漏问题，但是由于对象固定，所以影响不大，可以忽略
    private final FlowableProcessor<Object> mBus;

    private RxBus() {
        // toSerialized method made bus thread safe
        mBus = PublishProcessor.create().toSerialized();
    }

    public static RxBus getInstance() {
        return Holder.BUS;
    }

    public void post(Object obj) {
        new StrictSubscriber<>(mBus).onNext(obj);
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

//    /**
//     * 简单用法1
//     */
//    public class RxBusHelper {
//          RxBus.getInstance().toFlowable(String.class)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<String>() {
//                    @Override
//                    public void onSubscribe(Subscription s) {
//                        Log.e(TAG, "onSubscribe: " + s);
//                        s.request(Long.MAX_VALUE);  //注意这句代码
//                    }
//               });
//    }
}
