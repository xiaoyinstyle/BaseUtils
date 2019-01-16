package yin.style.baselib.activity.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import yin.style.baselib.R;
import yin.style.baselib.utils.ScreenUtil;

public class NetViewUtils {
        private View mTipView;
        private WindowManager mWindowManager;
        private WindowManager.LayoutParams mLayoutParams;

        private int netViewTop = -1;

        public void initTipView(Activity mContext) {
            LayoutInflater inflater = mContext.getLayoutInflater();
            mTipView = inflater.inflate(R.layout.base_view_network_tip, null); //提示View布局
            mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            mLayoutParams = new WindowManager.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                            | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    PixelFormat.TRANSLUCENT);
            //使用非CENTER时，可以通过设置XY的值来改变View的位置
            mLayoutParams.gravity = Gravity.TOP;
            mLayoutParams.x = 0;
            netViewTop = netViewTop == -1 ? ScreenUtil.getTitleHeight(mContext) : netViewTop;
            mLayoutParams.y = netViewTop;
        }

        public void hasNetWork(boolean has, boolean isCheckNetWork) {
            if (isCheckNetWork) {
                if (has) {
                    if (mTipView != null && mTipView.getParent() != null) {
                        mWindowManager.removeView(mTipView);
                    }
                } else {
                    if (mTipView.getParent() == null) {
                        mLayoutParams.y = netViewTop;
                        mWindowManager.addView(mTipView, mLayoutParams);
                    }
                }
            }
        }

        public void finish() {
            //当提示View被动态添加后直接关闭页面会导致该View内存溢出，所以需要在finish时移除
            if (mTipView != null && mTipView.getParent() != null) {
                mWindowManager.removeView(mTipView);
            }
        }

        public void setNetViewTop(int netViewTop) {
            this.netViewTop = netViewTop;
        }

        public View getNetView() {
            return mTipView;
        }
}
