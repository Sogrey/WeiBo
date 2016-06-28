/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.pro.modular.home.model;

import android.content.Context;
import android.text.TextUtils;

import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.StatusesAPI;
import com.sina.weibo.sdk.openapi.models.ErrorInfo;
import com.sina.weibo.sdk.utils.LogUtil;

import org.sogrey.weibo.http.framework.dream.HttpUtils;
import org.sogrey.weibo.http.weibo.Constants;
import org.sogrey.weibo.pro.modular.base.model.MyBaseModel;

/**
 * Created by Sogrey on 2016/6/11.
 */
public class HomeModel extends MyBaseModel {

    private static final String TAG=HomeModel.class.getSimpleName();

    /** 用于获取微博信息流等操作的API */
    private StatusesAPI mStatusesAPI;

    public HomeModel(Context context) {
        super(context);
        mStatusesAPI=new StatusesAPI(getContext(),Constants.APP_KEY,getmAccessToken());
    }

    /**
     * 获取微博当前登录用户的关注用的最新的微博列表
     *
     * @param page
     * @param onHttpResultListener
     */
    public void getPublicWeiboList(int page,final HttpUtils.OnHttpResultListener
            onHttpResultListener/*final IResultCallback callback*/) {
        // 对statusAPI实例化
        mStatusesAPI.friendsTimeline(0L,0L,15,page,false,0,false,new RequestListener() {
            @Override
            public void onComplete(String response) {
                if (!TextUtils.isEmpty(response)) {
                    LogUtil.i(TAG,response);
                    if (response.startsWith("{\"statuses\"")) {
                        // 调用 StatusList#parse 解析字符串成微博列表对象
                        onHttpResultListener.onResult(response);
                        return;
                    }
                }
                onHttpResultListener.onResult(null);
            }

            @Override
            public void onWeiboException(WeiboException e) {
                LogUtil.e(TAG,e.getMessage());
                ErrorInfo info=ErrorInfo.parse(e.getMessage());
                onHttpResultListener.onResult(info.toString());
            }
        });
    }

}
