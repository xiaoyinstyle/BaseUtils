package yin.style.baselib.utils;

import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.subscribers.SerializedSubscriber;

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
    //相当于Rxjava1.x中的Subject
    private final FlowableProcessor<Object> mBus;
    private static volatile RxBus sRxBus = null;

    private RxBus() {
        //调用toSerialized()方法，保证线程安全
        mBus = PublishProcessor.create().toSerialized();
    }

    public static synchronized RxBus getInstance() {
        if (sRxBus == null) {
            synchronized (RxBus.class) {
                if (sRxBus == null) {
                    sRxBus = new RxBus();
                }
            }
        }
        return sRxBus;
    }


    /**
     * 发送消息
     *
     * @param o
     */
    public void post(Object o) {
        new SerializedSubscriber<>(mBus).onNext(o);
    }

    /**
     * 确定接收消息的类型
     *
     * @param aClass
     * @param <T>
     * @return
     */
    public <T> Flowable<T> toFlowable(Class<T> aClass) {
        return mBus.ofType(aClass);
    }

    /**
     * 判断是否有订阅者
     *
     * @return
     */
    public boolean hasSubscribers() {
        return mBus.hasSubscribers();
    }

    /**
     * 简单用法1
     */
//    public static class RxBusHelper {
//          RxBus.getDefault().toFlowable(String.class)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<String>() {
//                    }
//                });
//    }
}
