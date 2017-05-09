package com.jskingen.baselib.picture.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jskingen.baselib.R;
import com.jskingen.baselib.activity.base.TitleActivity;
import com.jskingen.baselib.picture.fragment.PictureSelectFragment;
import com.jskingen.baselib.picture.inter.PictureSelectListener;
import com.jskingen.baselib.picture.model.MediaFile;
import com.jskingen.baselib.picture.utils.AnimationUtils;

import java.util.List;

public class PictureSelectActivity extends TitleActivity {

    private TextView picSelPreview;
    private TextView picSelNumb;
    private TextView picSelComplete;

    private PictureSelectFragment fragment;

    private int picNumb = 0;

    @Override
    protected int getViewByXml() {
        return R.layout.activity_picture_select;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        fragment = new PictureSelectFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fm_content, fragment, PictureSelectFragment.class.getName())
                .commit();

        picSelPreview = (TextView) findViewById(R.id.pic_sel_preview);
        picSelNumb = (TextView) findViewById(R.id.pic_sel_numb);
        picSelComplete = (TextView) findViewById(R.id.pic_sel_complete);

        picSelNumb.setVisibility(View.GONE);
        picSelPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PicturePreviewActivity.start(PictureSelectActivity.this, -1);
            }
        });

        picSelComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (picNumb != 0) {
                    fragment.setComplete();
                }
            }
        });
    }

    @Override
    protected void initData() {
        fragment.setListener(new PictureSelectListener() {
            @Override
            public void onChange(String fileName, List<MediaFile> selectList, List<MediaFile> allList) {
                if (!TextUtils.isEmpty(fileName))
                    title.setText(fileName);

                picNumb = null == selectList ? 0 : selectList.size();
                picSelNumb.setText("" + picNumb);
                if (picNumb == 0) {
                    picSelComplete.setEnabled(false);
                    picSelPreview.setEnabled(false);
                    picSelNumb.setVisibility(View.GONE);
                    picSelComplete.setText(R.string.pic_choice_null);
                } else {
                    picSelComplete.setEnabled(true);
                    picSelPreview.setEnabled(true);
                    AnimationUtils.start(picSelNumb);
                    picSelNumb.setVisibility(View.VISIBLE);
                    picSelComplete.setText(R.string.pic_choice_has);
                }
            }
        });
    }

    @Override
    protected void setTitle() {
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText(R.string.cancel);
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.setCancel();
            }
        });

        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
