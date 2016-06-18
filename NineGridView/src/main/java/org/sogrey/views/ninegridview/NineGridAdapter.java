/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.views.ninegridview;

import android.content.Context;
import android.view.View;

import java.util.List;

/**
 * 适配器
 */
public abstract class NineGridAdapter {

    protected Context      context;
    protected List<String> list;

    public NineGridAdapter(Context context,List<String> list) {
        this.context=context;
        this.list=list;
    }

    public abstract int getCount();

    public abstract String getUrl(int positopn);

    public abstract Object getItem(int position);

    public abstract long getItemId(int position);

    public abstract View getView(int i,View view);
}
