package com.jskingen.baselib.picture.utils;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;

import com.jskingen.baselib.utils.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by ChneY on 2017/5/11.
 * <p>
 * 保存图片
 */

public class ImageUtils {
    public static void addIamge2Album(Context context, File file) {// 其次把文件插入到系统图库
//        try {
//            MediaStore.Images.Media.insertImage(context.getContentResolver(),
//                    file.getAbsolutePath(), file.getName(), null);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        // 最后通知图库更新
//        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getPath())));
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, FileUtils.getUri2File(context, file)));

    }
}
