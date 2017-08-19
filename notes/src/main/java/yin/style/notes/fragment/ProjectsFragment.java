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
import yin.style.notes.SortListener;
import yin.style.notes.activity.AddProjectsActivity;
import yin.style.notes.activity.DetailsActivity;
import yin.style.notes.adapter.ProjectsAdapter;
import yin.style.notes.dao.RealmHelper;
import yin.style.notes.model.ProjectBean;
import yin.style.notes.model.RuleProjects;
import yin.style.notes.utils.SPCache;
import yin.style.notes.utils.SortPopwindow;
import yin.style.recyclerlib.inter.OnItemClickListener;
import yin.style.recyclerlib.inter.OnItemClickLongListener;

/**
 * Created by Chne on 2017/8/12.
 */

public class ProjectsFragment extends RecyclerViewFragment {
    private TextView tvProjectHeadSort;
    private TextView tvProjectHeadType;
    private TextView tvProjectHeadStart;
    private TextView tvProjectHeadEnd;

    private ProjectsAdapter adapter;
    private List<ProjectBean> list = new ArrayList<>();
    private int pageNumb = 0;

    private SortPopwindow popwindow;

    private RuleProjects ruleProjects;
    private Drawable up;
    private Drawable down;

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
        popwindow = new SortPopwindow(mContext, view, SortPopwindow.FLAG_PROJECTS, new SortListener() {
            @Override
            public void finish() {
                setView();
            }
        });

        View headView = View.inflate(mContext, R.layout.head_projects, null);
        headView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        tvProjectHeadSort = (TextView) headView.findViewById(R.id.tv_project_head_sort);
        tvProjectHeadType = (TextView) headView.findViewById(R.id.tv_project_head_type);
        tvProjectHeadStart = (TextView) headView.findViewById(R.id.tv_project_head_start);
        tvProjectHeadEnd = (TextView) headView.findViewById(R.id.tv_project_head_end);
        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popwindow.show();
            }
        });
        adapter.addHeaderView(headView);
    }

    @Override
    protected void initData() {
        up = AppCompatResources.getDrawable(mContext, R.mipmap.ic_to_up);
        up.setBounds(0, 0, up.getMinimumWidth(), up.getMinimumHeight());
        down = AppCompatResources.getDrawable(mContext, R.mipmap.ic_to_down);
        down.setBounds(0, 0, down.getMinimumWidth(), down.getMinimumHeight());

        setView();

        list.clear();
        loadMore(pageNumb);
        doSubscribe(this);
    }

    private void setView() {
        ruleProjects = SPCache.getInstance().getRuleProjects();
        tvProjectHeadSort.setCompoundDrawables(null, null, ruleProjects.isUp() ? up : down, null);
        tvProjectHeadSort.setText(RuleProjects.getFlagText(ruleProjects.getFlag()));
        //****
        tvProjectHeadType.setText(RuleProjects.getFlagText(ruleProjects.getFlag()));
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
}
