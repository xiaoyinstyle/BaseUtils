package yin.style.sample.utils;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import yin.style.baselib.activity.base.TitleActivity;
import yin.style.baselib.permission.OnPermissionsListener;
import yin.style.baselib.permission.XPermission;
import yin.style.baselib.utils.ToastUtils;
import com.jskingen.baseutils.R;

import butterknife.BindView;
import butterknife.OnClick;

public class mPermissionsActivity extends TitleActivity {
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.text)
    TextView textView;

    private String[] PERMISSIONS = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

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

    @OnClick({R.id.getPermissions, R.id.getList})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.getPermissions:
                boolean isShowDialog = ((CheckBox) findViewById(R.id.checkbox)).isChecked();
                XPermission.getPermissions(mPermissionsActivity.this, PERMISSIONS, isShowDialog, false, new OnPermissionsListener() {
                    @Override
                    public void missPermission(String[] permissions) {
                        ToastUtils.show("未获取权限个数：" + permissions.length);
                    }
                });
                break;
            case R.id.getList:
                String[] str = XPermission.lacksPermissions(mPermissionsActivity.this, PERMISSIONS);
                String s = "";
                for (int i = 0; i < str.length; i++) {
                    s += str[i] + "\n";
                }
                textView.setText(s);
                break;
        }
    }
}
