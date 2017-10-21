package com.jskingen.baseutils.utils.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by Chne on 2017/10/21.
 */

public class mRadioButton extends android.support.v7.widget.AppCompatRadioButton {


    public mRadioButton(Context context) {
        super(context);
        init();
    }

    public mRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public mRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.e("AAA", "" + isChecked());


        super.onDraw(canvas);
    }

    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}
