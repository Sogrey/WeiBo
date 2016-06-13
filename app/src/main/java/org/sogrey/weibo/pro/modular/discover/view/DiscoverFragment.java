/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.pro.modular.discover.view;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import org.sogrey.frame.utils.ToastUtil;
import org.sogrey.weibo.R;
import org.sogrey.weibo.pro.modular.base.view.MyBaseFragment;
import org.sogrey.weibo.pro.modular.base.view.navigation.impl.DefaultNav;
import org.sogrey.weibo.pro.modular.discover.presenter.DiscoverPresenter;

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

    @Override
    protected int bindLayoutId() {
        return R.layout.fragment_discover;
    }

    @Override
    protected void initContentView(View contentView) {
        initNavigation(contentView);
    }

    private void initNavigation(View contentView) {
        DefaultNav.Builder builder=new DefaultNav.Builder(getContext(),(ViewGroup)contentView);
        builder.setLeftText("添加好友")
               .setRightText("设置")
               .setCenterText(R.string.nav_message_text_center)
               .setOnLeftTextClickListener(new OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       ToastUtil.showToast(getContext(),"添加好友");
                   }
               })
               .setOnRightTextClickListener(new OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       ToastUtil.showToast(getContext(),"设置");
                   }
               })
               .setLeftTextColor(R.color.dark_gray)
               .setRightTextColor(R.color.dark_gray)
               .setCenterTextColor(R.color.black)
               .create();
    }
}
