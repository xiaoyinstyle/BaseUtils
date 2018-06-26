package yin.style.baselib.net.adapter;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public abstract class SampleSubscriber implements Subscriber {
    @Override
    public void onSubscribe(Subscription s) {

    }


    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onComplete() {

    }
}
