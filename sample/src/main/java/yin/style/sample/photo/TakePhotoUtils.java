package yin.style.sample.photo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.UUID;

import yin.style.baselib.utils.FileUtils;
import yin.style.baselib.view.PopupWindow.CommonPopupWindow;
import yin.style.sample.R;

/**
 * Created by BangDu on 2017/12/15.
 */

public class TakePhotoUtils {
    private CommonPopupWindow popupWindow;
    private Activity mContext;

//    public void showBottomDialog() {
//        if (popupWindow != null && popupWindow.isShowing()) return;
//
//        View upView = LayoutInflater.from(mContext).inflate(R.layout.popup_up, null);
//        //测量View的宽高
//        CommonPopupWindow.measureWidthAndHeight(upView);
//        popupWindow = new CommonPopupWindow.Builder(mContext)
//                .setView(R.layout.popup_up)
//                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
//                .setBackGroundLevel(0.5f)//取值范围0.0f-1.0f 值越小越暗
//                .setAnimationStyle(R.style.AnimUp)
//                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
//                    @Override
//                    public void getChildView(View view, int i) {
//                        view.findViewById(R.id.btn_take_photo).setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                if (imageFile != null && imageFile.exists())
//                                    imageFile.delete();
//                                imageFile = FileUtils.getImageFile(mContext, UUID.randomUUID() + ".jpg");
//                                takeCamera(imageFile);
//
//                                if (popupWindow != null) {
//                                    popupWindow.dismiss();
//                                }
//                                if (onClickListener != null)
//                                    onClickListener.onClick(1);
//                            }
//                        });
//                        view.findViewById(R.id.btn_select_photo).setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                openAlbum();
//                                if (onClickListener != null)
//                                    onClickListener.onClick(2);
//                                if (popupWindow != null) {
//                                    popupWindow.dismiss();
//                                    if (onClickListener != null)
//                                        onClickListener.dismiss();
//                                }
//                            }
//                        });
//                        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                onClickListener.onClick(0);
//                                if (popupWindow != null) {
//                                    popupWindow.dismiss();
//                                    if (onClickListener != null)
//                                        onClickListener.dismiss();
//                                }
//                            }
//                        });
//                        view.setOnTouchListener(new View.OnTouchListener() {
//                            @Override
//                            public boolean onTouch(View v, MotionEvent event) {
//                                if (popupWindow != null) {
//                                    popupWindow.dismiss();
//                                    if (onClickListener != null)
//                                        onClickListener.dismiss();
//                                }
//                                return true;
//                            }
//                        });
//                    }
//                })
//                .create();
//        popupWindow.showAtLocation(mContext.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
//
//    }
//
//    private void openAlbum() {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        mContext.startActivityForResult(intent, RESULT_OPEN_ALBUM);
//    }
//
//    private void takeCamera(File file) {
//        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        Uri tempUri = FileUtils.getUri2File(mContext, file);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
//            openCameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
//        mContext.startActivityForResult(openCameraIntent, RESULT_TAKE_PHOTE);
//    }

    OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(int tag);

        void dismiss();
    }
}
