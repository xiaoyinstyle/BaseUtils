package yin.style.notes.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jskingen.baselib.activity.base.RecyclerViewActivity;
import com.jskingen.baselib.utils.RxBus;
import com.jskingen.baselib.utils.ToastUtils;
import com.jskingen.baselib.view.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.functions.Action1;
import yin.style.notes.R;
import yin.style.notes.adapter.DetailAdapter;
import yin.style.notes.dao.RealmHelper;
import yin.style.notes.model.DetailsBean;
import yin.style.notes.model.ProjectBean;
import yin.style.notes.utils.ExcelUtil;
import yin.style.recyclerlib.inter.OnItemClickListener;

public class DetailsActivity extends RecyclerViewActivity {
    private FloatingActionButton fbt_add;

    private DetailAdapter adapter;
    private List<DetailsBean> list = new ArrayList<>();
    private int pageNumb = 0;
    private long projectsId;

    private LoadingDialog dialog;

    @Override
    protected int getViewByXml() {
        return R.layout.activity_details;
    }

    @Override
    protected void setTitle() {
        title.setText("项目明细");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("导出");
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveExcel();
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

        fbt_add = (FloatingActionButton) findViewById(R.id.fbt_add);
        fbt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddDetailsActivity.class);
                intent.putExtra("projectsId", projectsId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        projectsId = getIntent().getLongExtra("projectsId", -1);

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
        List<DetailsBean> tempList = RealmHelper.getInstance().findDetailsList(page, projectsId);
        if (tempList.size() < RealmHelper.pageSize) {
            mRecyclerView.setLoadingMoreEnabled(false);
        }
        list.addAll(tempList);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected RecyclerView.Adapter setAdapter() {
        adapter = new DetailAdapter(mContext, list);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(mContext, AddDetailsActivity.class);
                intent.putExtra("detailsId", list.get(position).getId());
                intent.putExtra("projectsId", projectsId);
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

    /**
     * 保存 数据到 Excel
     */
    private void saveExcel() {
        if (dialog == null)
            dialog = new LoadingDialog.Builder(this)
                    .setMessage("保存中..")
                    .setTextColor(Color.WHITE)
                    .setBackground(R.drawable.progress_custom_bg_black)
                    .setCancelable(false)
                    .create();

        dialog.show();
        tv_right.postDelayed(new Runnable() {
            @Override
            public void run() {
                final ProjectBean bean = RealmHelper.getInstance().findProject(projectsId);
                final List<DetailsBean> list = RealmHelper.getInstance().findDetailsALL(projectsId);
                ExcelUtil.writeExecleToFile(mContext, bean, list);
                ToastUtils.show("导出成功");
                dialog.hide();
            }
        },1000);
    }

}
