package yin.style.baselib.activity.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.KeyEvent;

import yin.style.baselib.view.LoadingDialog;

public class NormalDialogProxy implements IDialog {
    private final String TAG = "NormalDialogProxy";
    private int index;
    private LoadingDialog loadingDialog;
    private IDialogListener listener;

    public NormalDialogProxy(Activity mActivity) {
        loadingDialog = new LoadingDialog(mActivity);
        loadingDialog.setMessage("加载中");
        loadingDialog.setTextColor(Color.WHITE);
//            loadingDialog.setBackground(0);
//            loadingDialog.setIcon(0);
//        loadingDialog.show();

        loadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                index = 0;
                if (listener != null)
                    listener.dismiss();
            }
        });
        loadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        dismissDialog();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void showDialog() {
        index++;
//        Log.w(TAG, "showDialog: " + index);

        if (index == 1 && loadingDialog != null) {
            loadingDialog.show();
            if (listener != null)
                listener.show();
        }
    }

    @Override
    public void dismissDialog() {
        index--;
//        Log.w(TAG, "showDialog: " + index);

        if (index <= 0 && loadingDialog != null)
            loadingDialog.dismiss();
        if (index < 0)
            index = 0;
    }

    @Override
    public void setDialogText(String msg) {
        if (loadingDialog != null && msg != null)
            loadingDialog.setMessage(msg);
    }

    @Override
    public void destroy() {
        if (loadingDialog != null)
            loadingDialog.destroy();
    }

    @Override
    public boolean isShowing() {
        return loadingDialog.isShowing();
    }

    @Override
    public void setListener(IDialogListener listener) {
        this.listener = listener;
    }
}
