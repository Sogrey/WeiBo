/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

/**
 *
 */
package org.sogrey.weibo.http.framework.impl.xUtils;

import org.sogrey.weibo.http.framework.IResultCallback;
import org.xutils.HttpManager;
import org.xutils.common.Callback;
import org.xutils.common.Callback.Cancelable;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sogrey
 */
public class xUtilsCommand {

    /** 单例模式 对象 */
    private static xUtilsCommand sInstance;
    private HttpManager mHttpManager=null;

    private Map<String,Cancelable> xUtilsCommandList=null;

    private xUtilsCommand() {
        if (mHttpManager==null) {
            mHttpManager=x.http();
        }
        if (xUtilsCommandList==null) {
            xUtilsCommandList=new HashMap<>();
        }
        // xUtilsRequestParam param = new xUtilsRequestParam(
        // "https://www.baidu.com");
        // RequestParams params = param.getRequestParam();
        // if (TextUtils.isEmpty(params.getUri())) {
        //
        // }
        // get(12,param,);
        // Callback.Cancelable cancelable = mHttpManager.get(arg0, arg1)
    }

    /**
     * 单例模式 <br>
     * 一个类最多只能有一个实例 <br>
     * 1、有一个私有静态成员 <br>
     * 2、有一个公开静态方法getInstance得到这个私有静态成员 <br>
     * 3、有一个私有的构造方法（不允许被实例化） <br>
     */

    public static xUtilsCommand getInstance() {
        if (sInstance==null) {
            synchronized (xUtilsCommand.class) {
                if (sInstance==null) {
                    sInstance=new xUtilsCommand();
                }
            }
        }
        return sInstance;
    }

    public void get(
            final int tag,xUtilsRequestParam xParam,
            final IResultCallback callback
    ) {
        Cancelable    cancelable=null;
        RequestParams params    =xParam.getRequestParam();
        if (params!=null) {
            cancelable=mHttpManager.get(
                    params,
                    new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            if (callback!=null) {
                                callback.onSuccess(tag,result);
                            }
                        }

                        @Override
                        public void onError(Throwable ex,boolean isOnCallback) {
                            if (callback!=null) {
                                callback.onError(tag,ex.getMessage());
                            }
                        }

                        @Override
                        public void onCancelled(CancelledException cex) {
                            if (callback!=null) {
                                callback.onCancelled(tag);
                            }
                        }

                        @Override
                        public void onFinished() {
                            // nothing to do.
                        }
                    }
            );
            if (cancelable!=null) {
                xUtilsCommandList.put(params.getUri(),cancelable);
            }
        }
    }

    public void post(
            final int tag,xUtilsRequestParam xParam,
            final IResultCallback callback
    ) {
        Cancelable    cancelable=null;
        RequestParams params    =xParam.getRequestParam();
        if (params!=null) {
            cancelable=mHttpManager.post(
                    params,
                    new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            if (callback!=null) {
                                callback.onSuccess(tag,result);
                            }
                        }

                        @Override
                        public void onError(Throwable ex,boolean isOnCallback) {
                            if (callback!=null) {
                                callback.onError(tag,ex.getMessage());
                            }
                        }

                        @Override
                        public void onCancelled(CancelledException cex) {
                            if (callback!=null) {
                                callback.onCancelled(tag);
                            }
                        }

                        @Override
                        public void onFinished() {
                            // nothing to do.
                        }
                    }
            );
            if (cancelable!=null) {
                xUtilsCommandList.put(params.getUri(),cancelable);
            }
        }
    }

    public void cancleCommand(String uri) {
        Cancelable cancelable=xUtilsCommandList.get(uri);
        if (cancelable!=null) {
            cancelable.cancel();
            System.out.println("cancle >>  "+uri);
        }
    }

    public void cancleAll() {
        for (Map.Entry entry : xUtilsCommandList.entrySet()) {
            Cancelable cancelable=(Cancelable)entry.getValue();
            cancelable.cancel();
            System.out.println("cancle >>  "+entry.getKey());
        }
    }
}
