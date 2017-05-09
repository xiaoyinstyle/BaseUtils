package yin.style.recyclerlib.inter;

import android.view.View;

/**
 * Created by ChneY on 2017/3/28.
 * <p>
 * 点击事件
 */

public interface OnExplandItemClickListener {
    void onItemClick(View view, int groupPosition, int childPosition);
}