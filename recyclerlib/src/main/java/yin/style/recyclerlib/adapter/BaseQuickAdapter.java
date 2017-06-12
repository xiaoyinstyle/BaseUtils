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

public abstract class BaseQuickAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
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
    private boolean showEmptyView = true;

    public BaseQuickAdapter(@LayoutRes int layoutResId, List mData) {
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
        if (viewType == TYPE_EMPTY) {//空布局
            View empty = getEmptyView(parent);
            setOnclickEmpty(empty);
            return new BaseViewHolder(empty);
        } else if (viewType == TYPE_HEADER) {//Header布局
            return new BaseViewHolder(headerView);
        } else if (viewType == TYPE_ITEM) {//正常布局
            View view = LayoutInflater.from(mContext).inflate(layoutResId, parent, false);
            return new ItemViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {//Footer布局
            return new BaseViewHolder(footerView);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            final int new_position = position - headerViewCount;

            setViewHolder(holder, mData.get(new_position), new_position);
            if (onItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null)
                            onItemClickListener.onItemClick(holder.itemView, new_position);
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (onItemClickLongListener != null)
                            onItemClickLongListener.onItemLongClick(holder.itemView, new_position);
                        return false;
                    }
                });
            }
        }
    }

    private View getEmptyView(ViewGroup view) {
        EmptyView eV = new EmptyView(mContext);
        eV.removeAllViews();
        eV.setGravity(Gravity.CENTER);
        eV.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        if (emptyView == null)
            emptyView = LayoutInflater.from(mContext).inflate(R.layout.listview_empty, view, false);
        eV.addView(emptyView);
        return eV;
    }

    /**
     * @param position 是否 是headview或者FooterView
     */
    public boolean isOtherView(int position) {
        if (position < headerViewCount || position > headerViewCount + mData.size() - 1)
            return true;
        else
            return false;
    }

    /**
     * @param showEmptyView 是否显示默认的空布局，默认 true:显示
     */
    public void setShowEmptyView(boolean showEmptyView) {
        this.showEmptyView = showEmptyView;
    }

    /**
     * @param emptyView 设置空布局
     */
    public void setEmptyView(View emptyView) {
        if (emptyView == null)
            setShowEmptyView(false);
        else {
            setShowEmptyView(true);
            this.emptyView = emptyView;
        }
    }

    /**
     * 隐藏 HeaderView
     */
    public void removeHeaderView() {
        headerViewCount = 0;
        notifyDataSetChanged();
    }

    /**
     * 显示 HeaderView
     */
    public void showHeaderView() {
        headerViewCount = 1;
        notifyDataSetChanged();
    }

    /**
     * @param headerView 设置头部headerView View
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
    private void setOnclickEmpty(View ev) {
        ev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onEmptyClickListener != null)
                    onEmptyClickListener.onItemClick(emptyView, 0);
            }
        });
        ev.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onEmptyClickLongListener != null)
                    onEmptyClickLongListener.onItemLongClick(emptyView, 0);
                return false;
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

    /**
     *
     */
    public void addAll(List<T> data) {
        mData.addAll(data);
        notifyItemRangeInserted(mData.size() - data.size() + headerViewCount, data.size());
    }

    public void add(T data) {
        mData.add(data);
        notifyItemInserted(mData.size() - 1 + headerViewCount);
    }

    public void addAll(int position, List<T> data) {
        mData.addAll(position, data);
        notifyItemRangeInserted(position + headerViewCount, data.size());
    }

    public void add(int position, T data) {
        mData.add(position, data);
        notifyItemInserted(position + headerViewCount);
        // 加入如下代码保证position的位置正确性
        if (position + headerViewCount != mData.size() - 1) {
            notifyItemRangeChanged(position + headerViewCount, mData.size() - position + headerViewCount);
        }
    }

    public void remove(int position) {
        mData.remove(position);
        notifyItemRemoved(position + headerViewCount);
        // 加入如下代码保证position的位置正确性
        if (position + headerViewCount != mData.size() - 1) {
            notifyItemRangeChanged(position + headerViewCount, mData.size() - position);
        }
    }

    public void change(int position, T data) {
        mData.remove(position);
        mData.add(position, data);
        notifyItemChanged(position + headerViewCount);
    }

    public void notifyChanged() {
        if (showEmptyView)
            notifyDataSetChanged();
    }
}