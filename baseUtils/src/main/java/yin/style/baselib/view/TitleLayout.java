package yin.style.baselib.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import yin.style.baselib.R;

/**
 * Author by ChneYin, Email 976370887@qq.com, Date on  2018/4/13.
 * 自定义 TitleLayout
 */
public class TitleLayout extends FrameLayout {
    private ImageView iv_left;
    private ImageView iv_left2;
    private TextView tv_left;
    private ImageView iv_right;
    private ImageView iv_right2;
    private TextView tv_right;
    private TextView title;
    private View v_line;

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
    }
}
