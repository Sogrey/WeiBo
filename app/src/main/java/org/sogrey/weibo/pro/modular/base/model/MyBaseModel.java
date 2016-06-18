/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.pro.modular.base.model;

import android.content.Context;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import org.sogrey.mvp.model.impl.MvpBaseModel;
import org.sogrey.weibo.http.weibo.AccessTokenKeeper;

/**
 * 本项目的MVP-Model
 * Created by Sogrey on 2016/6/9.
 */
public abstract class MyBaseModel extends MvpBaseModel {

    private Context context;


    private Oauth2AccessToken mAccessToken;

    public MyBaseModel(Context context) {
        this.context=context;
        mAccessToken=AccessTokenKeeper.readAccessToken(context);
    }

    public Oauth2AccessToken getmAccessToken() {
        return mAccessToken;
    }

    public Context getContext() {
        return context;
    }
}
