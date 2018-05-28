package yin.style.sample.utils;

import android.os.Bundle;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import yin.style.baselib.activity.base.TitleActivity;
import yin.style.baselib.activity.view.TitleLayout;
import yin.style.baselib.utils.ScreenUtil;
import yin.style.baselib.utils.ToastUtils;
import yin.style.baselib.view.popupWindow.CommonPopupWindow;
import yin.style.sample.R;

import java.util.ArrayList;
import java.util.List;

import yin.style.recyclerlib.adapter.BaseQuickAdapter;
import yin.style.recyclerlib.holder.BaseViewHolder;
import yin.style.recyclerlib.inter.OnItemClickListener;

public class mPopWindowActivity extends TitleActivity implements CommonPopupWindow.ViewInterface {
    private CommonPopupWindow popupWindow;

    @Override
    protected void setTitle(TitleLayout titleLayout) {

    }

    @Override
    protected int getViewByXml() {
        return R.layout.activity_m_pop_window;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
    }

    @Override
    protected void initData() {

    }

    //向下弹出
    public void showDownPop(View view) {
        if (popupWindow != null && popupWindow.isShowing()) return;

        popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_down)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.AnimDown)
                .setViewOnclickListener(this)
                .setOutsideTouchable(true)
                .create();
        popupWindow.showAsDropDown(view);
    }

    //向右弹出
    public void showRightPop(View view) {
        if (popupWindow != null && popupWindow.isShowing()) return;

        popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_left_or_right)
                .setWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.AnimHorizontal)
//                .setViewOnclickListener(this)
                .create();
        popupWindow.showAsDropDown(view, view.getWidth(), -view.getHeight());
    }

    //向左弹出
    public void showLeftPop(View view) {
        if (popupWindow != null && popupWindow.isShowing()) return;

        popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_left_or_right)
                .setWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.AnimRight)
//                .setViewOnclickListener(this)
                .create();
        popupWindow.showAsDropDown(view, -popupWindow.getWidth(), -view.getHeight());
    }


    //全屏弹出
    public void showAll(View view) {
        if (popupWindow != null && popupWindow.isShowing()) return;

        View upView = LayoutInflater.from(this).inflate(R.layout.popup_up, null);
        //测量View的宽高
        CommonPopupWindow.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_up)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                .setBackGroundLevel(0.5f)//取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(this)
                .create();
        popupWindow.showAtLocation(findViewById(R.id.root), Gravity.BOTTOM, 0, 0);
    }

    //向上弹出
    public void showUpPop(View view) {
        if (popupWindow != null && popupWindow.isShowing()) return;

        popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_left_or_right)
                .setWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//                .setViewOnclickListener(this)
                .create();
        popupWindow.showAsDropDown(view, 0, -(popupWindow.getHeight() + view.getMeasuredHeight()));
    }


    public void showReminder(View view) {
        if (popupWindow != null && popupWindow.isShowing()) return;
        popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.query_info)
                .setWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .create();
        popupWindow.showAsDropDown(view, (int) (-popupWindow.getWidth() + ScreenUtil.dp2px(this, 20)), -(popupWindow.getHeight() + view.getMeasuredHeight()));
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        //获得PopupWindow布局里的View
        switch (layoutResId) {
            case R.layout.popup_down:
                RecyclerView recycle_view = (RecyclerView) view.findViewById(R.id.recycle_view);
                recycle_view.setLayoutManager(new GridLayoutManager(this, 3));
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 30; i++) {
                    list.add("Android");
                }

                BaseQuickAdapter mAdapter = new BaseQuickAdapter<String>(mContext, list) {
                    @Override
                    protected int getLayoutResId() {
                        return R.layout.item_recyclerview;
                    }

                    @Override
                    protected void setViewHolder(BaseViewHolder baseViewHolder, String s, int position) {
                        baseViewHolder.setText(R.id.text, list.get(position));
                    }
                };
                recycle_view.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        ToastUtils.show("" + position);
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                    }
                });
                break;
            case R.layout.popup_up:
                Button btn_take_photo = (Button) view.findViewById(R.id.btn_take_photo);
                Button btn_select_photo = (Button) view.findViewById(R.id.btn_select_photo);
                Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
                btn_take_photo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.show("拍照");
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                    }
                });
                btn_select_photo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.show("相册选取");
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                    }
                });
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                    }
                });
                view.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                        return true;
                    }
                });
                break;
//            case R.layout.popup_left_or_right:
//                TextView tv_like = (TextView) view.findViewById(R.id.tv_like);
//                TextView tv_hate = (TextView) view.findViewById(R.id.tv_hate);
//                tv_like.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        toast("赞一个");
//                        popupWindow.dismiss();
//                    }
//                });
//                tv_hate.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        toast("踩一下");
//                        popupWindow.dismiss();
//                    }
//                });
//                break;
        }
    }
}
