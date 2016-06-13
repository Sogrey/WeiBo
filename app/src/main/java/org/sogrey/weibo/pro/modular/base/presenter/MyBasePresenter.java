/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.pro.modular.base.presenter;

import android.content.Context;

import org.sogrey.mvp.presenter.impl.MvpBasePresenter;
import org.sogrey.mvp.view.MvpView;

/**
 * 本项目的Mvp-Presenter
 * Created by Sogrey on 2016/6/9.
 *
 * @param <V>
 *         the type parameter
 */
public class MyBasePresenter<V extends MvpView> extends MvpBasePresenter<V> {

    /**
     * The context.
     */
    private Context mContext;

    /**
     * Instantiates a new  base presenter.
     *
     * @param context
     *         the context
     */
    public MyBasePresenter(Context context) {
        this.mContext=context;
    }

    /**
     * Gets context.
     *
     * @return the context
     */
    public Context getContext() {
        return this.mContext;
    }
}
