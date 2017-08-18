package yin.style.notes.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jskingen.baselib.fragment.RecyclerViewFragment;
import com.jskingen.baselib.utils.RxBus;
import com.jskingen.baselib.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Subscription;
import rx.functions.Action1;
import yin.style.notes.R;
import yin.style.notes.activity.AddProjectsActivity;
import yin.style.notes.activity.DetailsActivity;
import yin.style.notes.adapter.ProjectsAdapter;
import yin.style.notes.dao.RealmHelper;
import yin.style.notes.model.ProjectBean;
import yin.style.notes.model.RuleProjects;
import yin.style.notes.utils.DateUtil;
import yin.style.notes.utils.SPCache;
import yin.style.recyclerlib.inter.OnItemClickListener;
import yin.style.recyclerlib.inter.OnItemClickLongListener;

/**
 * Created by Chne on 2017/8/12.
 */

public class ProjectsFragment extends RecyclerViewFragment implements View.OnClickListener {
    private TextView tvProjectHeadBudget;
    private TextView tvProjectHeadDate;
    private TextView tvProjectHeadStart;
    private TextView tvProjectHeadEnd;

    private ProjectsAdapter adapter;
    private List<ProjectBean> list = new ArrayList<>();
    private int pageNumb = 0;

    private TimePickerView tpvStart;
    private TimePickerView tpvEnd;

    @Override
    protected void setTitle() {
        title.setText("Notes");
        hiddenBackButton();
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("新增");
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, AddProjectsActivity.class));
            }
        });
    }

    @Override
    protected void setItemDecoration() {
//        super.setItemDecoration();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pageNumb++;
                        loadMore(pageNumb);
                        mRecyclerView.loadMoreComplete();
                        ToastUtils.show("加载完成");
                    }
                }, 1500);
            }
        });

        addHeadView();
    }

    private void addHeadView() {
        View headView = View.inflate(mContext, R.layout.head_projects, null);
        headView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        tvProjectHeadBudget = (TextView) headView.findViewById(R.id.tv_project_head_budget);
        tvProjectHeadDate = (TextView) headView.findViewById(R.id.tv_project_head_date);
        tvProjectHeadStart = (TextView) headView.findViewById(R.id.tv_project_head_start);
        tvProjectHeadEnd = (TextView) headView.findViewById(R.id.tv_project_head_end);

        tvProjectHeadBudget.setOnClickListener(this);
        tvProjectHeadDate.setOnClickListener(this);
        tvProjectHeadStart.setOnClickListener(this);
        tvProjectHeadEnd.setOnClickListener(this);
        adapter.addHeaderView(headView);

        tpvStart = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                ruleProjects.setStartTime(DateUtil.date2Str(date, "yyyy-MM-dd"));
                SPCache.getInstance().setRuleProjects(ruleProjects);

                tvProjectHeadStart.setText(ruleProjects.getStartTime());
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
                ruleProjects.setEndTime(DateUtil.date2Str(date, "yyyy-MM-dd"));
                SPCache.getInstance().setRuleProjects(ruleProjects);

                tvProjectHeadEnd.setText(ruleProjects.getEndTime());
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

    }

    RuleProjects ruleProjects;

    @Override
    protected void initData() {
        up = AppCompatResources.getDrawable(mContext, R.mipmap.ic_to_up);
        up.setBounds(0, 0, up.getMinimumWidth(), up.getMinimumHeight());
        down = AppCompatResources.getDrawable(mContext, R.mipmap.ic_to_down);
        down.setBounds(0, 0, down.getMinimumWidth(), down.getMinimumHeight());

        ruleProjects = SPCache.getInstance().getRuleProjects();
        setView(ruleProjects);

        list.clear();
        loadMore(pageNumb);
        doSubscribe(this);
    }

    private Drawable up;
    private Drawable down;

    private void setView(RuleProjects ruleProjects) {

        switch (ruleProjects.getFlag()) {
            case RuleProjects.FLAG_TIME:
                tvProjectHeadDate.setTextColor(getResources().getColor(R.color.textOrange));
                tvProjectHeadBudget.setTextColor(getResources().getColor(R.color.textGrey));
                tvProjectHeadDate.setCompoundDrawables(null, null, ruleProjects.isUp() ? up : down, null);
                tvProjectHeadBudget.setCompoundDrawables(null, null, null, null);
                break;
            case RuleProjects.FLAG_MONEY:
                tvProjectHeadDate.setTextColor(getResources().getColor(R.color.textGrey));
                tvProjectHeadBudget.setTextColor(getResources().getColor(R.color.textOrange));
                tvProjectHeadDate.setCompoundDrawables(null, null, null, null);
                tvProjectHeadBudget.setCompoundDrawables(null, null, ruleProjects.isUp() ? up : down, null);
                break;
        }
        tvProjectHeadStart.setText(ruleProjects.getStartTime());
        tvProjectHeadEnd.setText(ruleProjects.getEndTime());
    }

    /**
     * 从数据库 读取 更多数据
     *
     * @param page
     */
    private void loadMore(int page) {
        List<ProjectBean> tempList = RealmHelper.getInstance().findProjectsList(page);
        if (tempList.size() < RealmHelper.pageSize) {
            mRecyclerView.setLoadingMoreEnabled(false);
        }
        list.addAll(tempList);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected RecyclerView.Adapter setAdapter() {
        adapter = new ProjectsAdapter(R.layout.item_main, list);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(mContext, DetailsActivity.class);
                intent.putExtra("projectsId", list.get(position).getId());
                startActivity(intent);
            }
        });
        adapter.setOnItemClickLongListener(new OnItemClickLongListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                Intent intent = new Intent(mContext, AddProjectsActivity.class);
                intent.putExtra("projectsId", list.get(position).getId());
                startActivity(intent);
            }
        });
        return adapter;
    }


    /**
     * 复制并调用 这里到 activity或者fragment中  使用 RxBus.getInstance().post("1024");方法调用
     * 其中post的参数时String时为主线程，为Int时 为子线程。
     */
    public void doSubscribe(Object o) {
        Subscription subscription = RxBus.getInstance()
                .doSubscribe(ProjectBean.class, new Action1<ProjectBean>() {
                    @Override
                    public void call(ProjectBean bean) {
                        refreshData(bean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

        RxBus.getInstance().addSubscription(o, subscription);
    }

    private void refreshData(ProjectBean bean) {
        if (!bean.isUpdateData()) {
            list.add(0, bean);
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (bean.getId() == list.get(i).getId()) {
                    list.set(i, bean);
                    break;
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unSubscribe(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_project_head_budget:
                if (ruleProjects.getFlag() == RuleProjects.FLAG_MONEY)
                    ruleProjects.setUp(!ruleProjects.isUp());
                else {
                    ruleProjects.setUp(true);
                    ruleProjects.setFlag(RuleProjects.FLAG_MONEY);
                }

                SPCache.getInstance().setRuleProjects(ruleProjects);
                setView(ruleProjects);
                break;
            case R.id.tv_project_head_date:
                if (ruleProjects.getFlag() == RuleProjects.FLAG_TIME)
                    ruleProjects.setUp(!ruleProjects.isUp());
                else {
                    ruleProjects.setUp(true);
                    ruleProjects.setFlag(RuleProjects.FLAG_TIME);
                }

                SPCache.getInstance().setRuleProjects(ruleProjects);
                setView(ruleProjects);
                break;
            case R.id.tv_project_head_start:
                tpvStart.show();
                break;
            case R.id.tv_project_head_end:
//                tpvEnd.setDate();
                tpvEnd.show();
                break;
        }
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
                case R.id.btnClear:
                    ruleProjects.setEndTime("");
                    SPCache.getInstance().setRuleProjects(ruleProjects);

                    tvProjectHeadEnd.setText("");
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
                case R.id.btnClear:
                    ruleProjects.setStartTime("");
                    SPCache.getInstance().setRuleProjects(ruleProjects);

                    tvProjectHeadStart.setText("");
                    break;
            }
            tpvStart.dismiss();
        }
    };

}
