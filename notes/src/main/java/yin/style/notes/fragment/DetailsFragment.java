package yin.style.notes.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import yin.style.baselib.fragment.RecyclerViewFragment;
import yin.style.baselib.utils.RxBus;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.functions.Action1;
import yin.style.notes.R;
import yin.style.notes.SortListener;
import yin.style.notes.adapter.DetailAdapter;
import yin.style.notes.dao.RealmHelper;
import yin.style.notes.model.DetailsBean;
import yin.style.notes.model.RuleDetails;
import yin.style.notes.utils.SPCache;
import yin.style.notes.utils.SortPopwindow;

/**
 * Created by Chne on 2017/8/12.
 * 员工 工资列表
 */

public class DetailsFragment extends RecyclerViewFragment {
    private TextView tvDetailHeadSort;
    private TextView tvDetailHeadType;
    private TextView tvDetailHeadStart;
    private TextView tvDetailHeadEnd;

    private SortPopwindow popwindow;
    private RuleDetails ruleDetails;
    private Drawable up;
    private Drawable down;

    private List<DetailsBean> list = new ArrayList<>();
    private DetailAdapter adapter;
    private int pageNumb = 0;

    @Override
    protected void setTitle() {
        title.setText("详情");
        hiddenBackButton();
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
                    }
                }, 1500);
            }
        });

        addHeadView();
    }

    private void addHeadView() {
        popwindow = new SortPopwindow(mContext, rootView, SortPopwindow.FLAG_DETAILS, new SortListener() {
            @Override
            public void finish() {
                setView();
            }
        });

        View headView = View.inflate(mContext, R.layout.head_details, null);
        headView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        tvDetailHeadSort = (TextView) headView.findViewById(R.id.tv_detail_head_sort);
        tvDetailHeadType = (TextView) headView.findViewById(R.id.tv_detail_head_type);
        tvDetailHeadStart = (TextView) headView.findViewById(R.id.tv_detail_head_start);
        tvDetailHeadEnd = (TextView) headView.findViewById(R.id.tv_detail_head_end);
        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popwindow.show();
            }
        });

        adapter.addHeaderView(headView);
    }

    private void setView() {
        ruleDetails = SPCache.getInstance().getRuleDetails();
        tvDetailHeadSort.setCompoundDrawables(null, null, ruleDetails.isUp() ? up : down, null);
        tvDetailHeadSort.setText(RuleDetails.getFlagText(ruleDetails.getFlag()));
        //***
        tvDetailHeadType.setText(RuleDetails.getFlagText(ruleDetails.getFlag()));
        tvDetailHeadStart.setText(ruleDetails.getStartTime());
        tvDetailHeadEnd.setText(ruleDetails.getEndTime());
    }

    @Override
    protected RecyclerView.Adapter setAdapter() {
        adapter = new DetailAdapter(mContext, list);
        return adapter;
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

    /**
     * 从数据库 读取 更多数据
     *
     * @param page
     */
    private void loadMore(int page) {
        List<DetailsBean> tempList = RealmHelper.getInstance().findDetailsList(page);
        if (tempList.size() < RealmHelper.pageSize) {
            mRecyclerView.setLoadingMoreEnabled(false);
        }
        list.addAll(tempList);
        adapter.notifyDataSetChanged();
    }

    /**
     * 复制并调用 这里到 activity或者fragment中  使用 RxBus.getInstance().post("1024");方法调用
     * 其中post的参数时String时为主线程，为Int时 为子线程。
     */
    public void doSubscribe(Object o) {
        Subscription subscription = RxBus.getInstance()
                .doSubscribe(DetailsBean.class, new Action1<DetailsBean>() {
                    @Override
                    public void call(DetailsBean bean) {
                        refreshData(bean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

        RxBus.getInstance().addSubscription(o, subscription);
    }

    private void refreshData(DetailsBean bean) {
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
