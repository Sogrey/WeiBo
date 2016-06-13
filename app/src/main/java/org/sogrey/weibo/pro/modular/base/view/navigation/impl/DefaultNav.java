/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.pro.modular.base.view.navigation.impl;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.sogrey.weibo.R;

/**
 * 通用导航条
 * Created by Sogrey on 2016/6/13.
 */
public class DefaultNav extends AbsNav<DefaultNav.Builder.DefaultNavigationParams> {

    protected DefaultNav(DefaultNav.Builder.DefaultNavigationParams param) {
        super(param);
    }

    @Override
    public int getLayoutId() {
        return R.layout.nav_dafult;
    }

    @Override
    public void createAndBind() {
        super.createAndBind();
        setText(R.id.txt_nav_left,getParam().textLeft,getParam().textColorLeft,getParam()
                .textLeftOnClickListener);
        setText(R.id.txt_nav_right,getParam().textRight,getParam().textColorRight,getParam()
                .textRightOnClickListener);
        setText(R.id.txt_nav_center,getParam().textCenter,getParam().textColorCenter,getParam()
                .textCenterOnClickListener);
    }

    protected void setText(int viewId,int textId,View.OnClickListener l) {
        setText(viewId,getString(textId),0,l);
    }

    protected void setText(int viewId,String text,View.OnClickListener l) {
        setText(viewId,text,0,l);
    }

    protected void setText(int viewId,int textId,int textColor,View.OnClickListener l) {
        setText(viewId,getString(textId),textColor,l);
    }

    protected void setText(int viewId,String text,int textColor,View.OnClickListener l) {
        View view=findViewById(viewId);
        if (view==null||!(view instanceof TextView))
            return;
        TextView textView=(TextView)view;
        if (TextUtils.isEmpty(text)) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(text);
            if (textColor!=0) {
                textView.setTextColor(getColor(textColor));
            }
            textView.setOnClickListener(l);
        }
    }

    public static class Builder extends AbsNav.Builder {

        protected DefaultNav.Builder.DefaultNavigationParams params;

        public Builder(Context context,ViewGroup parent) {
            params=new DefaultNav.Builder.DefaultNavigationParams(context,parent);
        }

        public DefaultNav.Builder setLeftText(String leftText) {
            this.params.textLeft=leftText;
            return this;
        }

        public DefaultNav.Builder setLeftText(int leftText) {
            this.params.textLeft=this.params.getString(leftText);
            return this;
        }

        public DefaultNav.Builder setRightText(String rightText) {
            this.params.textRight=rightText;
            return this;
        }

        public DefaultNav.Builder setRightText(int rightText) {
            this.params.textRight=this.params.getString(rightText);
            return this;
        }

        public DefaultNav.Builder setCenterText(String centerText) {
            this.params.textCenter=centerText;
            return this;
        }

        public DefaultNav.Builder setCenterText(int centerText) {
            this.params.textCenter=this.params.getString(centerText);
            return this;
        }

        public DefaultNav.Builder setLeftTextColor(int color) {
            this.params.textColorLeft=color;
            return this;
        }

        public DefaultNav.Builder setCenterTextColor(int color) {
            this.params.textColorCenter=color;
            return this;
        }

        public DefaultNav.Builder setRightTextColor(int color) {
            this.params.textColorRight=color;
            return this;
        }

        public DefaultNav.Builder setOnLeftTextClickListener(View.OnClickListener l) {
            this.params.textLeftOnClickListener=l;
            return this;
        }

        public DefaultNav.Builder setOnCenterTextClickListener(View.OnClickListener l) {
            this.params.textCenterOnClickListener=l;
            return this;
        }

        public DefaultNav.Builder setOnRightTextClickListener(View.OnClickListener l) {
            this.params.textRightOnClickListener=l;
            return this;
        }

        @Override
        public AbsNav create() {
            DefaultNav defaultNav=new DefaultNav(params);
            defaultNav.createAndBind();
            return defaultNav;
        }

        public static class DefaultNavigationParams extends AbsNav.Builder.NavParam {

            public String               textLeft;
            public String               textCenter;
            public String               textRight;
            public int                  textColorLeft;
            public int                  textColorRight;
            public int                  textColorCenter;
            public View.OnClickListener textLeftOnClickListener;
            public View.OnClickListener textRightOnClickListener;
            public View.OnClickListener textCenterOnClickListener;

            public DefaultNavigationParams(Context context,ViewGroup parent) {
                super(context,parent);
            }

            public String getString(int resId) {
                return context.getResources().getString(resId);
            }

        }
    }
}
