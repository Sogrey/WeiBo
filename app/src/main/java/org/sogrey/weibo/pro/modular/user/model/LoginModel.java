/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.pro.modular.user.model;

import android.content.Context;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

import org.sogrey.weibo.http.framework.IResultCallback;
import org.sogrey.weibo.http.framework.dream.HttpUtils;
import org.sogrey.weibo.http.weibo.WBHttp.WeiBoHttpCommand;
import org.sogrey.weibo.http.weibo.WeiboApiConfig;
import org.sogrey.weibo.pro.modular.base.model.MyBaseModel;

/**
 * 登录
 * Created by Sogrey on 2016/6/14.
 */
public class LoginModel extends MyBaseModel {
    
    /** 微博授权时，启动 SSO 界面的 Activity */
    private Context           mContext;
    /** 授权认证所需要的信息 */
    private AuthInfo          mAuthInfo;
    /** SSO 授权认证实例 */
    private SsoHandler        mSsoHandler;
    /** 微博授权认证回调 */
    private WeiboAuthListener mAuthListener;

    public LoginModel(Context context) {
        super(context);
        mContext=context;
    }
    public void login(HttpUtils.OnHttpResultListener onHttpResultListener) {
        WeiboApiConfig.getInstance().authWebLogin(getContext(),onHttpResultListener);
    }

    public void login(IResultCallback iResultCallback) {
        WeiBoHttpCommand.getInstance().login(mContext,iResultCallback);
    }
}
