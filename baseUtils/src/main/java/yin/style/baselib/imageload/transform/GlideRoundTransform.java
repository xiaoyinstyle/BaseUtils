package yin.style.baselib.imageload.transform;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * 将图片转化为圆角
 * 构造中第二个参数定义半径
 * Created by leslie on 2016/8/11.
 */
public class GlideRoundTransform extends BitmapTransformation {

    private Paint mBorderPaint;
    private float mBorderWidth = 0f;

    private float radius = 0f;

    public GlideRoundTransform(Context context) {
        this(context, 4);
    }

    public GlideRoundTransform(Context context, int angle) {
        super(context);
        this.radius = Resources.getSystem().getDisplayMetrics().density * angle;
    }

    public GlideRoundTransform(Context context, int angle, float mBorderWidth, @ColorInt int borderColor) {
        super(context);
        this.radius = Resources.getSystem().getDisplayMetrics().density * angle;

        this.mBorderWidth = Resources.getSystem().getDisplayMetrics().density * mBorderWidth;

        mBorderPaint = new Paint();
        mBorderPaint.setDither(true);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(borderColor);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(this.mBorderWidth);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return roundCrop(pool, toTransform);
    }

    //    private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
//        if (source == null) return null;
//
//        int width = source.getWidth();
//        int height = source.getHeight();
//
//        Bitmap result = pool.get(width, height, Bitmap.Config.ARGB_8888);
//        if (result == null) {
//            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        }
//        Canvas canvas = new Canvas(result);
//        Paint paint = new Paint();
//        if (mBorderPaint != null) {
//            source = scaleBitmap(source, (int) (width - mBorderWidth), (int) (height - mBorderWidth));
//        }
//        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
//
//        paint.setAntiAlias(true);
//        RectF rectF = new RectF(mBorderWidth / 2, mBorderWidth / 2, width - mBorderWidth / 2, height - mBorderWidth / 2);
//        canvas.drawRoundRect(rectF, radius, radius, paint);
//
//
//        if (mBorderPaint != null) {
//            RectF rectF2 = new RectF(mBorderWidth / 2, mBorderWidth / 2, width - mBorderWidth / 2, height - mBorderWidth / 2);
//            canvas.drawRoundRect(rectF2, radius, radius, mBorderPaint);
//        }
//        return result;
//    }
    private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        int width = source.getWidth();
        int height = source.getHeight();

        Bitmap result = pool.get(width, height, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));

        paint.setAntiAlias(true);
        RectF rectF = new RectF(0, 0, width, height);
        canvas.drawRoundRect(rectF, radius, radius, paint);


        if (mBorderPaint != null) {
            // 保存画布状态
            canvas.save();
            float scale = 1 - mBorderWidth / source.getWidth() * 2;
            float scale2 = 1 - mBorderWidth / source.getHeight() * 2;
            canvas.scale(scale, scale2, source.getWidth() / 2, source.getHeight() / 2);
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            canvas.drawRoundRect(rectF, radius, radius, paint);
            canvas.restore();

            RectF rectF2 = new RectF(mBorderWidth / 2, mBorderWidth / 2, width - mBorderWidth / 2, height - mBorderWidth / 2);
            canvas.drawRoundRect(rectF2, radius, radius, mBorderPaint);
        }
        return result;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

    }

    /**
     * 根据给定的宽和高进行拉伸
     *
     * @param origin    原图
     * @param newWidth  新图的宽
     * @param newHeight 新图的高
     * @return new Bitmap
     */
    private Bitmap scaleBitmap(Bitmap origin, int newWidth, int newHeight) {
        if (origin == null) {
            return null;
        }
        int height = origin.getHeight();
        int width = origin.getWidth();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);// 使用后乘
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (!origin.isRecycled()) {
            origin.recycle();
        }
        return newBM;
    }
}