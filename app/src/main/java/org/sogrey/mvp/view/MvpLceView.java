/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.mvp.view;

/**
 * 用于网络请求时，等待提示及视图的显示、数据的加载
 * The interface Mvp lce view.
 * Created by Sogrey on 06.09.2016
 *
 * @param <M>
 *         the type parameter
 */
public interface MvpLceView<M> extends MvpView {

    /**
     * 用于显示进度（上拉刷新，下拉加载更多）
     * Show loading.
     * Created by Sogrey on 06.09.2016
     *
     * @param isPullToRefresh
     *         the is pull to refresh
     */
    public void showLoading(boolean isPullToRefresh);

    /**
     * 用于更新显示视图
     * Show content.
     * Created by Sogrey on 06.09.2016
     */
    public void showContent();

    /**
     * 用于绑定及显示数据
     * Show data.
     * Created by Sogrey on 06.09.2016
     *
     * @param data
     *         the data
     */
    public void showData(M data);

    /**
     * 请求时异常处理（分上拉刷新和下拉加载更多）
     * Show error.
     * Created by Sogrey on 06.09.2016
     *
     * @param e
     *         the e
     * @param isPullToRefresh
     *         the is pull to refresh
     */
    public void showError(Exception e,boolean isPullToRefresh);
}
