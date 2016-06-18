/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.mvp.view.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;

import org.sogrey.frame.views.SwipeBackLayout;
import org.sogrey.weibo.R;


/**
 * 想要实现向右滑动删除Activity效果只需要继承SwipeBackActivity即可，如果当前页面含有ViewPager
 * 只需要调用SwipeBackLayout的setViewPager()方法即可
 *
 * @author Sogrey
 */
public abstract class SwipeBackActivity extends BaseActivity {

    protected SwipeBackLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout=(SwipeBackLayout)LayoutInflater.from(this).inflate(
                R.layout.base_aty_swipeback,null);
        layout.attachToActivity(this);
        layout.setDoSomethingBeforeFinish(new SwipeBackLayout.doSthingBeforeFinish() {
            @Override
            public void doSomething() {
                beforeFinished();
            }
        });
    }

    /**
     * 側滑finishActivity前要做的事
     */
    protected void beforeFinished() {
    }


    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.base_slide_right_in,R.anim.base_slide_remain);
    }

    // Press the back button in mobile phone
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0,R.anim.base_slide_right_out);
    }

    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }
}
