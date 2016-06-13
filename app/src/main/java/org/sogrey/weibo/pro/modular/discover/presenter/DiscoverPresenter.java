/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.pro.modular.discover.presenter;

import android.content.Context;

import org.sogrey.weibo.pro.modular.base.presenter.MyBasePresenter;
import org.sogrey.weibo.pro.modular.base.view.MyBaseView;
import org.sogrey.weibo.pro.modular.discover.model.DiscoverModel;

/**
 * Created by Sogrey on 2016/6/9.
 */
public class DiscoverPresenter extends MyBasePresenter<MyBaseView> {

    private DiscoverModel mDiscoverModel;

    public DiscoverPresenter(Context context) {
        super(context);
        this.mDiscoverModel=new DiscoverModel(context);
    }
}
