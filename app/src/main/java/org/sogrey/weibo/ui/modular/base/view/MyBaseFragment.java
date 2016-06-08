/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.ui.modular.base.view;

import org.sogrey.mvp.view.impl.MvpFragment;
import org.sogrey.weibo.ui.modular.base.presenter.MyBasePresenter;

/**
 * 本项目的Fragment基类
 * Created by Sogrey on 2016/6/9.
 */
public abstract class MyBaseFragment<P extends MyBasePresenter> extends MvpFragment<P> {

    @Override
    public P bindPresenter() {
        return null;
    }

}
