package org.sogrey.weibo.pro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import org.sogrey.mvp.view.base.BaseActivity;
import org.sogrey.weibo.R;
import org.sogrey.weibo.pro.modular.base.view.MyFragmentTabHost;
import org.sogrey.weibo.pro.modular.discover.view.DiscoverFragment;
import org.sogrey.weibo.pro.modular.home.view.HomeFragment;
import org.sogrey.weibo.pro.modular.message.view.MessageFragment;
import org.sogrey.weibo.pro.modular.publish.view.PublishTextActivity;
import org.sogrey.weibo.pro.modular.publish.view.pop.PublishWindow;
import org.sogrey.weibo.pro.modular.user.view.UserFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页---采用FragmentTabHost+TabWidget+FrameLayout(content)实现。
 * The type Main activity.
 * <br/>
 * Created by Sogrey on 06.11.2016 <br/>
 */
public class MainActivity extends BaseActivity {

    private MyFragmentTabHost mFragmentTabHost;
    private PublishWindow     publishWindow;
    /**
     * 当前选中的Tab
     * The Index.
     */
    private int index=0;
    /**
     * 保存Tab也基本信息.
     */
    private List<TabItem> mTabItems;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTabItems();
        initTabViews();
    }

    private void initTabViews() {
        //绑定&加载视图
        mFragmentTabHost=(MyFragmentTabHost)findViewById(android.R.id.tabhost);
        //指定Fragment绑定的布局
        mFragmentTabHost.setup(this,getSupportFragmentManager(),android.R.id.tabcontent);
        //去除TabWidget 分割线
        mFragmentTabHost.getTabWidget().setDividerDrawable(null);

        if (mTabItems!=null) {
            for (int i=0;i<mTabItems.size();i++) {
                TabItem tab=mTabItems.get(i);
                TabHost.TabSpec tabSpace=mFragmentTabHost.newTabSpec(
                        tab.getTabText()).setIndicator(tab.getTabbarIndicator());
                mFragmentTabHost.addTab(tabSpace,tab.getFragmentClass(),tab.getBundle());
                //默认设置第一个标签选中
                if (i==0) {
                    tab.setSelected(true);
                }
            }
        }
        //tab设置背景
        mFragmentTabHost.getTabWidget()
                        .setBackgroundColor(getResources()
                                                    .getColor(R.color.bg_main_tab));
        mFragmentTabHost.setOnTabChangedListener(new OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                //                for (TabItem tab : mTabItems) {
                //                    if (tab.getTabText().equals(tabId)) {
                //                        tab.setSelected(true);
                //                    } else {
                //                        tab.setSelected(false);
                //                    }
                //                }

                if (TextUtils.isEmpty(tabId)) {
                    showPopupWindow(2);
                    mFragmentTabHost.setCurrentTab(index);
                    return;
                }
                //重置Tab样式
                for (int i=0;i<mTabItems.size();i++) {
                    TabItem tabItem=mTabItems.get(i);

                    if (tabId.equals(tabItem.getTabText())) {
                        //选中设置为选中壮体啊
                        tabItem.setSelected(true);
                        index=i;
                    } else {
                        //没有选择Tab样式设置为正常
                        tabItem.setSelected(false);
                    }
                }
            }
        });

    }

    private void showPopupWindow(int i) {
        View v=mFragmentTabHost.getTabWidget()
                               .getChildAt(i);
        if (publishWindow==null) {
            publishWindow=new PublishWindow(this);
            publishWindow.init();
            publishWindow.setOnClickSendTextListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,PublishTextActivity.class));
                }
            });
        }
        publishWindow.showMoreWindow(this,v);
    }

    /**
     * 初始化Tab页
     * Init tab items.
     * <br/>
     * Created by Sogrey on 06.11.2016 <br/>
     */
    private void initTabItems() {
        if (mTabItems==null) {
            mTabItems=new ArrayList<>();
        }
        mTabItems.add(new TabItem(
                R.mipmap.tabbar_home,
                R.mipmap.tabbar_home_highlighted,
                R.string.tabbar_text_home,
                R.layout.tabbar_indicator,
                HomeFragment.class
        ));
        mTabItems.add(new TabItem(
                R.mipmap.tabbar_message_center,
                R.mipmap.tabbar_message_center_highlighted,
                R.string.tabbar_text_message,
                R.layout.tabbar_indicator,
                MessageFragment.class
        ));
        mTabItems.add(new TabItem(
                R.drawable.tabbar_compose_button,
                R.drawable.tabbar_compose_button_hightlighted,
                0,
                R.layout.tabbar_publish_indicator,
                null
        ));
        mTabItems.add(new TabItem(
                R.mipmap.tabbar_discover,
                R.mipmap.tabbar_discover_highlighted,
                R.string.tabbar_text_discover,
                R.layout.tabbar_indicator,
                DiscoverFragment.class
        ));
        mTabItems.add(new TabItem(
                R.mipmap.tabbar_profile,
                R.mipmap.tabbar_profile_highlighted,
                R.string.tabbar_text_user,
                R.layout.tabbar_indicator,
                UserFragment.class
        ));

    }

    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK) {
            finishThis();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }

    /**
     * The type Tab item.
     * <br/>
     * Created by Sogrey on 06.11.2016 <br/>
     */
    public class TabItem {

        /**
         * 正常现实的图片资源
         * The Tab img normal.
         */
        private int                       tabImgNormal;
        /**
         * 选中显示的图片资源
         * The Tab img pressed.
         */
        private int                       tabImgPressed;
        /**
         * tab名称
         * The Tab txt res.
         */
        private int                       tabTxtRes;
        /**
         * 指示器布局
         * The Tab indicator.
         */
        private int                       tabIndicator;
        /**
         * Tab对应的Fragment
         * The Fragment class.
         */
        private Class<? extends Fragment> fragmentClass;
        /**
         * 传递数据
         * The Bundle.
         */
        private Bundle                    bundle;
        /**
         * 是否当前选中.
         */
        private boolean                   isSelected;

        /**
         * Tabbar 指示器视图
         * The  tabbar indicator.
         */
        private View      mViewTabbarIndicator;
        private ImageView imgTab;
        private TextView  txtTab;

        /**
         * Instantiates a new Tab item.
         *
         * @param tabImgNormal
         *         the tab img normal.正常现实的图片资源。
         * @param tabImgPressed
         *         the tab img pressed.选中显示的图片资源。
         * @param tabTxtRes
         *         the tab txt res.tab名称。
         * @param fragmentClass
         *         the fragment class.Tab对应的Fragment。
         */
        public TabItem(
                int tabImgNormal,int tabImgPressed,int tabTxtRes,int tabIndicator,Class<? extends
                Fragment>
                fragmentClass
        ) {
            this.tabImgNormal=tabImgNormal;
            this.tabImgPressed=tabImgPressed;
            this.tabTxtRes=tabTxtRes;
            this.tabIndicator=tabIndicator;
            this.fragmentClass=fragmentClass;
        }

        /**
         * Gets tab img normal.
         *
         * @return the tab img normal
         */
        public int getTabImgNormal() {
            return tabImgNormal;
        }

        /**
         * Gets tab img pressed.
         *
         * @return the tab img pressed
         */
        public int getTabImgPressed() {
            return tabImgPressed;
        }

        /**
         * Gets tab txt res.
         *
         * @return the tab txt res
         */
        public int getTabTxtRes() {
            return tabTxtRes;
        }

        /**
         * 获取Tab名称
         * Gets tab text.
         *
         * @return the tab text
         */
        public String getTabText() {
            if (tabTxtRes<=0) {
                return "";
            }
            return getResources().getString(tabTxtRes);
        }

        /**
         * Gets fragment class.
         *
         * @return the fragment class
         */
        public Class<? extends Fragment> getFragmentClass() {
            return fragmentClass;
        }

        /**
         * Gets bundle.
         *
         * @return the bundle
         */
        public Bundle getBundle() {
            if (bundle==null) {
                bundle=new Bundle();
            }
            bundle.putInt("TAB_INDICATOR_TEXT",getTabTxtRes());
            return bundle;
        }

        /**
         * Sets bundle.
         *
         * @param bundle
         *         the bundle
         */
        public void setBundle(Bundle bundle) {
            this.bundle=bundle;
        }

        /**
         * 设置当前是否选中
         * Sets selected.
         *
         * @param isSelected
         *         the is selected
         */
        public void setSelected(boolean isSelected) {
            this.isSelected=isSelected;
            if (this.isSelected) {//选中当前
                if (getTabTxtRes()>0) {//TextView 存在
                    txtTab.setTextColor(getResources().getColor(R.color.tabbar_textcolor_pressed));
                    //绑定默认资源
                    imgTab.setImageResource(getTabImgPressed());
                } else {
                    //绑定默认资源
                    imgTab.setBackgroundResource(getTabImgPressed());
                }
            } else {
                if (getTabTxtRes()>0) {//TextView 存在
                    txtTab.setTextColor(getResources().getColor(R.color.tabbar_textcolor_normal));
                    //绑定默认资源
                    imgTab.setImageResource(getTabImgNormal());
                } else {
                    //绑定默认资源
                    imgTab.setBackgroundResource(getTabImgNormal());
                }
            }
        }

        /**
         * 获取指示器视图
         * Gets tabbar indicator.
         *
         * @return the tabbar indicator
         */
        public View getTabbarIndicator() {
            mViewTabbarIndicator=
                    getLayoutInflater().inflate(tabIndicator,null);
            imgTab=(ImageView)mViewTabbarIndicator.findViewById(R.id.img_tabbar_indicator);
            if (getTabTxtRes()>0) {//TextView 存在
                txtTab=(TextView)mViewTabbarIndicator.findViewById(R.id.txt_tabbar_indicator);
                txtTab.setText(getTabTxtRes());
                //绑定默认资源
                imgTab.setImageResource(getTabImgNormal());
            } else {
                //绑定默认资源
                imgTab.setBackgroundResource(getTabImgNormal());
            }

            return mViewTabbarIndicator;
        }
    }
}
