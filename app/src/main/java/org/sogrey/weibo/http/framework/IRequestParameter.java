/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.http.framework;

/**
 * 请求参数封装
 *
 * @param <T>
 */
public interface IRequestParameter<T> {

    public T put(String key,Object value);

    public Object get(String key);

    public int size();

    public T getRequestParam();
}
