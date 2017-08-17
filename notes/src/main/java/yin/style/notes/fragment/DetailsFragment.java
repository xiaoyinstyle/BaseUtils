package yin.style.notes.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jskingen.baselib.fragment.RecyclerViewFragment;
import com.jskingen.baselib.utils.RxBus;
import com.jskingen.baselib.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.functions.Action1;
import yin.style.notes.R;
import yin.style.notes.adapter.DetailAdapter;
import yin.style.notes.dao.RealmHelper;
import yin.style.notes.model.DetailsBean;
import yin.style.notes.utils.DateUtil;
import yin.style.recyclerlib.adapter.BaseQuickAdapter;
import yin.style.recyclerlib.holder.BaseViewHolder;

/**
 * Created by Chne on 2017/8/12.
 * 员工 工资列表
 */

public class DetailsFragment extends RecyclerViewFragment {

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
    }


    @Override
    protected RecyclerView.Adapter setAdapter() {
        adapter = new DetailAdapter(R.layout.item_details, list);
        return adapter;
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
