/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.http.weibo.WBHttp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.openapi.StatusesAPI;
import com.sina.weibo.sdk.openapi.UsersAPI;

import org.sogrey.frame.utils.ToastUtil;
import org.sogrey.weibo.R;
import org.sogrey.weibo.http.framework.IResultCallback;
import org.sogrey.weibo.http.weibo.AccessTokenKeeper;
import org.sogrey.weibo.http.weibo.Constants;
import org.sogrey.weibo.http.weibo.WeiboApiConfig;

/**
 * Created by Sogrey on 2016/6/26.
 */
public class WeiBoHttpCommand {

    private static WeiboApiConfig    weiboApiConfig;
    private static WeiBoHttpCommand  sInstance;
    /** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
    private        SsoHandler        mSsoHandler;
    private        AuthInfo          mAuthInfo;
    private        UsersAPI          mUsersAPI;
    /** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能 */
    private        Oauth2AccessToken mAccessToken;
    private        boolean           isInit;
    private        StatusesAPI       mStatusesAPI;

    private WeiBoHttpCommand() { }

    /**
     * 单例模式 <br>
     * 一个类最多只能有一个实例 <br>
     * 1、有一个私有静态成员 <br>
     * 2、有一个公开静态方法getInstance得到这个私有静态成员 <br>
     * 3、有一个私有的构造方法（不允许被实例化） <br>
     */

    public static WeiBoHttpCommand getInstance() {
        if (sInstance==null) {
            synchronized (WeiBoHttpCommand.class) {
                if (sInstance==null) {
                    sInstance=new WeiBoHttpCommand();
                }
            }
        }
        return sInstance;
    }

    public Oauth2AccessToken getmAccessToken() {
        return mAccessToken;
    }

    private void initSDK(Context context) {
        if (!isInit) {
            isInit=true;
            mAuthInfo=new AuthInfo(context,Constants.APP_KEY,Constants.REDIRECT_URL,Constants
                    .SCOPE);
            mSsoHandler=new SsoHandler((Activity)context,mAuthInfo);
            mAccessToken=AccessTokenKeeper.readAccessToken(context);
            mUsersAPI=new UsersAPI(context,Constants.APP_KEY,mAccessToken);
        }
    }

    /**
     * SSO登录授权
     * Login.
     * <br/>
     * Created by Sogrey on 06.27.2016 <br/>
     *
     * @param context
     *         the context
     * @param iResultCallback
     *         the result callback
     */
    public void login(Context context,IResultCallback iResultCallback) {
        initSDK(context);
        mSsoHandler.authorizeWeb(new AuthListener((Activity)context,WeiBoHttpTags.WEIBO_LOGIN,
                                                  iResultCallback
        ));
    }

    //    public void getPublicWeiboList(Context context,int page,IResultCallback iResultCallback){
    //        initSDK(context);
    //        // 对statusAPI实例化
    //        mStatusesAPI.friendsTimeline(0L,0L,15,page,false,0,false,new RequestListener() {
    //            @Override
    //            public void onComplete(String response) {
    //                if (!TextUtils.isEmpty(response)) {
    //                    LogUtil.i(TAG,response);
    //                    if (response.startsWith("{\"statuses\"")) {
    //                        // 调用 StatusList#parse 解析字符串成微博列表对象
    //                        if (iResultCallback!=null) {
    //                            iResultCallback.onSuccess();
    //                        }
    //                        onHttpResultListener.onResult(response);
    //                        return;
    //                    }
    //                }
    //                onHttpResultListener.onResult(null);
    //            }
    //
    //            @Override
    //            public void onWeiboException(WeiboException e) {
    //                LogUtil.e(TAG,e.getMessage());
    //                ErrorInfo info=ErrorInfo.parse(e.getMessage());
    //                onHttpResultListener.onResult(info.toString());
    //            }
    //        });
    //    }


    class AuthListener implements WeiboAuthListener {

        private Activity        activity;
        private IResultCallback iResultCallback;
        private int             tag;

        public AuthListener(Activity activity,int tag,IResultCallback iResultCallback) {
            this.activity=activity;
            this.iResultCallback=iResultCallback;
            this.tag=tag;
            this.iResultCallback.onStart(this.tag);
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
                //                ToastUtil.showToast(activity,"授权成功!");
                if (iResultCallback!=null) {
                    iResultCallback.onSuccess(this.tag,"登录成功");
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
                //                ToastUtil.showToast(activity,message);
                if (iResultCallback!=null) {
                    iResultCallback.onError(tag,message);
                }
            }
        }

        @Override
        public void onCancel() {
            ToastUtil.showToast(activity,R.string.weibosdk_demo_toast_auth_canceled);
            if (iResultCallback!=null) {
                iResultCallback.onCancelled(tag);
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            ToastUtil.showToast(activity,e.getMessage());
            if (iResultCallback!=null) {
                iResultCallback.onError(tag,e.getMessage());
            }
        }
    }
}
