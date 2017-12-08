package yin.style.baselib.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.progressindicator.AVLoadingIndicatorView;
import yin.style.baselib.R;

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

    public void setIndicator(@AVLoadingIndicatorView.Indicator int indicatorId, int indicatorColor) {
        AVLoadingIndicatorView progressView = (AVLoadingIndicatorView) findViewById(R.id.spinnerImageView);
        progressView.setIndicatorId(indicatorId);

        if (indicatorColor > 0) {
            progressView.setIndicatorColor(indicatorColor);
        }
    }

    public void setBackground(@DrawableRes int backgroundRes) {
        if (backgroundRes != 0) {
            View rootView = findViewById(R.id.ll_dialog_root);
            rootView.setBackgroundResource(backgroundRes);
        }
    }


    public void setMessage(CharSequence message) {
        if (!TextUtils.isEmpty(message)) {
            TextView txt = (TextView) findViewById(R.id.message);
            txt.setText(message);
        }
    }

    public void setTextColor(int textColor) {
        if (textColor != 0) {
            TextView txt = (TextView) findViewById(R.id.message);
            txt.setTextColor(textColor);
        }
    }

    public void setIcon(int icon) {
        if (icon != 0) {
            AVLoadingIndicatorView txt = (AVLoadingIndicatorView) findViewById(R.id.spinnerImageView);
            txt.setVisibility(View.GONE);
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(icon);
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

        public Builder setIndicator(@AVLoadingIndicatorView.Indicator int indicatorId, int indicatorColor) {
            loadingDialog.setIndicator(indicatorId, indicatorColor);
            return this;
        }

        public Builder setBackground(@DrawableRes int backgroundRes) {
            loadingDialog.setBackground(backgroundRes);
            return this;
        }

        public Builder setMessage(CharSequence message) {
            loadingDialog.setMessage(message);
            return this;
        }

        public Builder setIcon(int icon) {
            loadingDialog.setIcon(icon);
            return this;
        }

        public Builder setTextColor(int textColor) {
            loadingDialog.setTextColor(textColor);
            return this;
        }

        public Builder setCancelable(boolean flag) {
            loadingDialog.setCancelable(flag);
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
