/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.pro.modular.home.view;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import org.sogrey.frame.utils.ToastUtil;
import org.sogrey.weibo.R;
import org.sogrey.weibo.pro.modular.base.view.MyBaseFragment;
import org.sogrey.weibo.pro.modular.base.view.navigation.impl.DefaultNav;
import org.sogrey.weibo.pro.modular.home.presenter.HomePresenter;

/**
 * 主页
 * Created by Sogrey on 2016/6/11.
 */
public class HomeFragment extends MyBaseFragment<HomePresenter> {

    /**
     * The home presenter.
     */
    private HomePresenter mHomePresenter;

    @Override
    protected int bindLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public HomePresenter bindPresenter() {
        return mHomePresenter=new HomePresenter(getActivity());
    }

    @Override
    protected void initContentView(View contentView) {
        initNavigation(contentView);
        //        if (contentView!=null) {
        //            TextView txt=(TextView)contentView.findViewById(R.id.txt_home_fragment);
        //            txt.setOnClickListener(new OnClickListener() {
        //                @Override
        //                public void onClick(View v) {
        //                    //                    mHomePresenter.login("Grace","1163595644");
        //                }
        //            });
        //
        //        }
    }

    /**
     * 初始化导航条
     * Init navigation.
     * <br/>
     * Created by Sogrey on 06.13.2016 <br/>
     *
     * @param contentView
     *         the content view
     */
    private void initNavigation(View contentView) {
        DefaultNav.Builder builder=new DefaultNav.Builder(getContext(),(ViewGroup)contentView);
        builder.setLeftText(R.string.nav_home_text_left)
               .setRightText(R.string.nav_home_text_right)
               .setCenterText(R.string.nav_home_text_center)
               .setLeftTextColor(R.color.app_text_orage_color)
               .setRightTextColor(R.color.app_text_orage_color)
               .setCenterTextColor(R.color.black)
               .setOnLeftTextClickListener(new OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       ToastUtil.showToast(getContext(),"注册");
                   }
               })
               .setOnRightTextClickListener(new OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       ToastUtil.showToast(getContext(),"登录");
                   }
               }).create();
    }


}
