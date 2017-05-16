package com.jskingen.baselib.picture.activity;

import android.os.Bundle;
import android.view.View;

import com.jskingen.baselib.R;
import com.jskingen.baselib.activity.base.TitleActivity;
import com.jskingen.baselib.picture.fragment.PictureAlbumFragment;

public class PictureAlbumActivity extends TitleActivity {
    private PictureAlbumFragment fragment;

    @Override
    protected int getViewByXml() {
        return R.layout.activity_picture_album;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        fragment = new PictureAlbumFragment();
        fragment.setSelectActivity(PictureSelectActivity.class);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fm_content, fragment, PictureAlbumFragment.class.getName())
                .commit();
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void setTitle() {
        title.setText(R.string.pic_choice_picture);
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText(R.string.cancel);
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        iv_left.setVisibility(View.GONE);
    }
}
