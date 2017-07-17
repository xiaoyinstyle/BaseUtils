package com.jskingen.baselib.picture.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import com.jskingen.baselib.utils.FileUtils;
import com.jskingen.baselib.utils.ToastUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ChneY on 2017/5/13.
 */

public class TakePhoto {

    /**
     * 拍照 android7.0
     */
    public static void takePhoto(Activity activity, File tempFile, int requestCode) {
        // 判断存储卡是否可以用，可用进行存储
        if (FileUtils.hasSdcard()) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            Uri uri = FileUtils.getUri2File(activity, tempFile);
            if (Build.VERSION.SDK_INT >= 24) //24 android 7.0
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            activity.startActivityForResult(intent, requestCode);
        } else {
            ToastUtils.show("SD卡不可用！");
        }
    }

    /**
     * 裁剪图片
     */
    public static void cropPicture(Activity activity, File file, int code) {
        if (!file.exists())
            return;
        Uri uri = FileUtils.getUri2File(activity, file);
        cropPicture(activity, uri, code);
    }

    public static void cropPicture(Activity activity, Uri uri, int code) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= 24)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        if (android.os.Build.BRAND.contains("HUAWEI")) {//华为特殊处理 不然会显示圆
            intent.putExtra("aspectX", 9998);
            intent.putExtra("aspectY", 9999);
        } else {
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
        }

        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        activity.startActivityForResult(intent, code);
    }

    /**
     * 保存方法
     */
    public static void saveBitmap(Bitmap bitmap, File file) {
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
