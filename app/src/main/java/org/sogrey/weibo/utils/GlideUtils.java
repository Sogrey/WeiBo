/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.utils;

import android.content.Context;
import android.os.Environment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.SafeKeyGenerator;

import org.sogrey.frame.utils.FileUtil;
import org.sogrey.weibo.R;

import java.io.File;

/**
 * Created by Sogrey on 2016/3/8.
 */
public class GlideUtils {

    private static final String _SUFFIX =".0";
    /**
     * Glide 图片默认缓存地址
     */
    private static       String _PREFFIX=
            Environment.getExternalStorageDirectory()
                       .getAbsolutePath()+"/app/GlideCache/";

    /**
     * 為ImageView設置圖片
     *
     * @param context
     *         上下文
     * @param imageView
     *         ImageView控件實例對象
     * @param url
     *         圖片url（可以是本地圖片完整路徑）
     */
    public static void setImage(Context context,ImageView imageView,String url) {
        setImage(context,imageView,url,-1);
    }

    /**
     * 為ImageView設置圖片
     *
     * @param context
     *         上下文
     * @param imageView
     *         ImageView控件實例對象
     * @param url
     *         圖片url（可以是本地圖片完整路徑）
     * @param defualtResId
     *         默認顯示圖片資源id，默認 -1為應用LOGO圖標
     */
    public static void setImage(Context context,ImageView imageView,String url,int defualtResId) {
        //Glide 图片实际缓存地址
        _PREFFIX=FileUtil.getCacheDownloadDir(context)+"/GlideCache/";
        if (!Glide.isSetup()) {
            GlideBuilder gb =new GlideBuilder(context);
            DiskCache    dlw=DiskLruCacheWrapper.get(new File(_PREFFIX),250*1024*1024);
            gb.setDiskCache(dlw);
            Glide.setup(gb);

        }
        defualtResId=-1==defualtResId ? R.drawable.pic_empty : defualtResId;
        Glide.with(context).load(url).placeholder(defualtResId).into(imageView);
    }

    public static String getLocalPathByUrl(Context context,String url) {
        _PREFFIX=FileUtil.getCacheDownloadDir(context)+"/GlideCache/";
        return _PREFFIX+SafeKeyGenerator.getLocalPathByUrl(url)+_SUFFIX;
    }
}
