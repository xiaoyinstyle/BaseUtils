package com.jskingen.baselib.activity.base;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jskingen.baselib.R;

/**
 * Created by ChneY on 2017/4/22.
 * <p>
 * 正常使用的带有标题的 Activity
 */

public abstract class TitleActivity extends NormalAcitivity {
    protected ImageView iv_left;
    protected TextView tv_left;
    protected ImageView iv_right;
    protected TextView tv_right;
    protected TextView title;
    protected LinearLayout ll_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle();
    }

    protected void addTitleLayout(LinearLayout root) {
        View view = View.inflate(this, R.layout.base_title, null);
        root.addView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        iv_left = (ImageView) view.findViewById(R.id.iv_left);
        tv_left = (TextView) view.findViewById(R.id.tv_left);
        iv_right = (ImageView) view.findViewById(R.id.iv_right);
        tv_right = (TextView) view.findViewById(R.id.tv_right);
        title = (TextView) view.findViewById(R.id.tv_title);
        ll_title = (LinearLayout) view.findViewById(R.id.rl_title);
        //返回按键监听
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                InputUtils.close(getApplicationContext(), getCurrentFocus());
                finish();
            }
        });
    }

    protected abstract void setTitle();

    protected void showBackButton() {
        if (iv_left != null)
            iv_left.setVisibility(View.VISIBLE);
    }

    protected void hiddenBackButton() {
        if (iv_left != null)
            iv_left.setVisibility(View.GONE);
    }

    protected void showTitle() {
        if (ll_title != null)
            ll_title.setVisibility(View.VISIBLE);
    }

    protected void hiddenTitle() {
        if (ll_title != null)
            ll_title.setVisibility(View.GONE);
    }
}
