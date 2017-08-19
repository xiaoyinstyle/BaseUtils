package yin.style.notes.utils;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;

import java.util.Date;

import yin.style.notes.R;
import yin.style.notes.SortListener;
import yin.style.notes.model.RuleDetails;
import yin.style.notes.model.RuleProjects;

/**
 * Created by Chne on 2017/8/19.
 */

public class SortPopwindow implements View.OnClickListener {
    private RadioButton rbSortType1;
    private RadioButton rbSortType2;
    private RadioButton rbSortDown;
    private RadioButton rbSortUp;
    private CheckBox cbSortType1;
    private CheckBox cbSortType2;
    private CheckBox cbSortType3;
    private CheckBox cbSortType4;
    private TextView tvSortStart;
    private TextView tvSortEnd;
    private Button btSortReset;
    private Button btSortFinish;

    private PopupWindow popupWindow;
    private View v;

    private TimePickerView tpvStart;
    private TimePickerView tpvEnd;

    private Date dateStart;
    private Date dateEnd;

    private Activity mContext;
    public static final int FLAG_PROJECTS = 11;
    public static final int FLAG_DETAILS = 12;
    private int flag;

    private SortListener listener;

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void setAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        mContext.getWindow().setAttributes(lp);
    }

    public SortPopwindow(Activity context, View v, int flag, SortListener listener) {
        this.v = v;
        this.mContext = context;
        this.listener = listener;
        this.flag = flag;


        View layout = View.inflate(context, R.layout.popwindow_sort, null);

        int statusBarHeight1 = -1;
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight1 = mContext.getResources().getDimensionPixelSize(resourceId);
        }

        popupWindow = new PopupWindow(layout, context.getWindowManager().getDefaultDisplay().getWidth(),
                context.getWindowManager().getDefaultDisplay().getHeight() - statusBarHeight1);

        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setAnimationStyle(R.style.AnimationRightFade);

        int[] location = new int[2];
        v.getLocationOnScreen(location);

        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setView(layout);
    }


    private void setView(View view) {
        rbSortType1 = (RadioButton) view.findViewById(R.id.rb_sort_type_1);
        rbSortType2 = (RadioButton) view.findViewById(R.id.rb_sort_type_2);
        rbSortDown = (RadioButton) view.findViewById(R.id.rb_sort_down);
        rbSortUp = (RadioButton) view.findViewById(R.id.rb_sort_up);
        cbSortType1 = (CheckBox) view.findViewById(R.id.cb_sort_type_1);
        cbSortType2 = (CheckBox) view.findViewById(R.id.cb_sort_type_2);
        cbSortType3 = (CheckBox) view.findViewById(R.id.cb_sort_type_3);
        cbSortType4 = (CheckBox) view.findViewById(R.id.cb_sort_type_4);
        tvSortStart = (TextView) view.findViewById(R.id.tv_sort_start);
        tvSortEnd = (TextView) view.findViewById(R.id.tv_sort_end);
        btSortReset = (Button) view.findViewById(R.id.bt_sort_reset);
        btSortFinish = (Button) view.findViewById(R.id.bt_sort_finish);

        tpvStart = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                dateStart = date;
                tvSortStart.setText(DateUtil.date2Str(dateStart, "yyyy-MM-dd"));
            }
        }).setLayoutRes(R.layout.dialog_timepicker, new CustomListener() {
            @Override
            public void customLayout(View v) {
                //顶部标题
                TextView tvTitle = (TextView) v.findViewById(com.bigkoo.pickerview.R.id.tvTitle);
                tvTitle.setText("开始时间");//默认为空

                //确定和取消按钮
                v.findViewById(R.id.btnSubmit).setOnClickListener(tvpStartOnclick);
                v.findViewById(R.id.btnClear).setOnClickListener(tvpStartOnclick);
                v.findViewById(R.id.btnCancel).setOnClickListener(tvpStartOnclick);
            }
        }).isDialog(true)
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .build();

        tpvEnd = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                dateEnd = date;
                tvSortEnd.setText(DateUtil.date2Str(dateEnd, "yyyy-MM-dd"));
            }
        }).setLayoutRes(R.layout.dialog_timepicker, new CustomListener() {
            @Override
            public void customLayout(View v) {
                //顶部标题
                TextView tvTitle = (TextView) v.findViewById(com.bigkoo.pickerview.R.id.tvTitle);
                tvTitle.setText("结束时间");//默认为空

                //确定和取消按钮
                v.findViewById(R.id.btnSubmit).setOnClickListener(tvpEndOnclick);
                v.findViewById(R.id.btnClear).setOnClickListener(tvpEndOnclick);
                v.findViewById(R.id.btnCancel).setOnClickListener(tvpEndOnclick);
            }
        }).isDialog(true)
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .build();

        if (flag == FLAG_PROJECTS) {
            RuleProjects ruleProjects = SPCache.getInstance().getRuleProjects();
            if (ruleProjects.getFlag() == RuleProjects.FLAG_TIME) {
                rbSortType1.setChecked(true);
            } else {
                rbSortType2.setChecked(true);
            }
            rbSortType1.setText("时间");
            rbSortType2.setText("预算");
            if (ruleProjects.isUp()) {
                rbSortDown.setChecked(true);
            } else
                rbSortUp.setChecked(true);

            cbSortType1.setChecked(ruleProjects.isStart());
            cbSortType2.setChecked(ruleProjects.isRun());
            cbSortType3.setChecked(ruleProjects.isEnd());
            cbSortType1.setText("预定");
            cbSortType2.setText("进行中");
            cbSortType3.setText("结束");
            cbSortType4.setVisibility(View.GONE);

            dateStart = DateUtil.str2Date(ruleProjects.getStartTime(), "yyyy-MM-dd");
            dateEnd = DateUtil.str2Date(ruleProjects.getEndTime(), "yyyy-MM-dd");
            tvSortStart.setText(ruleProjects.getStartTime());
            tvSortEnd.setText(ruleProjects.getEndTime());
        } else {
            RuleDetails ruleDetails = SPCache.getInstance().getRuleDetails();
            if (ruleDetails.getFlag() == RuleProjects.FLAG_TIME) {
                rbSortType1.setChecked(true);
            } else {
                rbSortType2.setChecked(true);
            }
            rbSortType1.setText("时间");
            rbSortType2.setText("金额");

            if (ruleDetails.isUp()) {
                rbSortDown.setChecked(true);
            } else
                rbSortUp.setChecked(true);

            cbSortType1.setChecked(ruleDetails.isReceipt());
            cbSortType2.setChecked(ruleDetails.isMaterial());
            cbSortType3.setChecked(ruleDetails.isWage());
            cbSortType4.setChecked(ruleDetails.isOther());
            cbSortType1.setText("收款");
            cbSortType2.setText("材料");
            cbSortType3.setText("工资");
            cbSortType4.setText("其它");

            dateStart = DateUtil.str2Date(ruleDetails.getStartTime(), "yyyy-MM-dd");
            dateEnd = DateUtil.str2Date(ruleDetails.getEndTime(), "yyyy-MM-dd");
            tvSortStart.setText(ruleDetails.getStartTime());
            tvSortEnd.setText(ruleDetails.getEndTime());
        }
        tvSortStart.setOnClickListener(this);
        tvSortEnd.setOnClickListener(this);
        btSortReset.setOnClickListener(this);
        btSortFinish.setOnClickListener(this);
    }

    public void show() {
//        setAlpha(0.5f);
        popupWindow.showAtLocation(v, Gravity.RIGHT, 0, 300);
    }

    public void dismiss() {
//        setAlpha(1);
        popupWindow.dismiss();
    }


    //自定义时间选择 控件 dialog 的点击事件
    View.OnClickListener tvpEndOnclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnSubmit:
                    tpvEnd.returnData();
                    break;
                case R.id.btnCancel:
                    break;
            }
            tpvEnd.dismiss();
        }
    };

    //自定义时间选择 控件 dialog 的点击事件
    View.OnClickListener tvpStartOnclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnSubmit:
                    tpvStart.returnData();
                    break;
                case R.id.btnCancel:
                    break;
            }
            tpvStart.dismiss();
        }
    };

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sort_start:
                tpvStart.show();
                break;
            case R.id.tv_sort_end:
                tpvEnd.show();
                break;
            case R.id.bt_sort_reset:
                break;
            case R.id.bt_sort_finish:
                saveData();
                if (listener != null)
                    listener.finish();
                dismiss();
                break;
        }
    }

    private void saveData() {
        if (flag == FLAG_PROJECTS) {
            RuleProjects ruleProjects = new RuleProjects();
            ruleProjects.setFlag(rbSortType1.isChecked() ? RuleProjects.FLAG_TIME : RuleProjects.FLAG_MONEY);
            ruleProjects.setUp(rbSortUp.isChecked() ? true : false);
            ruleProjects.setStart(cbSortType1.isChecked());
            ruleProjects.setRun(cbSortType2.isChecked());
            ruleProjects.setEnd(cbSortType3.isChecked());
            if (dateEnd != null)
                ruleProjects.setEndTime(DateUtil.date2Str(dateEnd, "yyyy-MM-dd"));
            if (dateStart != null)
                ruleProjects.setStartTime(DateUtil.date2Str(dateStart, "yyyy-MM-dd"));
            SPCache.getInstance().setRuleProjects(ruleProjects);
        } else {
            RuleDetails ruleDetails = new RuleDetails();
            ruleDetails.setFlag(rbSortType1.isChecked() ? RuleDetails.FLAG_TIME : RuleDetails.FLAG_MONEY);
            ruleDetails.setUp(rbSortUp.isChecked() ? true : false);
            ruleDetails.setReceipt(cbSortType1.isChecked());
            ruleDetails.setMaterial(cbSortType2.isChecked());
            ruleDetails.setWage(cbSortType3.isChecked());
            ruleDetails.setOther(cbSortType3.isChecked());
            if (dateEnd != null)
                ruleDetails.setEndTime(DateUtil.date2Str(dateEnd, "yyyy-MM-dd"));
            if (dateStart != null)
                ruleDetails.setStartTime(DateUtil.date2Str(dateStart, "yyyy-MM-dd"));
            SPCache.getInstance().setRuleDetails(ruleDetails);
        }
    }
}
