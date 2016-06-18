/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.mvp.view.manager;

import android.app.Activity;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * 页面管理类;
 *
 * @author Sogrey
 */
public class ActivityManager {

    private static ActivityManager activityManager;
    private final HashMap<String,SoftReference<Activity>> taskMap   =new HashMap<String,
            SoftReference<Activity>>();
    private final HashMap<String,SoftReference<Class<?>>> taskMapCls=new HashMap<String,
            SoftReference<Class<?>>>();

    public static ActivityManager getActivityManager() {
        if (activityManager==null) {
            activityManager=new ActivityManager();
        }
        return activityManager;
    }

    public final void putActivity(Activity atv) {
        taskMap.put(atv.toString(),new SoftReference<Activity>(atv));
    }

    public final void removeActivity(Activity atv) {
        taskMap.remove(atv.toString());
    }

    public final void putActivityCls(Class<?> cls) {
        taskMapCls.put(cls.getName(),new SoftReference<Class<?>>(cls));
    }

    public final boolean hasActivityCls(Class<?> cls) {
        return taskMapCls.containsKey(cls.getName());
    }

    public final void removeActivityCls(Class<?> cls) {
        taskMapCls.remove(cls.getName());
    }

    public final void exit() {
        for (
                Iterator<Entry<String,SoftReference<Activity>>> iterator=taskMap
                        .entrySet().iterator();iterator.hasNext();) {
            SoftReference<Activity> activityReference=iterator.next()
                                                              .getValue();
            Activity activity=activityReference.get();
            if (activity!=null) {
                activity.finish();
            }
        }

        taskMap.clear();
    }
}
