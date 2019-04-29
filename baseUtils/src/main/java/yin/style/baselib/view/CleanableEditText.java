package yin.style.baselib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import yin.style.baselib.R;

/**
 * Author by ChneYin, Email 976370887@qq.com, Date on  2017/9/29.
 * 带清除的 EditText
 */
public class CleanableEditText extends EditText {
    private Drawable mRightDrawable;
    private boolean isHasFocus;
    private final int DEFAULT_SIZE = -1;
    //默认 图片的大小 字体大小的 0.8
    private int clearImage_size;

    private List<OnFocusChangeListener> listeners = new ArrayList<>();

    public CleanableEditText(Context context) {
        super(context);
        init(context, null);
    }

    public CleanableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CleanableEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CleanableEditText);
        clearImage_size = array.getDimensionPixelSize(R.styleable.CleanableEditText_clear_size, dip2px(context, DEFAULT_SIZE));
        array.recycle();

        //getCompoundDrawables:
        //Returns drawables for the left, top, right, and bottom borders.
        Drawable[] drawables = this.getCompoundDrawables();
        //即我们在布局文件中设置的android:drawableRight
        mRightDrawable = drawables[2];
        if (mRightDrawable == null) {
            mRightDrawable = getContext().getResources().getDrawable(R.mipmap.base_delete);
        }
        //取得right位置的Drawable
        setRightDrawableSize();

        //设置焦点变化的监听
        this.setOnFocusChangeListener(new FocusChangeListenerImpl());
        //设置EditText文字变化的监听
        this.addTextChangedListener(new TextWatcherImpl());
        //初始化时让右边clean图标不可见
        setClearDrawableVisible(false);
    }

    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void setRightDrawableSize() {
        if (clearImage_size <= 0)
            clearImage_size = (int) (getTextSize() * 0.95f);

        mRightDrawable.setBounds(0, 0, clearImage_size, clearImage_size);
    }

    @Override
    public void setTextSize(float size) {
        super.setTextSize(size);
        setRightDrawableSize();
    }

    @Override
    public void setTextSize(int unit, float size) {
        super.setTextSize(unit, size);
        setRightDrawableSize();
    }

    /**
     * 当手指抬起的位置在clean的图标的区域
     * 我们将此视为进行清除操作
     * getWidth():得到控件的宽度
     * event.getX():抬起时的坐标(改坐标是相对于控件本身而言的)
     * getTotalPaddingRight():clean的图标左边缘至控件右边缘的距离
     * getPaddingRight():clean的图标右边缘至控件右边缘的距离
     * 于是:
     * getWidth() - getTotalPaddingRight()表示:
     * 控件左边到clean的图标左边缘的区域
     * getWidth() - getPaddingRight()表示:
     * 控件左边到clean的图标右边缘的区域
     * 所以这两者之间的区域刚好是clean的图标的区域
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                boolean isClean = (event.getX() > (getWidth() - getTotalPaddingRight())) &&
                        (event.getX() < (getWidth() - getPaddingRight()));
                if (isClean) {
                    setText("");
                }
                break;

            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private class FocusChangeListenerImpl implements OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            isHasFocus = hasFocus;
            if (isHasFocus) {
                boolean isVisible = getText().toString().length() >= 1;
                setClearDrawableVisible(isVisible);
            } else {
                setClearDrawableVisible(false);
            }
            for (OnFocusChangeListener listener : listeners)
                if (listener != null)
                    listener.onFocusChange(v, hasFocus);
        }
    }

    @Override
    @Deprecated
    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        super.setOnFocusChangeListener(l);
    }

    /**
     * 焦点监听
     */
    public void addOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        listeners.add(onFocusChangeListener);
    }

    /**
     * 设置 删除图标大小
     */
    public void setClearImageSize(int clearImage_size) {
        this.clearImage_size = clearImage_size;
        setRightDrawableSize();
    }

    //当输入结束后判断是否显示右边clean的图标
    private class TextWatcherImpl implements TextWatcher {
        @Override
        public void afterTextChanged(Editable s) {
            boolean isVisible = getText().toString().length() >= 1;
            setClearDrawableVisible(isVisible);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
    }

    //隐藏或者显示右边clean的图标
    protected void setClearDrawableVisible(boolean isVisible) {
        //使用代码设置该控件left, top, right, and bottom处的图标
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1],
                isVisible && hasFocus() ? mRightDrawable : null, getCompoundDrawables()[3]);
    }

    // 显示一个动画,以提示用户输入
    public void setShakeAnimation() {
        this.setAnimation(shakeAnimation(5));
    }

    //CycleTimes动画重复的次数
    public Animation shakeAnimation(int CycleTimes) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 10);
        translateAnimation.setInterpolator(new CycleInterpolator(CycleTimes));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }
}
