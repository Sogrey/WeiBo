/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.mvp.view.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

/**
 * 基本fragment;
 *
 * @author Sogrey
 */
public abstract class BaseFragment extends Fragment {

    public  Context        mContext;
    private LayoutInflater inflater;
    private View           contentView;
    private ViewGroup      container;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,Bundle savedInstanceState
    ) {
        this.inflater=inflater;
        this.container=container;
        onCreateView(savedInstanceState);
        if (contentView==null)
            return super.onCreateView(inflater,container,savedInstanceState);
        initViews();
        initDatas();
        return contentView;
    }

    public abstract void initViews();

    public abstract void initDatas();

    protected void onCreateView(Bundle savedInstanceState) {
        //setContentView() here
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        contentView=null;
        container=null;
        inflater=null;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        this.mContext=context;
    }


    public void setContentView(int layoutResID) {
        setContentView(LayoutInflater.from(this.mContext).inflate(layoutResID,container,
                                                                  false
        ));
    }

    public View getContentView() {
        return contentView;
    }

    public void setContentView(View view) {
        contentView=view;
    }

    public View findViewById(int id) {
        if (contentView!=null)
            return contentView.findViewById(id);
        return null;
    }

    // http://stackoverflow.com/questions/15207305/getting-the-error-java-lang
    // -illegalstateexception-activity-has-been-destroyed
    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager=Fragment.class
                    .getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this,null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
