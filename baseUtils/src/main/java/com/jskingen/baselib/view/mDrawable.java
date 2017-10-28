package com.jskingen.baselib.view;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Chne on 2017/10/21.
 *
 *  试题的选择效果 适合 checkBox与radioButton
 * */

public class mDrawable extends Drawable {

    private Paint mPaintCircle;
    private Paint mPaintText;
    private String mText = "";
    private int textSizePx = 30;
    private int mWidth;

//    private mDrawable(String mText, boolean checkStatue) {
//        this(mText, checkStatue, 60, Color.WHITE, Color.BLUE, Color.parseColor("#dfdfdf"));
//    }
//
//    private mDrawable(String mText, boolean checkStatue, @ColorInt int bgColor) {
//        this(mText, checkStatue, 60, Color.WHITE, bgColor, Color.parseColor("#dfdfdf"));
//    }

    private mDrawable(String mText, boolean checkStatue, int textSize_px, @ColorInt int textColor, @ColorInt int bgColor, @ColorInt int circleColor) {
        this.mText = mText;
        this.textSizePx = textSize_px;
        mWidth = (int) (textSize_px * 1.6f);

        mPaintCircle = new Paint();
        mPaintCircle.setAntiAlias(true);

        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
        mPaintText.setTextSize(textSizePx);

        if (checkStatue) {
            mPaintCircle.setColor(bgColor);
            mPaintText.setColor(textColor);
        } else {
            mPaintCircle.setColor(circleColor);
            mPaintCircle.setStrokeWidth(3);
            mPaintCircle.setStyle(Paint.Style.STROKE);

            mPaintText.setColor(bgColor);
        }
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawCircle(getIntrinsicHeight() / 2, getIntrinsicHeight() / 2, (float) (mWidth * 0.4), mPaintCircle);

        Rect bounds = new Rect();
        mPaintText.getTextBounds(mText, 0, mText.length(), bounds);
        canvas.drawText(mText, getIntrinsicHeight() / 2 - bounds.width() / 2, getIntrinsicHeight() / 2 + bounds.height() / 2, mPaintText);
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getIntrinsicWidth() {
//        return super.getIntrinsicWidth();
        return (int) (mWidth * 1.3);
    }

    @Override
    public int getIntrinsicHeight() {
//        return super.getIntrinsicHeight();
        return (int) (mWidth * 1.1);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSPARENT;
    }

    public static StateListDrawable getCheckDrawable(String mText, int textSize_px,
                                                     @ColorInt int textColor, @ColorInt int bgColor, @ColorInt int circleColor) {

        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_checked}, new mDrawable(mText, true, textSize_px, textColor, bgColor, circleColor));
//        drawable.addState(new int[]{android.R.attr.state_, android.R.attr.state_pressed}, mEnabledPressedDrawable);
        drawable.addState(new int[0], new mDrawable(mText, false, textSize_px, textColor, bgColor, circleColor));
        return drawable;
    }
}
