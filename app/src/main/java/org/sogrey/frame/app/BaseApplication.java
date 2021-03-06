package org.sogrey.frame.app;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.socks.library.KLog;

import org.sogrey.weibo.BuildConfig;
import org.xutils.x;

/**
 * 全局管理类;
 *
 * @author Sogrey
 */
public class BaseApplication extends Application {

    public static final String TAG="BaseApplication";
    // 屏幕像素;
    public static       int    wpx=0;
    public static       int    hpx=0;
    private static BaseApplication sInstance;
    private static Context         applicationContext;
    public long dataFrist=0l;

    public static Context getContext() {
        return applicationContext;
    }

    public static synchronized BaseApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DisplayMetrics mDisplayMetrics=new DisplayMetrics();
        WindowManager  mWm            =(WindowManager)getSystemService(Context.WINDOW_SERVICE);
        mWm.getDefaultDisplay().getMetrics(mDisplayMetrics);
        wpx=mDisplayMetrics.widthPixels;
        hpx=mDisplayMetrics.heightPixels;
        sInstance=this;
        applicationContext=this.getApplicationContext();

        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.

        KLog.init(true);

        // 极光
        //		JPushInterface.setDebugMode(true); // 设置开启日志,发布时请关闭日志
        //		JPushInterface.init(this); // 初始化 JPush

        if (!BuildConfig.DEBUG)
            CrashHandler.getInstance().init(applicationContext);// 初始化异常监控
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
