package yin.style.sample.photo.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.internal.utils.MediaStoreCompat;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import yin.style.baselib.photo.PicturePreviewActivity;
import yin.style.baselib.fragment.NormalFragment;
import yin.style.baselib.permission.OnPermissionsListener;
import yin.style.baselib.permission.XPermission;
import yin.style.baselib.photo.PictureUtils;
import yin.style.baselib.utils.FileUtils;
import yin.style.baselib.utils.ToastUtils;
import yin.style.recyclerlib.inter.OnItemClickListener;
import yin.style.sample.R;
import yin.style.sample.photo.GifSizeFilter;
import yin.style.sample.photo.adapter.PhotoAdapter;

import static android.app.Activity.RESULT_OK;

/**
 * Created by 陈银 on 2017/12/13 14:03
 * <p>
 * 知乎 图片选择
 */
public class ChooseTypeFragment extends NormalFragment {
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.et_pic_nub)
    EditText etPicNub;
    @BindView(R.id.checkbox_take)
    CheckBox checkboxTake;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    PhotoAdapter photoAdapter;
    ArrayList<Object> list = new ArrayList<>();

    MediaStoreCompat mMediaStoreCompat;
    int REQUEST_CODE_CHOOSE = 205;
    int REQUEST_CODE_TAKEPHOTO = 206;

    @Override
    protected int getViewByXml() {
        return R.layout.fragment_photo_type_1;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        photoAdapter = new PhotoAdapter(mContext, list);
        recyclerview.setLayoutManager(new GridLayoutManager(mContext, 4));
        recyclerview.setAdapter(photoAdapter);

        photoAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                PictureUtils.preview(mContext, list, position, false);
            }
        });
    }

    @Override
    protected void initData() {
        mMediaStoreCompat = new MediaStoreCompat(mContext);
        mMediaStoreCompat.setCaptureStrategy(new CaptureStrategy(true, FileUtils.authority));
    }


    @OnClick({R.id.bt_takephoto, R.id.bt_album, R.id.bt_album_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_takephoto:
                // 只拍照
                XPermission.getPermissions(mContext, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}
                        , false, false, new OnPermissionsListener() {
                            @Override
                            public void missPermission(String[] strings) {
                                if (strings.length == 0) {
                                    if (mMediaStoreCompat != null) {
                                        mMediaStoreCompat.dispatchCaptureIntent(mContext, REQUEST_CODE_TAKEPHOTO);
                                    }
                                } else {
                                    ToastUtils.show("权限获取失败");
                                }
                            }
                        });

                break;
            case R.id.bt_album:
                //相册选择
                XPermission.getPermissions(mContext, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}
                        , false, false, new OnPermissionsListener() {
                            @Override
                            public void missPermission(String[] strings) {
                                if (strings.length == 0) {
                                    Matisse.from(mContext)
                                            .choose(MimeType.ofImage())
                                            .theme(R.style.Matisse_Zhihu)
                                            .countable(false)
                                            .maxSelectable(1)
//                                            .imageEngine(new PicassoEngine())
                                            .imageEngine(new GlideEngine())
                                            .forResult(REQUEST_CODE_CHOOSE);
                                } else {
                                    ToastUtils.show("权限获取失败");
                                }
                            }
                        });
                break;
            case R.id.bt_album_more:
                //选择张数
                //是否显示拍照
                int number = 3;
                try {
                    number = Integer.parseInt(etPicNub.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    number = 3;
                }
                Matisse.from(mContext)
                        .choose(MimeType.ofAll(), false)
                        .countable(true)
                        .capture(checkboxTake.isChecked())
                        .captureStrategy(new CaptureStrategy(true, FileUtils.authority))
                        .maxSelectable((number < 0 || number > 10) ? 3 : number)
                        .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                      .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                        .thumbnailScale(0.85f)
                        .imageEngine(new GlideEngine())
                        .forResult(REQUEST_CODE_CHOOSE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            List<Uri> uriList = Matisse.obtainResult(data);
            List<String> pathList = Matisse.obtainPathResult(data);

            list.clear();
            list.addAll(pathList);
            photoAdapter.notifyDataSetChanged();
        } else if (requestCode == REQUEST_CODE_TAKEPHOTO && resultCode == RESULT_OK) {
            Uri contentUri = mMediaStoreCompat.getCurrentPhotoUri();
            String path = mMediaStoreCompat.getCurrentPhotoPath();

            list.clear();
            list.add(path);
            photoAdapter.notifyDataSetChanged();
        }
    }
}
