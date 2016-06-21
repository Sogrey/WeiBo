/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.pro.modular.publish.view.pop;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import org.sogrey.weibo.R;


public class PublishWindow extends PopupWindow implements OnClickListener {

    Activity mContext;
    private String TAG=PublishWindow.class.getSimpleName();
    private int mWidth;
    private int mHeight;
    private int statusBarHeight;
    private Bitmap mBitmap=null;
    private Bitmap overlay=null;

    private RelativeLayout layout;

    private Handler mHandler=new Handler();

    private OnClickListener onClickSendTextListener;

    public PublishWindow(Activity context) {
        mContext=context;
    }

    public void init() {
        Rect frame=new Rect();
        mContext.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        statusBarHeight=frame.top;
        DisplayMetrics metrics=new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay()
                .getMetrics(metrics);
        mWidth=metrics.widthPixels;
        mHeight=metrics.heightPixels;

        setWidth(mWidth);
        setHeight(mHeight);
    }

    public void setOnClickSendTextListener(OnClickListener onClickSendTextListener) {
        this.onClickSendTextListener=onClickSendTextListener;
    }

    private Animation showAnimation1(final View view,int fromY,int toY) {
        AnimationSet set     =new AnimationSet(true);
        TranslateAnimation go=new TranslateAnimation(0,0,fromY,toY);
        go.setDuration(300);
        TranslateAnimation go1=new TranslateAnimation(0,0,-10,2);
        go1.setDuration(100);
        go1.setStartOffset(250);
        set.addAnimation(go1);
        set.addAnimation(go);

        set.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationStart(Animation animation) {

            }

        });
        return set;
    }


    public void showMoreWindow(Context context,View anchor) {
        layout=(RelativeLayout)LayoutInflater.from(mContext).inflate(R.layout.publish_more_window,null);
        setContentView(layout);

        final ImageButton imgClose=(ImageButton)layout.findViewById(R.id.imgbtn_close);
        imgClose.setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (isShowing()) {
                            ObjectAnimator mRotateAnimation=ObjectAnimator.ofFloat(imgClose,
                                                                                   "rotation",0,
                                                                                   45
                            ).setDuration(200);
                            mRotateAnimation.setInterpolator(new AccelerateInterpolator());
                            //启动动画
                            mRotateAnimation.start();
                            closeAnimation(layout);
                        }
                    }
                }
        );

        showAnimation(layout);
        setBackgroundDrawable(context.getResources().getDrawable(R.drawable
                                                                         .publish_popupwindow_bg));
        setOutsideTouchable(true);
        setFocusable(true);
        showAtLocation(anchor,Gravity.BOTTOM,0,statusBarHeight);
    }

    private void showAnimation(ViewGroup layout) {
        for (int i=0;i<layout.getChildCount();i++) {
            final View child=layout.getChildAt(i);
            if (child.getId()==R.id.lyt_close||child.getId()==R.id.imgbtn_close) {
                continue;
            }
            child.setOnClickListener(this);
            child.setVisibility(View.INVISIBLE);
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    child.setVisibility(View.VISIBLE);
                    ValueAnimator fadeAnim=ObjectAnimator.ofFloat(child,"translationY",600,0);
                    fadeAnim.setDuration(300);
                    KickBackAnimator kickAnimator=new KickBackAnimator();
                    kickAnimator.setDuration(150);
                    fadeAnim.setEvaluator(kickAnimator);
                    fadeAnim.start();
                }
            },i*50);
        }

    }

    private void closeAnimation(ViewGroup layout) {
        for (int i=0;i<layout.getChildCount();i++) {
            if (i==0||i==layout.getChildCount()-1) {
                continue;
            }
            final View child=layout.getChildAt(i);

            child.setOnClickListener(this);
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    child.setVisibility(View.VISIBLE);
                    ValueAnimator translationY=ObjectAnimator.ofFloat(child,"translationY",0,1000);
                    translationY.setDuration(300);
                    KickBackAnimator kickAnimator=new KickBackAnimator();
                    kickAnimator.setDuration(100);
                    translationY.setEvaluator(kickAnimator);
                    translationY.start();
                    translationY.addListener(new AnimatorListener() {

                        @Override
                        public void onAnimationStart(Animator animation) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            child.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                            // TODO Auto-generated method stub

                        }
                    });
                }
            },(layout.getChildCount()-2-i)*50);
        }

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        },6*50+60);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_send_text:

                break;
            case R.id.ll_send_moderated:
                break;
            case R.id.ll_send_picture:
                break;
            case R.id.ll_send_link:
                break;
            case R.id.ll_send_video:
                if (this.onClickSendTextListener!=null) {
                    this.onClickSendTextListener.onClick(v);
                }
                break;
            case R.id.ll_send_voice:

                break;

            default:
                break;
        }
        if (layout!=null&&isShowing()) {
            closeAnimation(layout);
        }
    }

    public void destroy() {
        if (null!=overlay) {
            overlay.recycle();
            overlay=null;
            System.gc();
        }
        if (null!=mBitmap) {
            mBitmap.recycle();
            mBitmap=null;
            System.gc();
        }
    }

}
