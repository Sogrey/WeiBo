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

import org.sogrey.mvp.view.impl.MvpActivity;
import org.sogrey.weibo.pro.modular.base.presenter.MyBasePresenter;

/**
 * 本项目的Activity基类
 * Created by Sogrey on 2016/6/9.
 *
 * @param <P>
 *         the type parameter
 */
public abstract class MyBaseActivity<P extends MyBasePresenter> extends MvpActivity<P> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View viewRoot=LayoutInflater.from(mContext).inflate(bindLayoutId(),null);
        //设置布局文件
        setContentView(viewRoot);
        //实例化View控件
        initContentViews(viewRoot);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //绑定数据
        initDatas();
    }

    /**
     * 绑定布局文件
     * Bind layout id int.
     * <br/>
     * Created by Sogrey on 06.11.2016 <br/>
     *
     * @return the int
     */
    protected abstract int bindLayoutId();

    /**
     * 实例化View控件
     * Init content views.
     * <br/>
     * Created by Sogrey on 06.11.2016 <br/>
     */
    protected abstract void initContentViews(View viewRoot);

    /**
     * 绑定数据
     * Init datas.
     * <br/>
     * Created by Sogrey on 06.11.2016 <br/>
     */
    protected abstract void initDatas();
}
