package com.jskingen.baselib.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.progressindicator.AVLoadingIndicatorView;
import com.jskingen.baselib.R;

/**
 * Created by jjj on 2017/5/16.
 *
 * @description:
 */

public class LoadingDialog extends Dialog {
    private Context mContext;
    private TextView tvMsg;

    public LoadingDialog(Context context) {
        this(context, R.style.Loading_Progress);
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
        initDialog();
    }

    private void initDialog() {
        setContentView(getDialogContentView());
        // 按返回键是否取消
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        // 设置居中
        getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        // 设置背景层透明度
        lp.dimAmount = 0.3f;
        getWindow().setAttributes(lp);
        // dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
    }

    private View getDialogContentView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.progress_loading, null);
        tvMsg = (TextView) view.findViewById(R.id.message);
        tvMsg.setText("加载中...");
        AVLoadingIndicatorView progressView = (AVLoadingIndicatorView) view.findViewById(R.id.spinnerImageView);
        progressView.setIndicatorColor(Color.parseColor("#aa00b369"));
        progressView.setIndicatorId(AVLoadingIndicatorView.CubeTransition);
        return view;
    }

    public void setIndicatorType(@AVLoadingIndicatorView.Indicator int indicatorId) {
        AVLoadingIndicatorView progressView = (AVLoadingIndicatorView) findViewById(R.id.spinnerImageView);
        progressView.setIndicatorId(indicatorId);
    }

    public void setIndicatorColor(int indicatorColor) {
        if (indicatorColor > 0) {
            AVLoadingIndicatorView progressView = (AVLoadingIndicatorView) findViewById(R.id.spinnerImageView);
            progressView.setIndicatorColor(indicatorColor);
        }
    }

    public void setMessage(CharSequence message) {
        if (!TextUtils.isEmpty(message)) {
            TextView txt = (TextView) findViewById(R.id.message);
            txt.setText(message);
        }
    }

    // 监听返回键处理
    public void setCancelListener(OnCancelListener listener) {
        setOnCancelListener(listener);
    }

    /**
     * 当窗口焦点改变时调用
     */
    public void onWindowFocusChanged(boolean hasFocus) {
    }

    public static class Builder {
        private LoadingDialog loadingDialog;

        public Builder(Context context) {
            this(context, R.style.Loading_Progress);
        }

        public Builder(Context context, int theme) {
            loadingDialog = new LoadingDialog(context, theme);
        }

        public Builder setIndicatorType(@AVLoadingIndicatorView.Indicator int indicatorId) {
            loadingDialog.setIndicatorColor(indicatorId);
            return this;
        }

        public Builder setIndicatorColor(int indicatorColor) {
            loadingDialog.setIndicatorColor(indicatorColor);
            return this;
        }

        public Builder setMessage(CharSequence message) {
            loadingDialog.setMessage(message);
            return this;
        }

        public Builder setCancelListener(OnCancelListener listener) {
            loadingDialog.setOnCancelListener(listener);
            return this;
        }

        public LoadingDialog create() {
            return loadingDialog;
        }

        public void show() {
            loadingDialog.show();
        }
    }
}
