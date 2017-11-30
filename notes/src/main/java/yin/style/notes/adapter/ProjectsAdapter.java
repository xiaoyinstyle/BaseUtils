package yin.style.notes.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.view.View;

import java.util.List;

import yin.style.notes.R;
import yin.style.notes.model.ProjectBean;
import yin.style.notes.utils.DateUtil;
import yin.style.notes.view.LabelTextView;
import yin.style.recyclerlib.adapter.BaseQuickAdapter;
import yin.style.recyclerlib.holder.BaseViewHolder;

/**
 * Created by Chne on 2017/8/18.
 */

public class ProjectsAdapter  extends BaseQuickAdapter<ProjectBean> {
    int labelColor[] = {Color.parseColor("#01BF9D"), Color.parseColor("#EA4B35"), Color.parseColor("#95A5A5")};

    public ProjectsAdapter(Context mContext, List mData) {
        super(mContext, mData);
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.item_main;
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
