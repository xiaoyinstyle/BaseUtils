package yin.style.baselib.activity.base;

import android.annotation.SuppressLint;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import yin.style.baselib.BaseHelp;
import yin.style.baselib.R;
import yin.style.baselib.activity.utils.NetViewUtils;
import yin.style.baselib.activity.view.StatusBarView;
import yin.style.baselib.utils.AppManager;

import butterknife.ButterKnife;
import yin.style.baselib.utils.net.NetUtils;
import yin.style.baselib.utils.net.NetworkChangeEvent;

/**
 * Created by ChneY on 2017/4/22.
 * <p>
 * 无标题的基础底层的 activity
 */

public abstract class NormalActivity extends AppCompatActivity {
    protected final String TAG = "LOG_ACT";
    protected ViewGroup rootView;
    protected StatusBarView statusBarView;

    protected Activity mContext;

    protected NetViewUtils netViewUtils;

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
        statusBarView = super.findViewById(R.id.base_status_bar);
        rootView.setBackgroundResource(R.color.base_layout_background);

        //初始化 网络状态显示View
        netViewUtils = new NetViewUtils();
        if (setCheckNetWork())
            netViewUtils.initTipView(this);//初始化提示View


        addTitleLayout(rootView);//加载Title布局
        setStatusView();  //设置沉浸式

        View view = View.inflate(this, getViewByXml(), null);
        rootView.addView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        ButterKnife.bind(this);
        initView(savedInstanceState);   //初始化布局
        initData(); //设置数据


        //默认不加载EventBus
        if (setEventBus())
            EventBus.getDefault().register(this);
    }

    /**
     * @return 不进入 Appmanager 管理
     */
    protected boolean removeAppManager() {
        return false;
    }

    protected void addTitleLayout(ViewGroup rootView) {

    }

    /**
     * 设置沉浸式
     */
    private void setStatusView() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            return;
        }

        statusBarView.setStatusBarView(mContext, true, getResources().getColor(R.color.colorPrimaryDark), false);
    }

    protected abstract int getViewByXml();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initData();

    @SuppressLint("ResourceType")
    @Nullable
    public final <T extends View> T findViewById(@IdRes int id) {
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

        //默认不加载EventBus
        if (setEventBus())
            EventBus.getDefault().unregister(this);

        //当提示View被动态添加后直接关闭页面会导致该View内存溢出，所以需要在finish时移除
        if (netViewUtils != null)
            netViewUtils.finish();
    }

    protected boolean setEventBus() {
        return BaseHelp.getInstance().setEventBus();
    }

    //关闭键盘
    protected final void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    /**
     * 设置沉浸式
     */
    public boolean setStatusBarView(Activity activity, boolean isShowStatus, int statusBarColor, boolean barTextDark) {
        return statusBarView.setStatusBarView(activity, isShowStatus, statusBarColor, barTextDark);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在无网络情况下打开APP时，系统不会发送网络状况变更的Intent，需要自己手动检查
        netViewUtils.hasNetWork(NetUtils.isConnected(mContext), setCheckNetWork());
    }

    /**
     * 网络状态View
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkChangeEvent(NetworkChangeEvent event) {
        netViewUtils.hasNetWork(event.isConnected, setCheckNetWork());
        if (event.isConnected)
            initData();
    }

    protected boolean setCheckNetWork() {
        return BaseHelp.getInstance().isCheckNetWork();
    }

    protected void setNetViewTop(int top) {
        netViewUtils.setNetViewTop(top);
    }

    protected View getNetView() {
        return netViewUtils.getNetView();
    }
}
