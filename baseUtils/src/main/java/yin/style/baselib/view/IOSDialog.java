package yin.style.baselib.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.ColorInt;
import androidx.annotation.LayoutRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import yin.style.baselib.R;

@SuppressLint("ValidFragment")
public class IOSDialog extends DialogFragment {
    private Activity activity;

    private ViewGroup rootView;
    private TextView tvTitle;
    private TextView tvMessage;
    private TextView tvNegative;
    private TextView tvPositive;
    private LinearLayout llContent;
    private LinearLayout llButton;
    private View vLine;
    private View vLine2;

    private int backgroundColor;

    private CharSequence titleText;
    private int titleColor;
    private boolean titleBold = true;

    private CharSequence messageText;
    private int messageColor;
    private boolean messageBold = false;

    private CharSequence positiveText;
    private int positiveColor;
    private boolean positiveBold = false;
    private OnClickListener positiveListener;

    private CharSequence negativeText;
    private int negativeColor;
    private boolean negativeBold = false;
    private OnClickListener negativeListener;

    private View contentView;

    public IOSDialog(Activity activity) {
        super();
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_fragment_dialog, container, false);

        init(view);
        setView();
        return view;
    }

    private void init(View view) {
        rootView = view.findViewById(R.id.ll_dialog_root);
        tvTitle = view.findViewById(R.id.tv_dialog_title);
        tvMessage = view.findViewById(R.id.tv_dialog_content);
        tvNegative = view.findViewById(R.id.tv_dialog_left);
        tvPositive = view.findViewById(R.id.tv_dialog_right);
        llContent = view.findViewById(R.id.ll_dialog_content);
        vLine = view.findViewById(R.id.v_dialog_line);
        vLine2 = view.findViewById(R.id.v_dialog_line_);
        llButton = view.findViewById(R.id.ll_dialog_button);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);       //设置actionbar的隐藏
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    public void show() {
        if (!isVisible()) {
            show(activity.getFragmentManager(), "iosDialog");
            setView();
        }
    }

    private void setView() {
        if (tvTitle == null)
            return;
        if (backgroundColor != 0)
            rootView.setBackgroundColor(backgroundColor);

        //title
        if (TextUtils.isEmpty(titleText)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setVisibility(View.VISIBLE);
            //内容
            tvTitle.setText(titleText);
            //颜色
            if (titleColor != 0)
                tvTitle.setTextColor(titleColor);
            //加粗
            tvTitle.setTypeface(Typeface.defaultFromStyle(titleBold ? Typeface.BOLD : Typeface.NORMAL));
        }

        //内容
        if (contentView == null) {
            if (TextUtils.isEmpty(messageText)) {
                tvMessage.setVisibility(View.GONE);
            } else {
                tvMessage.setVisibility(View.VISIBLE);
                //内容
                tvMessage.setText(messageText);
                //颜色
                if (messageColor != 0)
                    tvMessage.setTextColor(messageColor);
                //加粗
                tvMessage.setTypeface(Typeface.defaultFromStyle(messageBold ? Typeface.BOLD : Typeface.NORMAL));
            }
        } else {
            tvMessage.setVisibility(View.GONE);
            llContent.addView(contentView);
        }

        //左面按钮
        if (TextUtils.isEmpty(negativeText)) {
            tvNegative.setVisibility(View.GONE);
        } else {
            tvNegative.setVisibility(View.VISIBLE);
            //内容
            tvNegative.setText(negativeText);
            //颜色
            if (negativeColor != 0)
                tvNegative.setTextColor(negativeColor);
            //加粗
            tvNegative.setTypeface(Typeface.defaultFromStyle(negativeBold ? Typeface.BOLD : Typeface.NORMAL));

            tvNegative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (negativeListener == null || !negativeListener.onClick(getDialog(), view)) {
                        dismiss();
                    }
                }
            });
        }

        //右边按钮
        if (TextUtils.isEmpty(positiveText)) {
            tvPositive.setVisibility(View.GONE);
        } else {
            tvPositive.setVisibility(View.VISIBLE);
            //内容
            tvPositive.setText(positiveText);
            //颜色
            if (positiveColor != 0)
                tvPositive.setTextColor(positiveColor);
            //加粗
            tvPositive.setTypeface(Typeface.defaultFromStyle(positiveBold ? Typeface.BOLD : Typeface.NORMAL));

            tvPositive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (positiveListener == null || !positiveListener.onClick(getDialog(), view)) {
                        dismiss();
                    }
                }
            });
        }

        vLine.setVisibility(View.GONE);
        vLine2.setVisibility(View.VISIBLE);
        if (tvPositive.getVisibility() == View.VISIBLE && tvNegative.getVisibility() == View.VISIBLE) {
            llButton.setVisibility(View.VISIBLE);
            vLine.setVisibility(View.VISIBLE);
        } else if (tvPositive.getVisibility() == View.GONE && tvNegative.getVisibility() == View.GONE) {
            llButton.setVisibility(View.GONE);
            vLine2.setVisibility(View.GONE);
        }
    }


    /**
     * 设置DialogTitle
     *
     * @param title    文字内容
     * @param colorRes 字体颜色
     * @param bold     是否加粗
     * @return
     */

    public IOSDialog setTitle(CharSequence title, @ColorInt int colorRes, boolean bold) {
        titleText = title;
        titleColor = colorRes;
        titleBold = bold;
        return this;
    }

    public IOSDialog setTitle(CharSequence title) {
        return setTitle(title, titleColor, titleBold);
    }


    /**
     * 设置内容文字
     *
     * @param message  文字内容
     * @param colorRes 字体颜色
     * @param bold     是否加粗
     * @return
     */
    public IOSDialog setMessage(CharSequence message, @ColorInt int colorRes, boolean bold) {
        messageText = message;
        messageColor = colorRes;
        messageBold = bold;
        return this;
    }

    public IOSDialog setMessage(CharSequence message) {
        return setMessage(message, messageColor, messageBold);
    }

    /**
     * 自定义ContentView 有值时，不显示ContentText内容
     *
     * @param view
     * @return
     */
    public IOSDialog setContentView(View view) {
        contentView = view;
        return this;
    }

    public IOSDialog setContentView(@LayoutRes int layoutResID, ViewListener viewListener) {
        contentView = View.inflate(activity, layoutResID, null);
        if (viewListener != null)
            viewListener.viewHold(contentView);
        return this;
    }

    /**
     * PositiveButton
     *
     * @param text
     * @param colorRes
     * @param bold
     * @param listener
     * @return
     */
    public IOSDialog setPositiveButton(CharSequence text, @ColorInt int colorRes, boolean bold, OnClickListener listener) {
        positiveText = text;
        positiveColor = colorRes;
        positiveBold = bold;
        positiveListener = listener;
        return this;
    }

    public IOSDialog setPositiveButton(CharSequence text, @ColorInt int colorRes, OnClickListener listener) {
        return setPositiveButton(text, colorRes, positiveBold, listener);
    }

    public IOSDialog setPositiveButton(CharSequence text, OnClickListener listener) {
        return setPositiveButton(text, positiveColor, positiveBold, listener);
    }

    /**
     * NegativeButton
     *
     * @param text
     * @param colorRes
     * @param bold
     * @param listener
     * @return
     */
    public IOSDialog setNegativeButton(CharSequence text, @ColorInt int colorRes, boolean bold, OnClickListener listener) {
        negativeText = text;
        negativeColor = colorRes;
        negativeBold = bold;
        negativeListener = listener;
        return this;
    }

    public IOSDialog setNegativeButton(CharSequence text, @ColorInt int colorRes, OnClickListener listener) {
        return setNegativeButton(text, colorRes, negativeBold, listener);
    }

    public IOSDialog setNegativeButton(CharSequence text, OnClickListener listener) {
        return setNegativeButton(text, negativeColor, negativeBold, listener);
    }

    /**
     * 设置背景框
     *
     * @param backgroundColor
     */
    public void setBackground(@ColorInt int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    //    @Override
//    public void onDestroy() {
//        if (isVisible())
//            dismiss();
//        super.onDestroy();
//    }

    public interface ViewListener {
        void viewHold(View view);
    }

    public interface OnClickListener {
        boolean onClick(Dialog dialog, View view);
    }
}
