/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.pro.modular.home.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.StatusList;

import org.sogrey.frame.utils.ToastUtil;
import org.sogrey.mvp.view.impl.ResultView;
import org.sogrey.weibo.R;
import org.sogrey.weibo.pro.modular.base.view.MyBaseMapFragment;
import org.sogrey.weibo.pro.modular.base.view.navigation.impl.DefaultNav;
import org.sogrey.weibo.pro.modular.home.presenter.HomePresenter;
import org.sogrey.weibo.pro.modular.home.view.adapter.HomeAdapter;
import org.sogrey.weibo.pro.modular.user.presenter.LoginPresenter;
import org.sogrey.weibo.pro.modular.user.view.base.LoginView;
import org.sogrey.weibo.views.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页
 * Created by Sogrey on 2016/6/11.
 */
public class HomeFragment extends MyBaseMapFragment {

    /**
     * The home presenter.
     */
    private HomePresenter  mHomePresenter;
    private LoginPresenter loginPresenter;
    private HomePresenter  homePresenter;

    private XRefreshView xRefreshView;
    private RecyclerView recyclerView;

    private HomeAdapter homeAdapter;

    private List<Status> statusList=new ArrayList<Status>();

    private boolean isDownRefresh;
    
    @Override
    protected int bindLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void bindPresenter() {
        //绑定Presenter
        loginPresenter=new LoginPresenter(getContext());
        putPresenter(loginPresenter,new LoginView<String>() {
            @Override
            public void onResult(String data,String errorMessage) {
                if (!TextUtils.isEmpty(data)) {
                    //                    resetContentView(getContentView());
                    ToastUtil.showToast(getContext(),"授权成功");
                    loadWeiboData(true);
                }
            }
        });

        homePresenter=new HomePresenter(getContext());
        putPresenter(homePresenter,new ResultView<StatusList>() {
            @Override
            public void onResult(StatusList result,String errorMessage) {
                //刷新Adapter数据
                if (xRefreshView==null) {
                    return;
                }
                //隐藏下拉刷新视图
                if (isDownRefresh) {
                    xRefreshView.stopRefresh();
                } else {
                    xRefreshView.stopLoadMore();
                }
                if (result==null) {
                    ToastUtil.showToast(getContext(),errorMessage);
                } else {
                    //刷新数据
                    if (isDownRefresh) {
                        statusList.clear();
                    }
                    statusList.addAll(result.statusList);
                    homeAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void initContentView(View contentView) {
        initNavigation(contentView);
        initRefreshView(contentView);
    }

    /**
     * 初始化导航条
     * Init navigation.
     * <br/>
     * Created by Sogrey on 06.13.2016 <br/>
     *
     * @param contentView
     *         the content view
     */
    private void initNavigation(View contentView) {
        DefaultNav.Builder builder=new DefaultNav.Builder(getContext(),(ViewGroup)contentView);
        builder.setLeftText(R.string.nav_home_text_left)
               .setRightText(R.string.nav_home_text_right)
               .setCenterText(R.string.nav_home_text_center)
               .setLeftTextColor(R.color.app_text_orage_color)
               .setRightTextColor(R.color.app_text_orage_color)
               .setCenterTextColor(R.color.black)
               .setOnLeftTextClickListener(new OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       ToastUtil.showToast(getContext(),"注册");
                   }
               })
               .setOnRightTextClickListener(new OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       loginPresenter.login();
                   }
               }).create();
    }

    private void initRefreshView(View contentView) {
        xRefreshView=(XRefreshView)contentView.findViewById(R.id.xrefreshview);
        //是否允许下拉刷新
        xRefreshView.setPullRefreshEnable(true);
        // 设置是否可以上拉加载
        xRefreshView.setPullLoadEnable(true);
        // 设置刷新完成以后，headerview固定的时间
        xRefreshView.setPinnedTime(1000);
        // 静默加载模式不能设置footerview
        // 设置支持自动刷新
        xRefreshView.setAutoLoadMore(true);

        recyclerView=(RecyclerView)contentView.findViewById(R.id.recycler_view_test_rv);
        recyclerView.setHasFixedSize(true);
        //线性布局

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        homeAdapter=new HomeAdapter(getContext(),statusList);
        recyclerView.setAdapter(homeAdapter);
        homeAdapter.setCustomLoadMoreView(new XRefreshViewFooter(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(30));

        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {

            @Override
            public void onRefresh() {
                loadWeiboData(true);
            }

            @Override
            public void onLoadMore(boolean isSlience) {
                loadWeiboData(false);
            }
        });
    }


    private void loadWeiboData(boolean isDownRefresh) {
        this.isDownRefresh=isDownRefresh;
        homePresenter.getPublicWeiboList(isDownRefresh);
    }
}
