package yin.style.sample;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.android.schedulers.AndroidSchedulers;
import yin.style.baselib.rxbus.RxBus;

public class WakeScreenActivity extends AppCompatActivity {

    public static void openWakeScreen(Activity context) {
        context.startActivity(new Intent(context, WakeScreenActivity.class));
        context.overridePendingTransition(0, 0);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(8);
        setContentView(R.layout.activity_wake_screen);

        RxBus.getInstance()
                .toFlowable(String.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(String s) {
                        finish();
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}
