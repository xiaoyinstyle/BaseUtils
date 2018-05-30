package yin.style.sample.image;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import yin.style.baselib.activity.base.TitleActivity;
import yin.style.baselib.activity.view.TitleLayout;
import yin.style.baselib.imageload.GlideUtil;
import yin.style.sample.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class mImageActivity extends TitleActivity {
    @BindView(R.id.image_view)
    ImageView imageView;
    @BindView(R.id.image_round)
    ImageView imageRound;
    @BindView(R.id.image_round2)
    ImageView imageRound2;
    @BindView(R.id.image_gif)
    ImageView imageGif;
    @BindView(R.id.image_circle)
    ImageView imageCircle;
    @BindView(R.id.image_circle2)
    ImageView imageCircle2;
    @BindView(R.id.tv_size)
    TextView tvSize;

    @Override
    protected void setTitle(TitleLayout titleLayout) {
        title.setText("图片加载");
    }

    @Override
    protected int getViewByXml() {
        return R.layout.activity_m_image;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        GlideUtil.getInstance().setView(imageView, "http://attach.bbs.miui.com/forum/201209/11/222515j3sgvvjv36gg5n29.jpg");
        GlideUtil.getInstance().setRoundView(imageRound, "http://e.hiphotos.baidu.com/zhidao/pic/item/3812b31bb051f81962b410e8dbb44aed2f73e7fb.jpg", 10);
        GlideUtil.getInstance().setRoundView2(imageRound2, "http://e.hiphotos.baidu.com/zhidao/pic/item/3812b31bb051f81962b410e8dbb44aed2f73e7fb.jpg", 30, 10, Color.BLUE);
        GlideUtil.getInstance().setCircleView(imageCircle, "http://img5.imgtn.bdimg.com/it/u=1858405955,2052924480&fm=23&gp=0.jpg");
        GlideUtil.getInstance().setCircleView2(imageCircle2, "http://img5.imgtn.bdimg.com/it/u=1858405955,2052924480&fm=23&gp=0.jpg", 3, Color.BLUE);
        GlideUtil.getInstance().setGif(imageGif, "http://img1.imgtn.bdimg.com/it/u=3789472357,190916494&fm=214&gp=0.jpg");


    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.bt_clear, R.id.bt_read})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_clear:
                GlideUtil.Cache.clearImageAllCache(this);
                break;
            case R.id.bt_read:
                String size = GlideUtil.Cache.getCacheSize(this);
                tvSize.setText("" + size);
                break;
        }
    }
}
