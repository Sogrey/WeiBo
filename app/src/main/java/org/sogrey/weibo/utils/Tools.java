/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.utils;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Dream on 16/6/5.
 */
public class Tools {


    // 将微博的日期字符串转换为Date对象
    public static Date strToDate(String str) {
        // sample：Tue May 31 17:46:55 +0800 2011
        // E：周 MMM：字符串形式的月，如果只有两个M，表示数值形式的月 Z表示时区（＋0800）
        SimpleDateFormat sdf=new SimpleDateFormat(
                "E MMM dd HH:mm:ss Z yyyy",
                Locale.US
        );
        Date result=null;
        try {
            result=sdf.parse(str);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return result;

    }

    public static String setTextColor(String s,String color) {
        String result="<font color='"+color+"'>"+s+"</font>";

        return result;
    }

    public static String getTimeStr(String strTime) {
        Date oldTime    =strToDate(strTime);
        Date currentDate=new Date(System.currentTimeMillis());
        long time1      =currentDate.getTime();

        long time2=oldTime.getTime();

        long time=(time1-time2)/1000;

        if (time>=0&&time<60) {
            return "刚才";
        } else if (time>=60&&time<3600) {
            return time/60+"分钟前";
        } else if (time>=3600&&time<3600*24) {
            return time/3600+"小时前";
        } else {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return sdf.format(oldTime);
        }
    }
}
