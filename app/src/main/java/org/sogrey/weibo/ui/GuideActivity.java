/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.ui;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.sogrey.frame.activity.base.BaseActivity;
import org.sogrey.frame.views.CircleIndicator;
import org.sogrey.weibo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页
 * The type Guide activity.
 * Created by Sogrey on 06.07.2016
 */
public class GuideActivity extends BaseActivity {

    private ViewPager       mViewPager;
    /** ViewPager指示器 */
    private CircleIndicator mIndicator;
    private TextView        mTxtHintClick;
    /** 图片资源 */
    private List<Integer>   mImageList;
    /** 视图列表 */
    private List<ImageView> mImageViewList;

    @Override
    protected void init() {
        //准备布局文件
        setContentView(R.layout.activity_guide);
        //初始化View控件
        initViews();
        initDatas();
        //设置适配器
        GuideAdapter guideAdapter=new GuideAdapter();
        mViewPager.setAdapter(guideAdapter);
        mIndicator.setViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position,float positionOffset,int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //当滑动到最后一页
                if (position==mImageViewList.size()-1) {
                    mTxtHintClick.setVisibility(View.VISIBLE);
                } else {
                    mTxtHintClick.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * Init views.
     * Created by Sogrey on 06.07.2016
     */
    private void initViews() {
        mViewPager=(ViewPager)findViewById(R.id.vp_act_guide);
        mIndicator=(CircleIndicator)findViewById(R.id.ci_act_guide);
        mTxtHintClick=(TextView)findViewById(R.id.txt_click_into_app);
        mTxtHintClick.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startIntent(IndexActivity.class,R.anim.fade_in_center,R.anim.fade_out_center,new
                        OnStartIntentEndListener() {
                            @Override
                            public void post() {
                                finishThis();
                            }
                        });
            }
        });
    }

    /**
     * Init datas.
     * Created by Sogrey on 06.07.2016
     */
    private void initDatas() {
        mImageList=new ArrayList<>();
        mImageList.add(R.mipmap.surprise_background_default);
        mImageList.add(R.mipmap.surprise_background_grass);
        mImageList.add(R.mipmap.surprise_background_roof);
        mImageList.add(R.mipmap.surprise_background_window);

        mImageViewList=new ArrayList<>();
        for (Integer resId : mImageList) {
            ImageView img=new ImageView(mContext);
            mImageViewList.add(img);
        }
    }

    /**
     * ViewPager's adapter.Create and display each page.
     * The type Guide adapter.
     * Created by Sogrey on 06.07.2016
     */
    private class GuideAdapter extends PagerAdapter {

        public GuideAdapter() {

        }

        //页码。
        @Override
        public int getCount() {
            return mImageViewList==null ? 0 : mImageViewList.size();
        }

        //当前分页是不是一个View。
        @Override
        public boolean isViewFromObject(View view,Object object) {
            return view==object;
        }

        //当前页。
        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        //创建和绑定每个分页。
        @Override
        public Object instantiateItem(ViewGroup container,int position) {
            ImageView img=mImageViewList.get(position);
            img.setImageResource(mImageList.get(position));
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            container.addView(img,new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            ));
            return img;
        }

        //销毁&释放资源。
        @Override
        public void destroyItem(ViewGroup container,int position,Object object) {
            container.removeView(mImageViewList.get(position));
        }
    }
}