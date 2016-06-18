/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.http.weibo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

import org.sogrey.frame.utils.ToastUtil;
import org.sogrey.weibo.R;
import org.sogrey.weibo.http.framework.dream.HttpUtils;

/**
 * Created by Dream on 16/6/12.
 */
public class WeiboApiManager {

    public static WeiboApiManager   weiboApiManager;
    /** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
    private       SsoHandler        mSsoHandler;
    private       AuthInfo          mAuthInfo;
    /** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能 */
    private       Oauth2AccessToken mAccessToken;

    private WeiboApiManager() {

    }

    public static WeiboApiManager getInstance() {
        if (weiboApiManager==null) {
            weiboApiManager=new WeiboApiManager();
        }
        return weiboApiManager;
    }

    private void initSDK(Context context) {
        mAuthInfo=new AuthInfo(context,Constants.APP_KEY,
                               Constants.REDIRECT_URL,Constants.SCOPE
        );
        mSsoHandler=new SsoHandler((Activity)context,mAuthInfo);
    }

    public void authWebLogin(Context context,HttpUtils.OnHttpResultListener onHttpResultListener) {
        initSDK(context);
        mSsoHandler.authorizeWeb(new AuthListener((Activity)context,onHttpResultListener));
    }

    class AuthListener implements WeiboAuthListener {

        private Activity                       activity;
        private HttpUtils.OnHttpResultListener onHttpResultListener;

        public AuthListener(Activity activity,HttpUtils.OnHttpResultListener onHttpResultListener) {
            this.activity=activity;
            this.onHttpResultListener=onHttpResultListener;
        }

        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            mAccessToken=Oauth2AccessToken.parseAccessToken(values);
            //从这里获取用户输入的 电话号码信息
            String phoneNum=mAccessToken.getPhoneNum();
            if (mAccessToken.isSessionValid()) {
                // 显示 Token
                //                updateTokenView(false);

                // 保存 Token 到 SharedPreferences
                AccessTokenKeeper.writeAccessToken(activity,mAccessToken);
                ToastUtil.showToast(activity,"授权成功!");
                if (onHttpResultListener!=null) {
                    onHttpResultListener.onResult("登录成功!");
                }
            } else {
                // 以下几种情况，您会收到 Code：
                // 1. 当您未在平台上注册的应用程序的包名与签名时；
                // 2. 当您注册的应用程序包名与签名不正确时；
                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                String code   =values.getString("code");
                String message=activity.getString(R.string.weibosdk_demo_toast_auth_failed);
                if (!TextUtils.isEmpty(code)) {
                    message=message+"\nObtained the code: "+code;
                }
                ToastUtil.showToast(activity,message);
                if (onHttpResultListener!=null) {
                    onHttpResultListener.onResult(null);
                }
            }
        }

        @Override
        public void onCancel() {
            ToastUtil.showToast(activity,R.string.weibosdk_demo_toast_auth_canceled);
            if (onHttpResultListener!=null) {
                onHttpResultListener.onResult(null);
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            ToastUtil.showToast(activity,e.getMessage());
            if (onHttpResultListener!=null) {
                onHttpResultListener.onResult(null);
            }
        }
    }

}
