package yin.style.recyclerlib.view;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by ChneY on 2017/5/13.
 */

public class FooterView extends LinearLayout {
    public FooterView(Context context) {
        this(context, null);
        init();
    }

    public FooterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public FooterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        setOrientation(LinearLayout.VERTICAL);
    }
}
