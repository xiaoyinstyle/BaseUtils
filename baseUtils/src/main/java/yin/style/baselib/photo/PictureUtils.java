package yin.style.baselib.photo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BangDu on 2017/11/30.
 * 照片相关的工具类
 * 预览照片
 */

public class PictureUtils {
    public static final String IMAGELIST = "IMAGELIST";
    public static final String SHOWBTN = "SHOWBTN";
    public static final String POSITION = "POSITION";

    public static void preview(Activity activity, ArrayList<Object> imageList, int position, boolean showSaveBtn) {
        Intent intent = new Intent(activity, PicturePreviewActivity.class);
        intent.putExtra(IMAGELIST, imageList);
        intent.putExtra(SHOWBTN, showSaveBtn);
        intent.putExtra(POSITION, position);
        activity.startActivity(intent);
    }

    public static void preview(Activity activity, ArrayList<Object> imageList, boolean showSaveBtn) {
        preview(activity, imageList, 0, showSaveBtn);
    }
}
