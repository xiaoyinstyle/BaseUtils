package com.jskingen.baseutils.baseactivity;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jskingen.baselib.permission.OnPermissionsListener;
import com.jskingen.baselib.permission.XPermission;
import com.jskingen.baselib.utils.ToastUtils;
import com.jskingen.baseutils.R;

/**
 * Created by chenY on 2017/1/17
 */
public class TabFragment extends Fragment {
    private TextView textView;
    private TextView button;

    public static TabFragment newInstance(int index) {
        Bundle bundle = new Bundle();
        bundle.putInt("index", 'A' + index);
        TabFragment fragment = new TabFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment, null);
        textView = (TextView) view.findViewById(R.id.text);
        button = (Button) view.findViewById(R.id.button);
        textView.setText(String.valueOf((char) getArguments().getInt("index")));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPermissions();
            }
        });
        return view;
    }

    private String[] PERMISSIONS = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private void getPermissions() {
        XPermission.getPermissions(getActivity(), PERMISSIONS,  new OnPermissionsListener() {
            @Override
            public void result(String[] permissions) {
                ToastUtils.show("未获取权限个数：" + permissions.length);
            }
        });
    }
}
