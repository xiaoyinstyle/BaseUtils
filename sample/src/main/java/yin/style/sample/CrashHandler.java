package yin.style.sample;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import yin.style.baselib.utils.AppManager;

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private final long delayedTime = 500;//毫秒
    public static final String TAG = "CrashHandler";
    private static CrashHandler INSTANCE = new CrashHandler();
    private Context mContext;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    public void init(Context context) {
        mContext = context;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e(TAG, "uncaughtException: " + ex.getMessage());
        new Thread() {
            @Override
            public void run() {
                AppManager.getInstance().finishAllActivity();
                Intent intent = new Intent(mContext, MainActivity.class);
                PendingIntent restartIntent = PendingIntent.getActivity(mContext, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
                AlarmManager mgr = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
                mgr.set(AlarmManager.RTC, System.currentTimeMillis() + delayedTime, restartIntent);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }.start();
    }
}
