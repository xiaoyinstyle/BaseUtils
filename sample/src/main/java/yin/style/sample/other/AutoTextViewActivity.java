package yin.style.sample.other;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import yin.style.baselib.activity.base.TitleActivity;
import yin.style.sample.R;

public class AutoTextViewActivity extends TitleActivity implements View.OnClickListener {
    private static final String TAG = AutoTextViewActivity.class.getSimpleName();
    private AppCompatTextView mAutoSizeTV;
    private LinearLayout mAutosizeLL;
    private AppCompatTextView mAutoSizeDetails;
    private Button mAdd20dPWidth;
    private Button mReduce20dpWidth;
    private Button mAdd20dPHeight;
    private Button mReduce20dpHeight;
    private Context mContext;

    private Button mToggleAutosizeType;
    private Button mResetAllValues;
    private Button mSetAutosizePresets;

    private Boolean mIsToggleAutosizeType = false;

    private int[] mTextSizePresets = new int[]{20, 24, 30, 36, 40, 44, 50, 100};


    @Override
    protected int getViewByXml() {
        return R.layout.activity_auto_text_view;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mContext = this;

        mAutoSizeTV = (AppCompatTextView) findViewById(R.id.autosize_tv);
        mAdd20dPWidth = (Button) findViewById(R.id.add_20dp_width);
        mReduce20dpWidth = (Button) findViewById(R.id.reduce_20dp_width);
        mAdd20dPHeight = (Button) findViewById(R.id.add_20dp_height);
        mReduce20dpHeight = (Button) findViewById(R.id.reduce_20dp_height);
        mAutoSizeDetails = (AppCompatTextView) findViewById(R.id.autosize_args_details);
        mToggleAutosizeType = (Button) findViewById(R.id.change_autosize_type);
        mResetAllValues = (Button) findViewById(R.id.reset_all_perporties);
        mSetAutosizePresets = (Button) findViewById(R.id.set_autosize_presets);
        mAutosizeLL = (LinearLayout) findViewById(R.id.autosize_ll);

        mReduce20dpWidth.setOnClickListener(this);
        mAdd20dPWidth.setOnClickListener(this);
        mAdd20dPHeight.setOnClickListener(this);
        mReduce20dpHeight.setOnClickListener(this);
        mToggleAutosizeType.setOnClickListener(this);
        mResetAllValues.setOnClickListener(this);
        mSetAutosizePresets.setOnClickListener(this);


    }

    @Override
    protected void initData() {
        displayEditviewDetails();
        Log.d(TAG, "mAutoSizeTV:" + mAutoSizeTV.getTextSize());
        Log.d(TAG, "mAutoSizeDetails:" + mAutoSizeDetails.getTextSize());
        Log.d(TAG, "mAdd20dPWidth:" + mAdd20dPWidth.getTextSize());
        Log.d(TAG, "mReduce20dpWidth:" + mReduce20dpWidth.getTextSize());
//        Log.d(TAG, "autoSizeDefaultEdit:" + autoSizeDefaultEdit.getTextSize());

    }

    @Override
    protected void setTitle() {

    }


    private void changeAutosizeType() {
        if (mIsToggleAutosizeType) {
            mAutoSizeTV.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        } else {
            mAutoSizeTV.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_NONE);
        }
        mIsToggleAutosizeType = !mIsToggleAutosizeType;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_20dp_width:
                add20PXWidth();
                break;
            case R.id.reduce_20dp_width:
                reduce20dpWidth();
                break;
            case R.id.add_20dp_height:
                add20PXHeight();
                break;
            case R.id.reduce_20dp_height:
                reduce20dpHeight();
                break;
            case R.id.change_autosize_type:
                changeAutosizeType();
                break;
            case R.id.reset_all_perporties:
                resetAllValues();
                break;
            case R.id.set_autosize_presets:
                setAutosizePresets();
                break;
            default:
                break;
        }
    }

    private void reduce20dpHeight() {
        Log.d(TAG, "reduce20dpHeight");
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mAutosizeLL.getLayoutParams();
        params.width = mAutosizeLL.getWidth();// + 50 * mAutoSizeTV.getWidth() / mAutoSizeTV.getHeight();
        params.height = mAutosizeLL.getHeight() - 20;

        DisplayMetrics dm = getResources().getDisplayMetrics();
        if (params.height >= dm.heightPixels / 2) {
            params.height = dm.heightPixels / 2;
            showToast("textview的高度为屏幕高度一半，不能再增加了");
        }
        if (params.height <= 0) {
            params.height = 0;
            showToast("textview的高度为0，不能再减小了");
        }

        Log.e(TAG, "width: " + params.width + "; height: " + params.height);
        mAutosizeLL.setLayoutParams(params);
        mAutosizeLL.invalidate();

        displayEditviewDetails();
    }

    private void reduce20dpWidth() {
        Log.d(TAG, "reduce20dpWidth");
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mAutosizeLL.getLayoutParams();
        params.width = mAutosizeLL.getWidth() - 20;
        params.height = mAutosizeLL.getHeight();//+ 50 * mAutoSizeTV.getHeight() / mAutoSizeTV.getWidth();

        DisplayMetrics dm = getResources().getDisplayMetrics();
        if (params.width >= dm.widthPixels) {
            params.width = dm.widthPixels;
            showToast("textview的宽度等于屏幕宽度了，不能再增加了");
        }
        if (params.width <= 0) {
            params.width = 0;
            showToast("textview的宽度为0，不能再减小了");
        }
        Log.d(TAG, "width: " + params.width + "; height: " + params.height);
        Log.d(TAG, "size: " + mAutoSizeTV.getTextSize());
        mAutosizeLL.setLayoutParams(params);
        mAutosizeLL.invalidate();

        displayEditviewDetails();
    }

    private void setAutosizePresets() {
        mAutoSizeTV.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        mAutoSizeTV.setAutoSizeTextTypeUniformWithPresetSizes(mTextSizePresets, TypedValue.COMPLEX_UNIT_SP);
    }

    private void resetAllValues() {
        mAutoSizeTV.setText(getString(R.string.auto_size_context));
        mAutoSizeTV.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        mAutoSizeTV.setAutoSizeTextTypeUniformWithConfiguration(2, 100, 2, TypedValue.COMPLEX_UNIT_SP);
    }


    private void add20PXWidth() {
        Log.d(TAG, "add20PXWidth");
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mAutosizeLL.getLayoutParams();
        params.width = mAutosizeLL.getWidth() + 20;
        params.height = mAutosizeLL.getHeight();//+ 50 * mAutoSizeTV.getHeight() / mAutoSizeTV.getWidth();

        DisplayMetrics dm = getResources().getDisplayMetrics();
        if (params.width >= dm.widthPixels) {
            params.width = dm.widthPixels;
            showToast("textview的宽度等于屏幕宽度了，不能再增加了");
        }
        if (params.width <= 0) {
            params.width = 0;
            showToast("textview的宽度为0，不能再减小了");
        }
        Log.d(TAG, "width: " + params.width + "; height: " + params.height);
        Log.d(TAG, "size: " + mAutoSizeTV.getTextSize());
        mAutosizeLL.setLayoutParams(params);
        mAutosizeLL.invalidate();

        displayEditviewDetails();
    }


    private void add20PXHeight() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mAutosizeLL.getLayoutParams();
        params.width = mAutosizeLL.getWidth();// + 50 * mAutoSizeTV.getWidth() / mAutoSizeTV.getHeight();
        params.height = mAutosizeLL.getHeight() + 20;

        DisplayMetrics dm = getResources().getDisplayMetrics();
        if (params.height >= dm.heightPixels / 2) {
            params.height = dm.heightPixels / 2;
            showToast("textview的高度为屏幕高度一半，不能再增加了");
        }
        if (params.height <= 0) {
            params.height = 0;
            showToast("textview的高度为0，不能再减小了");
        }

        Log.e(TAG, "width: " + params.width + "; height: " + params.height);
        mAutosizeLL.setLayoutParams(params);
        mAutosizeLL.invalidate();

        displayEditviewDetails();

    }

    private void showToast(String toasthint) {
        Toast.makeText(mContext, toasthint, Toast.LENGTH_SHORT).show();
    }

    private void displayEditviewDetails() {
        String details = "";
        details += "TextSize: " + mAutoSizeTV.getTextSize() + "\n";
        details += "autoSizeMinTextSize: " + mAutoSizeTV.getAutoSizeMinTextSize() + "\n";
        details += "AutoSizeMaxTextSize: " + mAutoSizeTV.getAutoSizeMaxTextSize() + "\n";
        details += "AutoSizeStepGranularity: " + mAutoSizeTV.getAutoSizeStepGranularity() + "\n";
        details += "AutoSizeTextType: " + mAutoSizeTV.getAutoSizeTextType() + "\n";
        details += "AutoSizeTextAvailableSizes: " + mAutoSizeTV.getAutoSizeTextAvailableSizes().toString() + "\n";

        mAutoSizeDetails.setText(details);

    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
