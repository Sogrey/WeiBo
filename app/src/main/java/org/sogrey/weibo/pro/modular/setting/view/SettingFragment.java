/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.pro.modular.setting.view;

import android.view.View;

import org.sogrey.weibo.pro.modular.base.view.MyBaseFragment;
import org.sogrey.weibo.pro.modular.setting.presenter.SettingPresenter;

/**
 * 设置页面
 * Created by Sogrey on 2016/6/11.
 */
public class SettingFragment extends MyBaseFragment<SettingPresenter> {

    private SettingPresenter mSettingPresenter;

    @Override
    public SettingPresenter bindPresenter() {
        return mSettingPresenter=new SettingPresenter(getActivity());
    }

    @Override
    protected int bindLayoutId() {
        return 0;
    }

    @Override
    protected void initContentView(View contentView) {

    }
}
