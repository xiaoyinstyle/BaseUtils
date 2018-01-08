package yin.style.sample.photo.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;
import yin.style.baselib.fragment.NormalFragment;
import yin.style.baselib.permission.OnPermissionsListener;
import yin.style.baselib.permission.XPermission;
import yin.style.baselib.utils.FileUtils;
import yin.style.baselib.utils.LogUtils;
import yin.style.baselib.utils.ToastUtils;
import yin.style.baselib.view.popupWindow.CommonPopupWindow;
import yin.style.sample.R;
import yin.style.sample.photo.adapter.PhotoAdapter;

import static android.app.Activity.RESULT_OK;

/**
 * Created by BangDu on 2017/12/13.
 */

public class ChooseTypeFragment2 extends NormalFragment {
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    PhotoAdapter photoAdapter;
    List<Object> list = new ArrayList<>();

    @Override
    protected int getViewByXml() {
        return R.layout.fragment_photo_type_2;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        photoAdapter = new PhotoAdapter(mContext, list);
        recyclerview.setLayoutManager(new GridLayoutManager(mContext, 4));
        recyclerview.setAdapter(photoAdapter);
    }

    @Override
    protected void initData() {

    }

    private final int RESULT_TAKE_PHOTE = 100;
    private final int RESULT_OPEN_ALBUM = 101;
    private CommonPopupWindow popupWindow;
    private File imageFile;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == RESULT_TAKE_PHOTE) {
                LogUtils.e("文件：" + imageFile.exists());
                LogUtils.e("文件：" + imageFile.getPath());

                list.clear();
                list.add(imageFile);
                photoAdapter.notifyDataSetChanged();
            } else if (requestCode == RESULT_OPEN_ALBUM) {
                imageFile = FileUtils.getFile2Uri(mContext, data.getData());
                LogUtils.e("文件：" + imageFile.exists());
                LogUtils.e("文件：" + imageFile.getPath());

                list.clear();
                list.add(imageFile);
                photoAdapter.notifyDataSetChanged();
            }
        }
    }


    @OnClick({R.id.bt_takephoto})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_takephoto:
                // 只拍照
                showPictureDialog();
                break;
        }
    }

    private void showPictureDialog() {
        if (popupWindow != null && popupWindow.isShowing()) return;

        View upView = LayoutInflater.from(mContext).inflate(R.layout.popup_up, null);
        //测量View的宽高
        CommonPopupWindow.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(mContext)
                .setView(R.layout.popup_up)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                .setBackGroundLevel(0.5f)//取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int i) {
                        view.findViewById(R.id.btn_take_photo).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (imageFile != null && imageFile.exists())
                                    imageFile.delete();
                                imageFile = FileUtils.getImageFile(mContext, UUID.randomUUID() + ".jpg");
                                takeCamera(imageFile);

                                if (popupWindow != null) {
                                    popupWindow.dismiss();
                                }
                            }
                        });
                        view.findViewById(R.id.btn_select_photo).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                openAlbum();
                                if (popupWindow != null) {
                                    popupWindow.dismiss();
                                }
                            }
                        });
                        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (popupWindow != null) {
                                    popupWindow.dismiss();
                                }
                            }
                        });
                        view.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                if (popupWindow != null) {
                                    popupWindow.dismiss();
                                }
                                return true;
                            }
                        });
                    }
                })
                .create();
        popupWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

    }

    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        mContext.startActivityForResult(intent, RESULT_OPEN_ALBUM);
    }

    private void takeCamera(File file) {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri tempUri = FileUtils.getUri2File(mContext, file);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            openCameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
        startActivityForResult(openCameraIntent, RESULT_TAKE_PHOTE);
    }
}
