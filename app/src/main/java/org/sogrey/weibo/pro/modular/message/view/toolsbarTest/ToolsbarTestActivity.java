/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.pro.modular.message.view.toolsbarTest;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import org.sogrey.frame.utils.ToastUtil;
import org.sogrey.weibo.R;
import org.sogrey.weibo.pro.modular.base.presenter.MyBasePresenter;
import org.sogrey.weibo.pro.modular.base.view.MyBaseActivity;
import org.sogrey.weibo.pro.modular.base.view.toolsBar.impl.DefaultToolsbar;
import org.sogrey.weibo.pro.modular.message.presenter.MessagePresenter;

public class ToolsbarTestActivity extends MyBaseActivity<MyBasePresenter> {

    private MessagePresenter mMessagePresenter;

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_toolsbar_test;
    }

    @Override
    protected void initContentViews(View contentView) {
        initNavigation(contentView);
    }

    @Override
    protected void initDatas() {

    }

    @Override
    public MyBasePresenter bindPresenter() {
        return mMessagePresenter=new MessagePresenter(mContext);
    }

    private void initNavigation(View contentView) {
        LinearLayout ll=new LinearLayout(getContext());
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT
        );
        lp.setMargins(10,0,0,0);
        ImageView imgSearch=new ImageView(getContext());
        imgSearch.setImageResource(R.drawable.nav_toolsbar_search_selector);
        imgSearch.setId(R.id.txt_toolsbar_search);
        imgSearch.setScaleType(ScaleType.FIT_CENTER);
        ll.addView(imgSearch,lp);
        ImageView imgMenu=new ImageView(getContext());
        imgMenu.setImageResource(R.drawable.nav_toolsbar_more_selector);
        imgMenu.setId(R.id.txt_toolsbar_more);
        imgMenu.setScaleType(ScaleType.FIT_CENTER);
        ll.addView(imgMenu,lp);

        ViewGroup.LayoutParams VGLP=new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        DefaultToolsbar.Builder builder=new DefaultToolsbar.Builder(getContext(),(ViewGroup)
                contentView);
        builder.setBackId(R.drawable.nav_toolsbar_back_selector)
               .setIconId(R.mipmap.headlines_icon_weibo)
               .setTitleText("这是String型测试标题")
               //                              .addViewOIntoOperaView(ll,VGLP)
               .setBackClickListener(new OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       finish();
                   }
               })
               .setIconClickListener(new OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       ToastUtil.showToast(getContext(),"点击了图标");
                   }
               })
               .setTitleClickListener(new OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       ToastUtil.showToast(getContext(),"点击了标题");
                   }
               })
               //               .setOperaItemClickListener(R.id.txt_toolsbar_search,new
               // OnClickListener() {
               //                   @Override
               //                   public void onClick(View v) {
               //                       ToastUtil.showToast(getContext(),"点击了搜索");
               //                   }
               //               })
               //               .setOperaItemClickListener(R.id.txt_toolsbar_more,new
               // OnClickListener() {
               //                   @Override
               //                   public void onClick(View v) {
               //                       ToastUtil.showToast(getContext(),"点击了菜单");
               //                   }
               //               })
               .create();

    }
}
