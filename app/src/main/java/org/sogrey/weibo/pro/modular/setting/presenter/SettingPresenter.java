/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.pro.modular.setting.presenter;

import android.content.Context;

import org.sogrey.weibo.pro.modular.base.presenter.MyBasePresenter;
import org.sogrey.weibo.pro.modular.base.view.MyBaseView;
import org.sogrey.weibo.pro.modular.setting.model.SettingModel;

/**
 * Created by Sogrey on 2016/6/11.
 */
public class SettingPresenter extends MyBasePresenter<MyBaseView> {

    private SettingModel mSettingModel;

    public SettingPresenter(Context context) {
        super(context);
        this.mSettingModel=new SettingModel(context);
    }
}
