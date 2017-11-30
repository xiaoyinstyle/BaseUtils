package com.jskingen.baselib.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.jskingen.baselib.R;
import com.jskingen.baselib.activity.base.NormalAcitivity;
import com.jskingen.baselib.activity.model.TabEntity;
import com.jskingen.baselib.imageload.GlideUtil;
import com.jskingen.baselib.utils.ToastUtils;
import com.jskingen.baselib.view.imagezoom.ImageViewTouch;
import com.jskingen.baselib.view.imagezoom.ImageViewTouchBase;

import java.util.ArrayList;
import java.util.List;

public class PicturePreviewActivity extends NormalAcitivity {
    private static final String IMAGELIST = "IMAGELIST";
    private static final String SHOWBTN = "SHOWBTN";
    private ViewPager viewPager;
    private TextView tvLeft;

    private TextView tvRight;

    private MyAdapter adapter;
    protected List<View> lists = new ArrayList<>();
    private boolean showSaveBtn = true;
    private List<Object> picList = new ArrayList<>();

    public static void start(Context context, ArrayList<Object> list, boolean showSaveBtn) {
        Intent intent = new Intent(context, PicturePreviewActivity.class);
        intent.putExtra(IMAGELIST, list);
        intent.putExtra(SHOWBTN, showSaveBtn);
        context.startActivity(intent);
    }

    @Override
    protected int getViewByXml() {
        return R.layout.activity_iamge_preview;
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
                tvLeft.setText((position + 1) + "/" + (picList.size() + 1));
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

        //显示保存图片按钮
        showSaveBtn = getIntent().getBooleanExtra(SHOWBTN, true);
        if (showSaveBtn)
            tvRight.setVisibility(View.VISIBLE);
        else
            tvRight.setVisibility(View.GONE);
//        setOnclick(tvRight);
    }

    @Override
    protected void initData() {
        //添加数据
        picList = (List<Object>) getIntent().getSerializableExtra(IMAGELIST);

        tvLeft.setText(1 + "/" + (picList.size() + 1));
        if (picList.size() == 0) {
            ToastUtils.show("图片加载失败");
            finish();
        }

        for (int i = 0; i < picList.size(); i++) {
            getView(picList.get(i));
        }

        adapter = new MyAdapter(lists);
        viewPager.setAdapter(adapter);
    }


    /**
     * 添加 图片
     *
     * @param file
     * @return
     */
    protected View getView(Object file) {
        ImageViewTouch view = new ImageViewTouch(this);
        GlideUtil.getInstance(this).setView(view, file);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        view.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
        return view;
    }


    class MyAdapter extends PagerAdapter {

        List<View> viewLists;

        public MyAdapter(List<View> lists) {
            viewLists = lists;
        }

        @Override
        public int getCount() {                                                                 //获得size
            // TODO Auto-generated method stub
            return viewLists.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(View view, int position, Object object) {
            ((ViewPager) view).removeView(viewLists.get(position));
        }

        @Override
        public Object instantiateItem(View view, int position) {
            ((ViewPager) view).addView(viewLists.get(position), 0);
            return viewLists.get(position);
        }
    }

    class TabAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> tabEntities;

        /**
         * 构造方法
         */
        public TabAdapter(FragmentManager fm, List<Fragment> titles) {
            super(fm);
            this.tabEntities = titles;
        }

        /**
         * 返回显示的Fragment总数
         */
        @Override
        public int getCount() {
            return tabEntities.size();
        }

        /**
         * 返回要显示的Fragment的某个实例
         */
        @Override
        public Fragment getItem(int position) {
            return tabEntities.get(position);
        }

        }
}
