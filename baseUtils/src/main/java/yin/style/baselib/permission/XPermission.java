package yin.style.baselib.permission;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenY on 2017/1/17
 * 6.0权限请求页面.
 * 说明：正常使用时 内部进行了 6.0判断，在接口上 返回 true （以获取权限）
 * http://www.jb51.net/article/119896.htm
 * 需要注意，我们在进行运行时权限处理时使用的是权限名
 * ，但是用户一旦同意授权了，那么该权限所对应的权限组中所有其他的权限也会同时被授权。
 */

public class XPermission {
    //Android 中所有的危险权限，一共是9组24个权限

    //public static final String CALENDAR_GROUP = Manifest.permission_group.CALENDAR;//日历
    public static final String CALENDAR_READ = Manifest.permission.READ_CALENDAR;
    public static final String CALENDAR_WRITE = Manifest.permission.WRITE_CALENDAR;

    public static final String CAMERA = Manifest.permission.CAMERA;//相机

    //public static final String CONTACTS_GROUP = Manifest.permission_group.CONTACTS;//联系人
    public static final String CONTACTS_READ = Manifest.permission.READ_CONTACTS;
    public static final String CONTACTS_WRITE = Manifest.permission.WRITE_CONTACTS;
    public static final String CONTACTS_GET = Manifest.permission.GET_ACCOUNTS;

    //public static final String LOCATION_GROUP = Manifest.permission_group.LOCATION;//位置
    public static final String LOCATION_GPS = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String LOCATION_NET = Manifest.permission.ACCESS_COARSE_LOCATION;

    public static final String RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;//麦克风

    //public static final String PHONE_GROUP = Manifest.permission_group.PHONE;//手机
    public static final String PHONE_READ_STATE = Manifest.permission.READ_PHONE_STATE;
    public static final String PHONE_CALL = Manifest.permission.CALL_PHONE;
    public static final String PHONE_READ_CALL_LOG = Manifest.permission.READ_CALL_LOG;
    public static final String PHONE_WRITE_CALL_LOG = Manifest.permission.WRITE_CALL_LOG;
    public static final String PHONE_ADD_VOICEMAIL = Manifest.permission.ADD_VOICEMAIL;
    public static final String PHONE_USE_SIP = Manifest.permission.USE_SIP;
    public static final String PHONE_PROCESS_OUTGOING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS;

    public static final String SENSORS = Manifest.permission.BODY_SENSORS;//传感器

    //public static final String SMS_GROUP = Manifest.permission_group.SMS;//短信
    public static final String SMS_SEND_SMS = Manifest.permission.SEND_SMS;
    public static final String SMS_RECEIVE_SMS = Manifest.permission.RECEIVE_SMS;
    public static final String SMS_READ_SMS = Manifest.permission.READ_SMS;
    public static final String SMS_RECEIVE_WAP_PUSH = Manifest.permission.RECEIVE_WAP_PUSH;
    public static final String SMS_RECEIVE_MMS = Manifest.permission.RECEIVE_MMS;

    //public static final String STORAGE_GROUP = Manifest.permission_group.STORAGE;//存储卡
    public static final String STORAGE_READ = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String STORAGE_WRITE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

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
//        PackageManager pm = context.getPackageManager();
//        boolean hasPermission = (PackageManager.PERMISSION_GRANTED == pm.checkPermission(permission, context.getPackageName()));
//
//        return hasPermission;
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED;
    }
}
