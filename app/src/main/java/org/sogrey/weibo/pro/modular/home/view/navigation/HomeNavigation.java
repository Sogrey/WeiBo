/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.pro.modular.home.view.navigation;

import android.content.Context;
import android.view.ViewGroup;

import org.sogrey.weibo.pro.modular.base.view.navigation.impl.AbsNav;
import org.sogrey.weibo.pro.modular.home.view.navigation.HomeNavigation.Builder
        .HomeNavigationParams;

/**
 * 主页导航
 * Created by Sogrey on 2016/6/13.
 */
public class HomeNavigation extends AbsNav<HomeNavigation.Builder.HomeNavigationParams> {

    public HomeNavigation(HomeNavigationParams param) {
        super(param);
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    public static class Builder extends AbsNav.Builder {

        @Override
        public AbsNav create() {
            return null;
        }

        public static class HomeNavigationParams extends AbsNav.Builder.NavParam {

            public HomeNavigationParams(Context context,ViewGroup parent) {
                super(context,parent);
            }
        }
    }
}
