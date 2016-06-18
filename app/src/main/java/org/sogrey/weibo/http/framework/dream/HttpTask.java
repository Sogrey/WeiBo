/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.http.framework.dream;

import android.os.AsyncTask;

import org.sogrey.weibo.http.framework.IHttpCommand;
import org.sogrey.weibo.http.framework.IRequestParam;


/**
 * 异步任务执行网络请求类---公共类
 */
public class HttpTask extends AsyncTask<String,Void,String> {

    private String                         url;
    private IRequestParam                  requestParam;
    private HttpUtils.OnHttpResultListener onHttpResultListener;
    private IHttpCommand                   httpCommand;

    public HttpTask(
            String url,IRequestParam requestParam,
            IHttpCommand httpCommand,HttpUtils.OnHttpResultListener onHttpResultListener
    ) {
        this.url=url;
        this.requestParam=requestParam;
        this.httpCommand=httpCommand;
        this.onHttpResultListener=onHttpResultListener;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            return httpCommand.execute(url,requestParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        if (this.onHttpResultListener!=null) {
            this.onHttpResultListener.onResult(result);
        }
    }

}
