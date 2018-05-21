package yin.style.baselib.photo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import yin.style.baselib.R;
import yin.style.baselib.activity.adapter.FragmentAdapter;
import yin.style.baselib.activity.base.NormalActivity;
import yin.style.baselib.utils.ToastUtils;
/**
 * Created by 陈银 on 2017/12/27 11:20
 * <p>
 * 图片预览Activity
 */
public class PicturePreviewActivity extends NormalActivity {

    private ViewPager viewPager;
    private TextView tvLeft;

    private TextView tvRight;

    private FragmentAdapter adapter;
    protected List<Fragment> fragments = new ArrayList<>();
    private boolean showSaveBtn = true;
    private List<Object> picList = new ArrayList<>();
    private int position;


    @Override
    protected int getViewByXml() {
        return R.layout.base_iamge_preview;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        tvRight = (TextView) findViewById(R.id.tv_image_right);
        tvLeft = (TextView) findViewById(R.id.tv_image_left);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tvLeft.setText((position + 1) + "/" + picList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        position = getIntent().getIntExtra(PictureUtils.POSITION, 0);
        //显示保存图片按钮
        showSaveBtn = getIntent().getBooleanExtra(PictureUtils.SHOWBTN, false);
        if (showSaveBtn)
            tvRight.setVisibility(View.VISIBLE);
        else
            tvRight.setVisibility(View.GONE);
//        setOnclick(tvRight);
    }

    @Override
    protected void initData() {
        //添加数据
        picList = (List<Object>) getIntent().getSerializableExtra(PictureUtils.IMAGELIST);

        if (picList.size() == 0) {
            ToastUtils.show("图片加载失败");
            finish();
        }

        for (int i = 0; i < picList.size(); i++) {
            PictureFragment pictureFragment = new PictureFragment();
            pictureFragment.setFile(picList.get(i));
            fragments.add(pictureFragment);
        }

        adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(picList.size());

        position = position > picList.size() - 1 ? 0 : position;
        viewPager.setCurrentItem(position);
        tvLeft.setText((position + 1) + "/" + picList.size());
    }

    @Override
    protected boolean removeAppManager() {
        return true;
    }
}
