package yin.style.notes.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import yin.style.notes.model.DetailsBean;
import yin.style.notes.utils.DateUtil;
import yin.style.notes.utils.KeyBoardUtils;

public class AddDetailsActivity extends TitleActivity {

    @BindView(R.id.et_addDetails_content)
    EditText etAddDetailsContent;
    @BindView(R.id.et_addDetails_money)
    EditText etAddDetailsMoney;
    @BindView(R.id.rb_addDetails_flag_1)
    RadioButton rbAddDetailsFlag1;
    @BindView(R.id.rb_addDetails_flag_2)
    RadioButton rbAddDetailsFlag2;
    @BindView(R.id.rb_addDetails_flag_3)
    RadioButton rbAddDetailsFlag3;
    @BindView(R.id.rb_addDetails_flag_4)
    RadioButton rbAddDetailsFlag4;
    @BindView(R.id.et_addDetails_worker)
    EditText etAddDetailsWorker;
    @BindView(R.id.ll_addDetails_worker)
    LinearLayout llAddDetailsWorker;
    @BindView(R.id.tv_addDetails_date)
    TextView tvAddDetailsDate;
    @BindView(R.id.et_addDetails_remarks)
    EditText etAddDetailsRemarks;

    private long projectsId;//项目 id
    private long detailsId; //明细 id
    private LoadingDialog dialog;

    private TimePickerView tpvDate;//日期选择

    private String detailsContent;//详情
    private String detailsMoneys;//金额
    private int detailsFlag;//类别
    private Date detailsDate;//日期
    private String detailsWorker;//工人姓名
    private String detailsRemarks;//备注

    @Override
    protected void setTitle() {
        projectsId = getIntent().getLongExtra("projectsId", -1);
        detailsId = getIntent().getLongExtra("detailsId", -1);
        if (detailsId == -1)
            title.setText("创建明细");
        else
            title.setText("修改明细");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("保存");
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtils.closeKeybord(mContext, tv_right);
                saveDetails();
            }
        });
    }

    @Override
    protected int getViewByXml() {
        return R.layout.activity_add_details;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //合同时间选择器
        tpvDate = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                detailsDate = date;
                tvAddDetailsDate.setText(DateUtil.date2Str(detailsDate, "yyyy-MM-dd"));
            }
        }).setTitleText("合同日期")
                .setType(new boolean[]{true, true, true, false, false, false})
                .build();
        tpvDate.setDate(Calendar.getInstance());


        dialog = new LoadingDialog.Builder(this)
                .setMessage("保存中..")
                .setTextColor(Color.WHITE)
                .setBackground(R.drawable.progress_custom_bg_black)
                .setCancelable(false)
                .create();
    }

    @Override
    protected void initData() {
        llAddDetailsWorker.setVisibility(View.GONE);
        if (detailsId != -1) {
            detailsBean = RealmHelper.getInstance().finddDetails(detailsId);

            detailsContent = detailsBean.getContent();
            detailsMoneys = detailsBean.getMoney() + "";
            detailsWorker = detailsBean.getWorker();
            detailsRemarks = detailsBean.getRemarks();
            detailsDate = detailsBean.getTime();
            detailsFlag = detailsBean.getFlag();

            detailsWorker = detailsWorker == null ? "" : detailsWorker;

            etAddDetailsContent.setText(detailsContent);
            etAddDetailsMoney.setText(detailsMoneys);
            etAddDetailsWorker.setText(detailsWorker);
            etAddDetailsRemarks.setText(detailsRemarks);
            tvAddDetailsDate.setText(DateUtil.date2Str(detailsDate, "yyyy-MM-dd"));

            switch (detailsBean.getFlag()) {
                case DetailsBean.DETAILS_FLAG_RECEIPT:
//                    type = "收款";
                    rbAddDetailsFlag1.setChecked(true);
                    llAddDetailsWorker.setVisibility(View.GONE);
                    break;
                case DetailsBean.DETAILS_FLAG_MATERIAL:
//                    type = "材料";
                    rbAddDetailsFlag2.setChecked(true);
                    llAddDetailsWorker.setVisibility(View.GONE);
                    break;
                case DetailsBean.DETAILS_FLAG_WAGE:
//                    type = "工资";
                    rbAddDetailsFlag3.setChecked(true);
                    llAddDetailsWorker.setVisibility(View.VISIBLE);
                    break;
                case DetailsBean.DETAILS_FLAG_OTHER:
//                    type = "其他";
                    rbAddDetailsFlag4.setChecked(true);
                    llAddDetailsWorker.setVisibility(View.GONE);
                    break;
            }
        }
    }

    @OnClick({R.id.rb_addDetails_flag_1, R.id.rb_addDetails_flag_2, R.id.rb_addDetails_flag_3, R.id.rb_addDetails_flag_4, R.id.tv_addDetails_date, R.id.ll_root})
    public void onViewClicked(View view) {
        KeyBoardUtils.closeKeybord(mContext, tv_right);
        switch (view.getId()) {
            case R.id.rb_addDetails_flag_1:
                detailsFlag = DetailsBean.DETAILS_FLAG_RECEIPT;
                llAddDetailsWorker.setVisibility(View.GONE);
                break;
            case R.id.rb_addDetails_flag_2:
                detailsFlag = DetailsBean.DETAILS_FLAG_MATERIAL;
                llAddDetailsWorker.setVisibility(View.GONE);
                break;
            case R.id.rb_addDetails_flag_3:
                detailsFlag = DetailsBean.DETAILS_FLAG_WAGE;
                llAddDetailsWorker.setVisibility(View.VISIBLE);
                break;
            case R.id.rb_addDetails_flag_4:
                detailsFlag = DetailsBean.DETAILS_FLAG_OTHER;
                llAddDetailsWorker.setVisibility(View.GONE);
                break;
            case R.id.tv_addDetails_date:
                tpvDate.show();
                break;

        }
    }

    private void saveDetails() {
        detailsContent = etAddDetailsContent.getText().toString().trim();
        detailsMoneys = etAddDetailsMoney.getText().toString().trim();
        detailsWorker = etAddDetailsWorker.getText().toString().trim();
        detailsRemarks = etAddDetailsRemarks.getText().toString().trim();

        if (detailsContent.isEmpty()) {
            ToastUtils.show("请输入明细内容");
        } else if (detailsMoneys.isEmpty()) {
            ToastUtils.show("请输入明细金额");
        } else if (detailsFlag == 0) {
            ToastUtils.show("请选择明细的类别");
        } else if (detailsFlag == DetailsBean.DETAILS_FLAG_WAGE && detailsWorker.isEmpty()) {
            ToastUtils.show("请选择工人的姓名");
        } else if (detailsDate == null) {
            ToastUtils.show("请选择日期");
        } else {
            dialog.show();
            float money = Float.parseFloat(detailsMoneys);

            if (detailsBean == null)
                detailsBean = new DetailsBean();
            detailsBean.setProjectId(projectsId);
            detailsBean.setContent(detailsContent);
            detailsBean.setMoney(money);
            detailsBean.setFlag(detailsFlag);
            detailsBean.setWorker(detailsWorker);
            detailsBean.setTime(detailsDate);
            detailsBean.setRemarks(detailsRemarks);

            //添加或者 更新 数据库
            if (detailsId == -1) {
                for (int i = 0; i < 30; i++) {
                    detailsBean.setContent(detailsContent + "_" + i);
                    RealmHelper.getInstance().addDetails(detailsBean);
                }
//                RealmHelper.getInstance().addDetails(detailsBean);
                detailsBean.setUpdateData(false);
            } else {
                RealmHelper.getInstance().updateDetails(detailsBean);
                detailsBean.setUpdateData(true);
            }
            RxBus.getInstance().post(detailsBean);

            //增加用户体验想延时
            tv_right.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.show("保存成功");
                    dialog.hide();
                    finish();
                }
            }, 1800);
        }
    }

    DetailsBean detailsBean;
}
