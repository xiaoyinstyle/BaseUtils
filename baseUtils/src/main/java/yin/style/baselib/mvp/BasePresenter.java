package yin.style.baselib.mvp;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by BangDu on 2018/1/12.
 */

public class BasePresenter<V, M> {

    private CompositeSubscription mCompositeSubscription;
    public V view;
    public M model;

    public BasePresenter() {

    }

    public BasePresenter(V view) {
        this.view = view;
        model = createModel();
    }

    /**
     * 创建model
     *
     * @return model对象
     */
    public M createModel() {
        return null;
    }

    //Rxjava取消注册，避免内存泄露
    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
        if (view != null) {
            view = null;
        }
    }

    //添加到订阅的队列
    public void addSubscription(Observable observable, Subscriber subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }

}