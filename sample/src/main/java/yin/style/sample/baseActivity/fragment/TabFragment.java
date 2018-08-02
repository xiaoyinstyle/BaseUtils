package yin.style.sample.baseActivity.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.Button;
import android.widget.TextView;

import yin.style.baselib.fragment.NormalFragment;
import yin.style.baselib.utils.LogUtils;

import yin.style.sample.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenY on 2017/1/17
 */
public class TabFragment extends NormalFragment {
    @BindView(R.id.bt_change)
    Button btChange;

    private TextView textView;
    private TextView button;

    String a;

    TabFragment2 fragment1;
    TabFragment2 fragment2;

    List<TabFragment2> fragment2s = new ArrayList<>();

    public static TabFragment newInstance(int index) {
        Bundle bundle = new Bundle();
        bundle.putInt("index", 'A' + index);
        TabFragment fragment = new TabFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected int getViewByXml() {
        return R.layout.tab_fragment;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        textView = (TextView) findViewById(R.id.text);
        a = String.valueOf((char) getArguments().getInt("index"));
        textView.setText(a);


//        fragment1 = new TabFragment2();
//        fragment2 = new TabFragment2();


    }

    int index = 0;

    @Override
    protected boolean setDiligent() {
        return true;
    }

    @Override
    protected void initData() {
        LogUtils.e(TAG, getUserVisibleHint() + "_initData--" + a);
    }

    @Override
    protected boolean setLazy() {
        return true;
    }

    @OnClick(R.id.bt_change)
    public void onViewClicked() {
        index++;
        setStatusBarView(mContext, index % 2 == 0);

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        if (fragment1 == null) {
            fragment1 = TabFragment2.newInstance(8);
            transaction.add(R.id.fm_content, fragment1);
        }
        if (fragment2 == null) {
            fragment2 = TabFragment2.newInstance(9);
            transaction.add(R.id.fm_content, fragment2);
        }

        hideFragment(transaction);

        transaction.show(index % 2 == 1 ? fragment1 : fragment2);

        transaction.commit();
    }

    //隐藏所有的fragment
    private void hideFragment(FragmentTransaction transaction) {
        if (fragment1 != null) {
            transaction.hide(fragment1);
        }
        if (fragment2 != null) {
            transaction.hide(fragment2);
        }
    }

    @Override
    protected boolean setEventBus() {
        return true;
    }

    @Override
    protected boolean setCheckNetWork() {
        return true;
    }
}
