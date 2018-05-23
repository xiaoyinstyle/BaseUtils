package yin.style.recyclerlib.adapter;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import yin.style.recyclerlib.flowlayoutmanager.FlowLayoutManager;
import yin.style.recyclerlib.holder.BaseViewHolder;
import yin.style.recyclerlib.inter.OnExpandItemClickListener;
import yin.style.recyclerlib.inter.OnExpandItemClickLongListener;


/**
 * 可以展开的 Expland adapter
 * 内部 RecyclerView  可以是网格、流式布局、线性、瀑布流 (VERTICAL/HORIZONTAL)
 *
 * @author chenyin
 * @date 2017/3/28
 */
public abstract class BaseExpandAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    public static final int LAYOUT_GRID = 1;//网格
    public static final int LAYOUT_FLOW = 2;//流式
    public static final int LAYOUT_LINER = 3;//线性
    public static final int LAYOUT_STAGGER_VERTICAL = 4;//瀑布流 VERTICAL
    public static final int LAYOUT_STAGGER_HORIZONTAL = 5;//瀑布流 HORIZONTAL

    @IntDef({LAYOUT_GRID, LAYOUT_FLOW, LAYOUT_LINER, LAYOUT_STAGGER_VERTICAL, LAYOUT_STAGGER_HORIZONTAL})
    public @interface LAYOUT_TYPE {
    }

    protected List<T> list;
    private Context mContext;
    private boolean groupCanClick = false;//group是否有点击监听
    private RecyclerView.ItemDecoration itemDecoration;//间隔线
    private boolean isShowGroupItem[] = new boolean[0];

    private boolean isDefaultExpand = false;//初始化 是否展开 默认：false
    private boolean isNotClose = false;//初始化 不可关闭


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

                    if (!isNotClose) {
                        if (!isShowGroupItem[position]) {
                            ((ItemViewHolder) holder).mGroup.setVisibility(View.VISIBLE);
                            isShowGroupItem[position] = true;
                        } else {
                            ((ItemViewHolder) holder).mGroup.setVisibility(View.GONE);
                            isShowGroupItem[position] = false;
                        }
                        setGroupViewHolder(holder, isShowGroupItem[position], position);
                    }
                }
            });

            if (onItemClickLongListener != null && groupCanClick)
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        onItemClickLongListener.onItemLongClick(holder.itemView, position, -1);
                        return true;
                    }
                });

            if (((ViewGroup) holder.itemView).getChildCount() == 1)
                ((ViewGroup) holder.itemView).addView(((ItemViewHolder) holder).mGroup);

            if (isShowGroupItem[position])
                ((ItemViewHolder) holder).mGroup.setVisibility(View.VISIBLE);
            else
                ((ItemViewHolder) holder).mGroup.setVisibility(View.GONE);

            //添加内部布局
            ((ItemViewHolder) holder).mGroup.setLayoutManager(getLayoutManager(position));

            if (itemDecoration != null)
                ((ItemViewHolder) holder).mGroup.addItemDecoration(itemDecoration);

            ((ItemViewHolder) holder).mGroup.setAdapter(new ItemExpandAdapter(getChild(position), position) {
                @Override
                protected int getChildLayout() {
                    return BaseExpandAdapter.this.getChildLayout(position);
                }

                @Override
                protected void setViewHolder(BaseViewHolder holder, int groupPosition, int childPosition) {
                    BaseExpandAdapter.this.setChildViewHolder(holder, groupPosition, childPosition);
                }
            }.setOnItemClickListener(new OnExpandItemClickListener() {
                @Override
                public void onItemClick(View view, int groupPosition, int childPosition) {
                    if (onItemClickListener != null)
                        onItemClickListener.onItemClick(holder.itemView, groupPosition, childPosition);
                }
            }).setOnItemClickLongListener(new OnExpandItemClickLongListener() {
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
        //初始化 Expand 默认是否 展开状态
        if (isShowGroupItem == null || isShowGroupItem.length != i) {
            isShowGroupItem = new boolean[i];
            for (int j = 0; j < isShowGroupItem.length; j++) {
                if (isNotClose)
                    isShowGroupItem[j] = true;
                else
                    isShowGroupItem[j] = isDefaultExpand(i);
            }
        }

        return i;
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

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        this.itemDecoration = itemDecoration;
    }

    protected class ItemViewHolder<T> extends BaseViewHolder {
        public RecyclerView mGroup;

        public ItemViewHolder(View view) {
            super(view);
            mGroup = new RecyclerView(mContext);
            mGroup.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);//beforeDescendants
        }
    }

    //---------************************************************---------------
    protected abstract List getChild(int position);

    protected abstract int getGroupLayout();

    protected abstract int getChildLayout(int position);

    protected abstract void setGroupViewHolder(BaseViewHolder baseViewHolder, boolean isOpenGroup, int groupPosition);

    protected abstract void setChildViewHolder(BaseViewHolder baseViewHolder, int groupPosition, int childPosition);

    /**
     * 自定义   RecyclerView.LayoutManager
     *
     * @param position
     * @return
     */
    protected RecyclerView.LayoutManager getLayoutManager(int position) {
        if (getLayoutType(position) == LAYOUT_GRID)
            return new GridLayoutManager(mContext, getGridCount(position));
        else if (getLayoutType(position) == LAYOUT_FLOW) {
            FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
            flowLayoutManager.setAutoMeasureEnabled(true);
            return flowLayoutManager;
        } else if (getLayoutType(position) == LAYOUT_STAGGER_VERTICAL)
            return new StaggeredGridLayoutManager(getGridCount(position), StaggeredGridLayoutManager.VERTICAL);
        else if (getLayoutType(position) == LAYOUT_STAGGER_HORIZONTAL)
            return new StaggeredGridLayoutManager(getGridCount(position), StaggeredGridLayoutManager.HORIZONTAL);
        else // (getLayoutType(position) == LAYOUT_LINER)
            return new LinearLayoutManager(mContext);
    }

    /**
     * 自定义   RecyclerView.LayoutManager 类型
     *
     * @param position
     * @return
     */
    @LAYOUT_TYPE
    protected int getLayoutType(int position) {
        return LAYOUT_GRID;
    }

    /**
     * 设置展开的 RecyclerView 的Grid的列数
     */
    protected int getGridCount(int position) {
        return 1;
    }

    /*-------------------------------------*/
    /*-------------------------------------*/
    //设置默认 是否展开、或者关闭
    public void setDefaultExpand(boolean defaultExpand) {
        isDefaultExpand = defaultExpand;
    }

    //设置默认是否可以关闭
    public void setNotClose() {
        isNotClose = true;
    }

    //设置默认是否可以关闭
    public void setCanClose() {
        isNotClose = false;
    }

    protected boolean isDefaultExpand(int position) {
        return isDefaultExpand;
    }

    //某个Group是否展开
    public boolean hasGroupExpand(int groupPosition) {
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
