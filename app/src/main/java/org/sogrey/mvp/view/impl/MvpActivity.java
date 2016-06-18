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

import org.sogrey.mvp.presenter.MvpPresenter;
import org.sogrey.mvp.view.MvpView;
import org.sogrey.mvp.view.base.BaseActivity;
import org.sogrey.mvp.view.manager.ActivityManager;

/**
 * Mvp基类Activity
 * Created by Sogrey on 2016/6/9.
 *
 * @param <P>
 *         the type parameter
 */
public abstract class MvpActivity<P extends MvpPresenter> extends BaseActivity implements
        MvpView {

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
    protected void onDestroy() {
        if (this.presenter!=null) {
            this.presenter.detachView();
            this.presenter=null;
        }
        super.onDestroy();
    }
}
