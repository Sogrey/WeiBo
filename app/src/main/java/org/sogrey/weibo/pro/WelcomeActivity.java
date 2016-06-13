/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.pro;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.widget.ImageView;

import org.sogrey.frame.activity.base.BaseActivity;
import org.sogrey.frame.utils.SPUtils;
import org.sogrey.weibo.R;
import org.sogrey.weibo.utils.Constants;

/**
 * The type Welcome activity.欢迎页。
 * Created by Sogrey on 06.07.2016
 */
public class WelcomeActivity extends BaseActivity {

    /**
     * The Image logo.
     */
    private ImageView mImgLogo,
    /**
     * The Image slogan.
     */
    mImgSlogan;

    @Override
    protected void init() {
        setContentView(R.layout.activity_welcome);
        initViews();

    }

    /**
     * Init views.
     * Created by Sogrey on 06.07.2016
     */
    private void initViews() {
        mImgSlogan=(ImageView)findViewById(R.id.img_wel_slogan);
        mImgLogo=(ImageView)findViewById(R.id.img_wel_logo);
        initLogoAnim();
        initSLogoanAnim();
    }

    private void initLogoAnim() {
        //属性动画实现。
        ObjectAnimator animX=ObjectAnimator.ofFloat(mImgLogo,"scaleX",1.0f,1.2f,1.0f);
        ObjectAnimator animY=ObjectAnimator.ofFloat(mImgLogo,"scaleY",1.0f,1.2f,1.0f);
        //通过动画集合组合并执行动画。
        AnimatorSet animatorSet=new AnimatorSet();
        //设置动画时延
        animatorSet.setDuration(3000);
        //同时执行两个动画
        animatorSet.play(animX).with(animY);
        //启动动画
        animatorSet.start();
    }

    private void initSLogoanAnim() {
        //属性动画实现。
        final ObjectAnimator anim=ObjectAnimator.ofFloat(mImgSlogan,"alpha",0.0f,1.0f);
        //设置动画结束监听
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //判断是不是第一次
                boolean isFrist=(Boolean)SPUtils.get(mContext,Constants.SP_KEY_ISFRIST,true);
                if (isFrist) {
                    startIntent(GuideActivity.class,R.anim.anim_alpha_in_center,R.anim
                            .anim_alpha_out_center,new OnStartIntentEndListener() {
                        @Override
                        public void post() {
                            SPUtils.put(mContext,Constants.SP_KEY_ISFRIST,false);
                            finishThis();
                        }
                    });
                } else {
                    startIntent(MainActivity.class,R.anim.anim_alpha_in_center,R.anim
                            .anim_alpha_out_center,new OnStartIntentEndListener() {
                        @Override
                        public void post() {
                            finishThis();
                        }
                    });
                }
            }
        });
        //设置动画时延
        anim.setDuration(3000);
        //启动动画
        anim.start();
    }
}