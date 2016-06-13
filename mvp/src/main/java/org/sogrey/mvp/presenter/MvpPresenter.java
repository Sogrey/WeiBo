/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.mvp.presenter;

/**
 * Presenter层是连接（或适配）View和Model的桥梁。<br/>
 * Created by Sogrey on 2016/6/8.
 *
 * @param <V>
 *         the type parameter
 */
public interface MvpPresenter<V> {

    /**
     * 绑定视图
     * Attach view.
     * Created by Sogrey on 06.09.2016
     *
     * @param view
     *         the view
     */
    public void attachView(V view);

    /**
     * 解除绑定
     * Detach view.
     * Created by Sogrey on 06.09.2016
     */
    public void detachView();
}
