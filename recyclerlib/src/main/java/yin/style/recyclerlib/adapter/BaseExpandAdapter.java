package yin.style.recyclerlib.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import yin.style.recyclerlib.holder.BaseViewHolder;
import yin.style.recyclerlib.inter.OnExplandItemClickListener;
import yin.style.recyclerlib.inter.OnExplandItemClickLongListener;


/**
 * @author chenyin
 * @date 2017/3/28
 */
public abstract class BaseExpandAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    protected List<T> list;
    private Context mContext;
    private boolean groupCanClick = false;//group是否有点击监听
    private RecyclerView.ItemDecoration itemDecoration;//间隔线
    private boolean isShowGroupItem[] = new boolean[0];


    public BaseExpandAdapter(Context context, List list) {
        this.list = list;
        mContext = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout linearLayout = new LinearLayout(parent.getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.addView(LayoutInflater.from(parent.getContext()).inflate(getGroupLayout(), parent, false));
        return new ItemViewHolder(linearLayout);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {

            setGroupViewHolder(holder, isShowGroupItem[position], position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null && groupCanClick)
                        onItemClickListener.onItemClick(holder.itemView, position, -1);

                    if (!isShowGroupItem[position]) {
                        ((ItemViewHolder) holder).mGroup.setVisibility(View.VISIBLE);
                        isShowGroupItem[position] = true;
                    } else {
                        ((ItemViewHolder) holder).mGroup.setVisibility(View.GONE);
                        isShowGroupItem[position] = false;
                    }
                    setGroupViewHolder(holder, isShowGroupItem[position], position);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onItemClickLongListener != null && groupCanClick)
                        onItemClickLongListener.onItemLongClick(holder.itemView, position, -1);
                    return false;
                }
            });
            if (((ViewGroup) holder.itemView).getChildCount() == 1)
                ((ViewGroup) holder.itemView).addView(((ItemViewHolder) holder).mGroup);

            if (isShowGroupItem[position])
                ((ItemViewHolder) holder).mGroup.setVisibility(View.VISIBLE);
            else
                ((ItemViewHolder) holder).mGroup.setVisibility(View.GONE);

            ((ItemViewHolder) holder).mGroup.setLayoutManager(new LinearLayoutManager(mContext));
            if (itemDecoration != null)
                ((ItemViewHolder) holder).mGroup.addItemDecoration(itemDecoration);

            ((ItemViewHolder) holder).mGroup.setAdapter(new ItemExpandAdapter(getChild(position), position) {
                @Override
                protected int getChildLayout() {
                    return BaseExpandAdapter.this.getChildLayout();
                }

                @Override
                protected void setViewHolder(BaseViewHolder holder, int groupPosition, int childPosition) {
                    BaseExpandAdapter.this.setChildViewHolder(holder, groupPosition, childPosition);
                }
            }.setOnItemClickListener(new OnExplandItemClickListener() {
                @Override
                public void onItemClick(View view, int groupPosition, int childPosition) {
                    if (onItemClickListener != null)
                        onItemClickListener.onItemClick(holder.itemView, groupPosition, childPosition);
                }
            }).setOnItemClickLongListener(new OnExplandItemClickLongListener() {
                @Override
                public void onItemLongClick(View view, int groupPosition, int childPosition) {
                    if (onItemClickLongListener != null)
                        onItemClickLongListener.onItemLongClick(holder.itemView, groupPosition, childPosition);
                }
            }));
        }
    }

    @Override
    public int getItemCount() {
        int i = list.isEmpty() ? 0 : list.size();
        if (isShowGroupItem == null || isShowGroupItem.length != i)
            isShowGroupItem = new boolean[i];
        return i;
    }

    //----------------------*******------------------------
    //正常Adapter的 监听
    private OnExplandItemClickListener onItemClickListener;
    private OnExplandItemClickLongListener onItemClickLongListener;

    // Item 点击
    public void setOnItemClickListener(OnExplandItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    // Item 点击
    public void setOnItemClickListener(boolean groupCanClick, OnExplandItemClickListener onItemClickListener) {
        this.groupCanClick = groupCanClick;
        this.onItemClickListener = onItemClickListener;
    }

    //Item 长按
    public void setOnItemClickLongListener(OnExplandItemClickLongListener onItemClickLongListener) {
        this.onItemClickLongListener = onItemClickLongListener;
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        this.itemDecoration = itemDecoration;
    }

    class ItemViewHolder<T> extends BaseViewHolder {
        public RecyclerView mGroup;

        public ItemViewHolder(View view) {
            super(view);
            mGroup = new RecyclerView(mContext);
        }
    }

    //---------
    protected abstract List getChild(int position);

    protected abstract int getGroupLayout();

    protected abstract int getChildLayout();

    protected abstract void setGroupViewHolder(BaseViewHolder holder, boolean isOpenGroup, int groupPosition);

    protected abstract void setChildViewHolder(BaseViewHolder holder, int groupPosition, int childPosition);

    /*-------------------**/
    //某个Group是否展开
    public boolean hasGroupExpland(int groupPosition) {
        if (isShowGroupItem == null || isShowGroupItem.length == 0)
            return false;
        return isShowGroupItem[groupPosition];
    }

    //关闭某个Group
    public void closeGroupItem(int groupPosition) {
        if (isShowGroupItem == null || isShowGroupItem.length == 0)
            return;
        isShowGroupItem[groupPosition] = false;
        notifyDataSetChanged();
    }

    //展开某个Group
    public void openGroupItem(int groupPosition) {
        if (isShowGroupItem == null || isShowGroupItem.length == 0)
            return;
        isShowGroupItem[groupPosition] = true;
        notifyDataSetChanged();
    }
}
