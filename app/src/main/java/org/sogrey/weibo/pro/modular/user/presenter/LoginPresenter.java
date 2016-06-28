/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.pro.modular.user.presenter;

import android.content.Context;

import org.sogrey.weibo.http.framework.IResultCallback;
import org.sogrey.weibo.pro.modular.base.presenter.MyBasePresenter;
import org.sogrey.weibo.pro.modular.base.view.IResultView;
import org.sogrey.weibo.pro.modular.user.model.LoginModel;

import java.util.List;

/**
 * 登录
 * Created by Sogrey on 2016/6/14.
 */
public class LoginPresenter extends MyBasePresenter<IResultView> {

    private LoginModel loginModel;

    public LoginPresenter(Context context) {
        super(context);
        loginModel=new LoginModel(context);
    }

    public void login() {

        //        loginModel.login(new HttpUtils.OnHttpResultListener() {
        //            @Override
        //            public void onResult(String result) {
        //                //                if (!TextUtils.isEmpty(result)){
        //                //                    AppLoginConfig.getInstance().putBoolean
        // (getContext(),AppLoginConfig.IS_LOGIN,true);
        //                //                }
        //                getView().onResult(result,null);
        //            }
        //        });
        loginModel.login(new IResultCallback() {
            @Override
            public void onStart(int tag) {
                getView().onStart(tag);
            }

            @Override
            public void onSuccess(int tag,String result) {
                getView().onSuccess(tag,result);
            }

            @Override
            public void onError(int tag,String error) {
                getView().onError(tag,error);
            }

            @Override
            public void onCancelled(int tag) {
                getView().onCancelled(tag);
            }

            @Override
            public <T> void onParseBean(int tag,T bean) { }

            @Override
            public <T> void onParseListBean(int tag,List<T> beans) { }
        });
    }

}
