package com.jskingen.baseutils.photo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.jskingen.baselib.activity.base.TitleActivity;
import com.jskingen.baselib.picture.activity.PictureAlbumActivity;
import com.jskingen.baselib.picture.inter.OnSelectListener;
import com.jskingen.baselib.picture.model.MediaFile;
import com.jskingen.baselib.picture.utils.PictureManage;
import com.jskingen.baselib.utils.ToastUtils;
import com.jskingen.baseutils.R;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TakePhotoActivity extends TitleActivity {

    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.et_pic_nub)
    EditText etPicNub;
    @BindView(R.id.checkbox_take)
    CheckBox checkboxTake;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void setTitle() {

    }

    @Override
    protected int getViewByXml() {
        return R.layout.activity_take_photo;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }

    private final int TAKE_PHOTO_START = 10;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (TAKE_PHOTO_START == requestCode) {

        }
    }


    @OnClick({R.id.bt_takephoto, R.id.bt_album, R.id.bt_album_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_takephoto:
                // 只拍照

                break;
            case R.id.bt_album:
                //相册选择

                PictureManage.getInstance().openAlbum(this, new OnSelectListener() {
                    @Override
                    public void onComplete(List<MediaFile> selectListt) {
                        ToastUtils.show(selectListt.size() + "");
                    }
                });
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

//                MediaLoader.get(this).openAlbumMore(nub, checkboxTake.isChecked(), new MediaLoader.OnPictureCallback() {
//                    @Override
//                    public void onSuccess(List<String> paths, Bitmap bitmap) {
//
//                    }
//                });
                break;
        }
    }
}
