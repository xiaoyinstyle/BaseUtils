package yin.style.baselib.photo;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;

import yin.style.baselib.R;
import yin.style.baselib.fragment.NormalFragment;
import yin.style.baselib.utils.LogUtils;
import yin.style.baselib.view.photoview.PhotoViewAttacher;

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
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                )
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        ivBasePicture.setImageDrawable(resource);
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
