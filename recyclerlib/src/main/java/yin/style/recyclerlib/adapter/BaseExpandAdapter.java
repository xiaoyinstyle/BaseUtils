package yin.style.recyclerlib.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import yin.style.recyclerlib.holder.BaseViewHolder;
import yin.style.recyclerlib.inter.OnExpandItemClickListener;
import yin.style.recyclerlib.inter.OnExpandItemClickLongListener;

/**
 * 可以展开的 Expland adapter
 *
 * @author chenyin
 * @date 2018/12/07
 */
public abstract class BaseExpandAdapter<T> extends RecyclerView.Adapter {
    private Context mContext;
    public List<T> list = new ArrayList<>();
    private List<Boolean> mGroupItemStatus = new ArrayList<>(); // 保存一级标题的开关状态

    private boolean isDefaultExpand = false;//初始化 是否展开 默认：false
    private boolean isNotClose = false;//初始化 不可关闭

    private boolean groupCanClick = true;//group是否有点击监听
    private boolean onlyOpenOne = false;//group是否有点击监听

//    public ExpandableRecyclerViewAdapter(Context context, List<T> list) {
//        mContext = context;
//        this.list = list;
//        initGroupItemStatus();
//        notifyDataSetChanged();
//    }

    public BaseExpandAdapter(Context context) {
        mContext = context;
    }

    /**
     * 这是关键
     *
     * @param list
     * @param isAdd
     */
    public void setData(List list, boolean isAdd) {
        if (!isAdd)
            this.list.clear();

        this.list.addAll(list);

        initGroupItemStatus(isAdd);
        notifyDataSetChanged();
    }

    /**
     * 初始化一级列表开关状态
     */
    private void initGroupItemStatus(boolean isAdd) {
        if (!isAdd) {
            mGroupItemStatus.clear();
            for (int i = 0; i < list.size(); i++) {
                mGroupItemStatus.add(isDefaultExpand);
            }
        } else {
            for (int i = mGroupItemStatus.size() - 1; i < list.size(); i++) {
                mGroupItemStatus.add(isDefaultExpand);
            }
        }
    }

    /**
     * 根据item的位置，获取当前Item的状态
     *
     * @param position 当前item的位置（此position的计数包含groupItem和subItem合计）
     * @return 当前Item的状态（此Item可能是groupItem，也可能是SubItem）
     */
    private ItemStatus getItemStatusByPosition(int position) {
        ItemStatus itemStatus = new ItemStatus();
        int itemCount = 0;
        int i;
        //轮询 groupItem 的开关状态
        for (i = 0; i < mGroupItemStatus.size(); i++) {
            if (itemCount == position) { //position刚好等于计数时，item为groupItem
                itemStatus.setViewType(ItemStatus.VIEW_TYPE_GROUP_ITEM);
                itemStatus.setGroupItemIndex(i);
                break;
            } else if (itemCount > position) { //position大于计数时，item为groupItem(i - 1)中的某个subItem
                itemStatus.setViewType(ItemStatus.VIEW_TYPE_SUB_ITEM);
                itemStatus.setGroupItemIndex(i - 1); // 指定的position组索引
                // 计算指定的position前，统计的列表项和
                int temp = (itemCount - getChild(i - 1).size());
                // 指定的position的子项索引：即为position-之前统计的列表项和
                itemStatus.setSubItemIndex(position - temp);
                break;
            }

            itemCount++;
            if (mGroupItemStatus.get(i)) {
                itemCount += getChild(i).size();
            }
        }
        // 轮询到最后一组时，未找到对应位置
        if (i >= mGroupItemStatus.size()) {
            itemStatus.setViewType(ItemStatus.VIEW_TYPE_SUB_ITEM); // 设置为二级标签类型
            itemStatus.setGroupItemIndex(i - 1); // 设置一级标签为最后一组
            itemStatus.setSubItemIndex(position - (itemCount - getChild(i - 1).size()));
        }
        return itemStatus;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == ItemStatus.VIEW_TYPE_GROUP_ITEM) {
            // parent需要传入对应的位置，否则列表项触发不了点击事件
            view = LayoutInflater.from(mContext).inflate(getGroupLayout(), parent, false);
            viewHolder = new BaseViewHolder(view);
        } else if (viewType == ItemStatus.VIEW_TYPE_SUB_ITEM) {
            view = LayoutInflater.from(mContext).inflate(getChildLayout(-1), parent, false);
            viewHolder = new BaseViewHolder(view);
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final ItemStatus itemStatus = getItemStatusByPosition(position); // 获取列表项状态

        if (itemStatus.getViewType() == ItemStatus.VIEW_TYPE_GROUP_ITEM) { // 组类型
            final int groupIndex = itemStatus.getGroupItemIndex(); // 组索引

            if (!isNotClose || (groupCanClick && onItemClickListener != null))
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!isNotClose) {
                            if (mGroupItemStatus.get(groupIndex)) { // 一级标题打开状态
                                mGroupItemStatus.set(groupIndex, false);
                                notifyItemRangeRemoved(holder.getAdapterPosition() + 1, getChild(groupIndex).size());
                            } else { // 一级标题关闭状态
                                if (onlyOpenOne) {
                                    initGroupItemStatus(false); // 1. 实现只展开一个组的功能，缺点是没有动画效果
                                    mGroupItemStatus.set(groupIndex, true);
                                    notifyDataSetChanged(); // 1. 实现只展开一个组的功能，缺点是没有动画效果
                                } else {
                                    mGroupItemStatus.set(groupIndex, true);
                                    notifyItemRangeInserted(holder.getAdapterPosition() + 1, getChild(groupIndex).size()); // 2. 实现展开可多个组的功能，带动画效果
                                }
                            }
                        }
                        if (groupCanClick && onItemClickListener != null)
                            onItemClickListener.onItemClick(holder.itemView, itemStatus.getGroupItemIndex(), -1);
                    }
                });

            if (onItemClickLongListener != null)
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        return onItemClickLongListener.onItemLongClick(holder.itemView, itemStatus.getGroupItemIndex(), -1);
                    }
                });

            setGroupViewHolder((BaseViewHolder) holder, mGroupItemStatus.get(groupIndex), groupIndex);
        } else if (itemStatus.getViewType() == ItemStatus.VIEW_TYPE_SUB_ITEM) { // 子项类型

            if (onItemClickListener != null)
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListener.onItemClick(holder.itemView, itemStatus.getGroupItemIndex(), itemStatus.getSubItemIndex());
                    }
                });

            if (onItemClickLongListener != null)
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        return onItemClickLongListener.onItemLongClick(holder.itemView, itemStatus.getGroupItemIndex(), itemStatus.getSubItemIndex());
                    }
                });

            setChildViewHolder((BaseViewHolder) holder, itemStatus.getGroupItemIndex(), itemStatus.getSubItemIndex());
        }
    }


    @Override
    public int getItemCount() {
        int itemCount = 0;

        if (0 == mGroupItemStatus.size()) {
            return itemCount;
        }

        for (int i = 0; i < list.size(); i++) {
            itemCount++; // 每个一级标题项+1
            if (mGroupItemStatus.get(i)) { // 二级标题展开时，再加上二级标题的数量
                itemCount += getChild(i).size();
            }
        }
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        return getItemStatusByPosition(position).getViewType();
    }

    /*----------------------*/
    public void setOnlyOpenOne(boolean onlyOpenOne) {
        this.onlyOpenOne = onlyOpenOne;
    }

    public void setDefaultExpand(boolean defaultExpand) {
        isDefaultExpand = defaultExpand;
    }

    protected boolean isDefaultExpand(int position) {
        return isDefaultExpand;
    }

    //设置默认是否可以关闭
    public void setNotClose() {
        isNotClose = true;
    }

    //设置默认是否可以关闭
    public void setCanClose() {
        isNotClose = false;
    }

    //某个Group是否展开
    public boolean hasGroupExpand(int groupPosition) {
        if (mGroupItemStatus == null || mGroupItemStatus.size() == 0)
            return false;
        return mGroupItemStatus.get(groupPosition);
    }

    //关闭某个Group
    public void closeGroupItem(int groupPosition, boolean animation) {
        if (mGroupItemStatus == null || mGroupItemStatus.size() == 0)
            return;
        mGroupItemStatus.set(groupPosition, false);
        if (animation) {
            notifyItemRangeInserted(groupPosition + 1, getChild(groupPosition).size()); // 2. 实现展开可多个组的功能，带动画效果
        } else
            notifyDataSetChanged();
    }

    //展开某个Group
    public void openGroupItem(int groupPosition, boolean animation) {
        if (mGroupItemStatus == null || mGroupItemStatus.size() == 0)
            return;
        mGroupItemStatus.set(groupPosition, true);
        if (animation) {
            notifyItemRangeRemoved(groupPosition + 1, getChild(groupPosition).size());
        } else
            notifyDataSetChanged();
    }

    //----------------------*******------------------------
    //正常Adapter的 监听
    private OnExpandItemClickListener onItemClickListener;
    private OnExpandItemClickLongListener onItemClickLongListener;

    // Item 点击
    public void setOnItemClickListener(OnExpandItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    // Item 点击
    public void setOnItemClickListener(boolean groupCanClick, OnExpandItemClickListener onItemClickListener) {
        this.groupCanClick = groupCanClick;
        this.onItemClickListener = onItemClickListener;
    }

    //Item 长按
    public void setOnItemClickLongListener(OnExpandItemClickLongListener onItemClickLongListener) {
        this.onItemClickLongListener = onItemClickLongListener;
    }

    /*------------------------------*/

    protected abstract int getGroupLayout();

    protected abstract int getChildLayout(int pos);

    protected abstract void setGroupViewHolder(BaseViewHolder holder, boolean aBoolean, int position);

    protected abstract void setChildViewHolder(BaseViewHolder baseViewHolder, int groupPosition, int childPosition);

    protected abstract List getChild(int position);
}
