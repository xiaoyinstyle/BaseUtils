package com.bangdu.classnotice.fragment;

import android.os.Bundle;
import android.widget.ImageView;

import com.bangdu.classnotice.R;
import com.jskingen.baselib.fragment.NormalFragment;
import com.jskingen.baselib.imageload.GlideUtil;
import com.jskingen.baselib.utils.ToastUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.listener.OnLoadImageListener;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by BangDu on 2017/11/7.
 */

public class NewsFragment extends NormalFragment {
    @BindView(R.id.banner)
    Banner banner;
    String[] title = new String[]{
            "http://img.zcool.cn/community/01c8dc56e1428e6ac72531cbaa5f2c.jpg",
            "http://img.zcool.cn/community/01fda356640b706ac725b2c8b99b08.jpg",
            "http://img.zcool.cn/community/01fd2756e142716ac72531cbf8bbbf.jpg",
            "http://img.zcool.cn/community/0114a856640b6d32f87545731c076a.jpg"};
    List<String> images = Arrays.asList(title);


    @Override
    protected int getViewByXml() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        banner.setDelayTime(5 * 1000);
        banner.setImages(images, new OnLoadImageListener() {
            @Override
            public void OnLoadImage(ImageView view, Object url) {
                GlideUtil.getInstance().setView(view, url);
            }
        });
        banner.setDelayTime(5 * 1000);
        banner.setBannerTitle(title);
        banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                ToastUtils.show("" + position);
            }
        });

    }

    @Override
    protected void initData() {
    }

    //如果你需要考虑更好的体验，可以这么操作
    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        banner.isAutoPlay(true);
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }
}
