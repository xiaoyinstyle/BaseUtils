package yin.style.baselib.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import yin.style.baselib.R;

/**
 * Author by ChneYin, Email 976370887@qq.com, Date on  2018/4/13.
 * 自定义 TitleLayout
 */
public class TitleLayout extends FrameLayout {
    public ImageView iv_left;
    public ImageView iv_left2;
    public TextView tv_left;
    public ImageView iv_right;
    public ImageView iv_right2;
    public TextView tv_right;
    public TextView title;
    public View v_line;
    public ViewGroup ll_title;

    public TitleLayout(@NonNull Context context) {
        this(context, null);
    }

    public TitleLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.base_title, this);

        if (getBackground() == null) {
            setBackgroundResource(R.color.colorPrimary);
        }

        iv_left = (ImageView) findViewById(R.id.iv_left);
        tv_left = (TextView) findViewById(R.id.tv_left);
        iv_right = (ImageView) findViewById(R.id.iv_right);
        tv_right = (TextView) findViewById(R.id.tv_right);
        title = (TextView) findViewById(R.id.tv_title);
        ll_title = (FrameLayout) findViewById(R.id.rl_title);
        ll_title.setVisibility(View.VISIBLE);
    }


    public void hideTitle() {
        if (ll_title != null)
            ll_title.setVisibility(GONE);
    }

    public void showTitle() {
        if (ll_title != null)
            ll_title.setVisibility(VISIBLE);
    }
}
