package yin.style.recyclerlib.inter;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by BangDu on 2017/12/18.
 */

public abstract class OnItemTouchListener implements RecyclerView.OnItemTouchListener {
    private RecyclerView recyclerView;
    private final GestureDetectorCompat mGestureDetector;

    public OnItemTouchListener(Context context) {
        mGestureDetector = new GestureDetectorCompat(context, new ItemTouchHelperGestureListener());
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        this.recyclerView = rv;

        mGestureDetector.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        this.recyclerView = rv;
        mGestureDetector.onTouchEvent(e);
        return false;
    }

    public abstract void onItemClick(RecyclerView.ViewHolder viewHolder);

    public abstract void onItemLongClick(RecyclerView.ViewHolder viewHolder);

    private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {

        public boolean onSingleTapUp(MotionEvent event) {
            View child = recyclerView.findChildViewUnder(event.getX(), event.getY());
            if (child != null) {
                RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(child);
                onItemClick(viewHolder);
            }
            return true;
        }

        public void onLongPress(MotionEvent event) {
            View child = recyclerView.findChildViewUnder(event.getX(), event.getY());
            if (child != null) {
                RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(child);
                onItemLongClick(viewHolder);
            }
        }
    }
}