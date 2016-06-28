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
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.StatusList;
import com.socks.library.KLog;

import org.sogrey.frame.utils.ToastUtil;
import org.sogrey.mvp.view.impl.ResultView;
import org.sogrey.weibo.R;
import org.sogrey.weibo.http.framework.IResultCallback;
import org.sogrey.weibo.http.framework.impl.xUtils.xUtilsCommand;
import org.sogrey.weibo.http.framework.impl.xUtils.xUtilsRequestParam;
import org.sogrey.weibo.http.weibo.AccessTokenKeeper;
import org.sogrey.weibo.http.weibo.WBHttp.WeiBoHttpTags;
import org.sogrey.weibo.pro.modular.base.view.IResultView;
import org.sogrey.weibo.pro.modular.base.view.MyBaseMapFragment;
import org.sogrey.weibo.pro.modular.base.view.navigation.impl.DefaultNav;
import org.sogrey.weibo.pro.modular.home.presenter.HomePresenter;
import org.sogrey.weibo.pro.modular.home.view.adapter.HomeAdapter;
import org.sogrey.weibo.pro.modular.user.presenter.LoginPresenter;
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
    private LoginPresenter loginPresenter;
    private HomePresenter  homePresenter;

    private XRefreshView xRefreshView;
    private RecyclerView recyclerView;

    private HomeAdapter homeAdapter;

    private List<Status> statusList=new ArrayList<Status>();

    private boolean isDownRefresh;
    private IResultView _result=new IResultView() {
        @Override
        public void onStart(int tag) {

        }

        @Override
        public void onSuccess(int tag,String result) {
            switch (tag) {
                case WeiBoHttpTags.WEIBO_LOGIN: {//登录
                    if (!TextUtils.isEmpty(result)) {
                        ToastUtil.showToast(getContext(),"登录成功");
                        loadWeiboData(true);
                    }
                }
                break;
            }
        }

        @Override
        public void onError(int tag,String error) {
            switch (tag) {
                case WeiBoHttpTags.WEIBO_LOGIN: {//登录
                    if (!TextUtils.isEmpty(error)) {
                        ToastUtil.showToast(getContext(),error);
                    }
                }
                break;
            }
        }

        @Override
        public void onCancelled(int tag) {
        }

        @Override
        public <T> void onParseBean(int tag,T bean) {

        }

        @Override
        public <T> void onParseListBean(int tag,List<T> beans) {

        }
    };

    @Override
    protected int bindLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void bindPresenter() {
        bindLoginPresenter();
        bindHomePresenter();
        Oauth2AccessToken token=AccessTokenKeeper.readAccessToken(getContext());
        if (!TextUtils.isEmpty(token.getRefreshToken())) {
            loadWeiboData(true);
        }
    }

    private void bindLoginPresenter() {
        //绑定Presenter
        loginPresenter=new LoginPresenter(getContext());
        putPresenter(
                loginPresenter,/*new LoginView<String>() {
            @Override
            public void onResult(String data,String errorMessage) {
                if (!TextUtils.isEmpty(data)) {
                    //                    resetContentView(getContentView());
                    ToastUtil.showToast(getContext(),"授权成功");
                    loadWeiboData(true);
                }
            }
        }*/
                _result
        );
    }

    private void bindHomePresenter() {
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
                       xUtilsCommand command=xUtilsCommand.getInstance();
                       xUtilsRequestParam param=new xUtilsRequestParam("https://api.weibo"
                                                                       +".com/2/statuses/public_timeline.json");

                       //                       必选 	类型及范围 	说明
                       //                       access_token 	true 	string
                       // 采用OAuth授权方式为必填参数，OAuth授权后获得。
                       //                       count 	false 	int 	单页返回的记录条数，默认为50。
                       //                       page 	false 	int 	返回结果的页码，默认为1。
                       //                       base_app 	false 	int
                       // 是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0。
                       param.put("access_token",AccessTokenKeeper.readAccessToken(getContext())
                                                                 .getToken());
                       param.put("count",5);
                       param.put("page",1);
                       param.put("base_app",0);
                       command.get(123,param,new IResultCallback() {
                           @Override
                           public void onStart(int tag) {
                               KLog.e("","开始请求>>>>>"+tag);
                           }

                           @Override
                           public void onSuccess(int tag,String result) {
                               //                               KLog.e(result);
                               KLog.json(tag+"",result);
                               //                               ToastUtil.showToast(getContext(),
                               // result);
                           }

                           @Override
                           public void onError(int tag,String error) {

                           }

                           @Override
                           public void onCancelled(int tag) {

                           }

                           @Override
                           public <T> void onParseBean(int tag,T bean) {

                           }

                           @Override
                           public <T> void onParseListBean(int tag,List<T> beans) {

                           }
                       });
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
        bindHomePresenter();
        homePresenter.getPublicWeiboList(isDownRefresh);
    }
}
