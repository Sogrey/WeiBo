/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.pro.modular.base.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.sogrey.mvp.view.impl.MvpFragment;
import org.sogrey.weibo.pro.modular.base.presenter.MyBasePresenter;

/**
 * 本项目的Fragment基类--缓存视图<br/>
 * Created by Sogrey on 2016/6/9.
 *
 * @param <P>
 *         the type parameter
 */
public abstract class MyBaseFragment<P extends MyBasePresenter> extends MvpFragment<P> {

    /** 视图缓存 */
    private View mContentView;
    /**
     * The Inited.
     */
    private boolean inited=false;

    /** 绑定视图并实例化 */
    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,ViewGroup container,Bundle
            savedInstanceState
    ) {
        //如果试图不存在，绑定视图
        if (mContentView==null) {
            mContentView=inflater.inflate(bindLayoutId(),container,false);
            //实例化视图
            initContentView(mContentView);
        }
        //判断本Fragment对应的Activity视图是否存在
        ViewGroup parent=(ViewGroup)mContentView.getParent();
        //如果存在,清理掉，重新添加，以缓存视图
        if (parent!=null) {
            parent.removeView(mContentView);
        }
        return mContentView;
    }

    /** 视图实例化后，绑定数据 */
    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
        if (this.inited) {
            this.inited=true;
            initDatas();
        }
    }

    /**
     * 获取Fragment的视图
     * Gets content view.
     *
     * @return the content view
     */
    public View getContentView() {
        return mContentView;
    }

    /**
     * 绑定视图<br/>
     * Bind content view layout resources id.<br/>
     * Created by Sogrey on 06.11.2016
     *
     * @return the contentView layout resources id.
     */
    protected abstract int bindLayoutId();

    /**
     * 实例化视图控件
     * Init content view.
     * <br/>
     * Created by Sogrey on 06.11.2016 <br/>
     *
     * @param contentView
     *         the content view
     */
    protected abstract void initContentView(View contentView);

    /**
     * 加载数据
     * Init datas.
     * <br/>
     * Created by Sogrey on 06.11.2016 <br/>
     */
    public void initDatas() {}
}
