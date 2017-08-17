package yin.style.notes.view.NinePoint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by Chne on 2017/8/12.
 * <p>
 * 九宫格图案解锁
 */
public class PatternLockLayout extends RelativeLayout {
    public PatternLockLayout(Context context) {
        super(context);
    }

    public PatternLockLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PatternLockLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private boolean hasinit;                //初始化是否完成
    private PatternPoint[] points = new PatternPoint[9];        //九个圆圈对象
    private int width, height, side;                        //布局可用宽，布局可用高，小方格子的边长
    private int sidePadding, topBottomPadding;      //侧边和上下边预留空间

    private boolean startLine;      //是否开始连线
    private boolean errorMode;      //连线是否使用表示错误的颜色
    private boolean drawEnd;        //是否已经抬手
    private boolean resetFinished;  //重置是否已经完成(是否可以进行下一次连线)
    private float moveX, moveY;     //手指位置
    private ArrayList<PatternPoint> selectedPoints = new ArrayList<>();     //所有已经选中的点

    private static final int PAINT_COLOR_NORMAL = 0xffcccccc;
    private static final int PAINT_COLOR_SELECTED = 0xff00dd00;
    private static final int PAINT_COLOR_ERROR = 0xffdd0000;

    private Handler mHandler;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (widthMeasureSpec > heightMeasureSpec)
            super.onMeasure(heightMeasureSpec, heightMeasureSpec);
        else
            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (!hasinit) {
            //暂时写死，后面通过XML设置
            sidePadding = 40;
            topBottomPadding = 40;
            initPoints();
            resetFinished = true;
        }

        drawCircle(canvas);
        drawLine(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        moveX = event.getX();
        moveY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                int index = whichPointArea();
                if (-1 != index && resetFinished) {
                    addSelectedPoint(index);
                    startLine = true;
                }
            }
            break;
            case MotionEvent.ACTION_MOVE: {
                if (startLine && resetFinished) {
                    int index = whichPointArea();
                    if (-1 != index && points[index].status == PatternPointBase.STATE_NORMAL) {
                        //查看是否有中间插入点
                        insertPointIfNeeds(index);
                        //增加此点到队列中
                        addSelectedPoint(index);
                    }
                }
            }
            break;
            case MotionEvent.ACTION_UP: {
                if (startLine && resetFinished) {
                    resetFinished = false;
                    int delay = processFinish();
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            reset();
                        }
                    }, delay);
                }
            }
            break;
        }

        invalidate();

        return true;
    }


    public void setAllSelectedPointsError() {
        errorMode = true;
        for (PatternPoint point : selectedPoints) {
            point.status = PatternPointBase.STATE_ERROR;
        }
        invalidate();
    }

    private void reset() {
        for (PatternPoint point : points) {
            point.status = PatternPointBase.STATE_NORMAL;
        }
        selectedPoints.clear();
        startLine = false;
        errorMode = false;
        drawEnd = false;
        if (listener != null) {
            listener.onReset();
        }
        resetFinished = true;
        invalidate();
    }

    //返回值为reset延迟的毫秒数
    private int processFinish() {
        drawEnd = true;
        if (selectedPoints.size() < 2) {
            return 0;
        } else            //长度过短、密码错误的判断留给外面
        {
            int size = selectedPoints.size();
            StringBuilder sbPassword = new StringBuilder();
            for (int i = 0; i < size; i++) {
                sbPassword.append(selectedPoints.get(i).tag);
            }
            if (listener != null) {
                listener.onFinish(sbPassword.toString(), size);
            }
            return 800;
        }
    }

    public interface OnPatternStateListener {
        void onFinish(String password, int sizeOfPoints);

        void onReset();
    }

    private OnPatternStateListener listener;

    public void setOnPatternStateListener(OnPatternStateListener listener) {
        this.listener = listener;
    }

    private void insertPointIfNeeds(int curIndex) {
        final int[][] middleNumMatrix = new int[][]{{-1, -1, 1, -1, -1, -1, 3, -1, 4}, {-1, -1, -1, -1, -1, -1, -1, 4, -1}, {1, -1, -1, -1, -1, -1, 4, -1, 5}, {-1, -1, -1, -1, -1, 4, -1, -1, -1}, {-1, -1, -1, -1, -1, -1, -1, -1, -1}, {-1, -1, -1, 4, -1, -1, -1, -1, -1}, {3, -1, 4, -1, -1, -1, -1, -1, 7}, {-1, 4, -1, -1, -1, -1, -1, -1, -1}, {4, -1, 5, -1, -1, -1, 7, -1, -1}};

        int selectedSize = selectedPoints.size();
        if (selectedSize > 0) {
            int lastIndex = Integer.parseInt(selectedPoints.get(selectedSize - 1).tag) - 1;
            int middleIndex = middleNumMatrix[lastIndex][curIndex];
            if (middleIndex != -1 && (points[middleIndex].status == PatternPointBase.STATE_NORMAL) && (points[curIndex].status == PatternPointBase.STATE_NORMAL)) {
                addSelectedPoint(middleIndex);
            }

        }
    }

    private void addSelectedPoint(int index) {
        selectedPoints.add(points[index]);
        points[index].status = PatternPointBase.STATE_SELECTED;
    }

    private int whichPointArea() {
        for (int i = 0; i < 9; i++) {
            if (points[i].isPointArea(moveX, moveY)) {
                return i;
            }
        }
        return -1;
    }

    private void drawLine(Canvas canvas) {
        Paint paint = getCirclePaint(errorMode ? PatternPoint.STATE_ERROR : PatternPoint.STATE_SELECTED);
        paint.setStrokeWidth(15);

        for (int i = 0; i < selectedPoints.size(); i++) {
            if (i != selectedPoints.size() - 1)      //连接线
            {
                PatternPoint first = selectedPoints.get(i);
                PatternPoint second = selectedPoints.get(i + 1);
                canvas.drawLine(first.getCenterX(), first.getCenterY(),
                        second.getCenterX(), second.getCenterY(), paint);
            } else if (!drawEnd)                        //自由线,抬手之后就不用画了
            {
                PatternPoint last = selectedPoints.get(i);
                canvas.drawLine(last.getCenterX(), last.getCenterY(),
                        moveX, moveY, paint);
            }
        }
    }

    private void drawCircle(Canvas canvas) {
        for (int i = 0; i < 9; i++) {
            PatternPoint point = points[i];
            Paint paint = getCirclePaint(point.status);
            canvas.drawCircle(point.getCenterX(), point.getCenterY(), points[i].getRadius(), paint);
        }
    }

    private void initPoints() {
        width = getWidth() - getPaddingLeft() - getPaddingRight() - sidePadding * 2;
        height = getHeight() - getPaddingTop() - getPaddingBottom() - topBottomPadding * 2;

        //使用时暂定强制竖屏
        int left, top;
        left = getPaddingLeft() + sidePadding;
        top = height + getPaddingTop() + topBottomPadding - width;
        side = width / 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int leftX = left + j * side;
                int topY = top + i * side;
                int index = i * 3 + j;
                points[index] = new PatternPoint(leftX, topY, side, side / 3, String.valueOf(index + 1));
            }
        }

        mHandler = new Handler();

        hasinit = true;
    }

    private Paint getCirclePaint(int state) {
        Paint paint = new Paint();
        switch (state) {
            case PatternPoint.STATE_NORMAL:
                paint.setColor(PAINT_COLOR_NORMAL);
                break;
            case PatternPoint.STATE_SELECTED:
                paint.setColor(PAINT_COLOR_SELECTED);
                break;
            case PatternPoint.STATE_ERROR:
                paint.setColor(PAINT_COLOR_ERROR);
                break;
            default:
                paint.setColor(PAINT_COLOR_NORMAL);
        }
        return paint;
    }
}
