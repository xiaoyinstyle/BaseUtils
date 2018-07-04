package yin.style.baselib.photo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import yin.style.baselib.utils.FileUtils;
import yin.style.baselib.utils.LogUtils;

/**
 * Created by BangDu on 2017/11/30.
 * 照片相关的工具类
 * 预览照片
 */

public class PictureUtils {
    public static final String IMAGELIST = "IMAGELIST";
    public static final String SHOWBTN = "SHOWBTN";
    public static final String POSITION = "POSITION";

    public static final void preview(Activity activity, ArrayList<Object> imageList, int position, boolean showSaveBtn) {
        Intent intent = new Intent(activity, PicturePreviewActivity.class);
        intent.putExtra(IMAGELIST, imageList);
        intent.putExtra(SHOWBTN, showSaveBtn);
        intent.putExtra(POSITION, position);
        activity.startActivity(intent);
    }

    public static final void preview(Activity activity, ArrayList<Object> imageList, boolean showSaveBtn) {
        preview(activity, imageList, 0, showSaveBtn);
    }


    /**
     * 调用系统 打开图册
     *
     * @param mContext
     * @param requestCode
     */
    public static final void openAlbum(Activity mContext, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        mContext.startActivityForResult(intent, requestCode);

//        imageFile = FileUtils.getFile2Uri(mContext, data.getData());
//        LogUtils.e("文件：" + imageFile.exists());
//        LogUtils.e("文件：" + imageFile.getPath());
    }

    /**
     * 调用系统 打开拍照
     *
     * @param imageFile 保存路径
     */
    public static final void takeCamera(Activity mContext, File imageFile, int requestCode) {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri tempUri = FileUtils.getUri2File(mContext, imageFile);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            openCameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
        mContext.startActivityForResult(openCameraIntent, requestCode);

//        LogUtils.e("文件：" + imageFile.exists());
//        LogUtils.e("文件：" + imageFile.getPath());

    }

    /**
     * 调用系统裁剪的方法
     */
    public static final void cutAvatar(Activity mContext, File file, int requestCode) {
        cutAvatar(mContext, file, requestCode, 150, 150);
    }

    public static final void cutAvatar(Activity mContext, File file, int requestCode, int outputX, int outputY) {
        Uri uri = FileUtils.getUri2File(mContext, file);
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(uri, "image/*");
        //是否可裁剪
        intent.putExtra("corp", "true");
        //裁剪器高宽比
        if (android.os.Build.MODEL.contains("HUAWEI")) {//华为特殊处理 不然会显示圆
            intent.putExtra("aspectX", 9998);
            intent.putExtra("aspectY", 9999);
        } else {
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
        }
        //设置裁剪框高宽
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        //返回数据
        intent.putExtra("return-data", true);
        mContext.startActivityForResult(intent, requestCode);

//        Bundle bundle = data.getExtras();
//        File file;
//        if (bundle != null) {
//            file = saveBitmap((Bitmap) bundle.getParcelable("data"));
//            if (onUploadListener != null && file != null && file.exists())
//                onUploadListener.cut(file);
//        }
    }


}
