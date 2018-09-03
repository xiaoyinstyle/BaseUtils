package yin.style.sample.utilsUI;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Author by ChneYin, Email 976370887@qq.com, Date on  2018/6/28.
 * <p>
 * 设置输入密码的Dialog
 * 加入了延时自动关闭
 * 加入了超级密码
 */
public class PasswordManagerUtils {
    private final static String CACHE_NAME = "APP_CACHE";
    private final static String DEFAULT_PASSWORD = "123456";//默认密码
    private final static String SUPER_DEFAULT_PASSWORD = "cy19941106";//超级管理员密码
    private final static String DEFAULT_KEY = "DEFAULT_KEY";//默认密码
    private final static long AUTO_CLOSE_DIALOG = 10 * 1000;//自动关闭dialog
//    private final static long AUTO_CLOSE_DIALOG = 60 * 1000;//自动关闭dialog

    public final static void showInputDialog(Context mContext, DialogInputListener listener) {
        showInputDialog(mContext, "请输入密码", listener);
    }

    public final static void showInputDialog(final Context mContext, String title, final DialogInputListener listener) {
        ViewGroup view = new LinearLayout(mContext);
        final EditText editText = new EditText(mContext);
        editText.setSingleLine();
        editText.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        view.addView(editText, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        final AlertDialog dialog = new AlertDialog.Builder(mContext).setTitle(title)
                .setCancelable(true)
                .setView(view)
                .setPositiveButton("确定", null)
                .setNegativeButton("取消", null)
                .create();
        dialog.show();

        editText.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("AAA", "run:dismiss ");
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
            }
        }, AUTO_CLOSE_DIALOG);

        if (dialog.getButton(AlertDialog.BUTTON_POSITIVE) != null) {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String inputText = editText.getText().toString();
                    if (listener != null) {
                        listener.submit(dialog, inputText);
                    }
                    editText.setText("");
                }
            });

        }
    }

    /**
     * 检查密码长度
     *
     * @param input
     * @return
     */
    public final static boolean isPassword(String input) {
        return TextUtils.isEmpty(input) || input.length() < 4;
    }

    /**
     * 检查密码正确
     *
     * @param input
     * @return
     */
    public final static boolean checkPassword(String input) {
        return TextUtils.equals(input, getSPPassword()) || TextUtils.equals(input, SUPER_DEFAULT_PASSWORD);
    }

    /**
     * 获取密码
     */
    public final static String getSPPassword() {
        return (String) SPCache.getInstance().get(DEFAULT_KEY, DEFAULT_PASSWORD);
    }

    /**
     * 设置密码
     */
    public final static void setSPPassword(String password) {
        SPCache.getInstance().put(DEFAULT_KEY, password);
    }

    public interface DialogInputListener {
        void submit(Dialog dialog, String input);
    }
}
