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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sogrey on 2016/6/14.
 */
public abstract class MvpMapActivity extends BaseActivity {

    /**
     * The  context.
     */
    public  Context                   mContext;
    private Map<MvpPresenter,MvpView> mvpMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        this.mvpMap=new HashMap<>();
        bindPresenter();
    }

    public abstract void bindPresenter();

    public void putPresenter(MvpPresenter presenter,MvpView view) {
        presenter.attachView(view);
        mvpMap.put(presenter,view);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解除绑定
        if (mvpMap!=null) {
            for (MvpPresenter presenter : mvpMap.keySet()) {
                presenter.detachView();
            }
        }
        mvpMap=null;
    }
}
