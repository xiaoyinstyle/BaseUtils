package com.bangdu.classnotice;

import android.os.Bundle;
import android.view.WindowManager;

import com.bangdu.classnotice.fragment.ActionFragment;
import com.bangdu.classnotice.fragment.ClassSummaryFragment;
import com.bangdu.classnotice.fragment.CourseFragment;
import com.bangdu.classnotice.fragment.DutyFragment;
import com.bangdu.classnotice.fragment.NewsFragment;
import com.bangdu.classnotice.fragment.WeatherFragment;
import com.jskingen.baselib.activity.base.NormalAcitivity;

public class MainActivity extends NormalAcitivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //实现全屏效果：
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN) ;//隐藏状态栏
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN) ;//显示状态栏
    }

    @Override
    protected int getViewByXml() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {


        getSupportFragmentManager().beginTransaction().add(R.id.fl_main_summarize, new ClassSummaryFragment()).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_main_weather, new WeatherFragment()).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_main_news, new NewsFragment()).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_main_action, new ActionFragment()).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_main_course, new CourseFragment()).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_main_duty, new DutyFragment()).commit();


    }

    @Override
    protected void initData() {

    }
}
