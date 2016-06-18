/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.pro.modular.user.presenter;

import android.content.Context;

import org.sogrey.weibo.http.framework.dream.HttpUtils;
import org.sogrey.weibo.pro.modular.base.presenter.MyBasePresenter;
import org.sogrey.weibo.pro.modular.user.model.LoginModel;
import org.sogrey.weibo.pro.modular.user.view.base.LoginView;

/**
 * 登录
 * Created by Sogrey on 2016/6/14.
 */
public class LoginPresenter extends MyBasePresenter<LoginView> {

    private LoginModel loginModel;

    public LoginPresenter(Context context) {
        super(context);
        loginModel=new LoginModel(context);
    }

    public void login() {
        //        loginModel.login(new WeiboAuthListener() {
        //            @Override
        //            public void onComplete(Bundle bundle) {
        ////                getView().onComplete(bundle);
        //                getView().onResult(bundle.toString());
        //                LogUtil.e("",bundle.toString());
        //            }
        //
        //            @Override
        //            public void onWeiboException(WeiboException e) {
        //                LogUtil.e("",e.getMessage());
        ////                getView().onWeiboException(e);
        //            }
        //
        //            @Override
        //            public void onCancel() {
        ////                getView().onCancel();
        //            }
        //        });

        loginModel.login(new HttpUtils.OnHttpResultListener() {
            @Override
            public void onResult(String result) {
                //                if (!TextUtils.isEmpty(result)){
                //                    AppLoginConfig.getInstance().putBoolean(getContext(),AppLoginConfig.IS_LOGIN,true);
                //                }
                getView().onResult(result,null);
            }
        });
    }

}
