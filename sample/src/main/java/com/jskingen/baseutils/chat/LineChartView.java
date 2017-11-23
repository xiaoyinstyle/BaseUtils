package com.jskingen.baseutils.chat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


/**
 * 自定义组件：条形统计图
 */
public class LineChartView extends View {
    private Paint xLinePaint;// 坐标轴 轴线 画笔：
    private Paint hLinePaint;// 坐标轴水平内部 虚线画笔
    private Paint titlePaint;// 绘制文本的画笔
    private Paint paint;// 矩形画笔 柱状图的样式信息
    private Paint textPaint;// 矩形画笔 柱状图的样式信息
    private boolean showText = true;// 在柱状图上显示数字
    private float maxValue = 10000;

    private List<ChatXEntry> entryXList = new ArrayList<>();
    private List<ChatYEntry> entryYList = new ArrayList<>();

    private final int spaceLeft = 40;
    private Paint SpacePaint;

    private Paint circlePaint;
    private Paint linePaint;
    private Path linePath;
    private List<Float> xList = new ArrayList<>();
    private List<Float> yList = new ArrayList<>();

    private int colorCircle = Color.GREEN;
    private int colorLine = Color.RED;
    private int colorRect = Color.YELLOW;
    private int colorText = Color.BLACK;
    private int colorTitleText = Color.CYAN;
    private int textSize_SP = 10;
    private int textTitleSize_SP = 8;
    private  int lineWidth = 8;

    public LineChartView(Context context) {
        super(context);
        init();
    }

    public LineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        SpacePaint = new Paint();

        hLinePaint = new Paint();
        hLinePaint.setColor(Color.LTGRAY);
        hLinePaint.setTextAlign(Paint.Align.CENTER);

        circlePaint = new Paint();
        circlePaint.setColor(Color.LTGRAY);
        circlePaint.setTextAlign(Paint.Align.CENTER);

        paint = new Paint();
        paint.setColor(colorRect);
        paint.setAntiAlias(true);// 抗锯齿效果
        paint.setStyle(Paint.Style.FILL);

        textPaint = new Paint();
        textPaint.setColor(colorText);
        textPaint.setAntiAlias(true);// 抗锯齿效果
        textPaint.setTextSize(sp2px(textSize_SP));// 字体大小

        linePaint = new Paint();
        linePaint.setColor(colorLine);
        linePaint.setAntiAlias(true);// 抗锯齿效果
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(lineWidth);

        // 给画笔设置颜色
        xLinePaint = new Paint();
        xLinePaint.setColor(Color.DKGRAY);

        // 绘制 Y 周坐标
        titlePaint = new Paint();
        titlePaint.setColor(colorTitleText);
        titlePaint.setTextAlign(Paint.Align.RIGHT);
        titlePaint.setTextSize(sp2px(textTitleSize_SP));
        titlePaint.setAntiAlias(true);
        titlePaint.setStyle(Paint.Style.FILL);

        //初始化Path
        linePath = new Path();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight() - dp2px(50);
        // 绘制底部的线条
        canvas.drawLine(dp2px(spaceLeft), height + dp2px(3),
                width - dp2px(spaceLeft), height + dp2px(3),
                xLinePaint);

        int leftHeight = height - dp2px(5);// 左侧外周的 需要划分的高度：


        // 设置左部的数字
        for (int i = 0; i < entryYList.size(); i++) {
            canvas.drawText(entryYList.get(i).getText(),
                    dp2px(25), dp2px(13) + (1 - entryYList.get(i).getValue()) * leftHeight,
                    titlePaint);
        }
        // 设置左部的间隔线
        for (int i = 0; i < entryYList.size(); i++) {
            SpacePaint.setColor(entryYList.get(i).getColor() == 0 ? Color.WHITE : entryYList.get(i).getColor());

            Rect rect = new Rect();// 左部的间隔线
            rect.left = dp2px(spaceLeft - lineWidth);
            rect.right = dp2px(spaceLeft);
            rect.top = (int) (dp2px(10) + (1 - entryYList.get(i).getValue()) * leftHeight);
            rect.bottom = i > 0 ? (int) (dp2px(10)
                    + (1 - entryYList.get(i - 1).getValue()) * leftHeight) : (dp2px(10) + leftHeight);

            canvas.drawRect(rect, SpacePaint);
        }

        // 设置水平虚线
        for (int i = 0; i < entryYList.size(); i++) {
            canvas.drawLine(dp2px(spaceLeft), dp2px(10) + (1 - entryYList.get(i).getValue()) * leftHeight,
                    width - dp2px(spaceLeft), dp2px(10) + (1 - entryYList.get(i).getValue()) * leftHeight, hLinePaint);
        }


        // 绘制 X 周 做坐标
        int xAxisLength = width - dp2px(spaceLeft);
        int columCount = entryXList.size() + 1;
        int step = xAxisLength / columCount;

        // 设置底部的数字
        for (int i = 0; i < columCount - 1; i++) {
            // text, baseX, baseY, textPaint
            canvas.drawText(entryXList.get(i).getText(),
                    dp2px(25) + step * (i + 1), height + dp2px(20),
                    titlePaint);
        }


        linePath.reset();
        xList.clear();
        yList.clear();
        // 绘制矩形
        if (entryXList != null && entryXList.size() > 0) {
            for (int i = 0; i < entryXList.size(); i++) {// 循环遍历将7条柱状图形画出来
                float value = entryXList.get(i).getValue();

                Rect rect = new Rect();// 柱状图的形状

                rect.left = (int) (dp2px(spaceLeft) * 0.25 + step * (i + 1));
                rect.right = (int) (rect.left + dp2px(spaceLeft) * 0.5);
                int rh = (int) (leftHeight - leftHeight * (value / maxValue));
                rect.top = rh + dp2px(10);
                rect.bottom = height;

                canvas.drawRect(rect, paint);
                // 是否显示柱状图上方的数字
                if (showText) {
                    canvas.drawText(value + "",
                            dp2px(15) + step * (i + 1) - dp2px(15), rh + dp2px(5),
                            textPaint);
                }

                float x = (float) (dp2px(spaceLeft) * 0.5 + step * (i + 1));
                float y = rect.top;
                xList.add(x);
                yList.add(y);
                if (i == 0)
                    linePath.moveTo(x, y);
                else
                    linePath.lineTo(x, y);


            }

            canvas.drawPath(linePath, linePaint);

            for (int i = 0; i < xList.size(); i++) {
                circlePaint.setColor(Color.WHITE);
                canvas.drawCircle(xList.get(i), yList.get(i), 8, circlePaint);
                circlePaint.setColor(colorCircle);
                canvas.drawCircle(xList.get(i), yList.get(i), 4, circlePaint);
            }
        }

    }

    private int dp2px(int value) {
        float v = getContext().getResources().getDisplayMetrics().density;
        return (int) (v * value + 0.5f);
    }

    private int sp2px(int value) {
        float v = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (v * value + 0.5f);
    }

    /**
     * @param xEntrys
     * @param yEntrys
     */
    public void start(List<ChatXEntry> xEntrys, List<ChatYEntry> yEntrys) {
        init();
        entryXList.clear();
        entryXList.addAll(xEntrys);

        entryYList.clear();
        entryYList.addAll(yEntrys);
        invalidate();

    }

    public void setColorCircle(int colorCircle) {
        this.colorCircle = colorCircle;
    }

    public void setColorLine(int colorLine) {
        this.colorLine = colorLine;
    }

    public void setColorRect(int colorRect) {
        this.colorRect = colorRect;
    }

    public void setColorText(int colorText) {
        this.colorText = colorText;
    }

    public void setColorTitleText(int colorTitleText) {
        this.colorTitleText = colorTitleText;
    }

    public void setTextSize_SP(int textSize_SP) {
        this.textSize_SP = textSize_SP;
    }

    public void setTextTitleSize_SP(int textTitleSize_SP) {
        this.textTitleSize_SP = textTitleSize_SP;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    public void setShowText(boolean showText) {
        this.showText = showText;
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }
}
