/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.pro.modular.base.model;

import android.content.Context;

import org.sogrey.mvp.model.impl.MvpBaseModel;

/**
 * 本项目的MVP-Model
 * Created by Sogrey on 2016/6/9.
 */
public abstract class MyBaseModel extends MvpBaseModel {

    /**
     * The  context.
     */
    private Context mContext;

    /**
     * Instantiates a new  base model.
     *
     * @param context
     *         the context
     */
    public MyBaseModel(Context context) {
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
