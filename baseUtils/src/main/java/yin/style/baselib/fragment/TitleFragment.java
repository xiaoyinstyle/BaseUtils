package yin.style.baselib.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import yin.style.baselib.R;
import yin.style.baselib.activity.view.TitleLayout;

/**
 * Created by Chne on 2017/8/12.
 */

public abstract class TitleFragment extends NormalFragment {
    protected TitleLayout title;

    @Override
    protected void addTitleLayout(ViewGroup rootView) {
        title = new TitleLayout(mContext);
        rootView.addView(title);

        //返回按键监听
        title.getIconLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.finish();
            }
        });

        setTitle(title);
    }

    protected abstract void setTitle(TitleLayout titleLayout);

    protected void hiddenBackButton() {

        title.getIconLeft().setVisibility(View.GONE);
    }

    protected void showTitle() {
        title.setVisibility(View.VISIBLE);
    }

    protected void hiddenTitle() {
        title.setVisibility(View.GONE);
    }
}
