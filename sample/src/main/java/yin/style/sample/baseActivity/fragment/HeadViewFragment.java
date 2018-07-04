package yin.style.sample.baseActivity.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import yin.style.baselib.activity.view.TitleLayout;
import yin.style.baselib.fragment.NormalFragment;
import yin.style.baselib.utils.ScreenUtil;
import yin.style.baselib.view.HeaderScrollView;

import yin.style.sample.R;

import butterknife.BindView;

/**
 * Created by BangDu on 2017/12/6.
 */

public class HeadViewFragment extends NormalFragment {
    protected TitleLayout titleLayout;

    @BindView(R.id.tsv)
    HeaderScrollView tsv;
    @BindView(R.id.root)
    FrameLayout root;

    @Override
    protected int getViewByXml() {
        return R.layout.fragment_header_view;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
//        setStatusView(true);
        addTitle();
        tsv.setTitleView(titleLayout, true, 0);
        tsv.setScrollViewListener(new HeaderScrollView.OnScrollListener() {
            @Override
            public void onScrollChanged(int bannerHeight, int titleHeight, int scrollHeight, float scale) {
//                if (scale < 0.5) {
//                    tView.getBackground().setAlpha(0);
//                } else {
//                    tView.getBackground().setAlpha(255);
//                }

//                if (scale < 0.5) {
//                    tView.setBackgroundColor(0);
//                } else {
//                    tView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//                }
            }
        });
    }

    private void addTitle() {
        titleLayout = new TitleLayout(mContext);
        root.addView(titleLayout);

        titleLayout.setBackgroundColor(Color.BLUE);

        boolean isHas = setStatusBarView(mContext, false);
        titleLayout.setStatusTop(isHas);

        titleLayout.setText("标题");

//        tView.findViewById(R.id.rl_title).setVisibility(View.VISIBLE);
//        tView.findViewById(R.id.iv_left).setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        Log.e(TAG, getUserVisibleHint() + "_initData: ");
    }

    @Override
    protected boolean setLazy() {
        return true;
    }

    @Override
    protected boolean setDiligent() {
        return true;
    }
}
