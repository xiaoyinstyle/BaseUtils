package yin.style.recyclerlib.inter;

import android.view.View;

/**
 * Created by ChneY on 2017/3/28.
 * <p>
 * 长按事件
 */

public interface OnExpandItemClickLongListener {
    void onItemLongClick(View view, int groupPosition, int childPosition);
}

