package yin.style.sample.baseActivity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import yin.style.baselib.utils.LogUtils;
import yin.style.sample.R;

/**
 * Created by chenY on 2017/1/17
 */
public class TabTempFragment extends Fragment {
    View decorView;

    private TextView textView;
    private TextView button;

    String a;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        decorView = (ViewGroup) inflater.inflate(R.layout.tab_fragment2, container, false);
        initView(savedInstanceState);
        initData();
        return decorView;
    }

    public static TabTempFragment newInstance(int index) {
        Bundle bundle = new Bundle();
        bundle.putInt("index", 'A' + index);
        TabTempFragment fragment = new TabTempFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    protected void initView(Bundle savedInstanceState) {
        textView = (TextView) decorView.findViewById(R.id.text);
        a = String.valueOf((char) getArguments().getInt("index"));
        textView.setText(a);
    }

    protected void initData() {
        LogUtils.e("AAA", "initData--" + a);
    }
}
