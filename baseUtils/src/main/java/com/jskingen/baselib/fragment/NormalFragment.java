package com.jskingen.baselib.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jskingen.baselib.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ChneY on 2017/5/6.
 */

public abstract class NormalFragment extends Fragment {
    private Unbinder unbinder;
    private View view;
    protected Activity mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        //动态加载content
        LinearLayout ll_root = (LinearLayout) inflater.inflate(R.layout.base_activity, container, false);
        addTitleLayout(ll_root);//加载Title布局
        view = View.inflate(getContext(), getViewByXml(), null);
        ll_root.addView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        unbinder = ButterKnife.bind(this, ll_root);

        initView(savedInstanceState);   //初始化布局
        initData(); //设置数据
        return ll_root;
    }

    protected  void addTitleLayout(LinearLayout ll_root){

    };

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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
