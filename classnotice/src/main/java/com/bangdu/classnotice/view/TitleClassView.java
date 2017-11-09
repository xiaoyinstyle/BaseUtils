package com.bangdu.classnotice.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.bumptech.glide.load.resource.transcode.BitmapToGlideDrawableTranscoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by BangDu on 2017/11/9.
 */

public class TitleClassView extends View {

    private String text = "ewfewfref";
    private List<Map<String, Integer>> mListPoint = new ArrayList<Map<String, Integer>>();

    Paint mPaintText;
    Paint mPaintLine;

    BitmapShader mBitmapShader;

    public TitleClassView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TitleClassView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TitleClassView(Context context) {
        super(context);
        init();
    }

    private void init() {
        mPaintText = new Paint();
        mPaintText.setColor(Color.WHITE);
        mPaintText.setAntiAlias(true);
        mPaintText.setTextSize(35);
        mPaintText.setFakeBoldText(true);


        mPaintLine = new Paint();
        mPaintLine.setColor(Color.BLACK);
        mPaintLine.setAntiAlias(true);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmapShader == null)
            createBitMap();

        RectF rectF = new RectF(0, 0, getWidth(), getHeight());
        Path path = new Path();
        path.addArc(rectF, -145, 110);

        //        Paint arcPath = new Paint();
//        arcPath.setColor(Color.BLACK);
//        arcPath.setStyle(Paint.Style.STROKE);
//        arcPath.setStrokeWidth(2);
//        canvas.drawArc(rectF, -135, 90, true, arcPath);

        mPaintText.setShader(null);
        canvas.drawTextOnPath(text, path, 0, 35, mPaintText);

        mPaintText.setShader(mBitmapShader);
        canvas.drawTextOnPath(text, path, 0, 35, mPaintText);
    }

    private void createBitMap() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        float a = 0;
        while (a < (getWidth() + getHeight())) {
            a = a + 7;
            canvas.drawLine(a, 0, 0, a, mPaintLine);
        }

        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
    }

    public void setText(String text) {
        this.text = text;
        invalidate();
    }
}