/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.pro.modular.publish.model;

import android.content.Context;
import android.text.TextUtils;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.StatusesAPI;
import com.sina.weibo.sdk.openapi.models.ErrorInfo;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.utils.LogUtil;

import org.sogrey.frame.utils.ToastUtil;
import org.sogrey.weibo.http.framework.dream.HttpUtils;
import org.sogrey.weibo.http.weibo.AccessTokenKeeper;
import org.sogrey.weibo.http.weibo.Constants;
import org.sogrey.weibo.pro.modular.base.model.MyBaseModel;

/**
 * Created by Sogrey on 2016/6/11.
 */
public class PublishModel extends MyBaseModel {

    private static final String TAG=PublishModel.class.getSimpleName();
    protected Context           context;
    private   Oauth2AccessToken mAccessToken;
    private   StatusesAPI       mStatusesAPI;
    
    public PublishModel(Context context) {
        super(context);
    }

    public Oauth2AccessToken getmAccessToken() {
        if (mAccessToken==null) {
            mAccessToken=AccessTokenKeeper.readAccessToken(context);
        }
        return mAccessToken;
    }

    public StatusesAPI getStatusesAPI() {
        if (mStatusesAPI==null) {
            mStatusesAPI=new StatusesAPI(getContext(),Constants.APP_KEY,getmAccessToken());
        }
        return mStatusesAPI;
    }

    public void publishText(String text,final HttpUtils.OnHttpResultListener onHttpResultListener) {
        //思维方式非常关键
        getStatusesAPI().update(text,null,null,new RequestListener() {
            @Override
            public void onComplete(String response) {
                if (!TextUtils.isEmpty(response)) {
                    LogUtil.i(TAG,response);
                    if (response.startsWith("{\"created_at\"")) {
                        // 调用 Status#parse 解析字符串成微博对象
                        Status status=Status.parse(response);
                        ToastUtil.showToast(getContext(),"发送微博成功");
                        onHttpResultListener.onResult("发送微博成功");
                    } else {
                        ToastUtil.showToast(getContext(),response);
                        onHttpResultListener.onResult(null);
                    }
                } else {
                    onHttpResultListener.onResult(null);
                }
            }

            @Override
            public void onWeiboException(WeiboException e) {
                LogUtil.e(TAG,e.getMessage());
                ErrorInfo info=ErrorInfo.parse(e.getMessage());
                ToastUtil.showToast(getContext(),info.toString());
                onHttpResultListener.onResult(null);
            }
        });
    }
}
