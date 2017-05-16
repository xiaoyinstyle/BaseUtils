package com.jskingen.baseutils.utils;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jskingen.baselib.activity.base.TitleActivity;
import com.jskingen.baselib.utils.PermissionUtil;
import com.jskingen.baselib.utils.ToastUtils;
import com.jskingen.baseutils.R;

import butterknife.BindView;
import butterknife.OnClick;

public class mPermissionsActivity extends TitleActivity {
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.text)
    TextView textView;

    private String[] PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void setTitle() {
        title.setText("权限管理");
    }

    @Override
    protected int getViewByXml() {
        return R.layout.activity_m_permissions;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.getPermissions, R.id.openDialog, R.id.getList})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.getPermissions:
                boolean isShowDialog = ((CheckBox) findViewById(R.id.checkbox)).isChecked();

                PermissionUtil.getInstance().getPermissions(mPermissionsActivity.this, isShowDialog, PERMISSIONS, new PermissionUtil.onRequestPermissionsListener() {
                    @Override
                    public void result(boolean hasRequest, String[] permissions) {
                        if (hasRequest) {
                            textView.setText("已有获取到所有权限");
                        } else {
                            ToastUtils.show("权限获取失败,程序无法正常运行");
                            textView.setText("权限不全");
                            ToastUtils.show("未获取权限个数：" + permissions.length);
                        }
                    }
                });
                break;
            case R.id.openDialog:
                PermissionUtil.getInstance().showDialog(mPermissionsActivity.this, new PermissionUtil.onPermissionsDialogListener() {
                    @Override
                    public void result(boolean isOpen) {
                        if (isOpen) {
                            textView.setText("进入设置界面，并返回了");
                            ToastUtils.show("未获取权限个数：" + PermissionUtil.getInstance().lacksPermissions(mPermissionsActivity.this, PERMISSIONS).length);
                        } else
                            textView.setText("取消弹框");
                    }
                });
                break;
            case R.id.getList:
                String[] str = PermissionUtil.getInstance().lacksPermissions(mPermissionsActivity.this, PERMISSIONS);
                String s = "";
                for (int i = 0; i < str.length; i++) {
                    s += str[i] + "\n";
                }
                textView.setText(s);
                break;
        }
    }
}
