/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.mvp.view.impl;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import org.sogrey.frame.activity.base.ActivityManager;
import org.sogrey.frame.app.BaseApplication;
import org.sogrey.frame.utils.ToastUtil;
import org.sogrey.mvp.presenter.MvpPresenter;
import org.sogrey.mvp.view.MvpView;

/**
 * Mvp基类Activity
 * Created by Sogrey on 2016/6/9.
 *
 * @param <P>
 *         the type parameter
 */
public abstract class MvpActivity<P extends MvpPresenter> extends AppCompatActivity implements
        MvpView {

    /**
     * The  context.
     */
    public  Context mContext;
    /**
     * The Presenter.
     */
    private P       presenter;

    /**
     * The Activity Manager.
     */
    private ActivityManager manager=ActivityManager.getActivityManager();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mContext=this;
        manager.putActivity(this);
        this.presenter=bindPresenter();
        if (this.presenter!=null) {
            this.presenter.attachView(this.mContext);
        }
    }

    public Context getContext(){
        return this.mContext;
    }
    /**
     * 绑定(方法由子类实现，因为类型不确定--泛型）
     * Bind presenter p.
     * Created by Sogrey on 06.09.2016
     *
     * @return the p
     */
    public abstract P bindPresenter();

    @Override
    protected void onResume() {
        super.onResume();
        WindowManager.LayoutParams params=getWindow().getAttributes();
        if (params.softInputMode==WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE) {
            // 隐藏软键盘
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            params.softInputMode=WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN;
        }
    }

    @Override
    protected void onDestroy() {
        if (this.presenter!=null) {
            this.presenter.detachView();
            this.presenter=null;
        }
        manager.removeActivity(this);
        manager.removeActivityCls(this.getClass());
        super.onDestroy();
    }

    /**
     * Exit.
     * Created by Sogrey on 06.07.2016
     */
    public void exit() {
        //TODO 这里是将网络请求与Activity生命周期绑定，Activity销毁了，与此对应的网络请求也取消掉，（后续再补充）

        //        if (mLinkedMap!=null) {
        //            mLinkedMap.clear();
        //            mLinkedMap=null;
        //        }
        manager.exit();
    }

    protected void finishThis() {
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK) {
            //            if ( isShowExitDialog( ) ) {//彈出退出確認對話框
            //                showExitDialog( );
            //            } else
            {//不彈退出確認對話框
                if (System.currentTimeMillis()-BaseApplication.getInstance().dataFrist<
                    2000) {
                    exit();
                } else {
                    ToastUtil.showToast(mContext,"再次点击返回键退出");
                    BaseApplication.getInstance().dataFrist=System.currentTimeMillis();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }
}
