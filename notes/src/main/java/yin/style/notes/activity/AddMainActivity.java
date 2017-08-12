package yin.style.notes.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jskingen.baselib.activity.base.TitleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yin.style.notes.R;

public class AddMainActivity extends TitleActivity {
    @BindView(R.id.et_addMain_project_name)
    EditText etAddMainProjectName;
    @BindView(R.id.et_addMain_user_name)
    EditText etAddMainUserName;
    @BindView(R.id.tv_addMain_data_contract)
    TextView tvAddMainDataContract;
    @BindView(R.id.tv_addMain_data_start)
    TextView tvAddMainDataStart;
    @BindView(R.id.tv_addMain_data_end)
    TextView tvAddMainDataEnd;
    @BindView(R.id.et_addMain_remarks)
    EditText etAddMainRemarks;

    private int mainId;

    private String projectName;

    @Override
    protected void setTitle() {
        mainId = getIntent().getIntExtra("mainId", 0);
        if (mainId == 0)
            title.setText("创建新项目");
        else
            title.setText("修改项目");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("保存");
    }

    @Override
    protected int getViewByXml() {
        return R.layout.activity_add_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_addMain_data_contract, R.id.tv_addMain_data_start, R.id.tv_addMain_data_end})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_addMain_data_contract:
                break;
            case R.id.tv_addMain_data_start:
                break;
            case R.id.tv_addMain_data_end:
                break;
        }
    }
}
