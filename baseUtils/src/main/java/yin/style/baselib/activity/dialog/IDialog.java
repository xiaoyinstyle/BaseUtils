package yin.style.baselib.activity.dialog;

public interface IDialog {
    void showDialog();

    void dismissDialog();

    void setDialogText(String msg);

    void destroy();

    boolean isShowing();

    void setListener(IDialogListener listener);
}
