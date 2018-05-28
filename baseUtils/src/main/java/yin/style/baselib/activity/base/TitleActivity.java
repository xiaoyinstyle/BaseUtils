package yin.style.baselib.activity.base;

import android.view.View;
import android.view.ViewGroup;

import yin.style.baselib.activity.view.TitleLayout;

/**
 * Created by ChneY on 2017/4/22.
 * <p>
 * 正常使用的带有标题的 Activity
 */

public abstract class TitleActivity extends NormalActivity {

    protected TitleLayout title;

    @Override
    protected final void addTitleLayout(ViewGroup rootView) {
        title = new TitleLayout(mContext);
        rootView.addView(title);

        //返回按键监听
        title.getIconLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        setTitle(title);
    }

    protected abstract void setTitle(TitleLayout titleLayout);

    protected final void hiddenBackButton() {
        title.getIconLeft().setVisibility(View.GONE);
    }

    protected void showTitle() {
        title.setVisibility(View.VISIBLE);
    }

    protected void hiddenTitle() {
        title.setVisibility(View.GONE);
    }
}
