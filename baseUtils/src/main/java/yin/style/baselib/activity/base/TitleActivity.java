package yin.style.baselib.activity.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import yin.style.baselib.activity.view.TitleLayout;

/**
 * Created by ChneY on 2017/4/22.
 * <p>
 * 正常使用的带有标题的 Activity
 */

public abstract class TitleActivity extends NormalActivity {
    protected ImageView iv_left;
    protected TextView tv_left;
    protected ImageView iv_right;
    protected TextView tv_right;
    protected TextView title;
    protected TitleLayout ll_title;

    @Override
    protected void addTitleLayout(ViewGroup rootView) {
         ll_title = new TitleLayout(mContext);
        rootView.addView(ll_title, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        iv_left = ll_title.iv_left;
        tv_left = ll_title.tv_left;
        iv_right =ll_title.iv_right;
        tv_right = ll_title.tv_right;
        title =ll_title.title;
        ll_title.setVisibility(View.VISIBLE);
        //返回按键监听
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                InputUtils.close(getApplicationContext(), getCurrentFocus());
                finish();
            }
        });

        setTitle();
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
