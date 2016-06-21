/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.pro.modular.publish.presenter;

import android.content.Context;

import org.sogrey.mvp.view.impl.ResultView;
import org.sogrey.weibo.http.framework.dream.HttpUtils;
import org.sogrey.weibo.pro.modular.base.presenter.BasePresenter;
import org.sogrey.weibo.pro.modular.publish.model.PublishModel;

/**
 * Created by Sogrey on 2016/6/11.
 */
public class PublishPresenter extends BasePresenter<PublishModel,ResultView<String>> {

    private PublishModel mPublishModel;

    public PublishPresenter(Context context) {
        super(context);
        this.mPublishModel=new PublishModel(context);
    }

    /**
     * 发布纯文本weibo
     * Publish text.
     * <br/>
     * Created by Sogrey on 06.19.2016 <br/>
     *
     * @param text
     *         the text
     */
    public void publishText(String text) {
        this.mPublishModel.publishText(text,new HttpUtils.OnHttpResultListener() {
            @Override
            public void onResult(String result) {
                getView().onResult(result,null);
            }
        });
    }
}
