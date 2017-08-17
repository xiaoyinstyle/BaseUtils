package yin.style.notes.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jskingen.baselib.fragment.RecyclerViewFragment;
import com.jskingen.baselib.utils.RxBus;
import com.jskingen.baselib.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.functions.Action1;
import yin.style.notes.R;
import yin.style.notes.activity.AddProjectsActivity;
import yin.style.notes.activity.DetailsActivity;
import yin.style.notes.dao.RealmHelper;
import yin.style.notes.model.ProjectBean;
import yin.style.notes.utils.DateUtil;
import yin.style.notes.view.LabelTextView;
import yin.style.recyclerlib.adapter.BaseQuickAdapter;
import yin.style.recyclerlib.holder.BaseViewHolder;
import yin.style.recyclerlib.inter.OnItemClickListener;
import yin.style.recyclerlib.inter.OnItemClickLongListener;

/**
 * Created by Chne on 2017/8/12.
 */

public class ProjectsFragment extends RecyclerViewFragment {
    private ProjectsAdapter adapter;
    private List<ProjectBean> list = new ArrayList<>();
    private int pageNumb = 0;

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
    }

    @Override
    protected void initData() {
        list.clear();
        loadMore(pageNumb);
        doSubscribe(this);
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

    int labelColor[] = {Color.parseColor("#01BF9D"), Color.parseColor("#EA4B35"), Color.parseColor("#95A5A5")};

    class ProjectsAdapter extends BaseQuickAdapter<ProjectBean> {

        public ProjectsAdapter(@LayoutRes int layoutResId, List mData) {
            super(layoutResId, mData);
        }

        @Override
        protected void setViewHolder(BaseViewHolder baseViewHolder, ProjectBean bean, int position) {
            LabelTextView labelTextView = baseViewHolder.getView(R.id.ltv_item_main_statue);
            labelTextView.setVisibility(View.VISIBLE);
            if (bean.getFlag() == ProjectBean.PROJECTS_FLAG_START) {
                labelTextView.setLabelBackgroundColor(labelColor[0]);
            } else if (bean.getFlag() == ProjectBean.PROJECTS_FLAG_RUNNING) {
                labelTextView.setLabelBackgroundColor(labelColor[1]);
            } else if (bean.getFlag() == ProjectBean.PROJECTS_FLAG_END) {
                labelTextView.setLabelBackgroundColor(labelColor[2]);
            } else {
                labelTextView.setVisibility(View.GONE);
            }
            labelTextView.setLabelText(ProjectBean.getFlagText(bean.getFlag()));

            baseViewHolder.setText(R.id.iv_item_main_name, bean.getProjects());
            baseViewHolder.setText(R.id.iv_item_main_user, bean.getFirstParty());
            baseViewHolder.setText(R.id.iv_item_main_budget, "" + bean.getBudget());

            baseViewHolder.setText(R.id.iv_item_main_create, DateUtil.date2Str(bean.getCreateTime(), "yyyy-MM-dd"));
            baseViewHolder.setText(R.id.iv_item_main_income_expend, bean.getInCome() + "/" + bean.getExpend());
        }
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
