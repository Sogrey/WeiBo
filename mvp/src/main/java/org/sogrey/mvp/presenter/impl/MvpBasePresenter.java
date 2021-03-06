/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.mvp.presenter.impl;

import org.sogrey.mvp.presenter.MvpPresenter;
import org.sogrey.mvp.view.MvpView;

/**
 * P层——关联——中介
 * Created by Sogrey on 2016/6/9.
 */
public class MvpBasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private V view;

    @Override
    public void attachView(V view) {
        this.view=view;
    }

    @Override
    public void detachView() {
        this.view=null;
    }
}
