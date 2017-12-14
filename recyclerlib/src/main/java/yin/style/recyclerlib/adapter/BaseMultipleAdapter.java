package yin.style.recyclerlib.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import yin.style.recyclerlib.R;
import yin.style.recyclerlib.holder.BaseViewHolder;
import yin.style.recyclerlib.inter.OnItemClickListener;
import yin.style.recyclerlib.inter.OnItemClickLongListener;
import yin.style.recyclerlib.view.EmptyView;
import yin.style.recyclerlib.view.FooterView;
import yin.style.recyclerlib.view.HeaderView;

/**
 * Created by chenY on 2017/1/19.
 */

public abstract class BaseMultipleAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private View emptyView;
    private View headerView;
    private View footerView;

    private static final int TYPE_EMPTY = 0x00000111;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 0x00000222;
    private static final int TYPE_HEADER = 0x00000333;
    protected List<T> mData;
    protected int layoutResId;

    protected Context mContext;
    private int headerViewCount = 0;
    private int footerViewCount = 0;
    private boolean showEmptyView = false;

    public BaseMultipleAdapter(@LayoutRes int layoutResId, List mData) {
        this.layoutResId = layoutResId;
        this.mData = mData;
    }

    //正常Adapter的 监听
    private OnItemClickListener onItemClickListener;
    private OnItemClickLongListener onItemClickLongListener;
    //空布局的 监听
    private OnItemClickListener onEmptyClickListener;
    private OnItemClickLongListener onEmptyClickLongListener;

    // Item 点击
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //Item 长按
    public void setOnItemClickLongListener(OnItemClickLongListener onItemClickLongListener) {
        this.onItemClickLongListener = onItemClickLongListener;
    }

    //emptyView点击
    public void setOnEmptyClickListener(OnItemClickListener onItemClickListener) {
        this.onEmptyClickListener = onItemClickListener;
    }

    //emptyView长按
    public void setOnEmptyClickLongListener(OnItemClickLongListener onItemClickListener) {
        this.onEmptyClickLongListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        if (mData == null || mData.size() == 0)
            if (showEmptyView)
                return 1;
            else
                return 0;
        else {
            return mData.size() + footerViewCount + headerViewCount;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (showEmptyView && (mData == null || mData.size() == 0)) {
            return TYPE_EMPTY;
        } else {
            if (position < headerViewCount && headerView != null) {
                return TYPE_HEADER;
            } else if (footerViewCount != 0 && position + 1 == getItemCount()) {
                return TYPE_FOOTER;
            } else {
                return TYPE_ITEM;
            }
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        if (viewType == TYPE_EMPTY) {
            //空布局
            EmptyView eV = new EmptyView(mContext);
            eV.setGravity(Gravity.CENTER);
            eV.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            if (emptyView == null) {
                emptyView = LayoutInflater.from(mContext).inflate(R.layout.listview_empty, parent, false);
            }
            eV.addView(emptyView);
            setOnclickEmpty();
            return new BaseViewHolder(eV);
        } else if (viewType == TYPE_HEADER) {
            //Header布局
            HeaderView hv = new HeaderView(mContext);
            hv.setGravity(Gravity.CENTER);
            hv.addView(headerView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            return new BaseViewHolder(hv);
        } else if (viewType == TYPE_ITEM) {
            //正常布局
            View view = LayoutInflater.from(mContext).inflate(layoutResId, parent, false);
            return new ItemViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            //Footer布局
            FooterView fv = new FooterView(mContext);
            fv.setGravity(Gravity.CENTER);
            fv.addView(footerView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            if (footerView == null)
//                throw new NullPointerException("footerView is not empty");
            return new BaseViewHolder(fv);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            final int new_position = holder.getLayoutPosition() - headerViewCount;
            setViewHolder(holder, mData.get(new_position), new_position);
            if (onItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(holder.itemView, new_position);
                    }
                });
            }
            if (onItemClickLongListener != null) {
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        onItemClickLongListener.onItemLongClick(holder.itemView, new_position);
                        return true;
                    }
                });
            }
        }
    }

    public boolean isFullView(int position) {
        if (position < headerViewCount || position > headerViewCount + mData.size() - 1)
            return true;
        else
            return false;
    }

    /**
     * 设置空布局
     *
     * @param emptyView
     */
    public void setEmptyView(View emptyView) {
        if (emptyView == null)
            showEmptyView = false;
        else
            this.emptyView = emptyView;
    }

    /**
     * 设置底部Footer View
     *
     * @param footerView
     */
    public void setFooterView(View footerView) {
        this.footerView = footerView;
    }

    /**
     * 设置头部headerView View
     *
     * @param headerView
     */
    public void addHeaderView(View headerView) {
        if (headerView != null) {
            this.headerView = headerView;
            headerViewCount = 1;
        }
    }

    /**
     * 隐藏 FooterView
     */
    public void removeFooterView() {
        footerViewCount = 0;
        notifyDataSetChanged();
    }

    /**
     * 显示 FooterView
     */
    public void showFooterView() {
        footerViewCount = 1;
        notifyDataSetChanged();
    }

    public void addFooterView(View view) {
        if (view != null) {
            footerViewCount = 1;
            footerView = view;
        } else
            footerViewCount = 0;
    }

    /**
     * 空布局 点击事件
     */
    private void setOnclickEmpty() {
        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onEmptyClickListener != null)
                    onEmptyClickListener.onItemClick(emptyView, 0);
            }
        });

        emptyView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onEmptyClickLongListener != null)
                    onEmptyClickLongListener.onItemLongClick(emptyView, 0);
                return true;
            }
        });
    }

    class ItemViewHolder<T> extends BaseViewHolder {
        public ItemViewHolder(View view) {
            super(view);
        }
    }

    public int getHeaderCount() {
        return headerViewCount;
    }

    public int getFooterCount() {
        return footerViewCount;
    }

    protected abstract void setViewHolder(BaseViewHolder baseViewHolder, T t, int position);


}
