/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved. 
 */

package org.sogrey.weibo.pro.modular.publish.view;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.sogrey.mvp.view.impl.ResultView;
import org.sogrey.weibo.R;
import org.sogrey.weibo.pro.modular.base.view.MyBaseMapActivity;
import org.sogrey.weibo.pro.modular.base.view.navigation.impl.DefaultNav;
import org.sogrey.weibo.pro.modular.publish.presenter.PublishPresenter;

public class PublishTextActivity extends MyBaseMapActivity {

    private PublishPresenter publishPresenter;
    private EditText         et_content;

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_publish_text;
    }

    @Override
    protected void initContentViews(View viewRoot) {
        initView(viewRoot);
        initNavigation(viewRoot);
    }

    private void initView(View v) {et_content=(EditText)v.findViewById(R.id.et_content);}
    
    private void initNavigation(View v) {
        LinearLayout       ll_compose_text=(LinearLayout)v.findViewById(R.id.ll_compose_text);
        DefaultNav.Builder builder        =new DefaultNav.Builder(this,ll_compose_text);
        builder
                .setLeftText(R.string.compose_cancel_text)
                .setCenterText(R.string.compose_send_weibo_text)
                .setRightText(R.string.compose_send_text)
                .setOnLeftTextClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .setOnRightTextClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //发表微博
                        publishPresenter.publishText(et_content.getText().toString());
                    }
                }).create();
    }
    
    @Override
    protected void initDatas() {

    }

    @Override
    public void bindPresenter() {
        publishPresenter=new PublishPresenter(this);
        putPresenter(publishPresenter,new ResultView<String>() {
            @Override
            public void onResult(String data,String errorMessage) {
                if (!TextUtils.isEmpty(data)) {
                    //发布成功关闭页面
                    finish();
                }
            }
        });
    }
}
