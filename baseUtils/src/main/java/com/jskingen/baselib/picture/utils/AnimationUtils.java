package com.jskingen.baselib.picture.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

/**
 * Created by ChneY on 2017/5/8.
 */

public class AnimationUtils {

    /**
     * 模仿抖动动画
     */
    public static void start(final View view) {
        ScaleAnimation animation = new ScaleAnimation(0.9f, 1.0f, 0.9f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(100);//设置动画持续时间
        animation.setRepeatCount(1);//设置重复次数
        animation.setFillAfter(true);//动画执行完后是否停留在执行完的状态

        view.clearAnimation();
        view.setAnimation(animation);
        animation.startNow();

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
