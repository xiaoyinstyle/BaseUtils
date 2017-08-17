package yin.style.notes.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.jskingen.baselib.activity.base.TitleActivity;
import com.jskingen.baselib.utils.RxBus;
import com.jskingen.baselib.utils.ToastUtils;
import com.jskingen.baselib.view.LoadingDialog;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yin.style.notes.R;
import yin.style.notes.dao.RealmHelper;
import yin.style.notes.model.ProjectBean;
import yin.style.notes.utils.DateUtil;
import yin.style.notes.utils.KeyBoardUtils;

public class AddProjectsActivity extends TitleActivity {

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
    @BindView(R.id.rb_flag_1)
    RadioButton rbFlag1;
    @BindView(R.id.rb_flag_2)
    RadioButton rbFlag2;
    @BindView(R.id.rb_flag_3)
    RadioButton rbFlag3;
    @BindView(R.id.et_addMain_budget)
    EditText etAddMainBudget;

    private String projectName;//项目名称
    private String projectUser;//甲方
    private String projectBudget;//预算
    private int projectFlag;//当前状态
    private String projectRemarks;//备注

    private TimePickerView tpvCreate;//合同日期
    private TimePickerView tpvStart;//开工时间
    private TimePickerView tpvEnd;//交工时间
    private Date createTime;//合同日期
    private Date startTime;//开工时间
    private Date endTime;//交工时间

    private ProjectBean projectBean;
    private LoadingDialog dialog;
    private long projectsId;//项目的 Id

    @Override
    protected void setTitle() {
        projectsId = getIntent().getLongExtra("projectsId", -1);
        if (projectsId == -1)
            title.setText("创建新项目");
        else
            title.setText("修改项目");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("保存");
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtils.closeKeybord(mContext, tv_right);
                saveProjects();
            }
        });
    }


    @Override
    protected int getViewByXml() {
        return R.layout.activity_add_projects;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //合同时间选择器
        tpvCreate = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                createTime = date;
                tvAddMainDataContract.setText(DateUtil.date2Str(createTime, "yyyy-MM-dd"));
            }
        }).setTitleText("合同日期")
                .setType(new boolean[]{true, true, true, false, false, false})
                .build();
        tpvCreate.setDate(Calendar.getInstance());

        //开工时间选择器
        tpvStart = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                startTime = date;
                tvAddMainDataStart.setText(DateUtil.date2Str(startTime, "yyyy-MM-dd"));
            }
        }).setTitleText("开工日期")
                .setType(new boolean[]{true, true, true, false, false, false})
                .build();
        tpvStart.setDate(Calendar.getInstance());

        //交工时间选择器
        tpvEnd = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                endTime = date;
                tvAddMainDataEnd.setText(DateUtil.date2Str(endTime, "yyyy-MM-dd"));
            }
        }).setTitleText("交工日期")
                .setType(new boolean[]{true, true, true, false, false, false})
                .build();
        tpvEnd.setDate(Calendar.getInstance());


        dialog = new LoadingDialog.Builder(this)
                .setMessage("保存中..")
                .setTextColor(Color.WHITE)
                .setBackground(R.drawable.progress_custom_bg_black)
                .setCancelable(false)
                .create();
    }

    @Override
    protected void initData() {
        if (projectsId != -1) {
            projectBean = RealmHelper.getInstance().findProject(projectsId);

            projectName = TextUtils.isEmpty(projectBean.getProjects()) ? "" : projectBean.getProjects();
            projectUser = TextUtils.isEmpty(projectBean.getFirstParty()) ? "" : projectBean.getFirstParty();
            projectRemarks = TextUtils.isEmpty(projectBean.getRemarks()) ? "" : projectBean.getRemarks();
            projectBudget = projectBean.getBudget() + "";
            projectFlag = projectBean.getFlag();
            startTime = projectBean.getStartTime();
            createTime = projectBean.getCreateTime();
            endTime = projectBean.getEndTime();

            etAddMainProjectName.setText(projectName);
            etAddMainUserName.setText(projectUser);
            etAddMainBudget.setText(projectBudget);

            switch (projectFlag) {
                case ProjectBean.PROJECTS_FLAG_START:
                    rbFlag1.setChecked(true);
                    break;
                case ProjectBean.PROJECTS_FLAG_RUNNING:
                    rbFlag2.setChecked(true);
                    break;
                case ProjectBean.PROJECTS_FLAG_END:
                    rbFlag3.setChecked(true);
                    break;
            }

            if (startTime != null)
                tvAddMainDataStart.setText(DateUtil.date2Str(startTime, "yyyy-MM-dd"));
            if (createTime != null)
                tvAddMainDataContract.setText(DateUtil.date2Str(createTime, "yyyy-MM-dd"));
            if (endTime != null)
                tvAddMainDataEnd.setText(DateUtil.date2Str(endTime, "yyyy-MM-dd"));

            etAddMainRemarks.setText(projectRemarks);
        }
    }

    @OnClick({R.id.ll_root, R.id.tv_addMain_data_contract, R.id.iv_addMain_clear_create, R.id.tv_addMain_data_start,
            R.id.iv_addMain_clear_start, R.id.tv_addMain_data_end, R.id.iv_addMain_clear_end,
            R.id.rb_flag_1, R.id.rb_flag_2, R.id.rb_flag_3})
    public void onViewClicked(View view) {
        KeyBoardUtils.closeKeybord(mContext, tv_right);
        switch (view.getId()) {
            case R.id.tv_addMain_data_contract:
                tpvCreate.show();
                break;
            case R.id.tv_addMain_data_start:
                tpvStart.show();
                break;
            case R.id.tv_addMain_data_end:
                tpvEnd.show();
                break;
            case R.id.iv_addMain_clear_create:
                createTime = null;
                tvAddMainDataContract.setText("");
                break;
            case R.id.iv_addMain_clear_start:
                startTime = null;
                tvAddMainDataStart.setText("");
                break;
            case R.id.iv_addMain_clear_end:
                endTime = null;
                tvAddMainDataEnd.setText("");
                break;
            case R.id.rb_flag_1:
                projectFlag = ProjectBean.PROJECTS_FLAG_START;
                break;
            case R.id.rb_flag_2:
                projectFlag = ProjectBean.PROJECTS_FLAG_RUNNING;
                break;
            case R.id.rb_flag_3:
                projectFlag = ProjectBean.PROJECTS_FLAG_END;
                break;
        }
    }

    /**
     * 保存项目
     */
    private void saveProjects() {
        projectName = etAddMainProjectName.getText().toString().trim();
        projectUser = etAddMainUserName.getText().toString().trim();
        projectRemarks = etAddMainRemarks.getText().toString().trim();
        projectBudget = etAddMainBudget.getText().toString().trim();

        if (projectName.isEmpty()) {
            ToastUtils.show("请输入项目名称");
        } else if (projectFlag == 0) {
            ToastUtils.show("请选择项目当前状态");
        } else if (projectUser.isEmpty()) {
            ToastUtils.show("请输入甲方代表姓名");
        } else if (projectBudget.isEmpty()) {
            ToastUtils.show("请输入预算金额");
        } else if (createTime == null) {
            ToastUtils.show("请选择合同签订日期");
        } else {
            dialog.show();

            float budget = Float.parseFloat(projectBudget);

            if (projectBean == null)
                projectBean = new ProjectBean();
            projectBean.setProjects(projectName);
            projectBean.setFlag(projectFlag);
            projectBean.setFirstParty(projectUser);
            projectBean.setBudget(budget);
            projectBean.setCreateTime(createTime);
            projectBean.setStartTime(startTime);
            projectBean.setEndTime(endTime);
            projectBean.setRemarks(projectRemarks);

            //添加或者 更新 数据库
            if (projectsId == -1) {
//                for (int i = 0; i < 30; i++) {
//                    projectBean.setProjects(projectName + "_" + i);
//                    RealmHelper.getInstance().addProjects(projectBean);
//                }
                RealmHelper.getInstance().addProjects(projectBean);
                projectBean.setUpdateData(false);
            } else {
                RealmHelper.getInstance().updateProjects(projectBean);
                projectBean.setUpdateData(true);
            }
            RxBus.getInstance().post(projectBean);

            //增加用户体验想延时
            etAddMainProjectName.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.show("保存成功");
                    dialog.hide();
//                    setResult();
                    finish();
                }
            }, 2000);

        }
    }
}
