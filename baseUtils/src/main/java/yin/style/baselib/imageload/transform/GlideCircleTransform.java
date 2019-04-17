package yin.style.baselib.imageload.transform;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * Created by leslie on 2016/8/11.
 */
public class GlideCircleTransform extends BitmapTransformation {

    private Paint mBorderPaint;
    private float mBorderWidth = 0;

    public GlideCircleTransform() {
        super();
    }

    public GlideCircleTransform( int borderWidth, @ColorInt int borderColor) {
        super();
        mBorderWidth = Resources.getSystem().getDisplayMetrics().density * borderWidth;

        mBorderPaint = new Paint();
        mBorderPaint.setDither(true);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(borderColor);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(mBorderWidth);
    }


    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return circleCrop(pool, toTransform);
    }

    private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        int size = Math.min(source.getWidth(), source.getHeight());
        float x = (source.getWidth() - size) / 2;
        float y = (source.getHeight() - size) / 2;
        // TODO this could be acquired from the pool too
        Bitmap squared = Bitmap.createBitmap(source, (int) x, (int) y, size, size);
        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);

        if (mBorderPaint != null) {
            // 保存画布状态
            canvas.save();
            float scale = 1 - mBorderWidth / r;
            canvas.scale(scale, scale, r, r);
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            canvas.drawCircle(r, r, r, paint);
            canvas.restore();

            float borderRadius = r - mBorderWidth / 2;
            canvas.drawCircle(r, r, borderRadius - 0.5f, mBorderPaint);
        }

        return result;
    }

//    @Override
//    public String getId() {
//        return getClass().getName();
//    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

    }
}