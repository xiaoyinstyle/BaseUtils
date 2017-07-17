package com.jskingen.baselib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.jskingen.baselib.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenY on 2017/1/17
 * 6.0权限请求页面.
 * 说明：正常使用时 内部进行了 6.0判断，在接口上 返回 true （以获取权限）
 */

public class PermissionUtil {
    private static PermissionUtil instance;
    private final int mRequestCode = 80;

    private String[] PERMISSIONS = null;// 所需的全部权限
    private Activity activity;
    private boolean isShowDialog = false; //获取权限时，是否显示Dialog，默认 false 不显示

    private boolean canCanclable = false; //Dialog 是否可以手动取消，默认 flase 不可以手动取消

    public static PermissionUtil getInstance() {
        if (instance == null) {
            instance = new PermissionUtil();
        }
        return instance;
    }

    /**
     * @param isShowDialog 是否显示 提示Dialog ，默认 false，不显示
     * @param canCanclable isShowDialog为true时，canCanclable设置 才能生效
     * @param permissions
     */
    public void getPermissions(Activity activity, boolean isShowDialog, boolean canCanclable, String[] permissions, onRequestPermissionsListener listener) {
        this.activity = activity;
        this.isShowDialog = isShowDialog;
        this.listener = listener;
        this.canCanclable = canCanclable;
        this.PERMISSIONS = permissions;
        dlistener = null;
        setPermission(permissions);
    }

    public void getPermissions(Activity activity, boolean isShowDialog, String[] permissions, onRequestPermissionsListener listener) {
        getPermissions(activity, isShowDialog, false, permissions, listener);
    }

    public void getPermissions(Activity activity, String[] permissions, onRequestPermissionsListener listener) {
        getPermissions(activity, false, false, permissions, listener);
    }

    /**
     * 开始 获取权限
     *
     * @param PERMISSIONS
     */
    private void setPermission(String[] PERMISSIONS) {
        //如果没有需要开启的权限，或者获取失败，则退出权限获取，返回 true
        if (PERMISSIONS == null || PERMISSIONS.length < 1) {
            if (listener != null)
                listener.result(true, new String[0]);
            return;
        }
        //如果低于版本6.0 ，则退出权限获取，返回 true
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (listener != null)
                listener.result(true, new String[0]);
            return;
        }

        //去除已以获取的权限
        String[] newPermissions = lacksPermissions(activity, PERMISSIONS);
        if (newPermissions.length > 0) {
            ActivityCompat.requestPermissions(activity, newPermissions, mRequestCode);
        } else {
            listener.result(true, new String[0]);
        }
    }

    /**
     * 用户权限处理,
     * 如果全部获取, 则直接过. 返回 true
     * 根据 isShowDialog 提示Dialog.
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mRequestCode == requestCode && listener != null) {
            if (!isShowDialog) {
                listener.result(hasAllPermissions(activity, permissions), lacksPermissions(activity, PERMISSIONS));
                destroy();
            }else if (hasAllPermissions(activity, PERMISSIONS)) {
                listener.result(true, lacksPermissions(activity, PERMISSIONS));
            } else {
                showMissingPermissionDialog(activity, canCanclable, new onPermissionsDialogListener() {
                    @Override
                    public void result(boolean hasRequest) {
                        listener.result(hasRequest, lacksPermissions(activity, PERMISSIONS));
                    }
                });
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == mRequestCode && listener != null)
            listener.result(hasAllPermissions(activity, PERMISSIONS), lacksPermissions(activity, PERMISSIONS));

        if (requestCode == mRequestCode && dlistener != null)
            dlistener.result(true);

        destroy();
    }


    /**
     * 判断已有所有权限，不缺少返回true， 缺少返回false
     */
    public boolean hasAllPermissions(Context context, String[] PERMISSIONS) {
        if (lacksPermissions(context, PERMISSIONS).length > 0)
            return false;
        return true;
    }

    /**
     * @param permissions
     * @return 得到 未获取的权限集合
     */
    public String[] lacksPermissions(Context context, String[] permissions) {
        List<String> list = new ArrayList<>();

        for (int i = 0; permissions != null && i < permissions.length; i++) {
            if (lacksPermission(context, permissions[i])) {
                list.add(permissions[i]);
            }
        }

        String[] newPermissions = new String[list.size()];
        list.toArray(newPermissions);

        return newPermissions;
    }

    /**
     * 判断是否缺少权限(单个)
     *
     * @param permission
     * @return
     */
    private boolean lacksPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED;
    }

    /**
     * @param canCanclable 默认 false ，是否可以手动取消Dialog
     */
    public void showDialog(Activity activity, boolean canCanclable, onPermissionsDialogListener dlistener) {
        listener = null;
        this.activity = activity;
        this.dlistener = dlistener;
        showMissingPermissionDialog(activity, canCanclable, dlistener);
    }

    public void showDialog(Activity activity, onPermissionsDialogListener dlistener) {
        showDialog(activity, false, dlistener);
    }

    /**
     * @param canCanclable false 点击不消失；
     */
    private void showMissingPermissionDialog(final Activity activity, boolean canCanclable, final onPermissionsDialogListener dlistener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.help);
        builder.setMessage(R.string.string_help_text);
        // 取消
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dlistener != null)
                    dlistener.result(false);

                if (listener != null)
                    listener.result(false, lacksPermissions(activity, PERMISSIONS));
            }
        });

        builder.setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startAppSettings(activity);
            }
        });
        builder.setCancelable(canCanclable);

        builder.show();
    }

    // 启动应用的设置
    private void startAppSettings(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + activity.getPackageName()));
        activity.startActivityForResult(intent, mRequestCode);
    }

    public void destroy() {
        PERMISSIONS = null;// 所需的全部权限
        activity = null;
    }

    //监听
    private onRequestPermissionsListener listener;
    private onPermissionsDialogListener dlistener;

    /**
     * type==-1，表示 Dialog 的返回 ，其他为 权限 的返回，未使用单独使用 Diaolog时，可不做判断
     */
    public interface onRequestPermissionsListener {
        void result(boolean hasRequest, String[] permissions);
    }

    public interface onPermissionsDialogListener {
        void result(boolean isOpen);
    }


}
