package com.jskingen.baseutils.photo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.jskingen.baselib.activity.base.TitleActivity;
import com.jskingen.baseutils.R;

import butterknife.BindView;
import butterknife.OnClick;

public class TakePhotoActivity extends TitleActivity {

    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.et_pic_nub)
    EditText etPicNub;
    @BindView(R.id.checkbox_take)
    CheckBox checkboxTake;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void setTitle() {
        title.setText("图片选择");
    }

    @Override
    protected int getViewByXml() {
        return R.layout.activity_take_photo;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    @Override
    protected void initData() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @OnClick({R.id.bt_takephoto, R.id.bt_album, R.id.bt_album_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_takephoto:
                // 只拍照
                //相册选择

                break;
            case R.id.bt_album:
                //相册选择

                break;
            case R.id.bt_album_more:
                //选择张数
                //是否显示拍照
                int nub;
                try {
                    nub = Integer.parseInt(etPicNub.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    nub = 3;
                }

                break;
        }
    }
}
