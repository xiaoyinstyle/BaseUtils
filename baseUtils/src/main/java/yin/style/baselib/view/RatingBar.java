package yin.style.baselib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import yin.style.baselib.R;

public class RatingBar extends View {

    private boolean mStarEnable = true;//可以编辑

    // 正常、半个和选中的星星
    private Bitmap mStarNormal, mStarHalf, mStarSelected;
    //星星的总数
    private int mStarTotalNumber = 5;
    //选中的星星个数
    private float mSelectedNumber;
    // 星星之间的间距
    private int mStarDistance;
    // 是否画满
    private Status mStatus = Status.FULL;
    // 星星的宽高
    private float mStarWidth;
    private float mStarHeight;
    // 星星选择变化的回调
    private OnStarChangeListener mOnStarChangeListener;
    // 是不是要画满,默认不画半个的
    private boolean isFull;
    // 画笔
    private Paint mPaint = new Paint();

    // 用于判断是绘制半个，还是全部
    private enum Status {
        FULL, HALF
    }

    public RatingBar(Context context) {
        this(context, null);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RatingBar);

        // 未选中的图片资源
        int starNormalId = array.getResourceId(R.styleable.RatingBar_starEmptyRes, R.drawable.base_star_normal);

        mStarNormal = BitmapFactory.decodeResource(getResources(), starNormalId);

        // 选中全部的图片资源
        int starSelectedId = array.getResourceId(R.styleable.RatingBar_starSelectedRes, R.drawable.base_star_selected);

        mStarSelected = BitmapFactory.decodeResource(getResources(), starSelectedId);

        // 选中一半的图片资源
        int starHalfId = array.getResourceId(R.styleable.RatingBar_starHalfRes, R.drawable.base_star_half);
        if (starHalfId != 0) {
            mStarHalf = BitmapFactory.decodeResource(getResources(), starHalfId);
        } else {
            // 如果没设置一半的图片资源，就用全部的代替
            mStarHalf = mStarSelected;
        }

        mStarEnable = array.getBoolean(R.styleable.RatingBar_starEnable, mStarEnable);
        mStarTotalNumber = array.getInt(R.styleable.RatingBar_starTotalNumber, mStarTotalNumber);
        mSelectedNumber = array.getFloat(R.styleable.RatingBar_selectedNumber, mSelectedNumber);
        mStarDistance = (int) array.getDimension(R.styleable.RatingBar_starDistance, 0);
        mStarWidth = array.getDimension(R.styleable.RatingBar_starWidth, 0);
        mStarHeight = array.getDimension(R.styleable.RatingBar_starHeight, 0);
        isFull = array.getBoolean(R.styleable.RatingBar_starIsFull, true);
        array.recycle();

        // 如有指定宽高，获取最大值 去改变星星的大小（星星是正方形）
        int starWidth = (int) Math.max(mStarWidth, mStarHeight);
        if (starWidth > 0) {
            mStarNormal = resetBitmap(mStarNormal, starWidth);
            mStarSelected = resetBitmap(mStarSelected, starWidth);
            mStarHalf = resetBitmap(mStarHalf, starWidth);
        }

        // 计算一半还是全部（小数部分小于等于0.5就只是显示一半）
        if (!isFull) {
            int num = (int) mSelectedNumber;
            if (mSelectedNumber <= (num + 0.5f)) {
                mStatus = Status.HALF;
            }
        }


    }

    /**
     * 如果用户设置了图片的宽高，就重新设置图片
     */
    public Bitmap resetBitmap(Bitmap bitMap, int starWidth) {
        // 得到新的图片
        return Bitmap.createScaledBitmap(bitMap, starWidth, starWidth, true);
    }

    /**
     * 设置选中星星的数量
     */
    public void setSelectedNumber(int selectedNumber) {
        if (selectedNumber >= 0 && selectedNumber <= mStarTotalNumber) {
            this.mSelectedNumber = selectedNumber;
            invalidate();
        }
    }

    /**
     * 设置星星的总数量
     */
    public void setStarTotalNumber(int starTotalNumber) {
        if (starTotalNumber > 0) {
            this.mStarTotalNumber = starTotalNumber;
            invalidate();
        }
    }

    /**
     * 设置星星的间距
     */
    public void setStarDistance(int mStarDistance) {
        this.mStarDistance = mStarDistance;
        invalidate();
    }

    /**
     * 设置仅支持满颗的星星
     */
    public void setFull(boolean full) {
        isFull = full;
        invalidate();
    }

    /**
     * 设置是否可以编辑
     */
    public void setStarEnable(boolean mStarEnable) {
        this.mStarEnable = mStarEnable;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 用正常的一个星星图片去测量高
        int height = getPaddingTop() + getPaddingBottom() + mStarNormal.getHeight();
        // 宽 = 星星的宽度*总数 + 星星的间距*（总数-1） +padding
        int width = getPaddingLeft() + getPaddingRight() + mStarNormal.getWidth() * mStarTotalNumber + mStarDistance * (mStarTotalNumber - 1);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 循环绘制
        for (int i = 0; i < mStarTotalNumber; i++) {
            float left = getPaddingLeft();
            // 从第二个星星开始，给它设置星星的间距
            if (i > 0) {
                left = getPaddingLeft() + i * (mStarNormal.getWidth() + mStarDistance);
            }
            float top = getPaddingTop();
            // 绘制选中的星星
            if (i < mSelectedNumber) {
                // 比当前选中的数量小
                if (i < mSelectedNumber - 1) {
                    canvas.drawBitmap(mStarSelected, left, top, mPaint);
                } else {
                    // 在这里判断是不是要绘制满的
                    if (mStatus == Status.FULL) {
                        canvas.drawBitmap(mStarSelected, left, top, mPaint);
                    } else {
                        canvas.drawBitmap(mStarHalf, left, top, mPaint);
                    }
                }
            } else {
                // 绘制正常的星星
                canvas.drawBitmap(mStarNormal, left, top, mPaint);
            }

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mStarEnable)
            return true;
        switch (event.getAction()) {
            //减少绘制
            case MotionEvent.ACTION_MOVE:
                // 获取用户触摸的x位置
                float x = event.getX();
                // 一个星星占的宽度
                int starWidth = getWidth() / mStarTotalNumber;
                // 计算用户触摸星星的位置
                int position = (int) (x / starWidth + 1);
                if (position < 0) {
                    position = 0;
                }
                if (position > mStarTotalNumber) {
                    position = mStarTotalNumber;
                }
                // 计算绘制的星星是不是满的
                float result = x - starWidth * (position - 1);
                Status status;
                // 结果大于一半就是满的
                if (result > starWidth * 0.5f) {
                    // 满的
                    status = Status.FULL;
                } else {
                    // 一半的
                    status = Status.HALF;
                }
                if (isFull) {
                    status = Status.FULL;
                }
                //减少绘制
                if (mSelectedNumber != position || status != mStatus) {
                    mSelectedNumber = position;
                    mStatus = status;
                    invalidate();
                    if (mOnStarChangeListener != null) {
                        position = (int) (mSelectedNumber - 1);
                        // 选中的数量：满的就回调（1.0这种），一半就（0.5这种）
                        float selectedNumber = status == Status.FULL ? mSelectedNumber
                                : (mSelectedNumber - 0.5f);
                        mOnStarChangeListener.OnStarChanged(selectedNumber,
                                position < 0 ? 0 : position);
                    }
                }
                break;
        }
        return true;
    }

    public void setOnStarChangeListener(OnStarChangeListener mOnStarChangeListener) {
        this.mOnStarChangeListener = mOnStarChangeListener;
    }

    //  回调监听（选中的数量，位置）
    public interface OnStarChangeListener {
        void OnStarChanged(float selectedNumber, int position);
    }
}
