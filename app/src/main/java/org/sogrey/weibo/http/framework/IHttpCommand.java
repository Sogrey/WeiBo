/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.http.framework;

/**
 * 执行网络请求命令接口
 *
 * @param <T>
 */
public interface IHttpCommand<T> {

    public String execute(String url,IRequestParam<T> requestParam);
}
