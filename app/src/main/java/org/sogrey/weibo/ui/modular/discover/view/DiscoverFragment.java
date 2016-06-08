/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.ui.modular.discover.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.sogrey.weibo.ui.modular.base.view.MyBaseFragment;
import org.sogrey.weibo.ui.modular.discover.presenter.DiscoverPresenter;

/**
 * 发现页面视图
 * Created by Sogrey on 2016/6/9.
 */
public class DiscoverFragment extends MyBaseFragment<DiscoverPresenter> {

    /**
     * The  discover presenter.
     */
    private DiscoverPresenter mDiscoverPresenter;

    /** 创建对象 */
    @Override
    public DiscoverPresenter bindPresenter() {
        return mDiscoverPresenter=new DiscoverPresenter(getActivity());//getContext API23
    }

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,ViewGroup container,Bundle
            savedInstanceState
    ) {
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
        //        this.mDiscoverPresenter.attachView()
    }
}
