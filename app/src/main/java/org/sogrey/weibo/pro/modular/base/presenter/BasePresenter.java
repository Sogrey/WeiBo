/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.pro.modular.base.presenter;

import android.content.Context;

import com.google.gson.Gson;

import org.sogrey.mvp.presenter.impl.MvpBasePresenter;
import org.sogrey.mvp.view.MvpView;
import org.sogrey.weibo.pro.modular.base.model.MyBaseModel;


/**
 * Created by Dream on6/5/26.
 */
public abstract class BasePresenter<M extends MyBaseModel,V extends MvpView> extends
        MvpBasePresenter<V> {

    private Context context;
    private Gson    gson;

    private M baseModel;

    public BasePresenter(Context context) {
        this.context=context;
    }

    public M bindModel() {
        return null;
    }

    public M getModel() {
        if (this.baseModel==null) {
            this.baseModel=bindModel();
        }
        return baseModel;
    }

    public Context getContext() {
        return context;
    }

    public Gson getGson() {
        if (this.gson==null) {
            this.gson=new Gson();
        }
        return gson;
    }

    public interface OnUIThreadListener<T> {

        public void onResult(T result,String errorMessage);
    }
}
