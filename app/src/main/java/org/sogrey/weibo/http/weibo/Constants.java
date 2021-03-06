/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.http.weibo;


/**
 * The type Constants.
 * Created by Sogrey on 06.06.2016
 */
public interface Constants {

    /*SharedPreferences keys*/
    /** 第一次启动或重大更新后第一次启动 */
    public static final String SP_KEY_ISFRIST="sp_key_isfrist";
    /** 当前 DEMO 应用的 APP_KEY，第三方应用应该使用自己的 APP_KEY 替换该 APP_KEY */
    public static final String APP_KEY       ="2824359113";
    /**
     * The constant App_Secret.
     */
    public static final String App_Secret    ="72413a86659***************71a4";

    /**
     * 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页。
     * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
     */
    public static final String REDIRECT_URL="http://www.sina.com";

    /**
     * WeiboSDKDemo 应用对应的权限，第三方开发者一般不需要这么多，可直接设置成空即可。
     * 详情请查看 Demo 中对应的注释。
     */
    public static final String SCOPE=
            "email,direct_messages_read,direct_messages_write,"
            +"friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            +"follow_app_official_microblog,"+"invitation_write";
}