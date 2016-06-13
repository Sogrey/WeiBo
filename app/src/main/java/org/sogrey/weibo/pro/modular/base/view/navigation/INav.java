/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.pro.modular.base.view.navigation;

/**
 * 导航规范接口
 * Created by Sogrey on 2016/6/12.
 */
public interface INav {

    public int getLayoutId();

    /**
     * 绑定布局文件
     * 创建并绑定，构建
     * Create and bind.
     * <br/>
     * Created by Sogrey on 06.12.2016 <br/>
     */
    public void createAndBind();
}
