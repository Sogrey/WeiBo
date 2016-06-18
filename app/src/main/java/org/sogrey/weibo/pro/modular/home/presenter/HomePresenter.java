/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.pro.modular.home.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.sina.weibo.sdk.openapi.models.StatusList;

import org.sogrey.mvp.view.impl.ResultView;
import org.sogrey.weibo.http.framework.dream.HttpUtils;
import org.sogrey.weibo.pro.modular.base.presenter.MyBasePresenter;
import org.sogrey.weibo.pro.modular.home.model.HomeModel;

/**
 * Created by Sogrey on 2016/6/11.
 */
public class HomePresenter extends MyBasePresenter<ResultView<StatusList>> {

    private HomeModel mHomeModel;
    private int       page;

    public HomePresenter(Context context) {
        super(context);
        this.mHomeModel=new HomeModel(context);
    }

    public void getPublicWeiboList(boolean isDownRefresh) {
        //如果是下拉刷新,那么我就重置page页
        if (isDownRefresh) {
            page=1;
        }
        mHomeModel.getPublicWeiboList(page,new HttpUtils.OnHttpResultListener() {
            @Override
            public void onResult(String result) {
                if (!TextUtils.isEmpty(result)) {
                    if (result.startsWith("{\"statuses\"")) {
                        StatusList             statuses  =StatusList.parse(result);
                        ResultView<StatusList> resultView=getView();
                        resultView.onResult(statuses,null);
                        //获取到了数据,page页加1
                        page++;
                    } else {
                        getView().onResult(null,result);
                    }
                } else {
                    getView().onResult(null,result);
                }
            }
        });
    }
}
