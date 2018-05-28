package yin.style.baselib.activity.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
    private EditText et_input;
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

        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        if (getBackground() == null) {
            setBackgroundResource(R.color.colorPrimary);
        }

        iv_left = (ImageView) findViewById(R.id.base_iv_left);
        tv_left = (TextView) findViewById(R.id.base_tv_left);
        iv_right = (ImageView) findViewById(R.id.base_iv_right);
        tv_right = (TextView) findViewById(R.id.base_tv_right);
        title = (TextView) findViewById(R.id.base_tv_title);
        et_input = (EditText) findViewById(R.id.base_et_input);
    }

    public ImageView getIconLeft() {
        return iv_left;
    }

    public ImageView getIconLeft2() {
        return iv_left2;
    }

    public TextView getTextLeft() {
        return tv_left;
    }

    public ImageView getIconRight() {
        return iv_right;
    }

    public ImageView getIconRight2() {
        return iv_right2;
    }

    public TextView getTextRight() {
        return tv_right;
    }

    public TextView getTitle() {
        return title;
    }

    public View getLine() {
        return v_line;
    }

    public EditText getEditInput() {
        return et_input;
    }

    /**
     * 设置标题
     *
     * @param titleText
     */
    public void setText(CharSequence titleText) {
        title.setText(titleText);
    }

    /**
     * 左边文字按钮
     *
     * @param textRight
     * @param listener
     */
    public TextView setTextLeft(String textRight, OnClickListener listener) {
        tv_left.setVisibility(View.VISIBLE);
        tv_left.setText(textRight);
        tv_left.setOnClickListener(listener);
        return tv_left;
    }

    /**
     * 左边图标
     *
     * @param res
     * @param listener
     */
    @SuppressLint("ResourceType")
    public ImageView setIconLeft(@DrawableRes int res, OnClickListener listener) {
        iv_left.setVisibility(View.VISIBLE);
        if (res > 0)
            iv_left.setImageResource(res);
        iv_left.setOnClickListener(listener);

        return iv_left;
    }

    /**
     * 左边图标2
     *
     * @param res
     * @param listener
     */
    @SuppressLint("ResourceType")
    public ImageView setIconLeft2(@DrawableRes int res, OnClickListener listener) {
        iv_left2.setVisibility(View.VISIBLE);
        if (res > 0)
            iv_left2.setImageResource(res);
        iv_left2.setOnClickListener(listener);
        return iv_left2;
    }


    /**
     * 右边图标
     *
     * @param res
     * @param listener
     */
    @SuppressLint("ResourceType")
    public ImageView setIconRight(@DrawableRes int res, OnClickListener listener) {
        iv_right.setVisibility(View.VISIBLE);
        if (res > 0)
            iv_right.setImageResource(res);
        iv_right.setOnClickListener(listener);
        return iv_right;
    }

    /**
     * 右边图标2
     *
     * @param res
     * @param listener
     */
    @SuppressLint("ResourceType")
    public ImageView setIconRight2(@DrawableRes int res, OnClickListener listener) {
        iv_right2.setVisibility(View.VISIBLE);
        if (res > 0) {
            iv_right2.setImageResource(res);
        }
        iv_right2.setOnClickListener(listener);
        return iv_right2;
    }

    /**
     * 设置右边文字按钮
     *
     * @param textRight
     * @param listener
     */
    public TextView setTextRight(String textRight, OnClickListener listener) {
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText(textRight);
        tv_right.setOnClickListener(listener);
        return tv_right;
    }

    /**
     * 底部间隔线
     *
     * @param isShow
     */
    public void setLine(boolean isShow) {
        this.v_line.setVisibility(isShow ? VISIBLE : GONE);
    }

    /**
     * 隐藏标题 显示输入框
     *
     * @param iconLeftRes
     * @param text
     * @param colorText
     * @param hintText
     * @param colorHint
     */
    public EditText setInputView(@DrawableRes int background, @DrawableRes int iconLeftRes,
                                 String text, @ColorInt int colorText, String hintText, @ColorInt int colorHint) {
        title.setVisibility(GONE);
        et_input.setVisibility(VISIBLE);
        //  left,  top,  right,  bottom
        et_input.setCompoundDrawablesWithIntrinsicBounds(iconLeftRes, 0, 0, 0);
        et_input.setText(text);
        et_input.setTextColor(colorText);
        et_input.setHint(hintText);
        et_input.setHintTextColor(colorHint);

        et_input.setBackgroundResource(background);
        return et_input;
    }
}
