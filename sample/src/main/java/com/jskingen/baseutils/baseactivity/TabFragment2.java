package com.jskingen.baseutils.baseactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jskingen.baselib.fragment.NormalFragment;
import com.jskingen.baselib.utils.LogUtils;
import com.jskingen.baselib.utils.ToastUtils;
import com.jskingen.baseutils.R;

/**
 * Created by chenY on 2017/1/17
 */
public class TabFragment2 extends NormalFragment {
    private TextView textView;
    private TextView button;

    String a;

    public static TabFragment2 newInstance(int index) {
        Bundle bundle = new Bundle();
        bundle.putInt("index", 'A' + index);
        TabFragment2 fragment = new TabFragment2();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getViewByXml() {
        return R.layout.tab_fragment2;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        textView = (TextView) findViewById(R.id.text);
        a = String.valueOf((char) getArguments().getInt("index"));
        textView.setText(a);
    }

    @Override
    protected void initData() {
        LogUtils.e("AAA", "initData--" + a);
    }

    @Override
    protected boolean setLazy() {
        return false;
    }


}
