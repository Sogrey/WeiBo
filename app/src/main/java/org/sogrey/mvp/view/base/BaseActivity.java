/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.mvp.view.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import org.sogrey.frame.app.BaseApplication;
import org.sogrey.frame.utils.ToastUtil;
import org.sogrey.mvp.view.manager.ActivityManager;

import java.util.LinkedHashMap;

/**
 * 基本Activity类;
 *
 * @author Sogrey
 */
public class BaseActivity extends AppCompatActivity {

    /**
     * The constant DELAY.
     */
    private static final int DELAY=300;
    /**
     * The M context.
     */
    public    Context   mContext;
    /**
     * The M toast.
     */
    protected ToastUtil mToast;
    /**
     * The Manager.
     */
    private ActivityManager manager=ActivityManager.getActivityManager();
    /**
     * The M linked map.
     */
    private LinkedHashMap<String,Boolean> mLinkedMap;
    //    private DialogUtils                      mDailogUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mContext=this;
        manager.putActivity(this);
        mToast=ToastUtil.getSingleton(mContext);
        if (mLinkedMap==null) {
            mLinkedMap=new LinkedHashMap<String,Boolean>();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        WindowManager.LayoutParams params=getWindow().getAttributes();
        if (params.softInputMode==WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE) {
            // 隐藏软键盘
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            params.softInputMode=WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN;
        }
    }

    // 退出提示;
    //    public void showExitDialog ( ) {
    //        if ( mDailogUtils != null && mDailogUtils.isShowing( ) ) {
    //            mDailogUtils.dismiss( );
    //        }
    //        if ( mDailogUtils == null ) {
    //            mDailogUtils = new DialogUtils( this, R.style.CircularDialog ) {
    //
    //                @Override
    //                public void ok ( ) {
    //                    exit( );
    //                    toCancle( );
    //                }
    //
    //                @Override
    //                public void cancle ( ) {
    //                    toCancle( );
    //                }
    //
    //                @Override
    //                public void ignore ( ) {
    //                }
    //            };
    //            mDailogUtils.show( );
    //            TextView tv = new TextView( this );
    //            tv.setText( "确定退出程序？" );
    //            tv.setGravity( Gravity.CENTER );
    //            mDailogUtils.setContent( tv );
    //            mDailogUtils.setDialogTitle( "退出" );
    //            mDailogUtils.setDialogCancleBtn( "取消" );
    //            mDailogUtils.setDialogOkBtn( "确定" );
    //            mDailogUtils.setDialogCancleBtnColor( getResources( ).getColor(
    //                    R.color.s_dark_green ) );
    //            mDailogUtils.setDialogOkBtnColor( getResources( ).getColor(
    //                    R.color.s_dark_red ) );
    //        } else {
    //            mDailogUtils.show( );
    //        }
    //    }

    /**
     * Exit.
     * Created by Sogrey on 06.07.2016
     */
    public void exit() {
        if (mLinkedMap!=null) {
            mLinkedMap.clear();
            mLinkedMap=null;
        }
        manager.exit();
    }

    protected void finishThis() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.removeActivity(this);
        manager.removeActivityCls(this.getClass());
    }

    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK) {
            //            if ( isShowExitDialog( ) ) {//彈出退出確認對話框
            //                showExitDialog( );
            //            } else
            {//不彈退出確認對話框
                if (System.currentTimeMillis()-BaseApplication.getInstance().dataFrist<
                    2000) {
                    exit();
                } else {
                    ToastUtil.showToast(mContext,"再次点击返回键退出");
                    BaseApplication.getInstance().dataFrist=System.currentTimeMillis();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }

    /**
     * 若要自定義Activity退出時是顯示提示框還是toast,默認toast,若要修改需複寫此方法返回true。
     *
     * @return if true show exit dialog,Otherwise show toast.
     */
    public boolean isShowExitDialog() {
        return false;
    }

    /**
     * Put net work flag.
     *
     * @param key
     *         the key
     * @param val
     *         the val Created by Sogrey on 06.07.2016
     */
    public void putNetWorkFlag(String key,boolean val) {
        if (mLinkedMap==null) {
            mLinkedMap=new LinkedHashMap<String,Boolean>();
        }
        mLinkedMap.put(key,val);
    }

    /**
     * Gets net work flag.
     *
     * @param key
     *         the key
     * @param val
     *         the val
     *
     * @return the net work flag
     */
    public boolean getNetWorkFlag(String key,boolean val) {
        if (mLinkedMap==null) {
            mLinkedMap=new LinkedHashMap<String,Boolean>();
        }
        if (mLinkedMap.containsKey(key)) {
            val=mLinkedMap.get(key);
        } else {
            val=false;
        }
        return val;
    }

    /**
     * Clear all net work flag.
     * Created by Sogrey on 06.07.2016
     */
    public void clearAllNetWorkFlag() {
        if (mLinkedMap==null) {
            mLinkedMap=new LinkedHashMap<String,Boolean>();
        }
        mLinkedMap.clear();
        mLinkedMap=null;
    }

    /**
     * Activity 跳转
     *
     * @param cls
     *         要跳转到的Activity
     */
    public void startIntent(Class<?> cls) {
        startIntent(cls,0,0,null);
    }

    /**
     * Activity 跳转
     *
     * @param cls
     *         要跳转到的Activity
     * @param listener
     *         页面跳转结束监听
     */
    public void startIntent(Class<?> cls,OnStartIntentEndListener listener) {
        startIntent(cls,0,0,listener);
    }

    /**
     * Activity 跳转
     *
     * @param cls
     *         要跳转到的Activity
     * @param delay
     *         自定義延時（毫秒）
     * @param listener
     *         页面跳转结束监听
     */
    public void startIntent(Class<?> cls,int delay,OnStartIntentEndListener listener) {
        startIntent(cls,0,0,delay,listener);
    }

    /**
     * Activity 跳转(动画)
     *
     * @param cls
     *         要跳转到的Activity
     * @param animIn
     *         进入动画
     * @param animOut
     *         退出动画
     * @param listener
     *         页面跳转结束监听
     */
    public void startIntent(Class<?> cls,int animIn,int animOut,OnStartIntentEndListener listener) {
        startIntent(cls,animIn,animOut,-1,listener);
    }

    /**
     * Activity 跳转(动画)
     *
     * @param cls
     *         要跳转到的Activity
     * @param animIn
     *         进入动画
     * @param animOut
     *         退出动画
     * @param delay
     *         自定義延時（毫秒）
     * @param listener
     *         页面跳转结束监听
     */
    public void startIntent(
            Class<?> cls,final int animIn,final int animOut,int delay,
            OnStartIntentEndListener listener
    ) {
        startIntent(cls,null,animIn,animOut,delay,listener);
    }

    /**
     * Activity 跳转(动画)
     *
     * @param cls
     *         要跳转到的Activity
     * @param bundle
     *         传递参数
     * @param animIn
     *         进入动画
     * @param animOut
     * @param listener
     *         页面跳转结束监听
     */
    public void startIntent(
            Class<?> cls,Bundle bundle,int animIn,int animOut,OnStartIntentEndListener listener
    ) {
        startIntent(cls,bundle,animIn,animOut,-1,listener);
    }

    /**
     * Activity 跳转(动画)
     *
     * @param cls
     *         要跳转到的Activity
     * @param bundle
     *         传递参数
     * @param animIn
     *         进入动画
     * @param animOut
     *         退出动画
     * @param delay
     *         自定義延時（毫秒）
     * @param listener
     *         页面跳转结束监听
     */
    public void startIntent(
            Class<?> cls,Bundle bundle,final int animIn,final int animOut,
            int delay,final OnStartIntentEndListener listener
    ) {
        if (manager.hasActivityCls(cls)) {
            return;
        }
        manager.putActivityCls(cls);
        final Intent intent=new Intent();
        intent.setClass(this,cls);
        if (bundle!=null)
            intent.putExtras(bundle);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(intent);
                if (listener!=null) {
                    listener.post();
                }
                if (animIn!=0&&animOut!=0) {
                    try {
                        overridePendingTransition(animIn,animOut);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        },delay<0 ? DELAY : delay);
    }

    /**
     * Activity 跳转(需要返回结果)
     *
     * @param cls
     *         要跳转到的Activity
     * @param requestCode
     *         请求码
     */
    public void startIntentForResult(Class<?> cls,int requestCode) {
        startIntentForResult(cls,requestCode,0,0,null);
    }

    /**
     * Activity 跳转(需要返回结果)
     *
     * @param cls
     *         要跳转到的Activity
     * @param requestCode
     *         请求码
     * @param listener
     *         页面跳转结束监听
     */
    public void startIntentForResult(
            Class<?> cls,int requestCode,OnStartIntentEndListener
            listener
    ) {
        startIntentForResult(cls,requestCode,0,0,listener);
    }

    /**
     * Activity 跳转(需要返回结果)
     *
     * @param cls
     *         要跳转到的Activity
     * @param requestCode
     *         请求码
     * @param delay
     *         自定義延時（毫秒）
     * @param listener
     *         页面跳转结束监听
     */
    public void startIntentForResult(
            Class<?> cls,int requestCode,int delay,
            OnStartIntentEndListener listener
    ) {
        startIntentForResult(cls,requestCode,0,0,delay,listener);
    }

    /**
     * Activity 跳转(需要返回结果,动画)
     *
     * @param cls
     *         要跳转到的Activity
     * @param requestCode
     *         请求码
     * @param animIn
     *         进入动画
     * @param animOut
     *         退出动画
     * @param listener
     *         页面跳转结束监听
     */
    public void startIntentForResult(
            Class<?> cls,int requestCode,int animIn,
            int animOut,OnStartIntentEndListener listener
    ) {
        startIntentForResult(cls,null,requestCode,animIn,animOut,listener);
    }

    /**
     * Activity 跳转(需要返回结果,动画)
     *
     * @param cls
     *         要跳转到的Activity
     * @param requestCode
     *         请求码
     * @param animIn
     *         进入动画
     * @param animOut
     *         退出动画
     * @param delay
     *         自定義延時（毫秒）
     * @param listener
     *         页面跳转结束监听
     */
    public void startIntentForResult(
            Class<?> cls,int requestCode,int animIn,
            int animOut,int delay,OnStartIntentEndListener listener
    ) {
        startIntentForResult(cls,null,requestCode,animIn,animOut,delay,listener);
    }

    /**
     * Activity 跳转(需要返回结果,动画)
     *
     * @param cls
     *         要跳转到的Activity
     * @param bundle
     *         參數
     * @param requestCode
     *         请求码
     * @param animIn
     *         进入动画
     * @param animOut
     *         退出动画
     * @param listener
     *         页面跳转结束监听
     */
    public void startIntentForResult(
            Class<?> cls,Bundle bundle,int requestCode,
            int animIn,int animOut,OnStartIntentEndListener listener
    ) {
        startIntentForResult(cls,bundle,requestCode,animIn,animOut,-1,listener);
    }

    /**
     * Activity 跳转(需要返回结果,动画)
     *
     * @param cls
     *         要跳转到的Activity
     * @param bundle
     *         传递参数
     * @param requestCode
     *         请求码
     * @param animIn
     *         进入动画
     * @param animOut
     *         退出动画
     * @param delay
     *         自定義延時（毫秒）
     * @param listener
     *         页面跳转结束监听
     */
    public void startIntentForResult(
            Class<?> cls,Bundle bundle,final int requestCode,
            final int animIn,final int animOut,int delay,final OnStartIntentEndListener listener
    ) {
        if (manager.hasActivityCls(cls)) {
            return;
        }
        manager.putActivityCls(cls);
        final Intent intent=new Intent();
        intent.setClass(this,cls);
        if (bundle!=null)
            intent.putExtras(bundle);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivityForResult(intent,requestCode);
                if (listener!=null) {
                    listener.post();
                }
                if (animIn!=0&&animOut!=0) {
                    try {
                        overridePendingTransition(animIn,animOut);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        },delay<0 ? DELAY : delay);
    }

    /**
     * 页面跳转完毕监听。
     * The interface On start intent end listener.
     * Created by Sogrey on 06.07.2016
     */
    public interface OnStartIntentEndListener {

        /**
         * Post.事件到来说明页面跳转完毕。
         * Created by Sogrey on 06.07.2016
         */
        void post();
    }
}
