/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.pro.modular.base.view;

import org.sogrey.mvp.view.MvpView;

import java.util.List;

/**
 * Created by Sogrey on 2016/6/27.
 */
public interface IResultView extends MvpView {

    void onStart(int tag);

    void onSuccess(int tag,String result);

    void onError(int tag,String error);

    void onCancelled(int tag);

    <T> void onParseBean(int tag,T bean);

    <T> void onParseListBean(int tag,List<T> beans);
}
