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

import com.socks.library.KLog;

import org.sogrey.weibo.http.framework.IResultCallback;
import org.xutils.HttpManager;
import org.xutils.common.Callback;
import org.xutils.common.Callback.Cancelable;
import org.xutils.common.util.KeyValue;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sogrey
 */
public class xUtilsCommand {

    private static final String TAG="xUtilsCommand";

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
        callback.onStart(tag);
        Cancelable          cancelable=null;
        final RequestParams params    =xParam.getRequestParam();
        KLog.e(TAG,"Url=="+params.getUri());
        List<KeyValue> paramsList=params.getStringParams();
        if (paramsList!=null&&paramsList.size()>0) {
            KLog.e(TAG,"↓↓↓↓↓↓ [GET请求,tag="+tag+",请求参数如下:] ↓↓↓↓↓↓");
            for (KeyValue keyValue : paramsList) {
                KLog.e(TAG,keyValue.key+"="+keyValue.value);
            }
            KLog.e(TAG,"↑↑↑↑↑↑ [GET请求,tag="+tag+",请求参数如上:] ↑↑↑↑↑↑");
        }

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
                            cancleCommand(params.getUri());
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
        Cancelable          cancelable=null;
        final RequestParams params    =xParam.getRequestParam();
        KLog.e(TAG,"Url=="+params.getUri());
        List<KeyValue> paramsList=params.getStringParams();
        if (paramsList!=null&&paramsList.size()>0) {
            KLog.e(TAG,"↓↓↓↓↓↓ [POST请求,tag="+tag+",请求参数如下:] ↓↓↓↓↓↓");
            for (KeyValue keyValue : paramsList) {
                KLog.e(TAG,keyValue.key+"="+keyValue.value);
            }
            KLog.e(TAG,"↑↑↑↑↑↑ [POST请求,tag="+tag+",请求参数如上:] ↑↑↑↑↑↑");
        }
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
                            cancleCommand(params.getUri());
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
            xUtilsCommandList.remove(uri);
            System.out.println("cancle >>  "+uri);
        }
    }

    public void cancleAll() {
        for (Map.Entry entry : xUtilsCommandList.entrySet()) {
            Cancelable cancelable=(Cancelable)entry.getValue();
            cancelable.cancel();
            System.out.println("cancle >>  "+entry.getKey());
        }
        xUtilsCommandList.clear();
    }
}
