/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.pro.modular.user.view.base;

import org.sogrey.mvp.view.impl.MvpBaseView;

/**
 * Created by Sogrey on 2016/6/15.
 */
public interface LoginView<M> extends MvpBaseView {

    void onResult(M data,String errorMessage);
}
