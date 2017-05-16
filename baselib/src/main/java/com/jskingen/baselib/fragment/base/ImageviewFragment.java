package com.jskingen.baselib.fragment.base;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.jskingen.baselib.R;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageviewFragment extends Fragment {
    public static final String TAG_PATH = "TAG_PATH";
    public static final String TAG_CLICK = "TAG_CLICK";
    private boolean touchFinish = true;

    public static ImageviewFragment getInstance(String path, boolean touchFinish) {
        ImageviewFragment fragment = new ImageviewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TAG_PATH, path);
        bundle.putBoolean(TAG_CLICK, touchFinish);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_imageview, container, false);
        final ImageView imageView = (ImageView) contentView.findViewById(R.id.preview_image);
        final PhotoViewAttacher mAttacher = new PhotoViewAttacher(imageView);

        String path = getArguments().getString(TAG_PATH);
        touchFinish = getArguments().getBoolean(TAG_CLICK);
        Glide.with(container.getContext())
                .load(path)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(new SimpleTarget<Bitmap>(480, 800) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        imageView.setImageBitmap(resource);
                        mAttacher.update();
                    }
                });
        mAttacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                if (touchFinish)
                    getActivity().finish();
            }
        });
        return contentView;
    }

}
