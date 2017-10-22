package com.jskingen.baseutils.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by 陈银 on 2017/10/20 08:55
 * 描述：
 */

public class RoundDrawable extends TextView {
    /**
     * 需要绘制的文字
     */
    private String mText;

    private Paint mPaintText;
    private Paint mPaintCircle;

    private int mTextHeight;
    /**
     * 文本的颜色
     */
    private int mTextColor;
    /**
     * 文本的大小
     */
    private int mTextSize;
    /**
     * 绘制时控制文本绘制的范围
     */
    private Rect mBound;

    public RoundDrawable(Context context) {
        super(context);
        init();
    }

    public RoundDrawable(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundDrawable(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {

        mText = "A";
//        BitmapShader bitmapShader = new BitmapShader(mBitmap, TileMode.CLAMP, TileMode.CLAMP);
        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
        mPaintText.setColor(Color.GREEN);
        mPaintText.setTextSize((float) (mTextHeight * 0.8));

        mPaintCircle = new Paint();
        mPaintCircle.setAntiAlias(true);
        mPaintCircle.setColor(Color.BLUE);
        mPaintCircle.setStrokeWidth(5);
        mPaintCircle.setStyle(Paint.Style.FILL);

        mBound = new Rect();
        mPaintText.getTextBounds(mText, 0, mText.length(), mBound);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawCircle(30, 30, mPaint);
        canvas.drawCircle(30, 30, 30, mPaintCircle);
        canvas.drawText(mText, getHeight() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaintText);

        Drawable[] drawables = getCompoundDrawables();
        if(drawables != null){
            Drawable leftDrawable = drawables[0]; //drawableLeft
            if(leftDrawable !=null || rightDrawable != null){
                //1,获取text的width
                float textWidth = getPaint().measureText(getText().toString());
                //2,获取padding
                int drawablePadding = getCompoundDrawablePadding();
                int drawableWidth;
                float bodyWidth;
                if(leftDrawable !=null){
                    //3,获取drawable的宽度
                    drawableWidth = leftDrawable.getIntrinsicWidth();
                    //4,获取绘制区域的总宽度
                    bodyWidth= textWidth + drawablePadding + drawableWidth;
                }else{
                    drawableWidth = rightDrawable.getIntrinsicWidth();
                    bodyWidth= textWidth + drawablePadding + drawableWidth;
                    //图片居右设置padding
                    setPadding(0, 0, (int)(getWidth() - bodyWidth), 0);
                }
                canvas.translate((getWidth() - bodyWidth)/2,0);
            }
        }
        super.onDraw(canvas);
    }
}