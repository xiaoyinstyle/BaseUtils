package yin.style.baselib.activity.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import yin.style.baselib.R;
import yin.style.baselib.utils.AppManager;
import yin.style.baselib.utils.ScreenUtil;
import yin.style.baselib.utils.StatusBarCompat;

import butterknife.ButterKnife;
import yin.style.baselib.utils.StatusBarTextColor;

/**
 * Created by ChneY on 2017/4/22.
 * <p>
 * 无标题的基础底层的 activity
 */

public abstract class NormalActivity extends AppCompatActivity {
    protected final String TAG = "LOG_ACT";
    protected LinearLayout rootView;
    protected View titleView;

    protected Activity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //防止界面被重复打开
        if (!isTaskRoot()) {
            Intent intent = getIntent();
            String action = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)) {
                finish();
                return;
            }
        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.base_activity);
        if (!removeAppManager())
            AppManager.getInstance().addActivity(this);//Activity 管理器
        mContext = this;

        //动态加载content
        rootView = (LinearLayout) super.findViewById(R.id.base_root);
        addTitleLayout(rootView);//加载Title布局
        setStatusView();  //设置沉浸式

        View view = View.inflate(this, getViewByXml(), null);
        rootView.addView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        ButterKnife.bind(this);
        initView(savedInstanceState);   //初始化布局
        initData(); //设置数据
    }

    /**
     * @return 不进入 Appmanager 管理
     */
    protected boolean removeAppManager() {
        return false;
    }

    protected void addTitleLayout(ViewGroup rootView) {
        titleView = View.inflate(mContext, R.layout.base_title, null);
        titleView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        rootView.addView(titleView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        rootView.addView(titleView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    /**
     * 设置沉浸式
     */
    private void setStatusView() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            return;
        }

        setStatusBarView(mContext, true, getResources().getColor(R.color.colorPrimaryDark), false);
    }

    protected abstract int getViewByXml();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initData();

    @Nullable
    public View findViewById(@IdRes int id) {
        if (id <= 0) {
            return null;
        }
        return rootView.findViewById(id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!removeAppManager())
            AppManager.getInstance().finishActivity(this);//Activity 管理器
    }

    //关闭键盘
    protected void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    /**
     * 设置沉浸式
     */
    public boolean setStatusBarView(Activity activity, boolean isShowStatus, int statusBarColor, boolean barTextDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isShowStatus) {
                StatusBarCompat.compat(activity, statusBarColor);
                titleView.setPadding(0, ScreenUtil.getStatusHeight(activity), 0, 0);
            } else {
                titleView.setPadding(0, 0, 0, 0);
                StatusBarCompat.compat(activity, Color.TRANSPARENT);
            }
            StatusBarTextColor.StatusBarLightMode(activity, barTextDark);
            return true;
        } else {
            return false;
        }
    }
}
