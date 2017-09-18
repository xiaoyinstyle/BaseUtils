package com.jskingen.baseutils.http;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.jskingen.baselib.activity.base.TitleActivity;
import com.jskingen.baselib.updata.UpdateAppUtils;
import com.jskingen.baselib.updata.inter.OnUpdateListener;
import com.jskingen.baselib.utils.ToastUtils;
import com.jskingen.baseutils.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class mUpdateActivity extends TitleActivity {
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.checkbox1)
    CheckBox checkbox1;

    @Override
    protected void setTitle() {

    }

    @Override
    protected int getViewByXml() {
        return R.layout.activity_m_updata;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.bt1, R.id.bt2, R.id.bt3, R.id.bt4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt1:
                UpdateAppUtils.from(this)
                        .serverVersionName("2.0")
                        .serverVersionCode(2)
                        .update(new OnUpdateListener() {
                            @Override
                            public void result(boolean needUpdate) {
                                ToastUtils.show(needUpdate + "");
                            }
                        });

                break;
            case R.id.bt2:
                break;
            case R.id.bt3:
                break;
            case R.id.bt4:
                break;
        }
    }
}
