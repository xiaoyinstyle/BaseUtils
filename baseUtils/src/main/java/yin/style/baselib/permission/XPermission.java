package yin.style.baselib.permission;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenY on 2017/1/17
 * 6.0权限请求页面.
 * 说明：正常使用时 内部进行了 6.0判断，在接口上 返回 true （以获取权限）
 */

public class XPermission {
    public static final String CAMERA = Manifest.permission.CAMERA;
    public static final String WRITE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String READ = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String LOCATION_GPS = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String LOCATION_NET = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String PHONE = Manifest.permission.CALL_PHONE;
    public static final String SMS = Manifest.permission.SEND_SMS;

    private static final String TAG = "XPermissions";

    public static XPermission init(Activity activity) {
        return new XPermission(activity);
    }

    public XPermission setPermissions(String... permissions) {
        this.permissions = permissions;
        return this;
    }

    public XPermission showDialog(boolean isShowDialog, boolean dialogCanCancel) {
        this.isShowDialog = isShowDialog;
        this.dialogCanCancel = dialogCanCancel;
        return this;
    }

    public void get(OnPermissionsListener listener) {
        if (permissions == null)
            return;

        start(listener);
    }

    private Activity activity;
    private String[] permissions;
    private boolean isShowDialog;
    private boolean dialogCanCancel;

    private XPermission() {
    }

    private XPermission(Activity activity) {
        this.activity = activity;
    }

    private void start(OnPermissionsListener listener) {
        //如果低于版本6.0 ，则退出权限获取，返回 true
        if (Build.VERSION.SDK_INT < 23) {
            if (listener != null)
                listener.missPermission(new String[0]);
            return;
        }

        //去除已以获取的权限
        String[] newPermissions = lacksPermissions(activity, permissions);
        if (newPermissions.length > 0) {
            fragmentPermissions(activity, newPermissions, listener, isShowDialog, dialogCanCancel);
        } else {
            listener.missPermission(new String[0]);
        }
    }

    /**
     * 通过Fragment 获取 权限
     *
     * @param activity
     * @param PERMISSIONS
     * @param listener
     * @param isShowDialog
     * @param dialogCanCancel
     */
    private static void fragmentPermissions(Activity activity, String[] PERMISSIONS, OnPermissionsListener listener
            , boolean isShowDialog, boolean dialogCanCancel) {
        PermissionsFragment rxPermissionsFragment = (PermissionsFragment) activity.getFragmentManager().findFragmentByTag(TAG);
        if (rxPermissionsFragment == null) {
            rxPermissionsFragment = new PermissionsFragment();
            FragmentManager fragmentManager = activity.getFragmentManager();
            fragmentManager.beginTransaction()
                    .add(rxPermissionsFragment, TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        //设置监听
        rxPermissionsFragment.setListener(listener);
        //设置在没有成功获取权限时 是否弹框提示
        rxPermissionsFragment.setDialog(isShowDialog, dialogCanCancel);

        //去除已以获取的权限
        rxPermissionsFragment.requestPermissions(PERMISSIONS);
    }

    /**
     * @return 得到 未获取的权限集合
     */
    public static String[] lacksPermissions(Context context, String[] permissions) {
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
    private static boolean lacksPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED;
    }
}
