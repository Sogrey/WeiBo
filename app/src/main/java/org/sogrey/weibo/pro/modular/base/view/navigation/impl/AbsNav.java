/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.pro.modular.base.view.navigation.impl;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.sogrey.weibo.pro.modular.base.view.navigation.INav;

/**
 * Created by Sogrey on 2016/6/12.
 *
 * @param <P>
 *         the type parameter
 */
public abstract class AbsNav<P extends AbsNav.Builder.NavParam> implements INav {

    /**
     * The Param.
     */
    private P    param;//泛型为规范数据类型
    private View contentView;

    /**
     * 构造方法-参数传进来
     * Instantiates a new Abs nav.
     *
     * @param param
     *         the param
     */
    public AbsNav(P param) {
        this.param=param;
    }

    public P getParam() {
        return param;
    }

    @Nullable
    public View findViewById(@IdRes int id) {
        return contentView.findViewById(id);
    }

    public String getString(int id) {
        return param.context.getResources().getString(id);
    }

    public int getColor(int id) {
        return param.context.getResources().getColor(id);
    }

    @Override
    public void createAndBind() {
        contentView=LayoutInflater.from(param.context).inflate(getLayoutId(),param.parent,false);

        //判断其父容器是否存在，存在就移除他，只能有一个父容器
        ViewParent parent=contentView.getParent();
        if (parent!=null) {
            ViewGroup viewGroup=(ViewGroup)parent;
            viewGroup.removeView(contentView);
        }
        //重新绑定视图
        param.parent.addView(contentView,0);
    }
    

    /**
     * Builder建造者模式
     * The type Builder.
     * <br/>
     * Created by Sogrey on 06.12.2016 <br/>
     */
    public static abstract class Builder {

        /**
         * Builder 的create方法
         * Create abs nav.
         * <br/>
         * Created by Sogrey on 06.12.2016 <br/>
         *
         * @return the abs nav
         */
        public abstract AbsNav create();

        /**
         * 定义参数
         * The type Nav param.
         * <br/>
         * Created by Sogrey on 06.12.2016 <br/>
         */
        public static class NavParam {

            /**
             * The Context.
             */
            public Context   context;
            /**
             * The Parent.
             */
            public ViewGroup parent;

            /**
             * Instantiates a new Nav param.
             *
             * @param context
             *         the context
             * @param parent
             *         the parent
             */
            public NavParam(Context context,ViewGroup parent) {
                this.context=context;
                this.parent=parent;
            }
        }
    }

}
