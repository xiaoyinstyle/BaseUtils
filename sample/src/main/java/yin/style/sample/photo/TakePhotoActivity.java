package yin.style.sample.photo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import yin.style.baselib.activity.base.TitleActivity;
import yin.style.baselib.imageload.GlideUtil;
import yin.style.baselib.permission.OnPermissionsListener;
import yin.style.baselib.permission.XPermission;
import yin.style.baselib.utils.FileUtils;
import yin.style.baselib.utils.LogUtils;
import yin.style.baselib.utils.ToastUtils;
import yin.style.baselib.view.PopupWindow.CommonPopupWindow;
import com.jskingen.baseutils.R;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

public class TakePhotoActivity extends TitleActivity {

    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.et_pic_nub)
    EditText etPicNub;
    @BindView(R.id.checkbox_take)
    CheckBox checkboxTake;
    @BindView(R.id.imageView)
    ImageView imageView;

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

    }

    @Override
    protected void initData() {
    }

    List<Uri> mSelected;
    int REQUEST_CODE_CHOOSE = 105;

    private final int RESULT_TAKE_PHOTE = 100;
    private final int RESULT_OPEN_ALBUM = 101;
    private CommonPopupWindow popupWindow;
    private File imageFile;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            Log.d("Matisse", "mSelected: " + mSelected);
            if (mSelected.size() == 1) {
                File file = FileUtils.getFile2Uri(mContext, mSelected.get(0));
                LogUtils.e("文件：" + file.exists());
                LogUtils.e("文件：" + file.getPath());

                GlideUtil.getInstance().setView(imageView, file);
            }
        }

        if (resultCode == RESULT_OK) {
            if (requestCode == RESULT_TAKE_PHOTE) {
                LogUtils.e("文件：" + imageFile.exists());
                LogUtils.e("文件：" + imageFile.getPath());
                GlideUtil.getInstance().setView(imageView, imageFile);
            } else if (requestCode == RESULT_OPEN_ALBUM) {
                imageFile = FileUtils.getFile2Uri(mContext, data.getData());
                LogUtils.e("文件：" + imageFile.exists());
                LogUtils.e("文件：" + imageFile.getPath());
                GlideUtil.getInstance().setView(imageView, imageFile);
            }
        }
    }


    @OnClick({R.id.bt_takephoto, R.id.bt_album, R.id.bt_album_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_takephoto:
                // 只拍照
                //相册选择
                XPermission.getPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}
                        , false, false, new OnPermissionsListener() {
                            @Override
                            public void missPermission(String[] strings) {
                                if (strings.length == 0) {
                                    showPictureDialog();
                                } else {
                                    ToastUtils.show("权限获取失败");
                                }
                            }
                        });

                break;
            case R.id.bt_album:
                //相册选择
                XPermission.getPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}
                        , false, false, new OnPermissionsListener() {
                            @Override
                            public void missPermission(String[] strings) {
                                if (strings.length == 0) {
                                    Matisse.from(mContext)
                                            .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))
                                            .maxSelectable(1)
                                            .countable(false)
                                            .capture(true)
                                            .captureStrategy(new CaptureStrategy(true, FileUtils.authority))
//                                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                            .thumbnailScale(0.85f)
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

    private void showPictureDialog() {
        if (popupWindow != null && popupWindow.isShowing()) return;

        View upView = LayoutInflater.from(this).inflate(R.layout.popup_up, null);
        //测量View的宽高
        CommonPopupWindow.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(this)
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
        popupWindow.showAtLocation(findViewById(R.id.root), Gravity.BOTTOM, 0, 0);

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
