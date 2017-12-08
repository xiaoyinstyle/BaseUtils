package yin.style.sample.utils.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import yin.style.baselib.view.refreshView.RefreshHeader;
import yin.style.baselib.view.refreshView.RefreshLayout;
import com.jskingen.baseutils.R;

/**
 * Created by Chne on 2017/7/31.
 */

public class HeaderView extends LinearLayout implements RefreshHeader {
    private ImageView imageView;
    private Context mContext;

    public HeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    public HeaderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeaderView(Context context) {
        this(context, null);
    }

    private void init() {
        imageView = new ImageView(mContext);
        setGravity(Gravity.CENTER);
        addView(imageView);
    }

    @Override
    public void reset() {
        Log.e("AAA", "reset");
    }

    @Override
    public void pull() {
        imageView.setImageResource(R.mipmap.ic_launcher);
        Log.e("AAA", "pull");

    }

    @Override
    public void refreshing() {
        imageView.setImageResource(R.mipmap.ic_launcher_round);
        Log.e("AAA", "refreshing");
    }

    @Override
    public void onPositionChange(float currentPos, float lastPos, float refreshPos, boolean isTouch, RefreshLayout.State state) {
        Log.e("AA", "onPositionChange");
    }

    @Override
    public void complete() {

        Log.e("AAA", "complete");
    }
}
