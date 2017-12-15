package yin.style.sample.baseActivity.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import yin.style.baselib.fragment.NormalFragment;
import yin.style.baselib.utils.ScreenUtil;
import yin.style.baselib.view.HeaderScrollView;

import yin.style.sample.R;

import butterknife.BindView;

/**
 * Created by BangDu on 2017/12/6.
 */

public class HeadViewFragment extends NormalFragment {
    protected View tView;
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
        tsv.setTitleView(tView, true, 0);
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
        tView = View.inflate(mContext, R.layout.base_title, null);
        tView.setBackgroundColor(Color.BLUE);

        boolean isHas = setStatusBarView(mContext, false);
        tView.setPadding(0, isHas ? ScreenUtil.getStatusHeight(mContext) : 0, 0, 0);

        root.addView(tView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView title = (TextView) tView.findViewById(R.id.tv_title);
        title.setText("标题");

        tView.findViewById(R.id.rl_title).setVisibility(View.VISIBLE);
        tView.findViewById(R.id.iv_left).setVisibility(View.GONE);
    }

    @Override
    protected void initData() {

    }

}
