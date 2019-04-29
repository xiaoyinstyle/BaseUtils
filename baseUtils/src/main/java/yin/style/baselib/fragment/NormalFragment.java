package yin.style.baselib.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import yin.style.baselib.BaseHelp;
import yin.style.baselib.R;
import yin.style.baselib.activity.dialog.IDialog;
import yin.style.baselib.activity.utils.NetViewUtils;
import yin.style.baselib.activity.view.StatusBarView;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import yin.style.baselib.utils.net.NetUtils;
import yin.style.baselib.utils.net.NetworkChangeEvent;

/**
 * Created by ChneY on 2017/5/6.
 */

public abstract class NormalFragment extends Fragment {
    protected final String TAG = "LOG_Fra";

    private Unbinder unbinder;
    protected Activity mContext;
    protected LinearLayout rootView;
    protected StatusBarView statusBarView;

    protected boolean hasLoad;
    protected boolean hasInit;

    protected NetViewUtils netViewUtils;

    private IDialog iDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();

        iDialog = BaseHelp.getInstance().getIDialog(mContext);

        //动态加载content
        ViewGroup decorView = (ViewGroup) inflater.inflate(R.layout.base_activity, container, false);
        rootView = (LinearLayout) decorView.findViewById(R.id.base_root);
        statusBarView = decorView.findViewById(R.id.base_status_bar);

        //初始化 网络状态显示View
        netViewUtils = new NetViewUtils();
        if (setCheckNetWork())
            netViewUtils.initTipView(mContext);//初始化提示View

        addTitleLayout(rootView);//加载Title布局

        View view = View.inflate(getContext(), getViewByXml(), null);
        rootView.addView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        unbinder = ButterKnife.bind(this, rootView);

        hasInit = true;

        if (!setLazy()) {
            init(savedInstanceState); //设置数据
        }
        if (setLazy() && getUserVisibleHint()) {
            init(savedInstanceState); //设置数据
        }
        return rootView;
    }

    //懒加载
    private void init(Bundle savedInstanceState) {
        //默认不加载EventBus
        if (setEventBus())
            EventBus.getDefault().register(this);

        hasLoad = true;
        initView(savedInstanceState);   //初始化布局
        initData(); //设置数据
    }

    protected void addTitleLayout(ViewGroup rootView) {

    }

    protected abstract int getViewByXml();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initData();

    @SuppressLint("ResourceType")
    @Nullable
    public final View findViewById(@IdRes int id) {
        if (id <= 0) {
            return null;
        }
        return rootView.findViewById(id);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        //默认不加载EventBus
        if (setEventBus())
            EventBus.getDefault().unregister(this);

        //当提示View被动态添加后直接关闭页面会导致该View内存溢出，所以需要在finish时移除
        if (netViewUtils != null)
            netViewUtils.finish();

        //dialog
        if (iDialog != null)
            iDialog.destroy();
    }

    protected boolean setEventBus() {
        return BaseHelp.getInstance().setEventBus();
    }

    /**
     * 在这里实现Fragment数据的缓加载.
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            if (setLazy() && hasInit && !hasLoad) {
                init(null);
            } else if (setDiligent() && hasLoad) {
                initData();
            }
        }
    }

    /**
     * 默认开启懒加载
     *
     * @return
     */
    protected boolean setLazy() {
        return true;
    }

    /**
     * 勤快加载，与VIewPager配合使用 生效。只会刷新 initData（）
     *
     * @return
     */
    protected boolean setDiligent() {
        return false;
    }

    //关闭键盘
    protected final void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mContext.getWindow().getDecorView().getWindowToken(), 0);
    }

    /**
     * 设置沉浸式
     */
    public boolean setStatusBarView(Activity activity, boolean isShowStatus) {
        return statusBarView.setStatusBarView(activity, isShowStatus);
    }

    /**
     * 设置沉浸式 字体颜色
     */
    public boolean setStatusBarText(Activity activity, boolean barTextDark) {
        return statusBarView.setStatusBarText(activity, barTextDark);
    }

    @Override
    public void onResume() {
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



    //代理模式下的 Dialog
    public IDialog getDialog() {
        return iDialog;
    }

    public void setIDialog(IDialog iNormalDialog) {
        this.iDialog = iNormalDialog;
    }

    public void showDialog() {
        if (iDialog != null)
            iDialog.showDialog();
    }

    public void dismissDialog() {
        if (iDialog != null)
            iDialog.dismissDialog();
    }
}