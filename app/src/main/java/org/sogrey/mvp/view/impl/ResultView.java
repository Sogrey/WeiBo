/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.mvp.view.impl;

import org.sogrey.mvp.view.MvpView;

public interface ResultView<M> extends MvpView {

    public void onResult(M data,String errorMessage);
}
