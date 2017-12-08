package yin.style.baselib.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import yin.style.baselib.utils.LogUtils;

/**
 * Created by 陈银 on 2017/10/12 11:24
 * 描述：带滚动监听的scrollview
 */

public class HeaderScrollView extends NestedScrollView {
    private float alphaValue = 0;//背景透明度

    boolean isBgGradient = false;//背景渐变
    private boolean autoMeasureBanner = true;//自动测量banner高度
    private boolean autoMeasureTitle = true;//自动测量banner高度
    private int bannerHeight;
    private int titleHeight = 0;
    private View mBannerView;
    private View mTitleView;

    private OnScrollListener scrollViewListener = null;

    public HeaderScrollView(Context context) {
        super(context);
        initView(context);
    }


    public HeaderScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    public HeaderScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        if (autoMeasureBanner) {
            ViewTreeObserver vto = getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (getChildCount() > 0) {
                        getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        ViewGroup childGrop = (ViewGroup) getChildAt(0);
                        if (childGrop.getChildCount() == 0) return;

                        mBannerView = childGrop.getChildAt(0);
                        bannerHeight = mBannerView.getHeight();
                        LogUtils.e("HeaderScrollView_BannerView_h:" + mBannerView.getHeight());
                    }
                }
            });
        }

    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);

        if (getChildCount() == 0)
            return;

        //计算渐变值
        if (y <= 0) {
            alphaValue = 0;
        } else if (y > 0 && y <= (bannerHeight - titleHeight)) {
            //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            alphaValue = (float) y / (bannerHeight - titleHeight);
        } else {
            alphaValue = 1;
        }

        //背景颜色渐变
        if (isBgGradient && mTitleView != null) {
            mTitleView.getBackground().setAlpha((int) (255 * alphaValue));
        }

        //监听返回
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(bannerHeight, titleHeight, y, alphaValue);
        }
    }

    /**
     * 获取透明　比例
     *
     * @return
     */
    public float getAlphaValue() {
        return alphaValue;
    }

    /**
     * BannerView　高度
     *
     * @return
     */
    public int getBannerHeight() {
        return bannerHeight;
    }

    /**
     * TitleView　高度
     *
     * @return
     */
    public int getTitleHeight() {
        return titleHeight;
    }

    /**
     * 设置 TitleView的 背景透明度
     */
    public void setTitleBgAlpha(float alphaValue) {
        alphaValue = alphaValue < 0 ? 0 : alphaValue;
        alphaValue = alphaValue > 1 ? 1 : alphaValue;

        this.alphaValue = alphaValue;

        if (mTitleView != null) {
            mTitleView.getBackground().setAlpha((int) (255 * alphaValue));
        }
    }

    /**
     * 设置 TitleView 、 是否背景渐变、透明度
     *
     * @param titleView
     */
    public void setTitleView(View titleView, boolean isBgGradient, float orgAlpha) {
        mTitleView = titleView;
        this.isBgGradient = isBgGradient;
        setTitleBgAlpha(orgAlpha);

        if (autoMeasureTitle) {
            ViewTreeObserver vto = getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (mTitleView.getHeight() == 0)
                        return;
                    titleHeight = mTitleView.getHeight();
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    LogUtils.e("HeaderScrollView_TitleView_h:" + titleHeight);
                }
            });
        }
    }

    /**
     * 手动设置bannerView的高度
     *
     * @param bannerHeight
     */
    public void setBannerHeight(int bannerHeight) {
        this.bannerHeight = bannerHeight;
        autoMeasureBanner = false;
    }

    /**
     * 手动设置titleView的高度
     */
    public void setTitleHeight(int titleHeight) {
        this.titleHeight = titleHeight;
        autoMeasureTitle = false;
    }

    /**
     * @param scrollViewListener 　监听
     */
    public void setScrollViewListener(OnScrollListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;

    }

    public interface OnScrollListener {
        void onScrollChanged(int bannerHeight, int titleHeight, int scrollHeight, float scale);
    }
}