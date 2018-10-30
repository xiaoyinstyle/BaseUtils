package yin.style.sample.utilsUI;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import yin.style.baselib.activity.base.TitleActivity;
import yin.style.baselib.activity.view.TitleLayout;
import yin.style.baselib.utils.ScreenUtil;
import yin.style.baselib.view.scaleView.ScaleLinearLayout;
import yin.style.sample.R;
import yin.style.sample.utils.DensityUtils;

public class AutoSizeActivity extends TitleActivity {

    @Override
    public void setContentView(int layoutResID) {
        DensityUtils.setOrientation(this, DensityUtils.WIDTH);
        super.setContentView(layoutResID);
    }

    @Override
    protected int getViewByXml() {
        return R.layout.activity_auto_size;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        statusBarView.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "initView: statusBarView___h:" + ScreenUtil.getStatusHeight(mContext));
                Log.e(TAG, "initView: statusBarView___h:" + statusBarView.getHeight());
                Log.e(TAG, "initView: statusBarView___h:" + DensityUtils.getStatusBarHeight(mContext));
            }
        }, 200);

    }

    @Override
    protected void initData() {
//        tv1.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                tv1.setText(tv1.getWidth() + " x " + tv1.getHeight() + "   " + tv1.getTextSize());
//
////                tvDp1.setText(tvDp1.getWidth() + " x " + tvDp1.getHeight() + "   " + tvDp1.getTextSize());
////                tvDp3.setText(tvDp3.getWidth() + " x " + tvDp3.getHeight() + "   " + tvDp3.getTextSize());
//            }
//        }, 200);
    }

    @Override
    protected void setTitle(TitleLayout titleLayout) {
        titleLayout.setText("屏幕自适应");
    }


}
