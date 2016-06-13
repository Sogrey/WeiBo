/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

/**
 *
 */
package org.sogrey.weibo.http.framework;

import java.util.List;

/**
 * 返回結果監聽
 *
 * @author Sogrey
 */
public interface IResultCallback {

    void onSuccess(int tag,String result);

    void onError(int tag,String error);

    void onCancelled(int tag);

    <T> void onParseBean(int tag,T bean);

    <T> void onParseListBean(int tag,List<T> beans);
}
