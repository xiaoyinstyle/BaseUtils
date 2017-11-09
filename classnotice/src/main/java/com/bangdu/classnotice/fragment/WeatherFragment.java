package com.bangdu.classnotice.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bangdu.classnotice.R;
import com.jskingen.baselib.fragment.NormalFragment;
import com.jskingen.baselib.view.AutofitTextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by BangDu on 2017/11/7.
 */

public class WeatherFragment extends NormalFragment {
    @BindView(R.id.tv_f5_address)
    TextView tvF5Address;
    @BindView(R.id.tv_f5_time)
    TextView tvF5Time;
    @BindView(R.id.tv_f5_date)
    TextView tvF5Date;
    @BindView(R.id.iv_f5_weather)
    ImageView ivF5Weather;
    @BindView(R.id.tv_f5_weather)
    TextView tvF5Weather;
    @BindView(R.id.tv_f5_temp)
    TextView tvF5Temp;
    @BindView(R.id.tv_f5_temp_allDay)
    AutofitTextView tvF5TempAllDay;
    @BindView(R.id.tv_f5_other)
    TextView tvF5Other;

    @Override
    protected int getViewByXml() {
        return R.layout.fragment_weather;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {


        Typeface typeFace = Typeface.createFromAsset(mContext.getAssets(), "wljzy.TTF");
        tvF5Time.setTypeface(typeFace);
        tvF5Temp.setTypeface(typeFace);

        updateCurrentTime();
    }

    @Override
    protected void initData() {


        timer = new Timer(true);
        timer.schedule(task, 1000, 1000); //延时1000ms后执行，1000ms执行一次
        //timer.cancel(); //退出计时器
    }

    Timer timer;
    boolean format = false;

    private void updateCurrentTime() {
        format = !format;
        SimpleDateFormat timeFormat = new SimpleDateFormat(format ? "HH mm" : "HH:mm", Locale.CHINA);
        timeFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Date curDate = new Date(System.currentTimeMillis());
        String time = timeFormat.format(curDate);
        tvF5Time.setText(time);

        SimpleDateFormat sdateFormat = new SimpleDateFormat("yyyy年MM-月dd日 EEEE", Locale.CHINA);
        sdateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String date = sdateFormat.format(curDate);
        tvF5Date.setText(date);
    }

    TimerTask task = new TimerTask() {
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };

    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    updateCurrentTime();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
