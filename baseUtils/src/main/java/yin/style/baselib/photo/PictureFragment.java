package yin.style.baselib.photo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;

import uk.co.senab.photoview.PhotoViewAttacher;
import yin.style.baselib.R;
import yin.style.baselib.fragment.NormalFragment;
import yin.style.baselib.utils.LogUtils;

/**
 * Created by 陈银 on 2017/12/27 11:20
 * <p>
 * 懒加载方式 加载预览的图片
 */

public class PictureFragment extends NormalFragment {
    private ImageView ivBasePicture;

    private Object file;

    public void setFile(Object file) {
        this.file = file;
    }

    @Override
    protected int getViewByXml() {
        return R.layout.base_item_preview_image;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ivBasePicture = (ImageView) findViewById(R.id.iv_base_picture);
    }

    @Override
    protected void initData() {
        setView(file);
    }

    /**
     * 添加 图片
     */
    protected void setView(Object file) {
        if (file instanceof String) {
            LogUtils.e(file.toString());
        } else if (file instanceof File) {
            LogUtils.e(((File) file).getPath());
        }
        final PhotoViewAttacher mAttacher = new PhotoViewAttacher(ivBasePicture);

        Glide.with(this)
                .load(file)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(new SimpleTarget<Bitmap>(480, 800) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        ivBasePicture.setImageBitmap(resource);
                        mAttacher.update();
                    }
                });
        mAttacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                getActivity().finish();
            }
        });
    }
}
