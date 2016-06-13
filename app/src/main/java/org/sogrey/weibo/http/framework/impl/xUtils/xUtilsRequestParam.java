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

import org.sogrey.weibo.http.framework.IRequestParameter;
import org.xutils.http.RequestParams;

import javax.net.ssl.SSLSocketFactory;


/*
 * 適用於xUtils的網絡秦請求參數
 * @author Sogrey
 *
 */
public class xUtilsRequestParam implements
        IRequestParameter<RequestParams> {

    RequestParams params=null;

    public xUtilsRequestParam(String uri) {
        params=new RequestParams(uri);
        if (params!=null) {
            params.setCharset("UTF-8");// 默認Utf-8
        }
    }


    @Override
    public RequestParams put(String key,Object value) {
        if (params!=null)
            params.addParameter(key,value.toString());
        return params;
    }

    @Override
    public Object get(String key) {
        if (params!=null)
            return params.getParams(key);
        return null;
    }

    @Override
    public int size() {
        if (params!=null)
            return params.getBodyParams().size();
        return 0;
    }

    @Override
    public RequestParams getRequestParam() {
        if (params!=null)
            return params;
        return null;
    }

    public void setSslSocketFactory(SSLSocketFactory ssl) {
        if (params!=null)
            params.setSslSocketFactory(ssl); // 设置ssl
    }

}
