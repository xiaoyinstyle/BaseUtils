package yin.style.recyclerlib.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import yin.style.recyclerlib.holder.BaseViewHolder;
import yin.style.recyclerlib.inter.OnExplandItemClickListener;
import yin.style.recyclerlib.inter.OnExplandItemClickLongListener;

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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(holder.itemView, groupPosition, position);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemClickLongListener != null)
                    onItemClickLongListener.onItemLongClick(holder.itemView, groupPosition, position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.isEmpty() ? 0 : list.size();
    }

//************************

    //正常Adapter的 监听
    private OnExplandItemClickListener onItemClickListener;
    private OnExplandItemClickLongListener onItemClickLongListener;

    // Item 点击
    public ItemExpandAdapter setOnItemClickListener(OnExplandItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }

    //Item 长按
    public ItemExpandAdapter setOnItemClickLongListener(OnExplandItemClickLongListener onItemClickLongListener) {
        this.onItemClickLongListener = onItemClickLongListener;
        return this;
    }

    protected abstract int getChildLayout();

    protected abstract void setViewHolder(BaseViewHolder holder, int groupPosition, int position);

}
