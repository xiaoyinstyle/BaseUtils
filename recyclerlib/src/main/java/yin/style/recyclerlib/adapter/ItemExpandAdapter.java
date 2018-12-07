package yin.style.recyclerlib.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import yin.style.recyclerlib.holder.BaseViewHolder;
import yin.style.recyclerlib.inter.OnExpandItemClickListener;
import yin.style.recyclerlib.inter.OnExpandItemClickLongListener;

/**
 * @author chenyin
 * @date 2017/3/28
 */
abstract class ItemExpandAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private List<T> list;
    private int groupPosition;

    public ItemExpandAdapter(List<T> list, int groupPosition) {
        this.list = list;
        this.groupPosition = groupPosition;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getChildLayout(), parent, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position) {
        holder.itemView.setTag(position);

        setViewHolder(holder, groupPosition, position);

        if (onItemClickListener != null)
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(holder.itemView, groupPosition, position);
                }
            });

        if (onItemClickLongListener != null)
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return onItemClickLongListener.onItemLongClick(holder.itemView, groupPosition, position);
                }
            });
    }

    @Override
    public int getItemCount() {
        return (list == null || list.isEmpty()) ? 0 : list.size();
    }

//************************

    //正常Adapter的 监听
    private OnExpandItemClickListener onItemClickListener;
    private OnExpandItemClickLongListener onItemClickLongListener;

    // Item 点击
    public ItemExpandAdapter setOnItemClickListener(OnExpandItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }

    //Item 长按
    public ItemExpandAdapter setOnItemClickLongListener(OnExpandItemClickLongListener onItemClickLongListener) {
        this.onItemClickLongListener = onItemClickLongListener;
        return this;
    }

    protected abstract int getChildLayout();

    protected abstract void setViewHolder(BaseViewHolder holder, int groupPosition, int position);

}
