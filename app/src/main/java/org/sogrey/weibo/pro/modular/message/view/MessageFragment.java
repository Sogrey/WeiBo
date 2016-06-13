/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.pro.modular.message.view;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import org.sogrey.frame.utils.ToastUtil;
import org.sogrey.weibo.R;
import org.sogrey.weibo.pro.modular.base.view.MyBaseFragment;
import org.sogrey.weibo.pro.modular.base.view.navigation.impl.DefaultNav;
import org.sogrey.weibo.pro.modular.message.presenter.MessagePresenter;
import org.sogrey.weibo.pro.modular.message.view.toolsbarTest.ToolsbarTestActivity;

/**
 * 消息页面
 * Created by Sogrey on 2016/6/11.
 */
public class MessageFragment extends MyBaseFragment<MessagePresenter> {

    private MessagePresenter mMessagePresenter;

    @Override
    public MessagePresenter bindPresenter() {
        return mMessagePresenter=new MessagePresenter(getActivity());
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initContentView(View contentView) {
        initNavigation(contentView);
        if (contentView!=null) {
            View view=contentView.findViewById(R.id.txt_test_msg);
            if (view!=null)
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(),ToolsbarTestActivity.class));

                    }
                });
        }
    }

    private void initNavigation(View contentView) {
        DefaultNav.Builder builder=new DefaultNav.Builder(getContext(),(ViewGroup)contentView);
        builder.setLeftText(R.string.nav_message_text_left)
               .setRightText(R.string.nav_message_text_right)
               .setCenterText(R.string.nav_message_text_center)
               .setOnLeftTextClickListener(new OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       ToastUtil.showToast(getContext(),"发现群");
                   }
               })
               .setOnRightTextClickListener(new OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       ToastUtil.showToast(getContext(),"雷达");
                   }
               })
               .setLeftTextColor(R.color.dark_gray)
               .setRightTextColor(R.color.dark_gray)
               .setCenterTextColor(R.color.black)
               .create();
    }
}
