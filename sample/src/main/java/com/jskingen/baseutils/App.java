package com.jskingen.baseutils;

import android.app.Application;

import com.jskingen.baselib.BaseHelp;
import com.jskingen.baselib.Configuration;

/**
 * Created by ChneY on 2017/5/2.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {

        Configuration configuration = new Configuration.Builder(this)
                .baseUrl(Constant.BASE_URL)
                .debug(BuildConfig.DEBUG)
                .build();
        BaseHelp.getInstance().init(configuration);
    }
}
