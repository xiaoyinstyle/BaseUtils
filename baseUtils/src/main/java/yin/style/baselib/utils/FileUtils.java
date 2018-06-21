package yin.style.baselib.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import yin.style.baselib.BaseHelp;

/**
 * android 7.0 文件管理 utils
 * Created by chenY on 2017/1/11.
 * v1.0.2
 */

public class FileUtils {
    //    public static final String authority = BaseHelp.getInstance().getContext().getPackageName() + ".provider"; //authority值
    public static final String xml_name = "root";                    //xml文件的nam值
    public static final String fileName = BaseHelp.getInstance().getFileName();               //根目录下文件名

    public static File getFile2Uri(Context context, Uri uri) {
        if (getPath(context, uri) == null) {
            return null;
        }
        return new File(getPath(context, uri));
    }

    public static String getPath(Context context, Uri uri) {
        String path;
        if (Build.VERSION.SDK_INT < 24) {//24 android 7.0
            File file = new File(uri.getPath());
            if (file.exists()) {
                path = uri.getPath();
            } else {
                String[] filePathColumn = {MediaStore.MediaColumns.DATA};// 图片的路径
                Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
                cursor.moveToFirst();
                // 按我个人理解 这个是获得用户选择的图片的索引值
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                // 最后根据索引值获取图片路径
                path = cursor.getString(columnIndex);
                cursor.close();
            }
            return path;
        } else {
            String result;
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[]{MediaStore.Images.ImageColumns.DATA},//
                    null, null, null);
            if (cursor == null) {
                result = uri.getPath();
            } else {
                cursor.moveToFirst();
                int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                if (-1 == index) {
                    String pa;
                    if (hasSdcard()) {
                        pa = new File(Environment.getExternalStorageDirectory().getPath()).getPath();
                        result = pa + uri.getPath().substring(xml_name.length() + 1, uri.getPath().length());
                    } else
                        result = null;
                } else {
                    result = cursor.getString(index);
                }
                cursor.close();
            }
            return result;

//            String pa;
//            if (hasSdcard()) {
//                pa = new File(Environment.getExternalStorageDirectory().getPath()).getPath();
//                path = pa + uri.getPath().substring(xml_name.length() + 1, uri.getPath().length());
//                return path;
//            } else
//                return null;

        }
    }

    /**
     * 获取文件路径的 共享uri
     * android 7.0  添加权限 intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
     */
    public static Uri getUri2File(Context context, File file) {
        Uri fileUri;
        if (Build.VERSION.SDK_INT < 24) {//24 android 7.0
            fileUri = Uri.fromFile(file);
        } else {
            if (hasSdcard())
                try {
                    fileUri = FileProvider.getUriForFile(context, getAuthority(context), file);
                } catch (Exception e) {
                    e.printStackTrace();
                    fileUri = null;
                }
            else {
                fileUri = null;
            }
        }
        return fileUri;
    }

    public static File getRootFile(Context context) {
        File dir = null;
        try {
            if (hasSdcard())
                dir = new File(Environment.getExternalStorageDirectory(), fileName);
            else
                dir = new File(context.getFilesDir(), fileName);
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    // <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dir;
    }

    // 默认根目录路径
    public static String getRootPath(Context context) {
        return getRootFile(context).getPath();
    }

    //图片文件保存路径
    public static File getImageFile(Context context) {
        File file = new File(getRootPath(context), "image");
        if (!file.exists())
            file.mkdirs();
        //创建 系统不读取图片
        File nomedia = new File(file, ".nomedia");
        if (!nomedia.exists())
            nomedia.mkdirs();
        return file;
    }

//    //图片文件路径
//    public static File getImageFileTemp(Context context) {
//        try {
//            return File.createTempFile("temp_", ".png", getImageFile(context));
//        } catch (IOException e) {
//            e.printStackTrace();
//            return new File(getImageFile(context), "temp_" + UUID.randomUUID() + ".png");
//        }
//    }

    /**
     * 保存图片到 系统图片路劲
     *
     * @return 保存的图片
     */
    public static File getSystemImageFileTemp() {
        String cameraPath = Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_DCIM + File.separator + "Camera" + File.separator;

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        long t = System.currentTimeMillis();
        Date date = new Date(t);
        String temp = (t + "").substring((t + "").length() - 6, (t + "").length());
        return new File(new File(cameraPath), "IMG_" + format.format(date) + "_" + temp + ".jpg");
    }

    //图片文件路径
    public static File getImageFile(Context context, String fileName) {
        return new File(getImageFile(context), fileName);
    }

    //图片文件保存路径
    public static File getMapFile(Context context) {
        File file = new File(getRootPath(context), "amap");
        if (!file.exists())
            file.mkdirs();
        return file;
    }

    //    //头像文件保存路径
//    public static File getAvatarFile(Context context) {
//        File file = new File(getRootPath(context), "avatar");
//        if (!file.exists())
//            file.mkdirs();
//        return file;
//    }
//
    //下载保存路径
    public static File getDownloadFile(Context context, String fileName) {
        return new File(getDownloadFile(context), fileName);
    }
    //下载保存路径
    public static File getDownloadFile(Context context) {
        File file = new File(getRootPath(context), "download");
        if (!file.exists())
            file.mkdirs();
        return file;
    }
//
//    //文档保存路径
//    public static File getDocumentFile(Context context) {
//        File file = new File(getRootPath(context), "document");
//        if (!file.exists())
//            file.mkdirs();
//        return file;
//    }

    //7.0无法对没有SD卡的设备进行存储，一般不会存在
    public static boolean canSava() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && Environment.getExternalStorageDirectory().exists())
            return true;
        else {
            if (Build.VERSION.SDK_INT < 24)
                return true;
            else
                return false;
        }
    }

    /**
     * 判断sd卡是否可用
     */
    public static boolean hasSdcard() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && Environment.getExternalStorageDirectory().exists())
            return true;
        else {
            return false;
        }
    }

    public static String getAuthority(Context context) {
        return context.getPackageName() + ".provider";
    }
}
