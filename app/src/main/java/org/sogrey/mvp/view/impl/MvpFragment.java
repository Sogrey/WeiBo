/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.mvp.view.impl;

import android.app.Fragment;
import android.os.Bundle;

import org.sogrey.mvp.presenter.MvpPresenter;
import org.sogrey.mvp.view.MvpView;

/**
 * Mvp基类Fragment
 * Created by Sogrey on 2016/6/9.
 *
 * @param <P>
 *         the type parameter
 */
public abstract class MvpFragment<P extends MvpPresenter> extends Fragment implements MvpView {

    /**
     * The Presenter.
     */
    private P presenter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.presenter=bindPresenter();
        if (this.presenter!=null) {
            this.presenter.attachView(this);
        }
    }

    /**
     * 绑定(方法由子类实现，因为类型不确定--泛型）
     * Bind presenter p.
     * Created by Sogrey on 06.09.2016
     *
     * @return the p
     */
    public abstract P bindPresenter();

    /**
     * 获取Presenter对象
     * Gets presenter.
     *
     * @return the presenter
     */
    public P getPresenter() {
        return this.presenter;
    }

    @Override
    public void onDestroy() {
        if (this.presenter!=null) {
            this.presenter.detachView();
            this.presenter=null;
        }
        super.onDestroy();
    }
}
