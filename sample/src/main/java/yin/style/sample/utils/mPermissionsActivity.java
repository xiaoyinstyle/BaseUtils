package yin.style.sample.utils;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import yin.style.baselib.activity.base.TitleActivity;
import yin.style.baselib.permission.OnPermissionsListener;
import yin.style.baselib.permission.XPermission;
import yin.style.baselib.utils.ToastUtils;
import yin.style.sample.R;

import butterknife.BindView;
import butterknife.OnClick;

public class mPermissionsActivity extends TitleActivity {
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.text)
    TextView textView;

    //    private String[] PERMISSIONS = new String[]{XPermission.CAMERA, XPermission.LOCATION_GROUP, XPermission.STORAGE_GROUP};
//    private String[] PERMISSIONS = new String[]{XPermission.CAMERA};
//    private String[] PERMISSIONS = new String[]{XPermission.LOCATION_GPS};
    private String[] PERMISSIONS = new String[]{XPermission.LOCATION_GPS};
    private String[] PERMISSIONS2 = new String[]{XPermission.LOCATION_GPS, XPermission.LOCATION_NET};

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
                XPermission.init(mContext)
                        .setPermissions(PERMISSIONS)
//                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                        .setPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .showDialog(isShowDialog, true)
                        .get(new OnPermissionsListener() {
                            @Override
                            public void missPermission(String[] permissions) {
                                ToastUtils.show("未获取权限个数：" + permissions.length);
                            }
                        });

                break;
            case R.id.getList:
                String[] str = XPermission.lacksPermissions(mContext, PERMISSIONS2);
                String s = "";
                for (int i = 0; i < str.length; i++) {
                    s += str[i] + "\n";
                }
                textView.setText(TextUtils.isEmpty(s) ? "无" : s);
                break;
        }
    }
}
