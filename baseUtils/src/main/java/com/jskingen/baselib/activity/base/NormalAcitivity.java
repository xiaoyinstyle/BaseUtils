package com.jskingen.baselib.activity.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.jskingen.baselib.R;
import com.jskingen.baselib.utils.AppManager;
import com.jskingen.baselib.utils.LogUtils;

import butterknife.ButterKnife;

/**
 * Created by ChneY on 2017/4/22.
 * <p>
 * 无标题的基础底层的 activity
 */

public abstract class NormalAcitivity extends AppCompatActivity {
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);
        if (!removeAppManager())
            AppManager.getInstance().addActivity(this);//Activity 管理器

        //动态加载content
        LinearLayout ll_root = (LinearLayout) super.findViewById(R.id.base_root);
        addTitleLayout(ll_root);//加载Title布局
        view = View.inflate(this, getViewByXml(), null);
        ll_root.addView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

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

    protected void addTitleLayout(LinearLayout root) {
    }

    protected abstract int getViewByXml();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initData();

    @Nullable
    public View findViewById(@IdRes int id) {
        if (id < 0) {
            return null;
        }
        return view.findViewById(id);
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
}
