/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sogrey.frame.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * © 2012 amsoft.cn 名称：AbAppUtil.java 描述：应用工具类.
 *
 * @author 还如一梦中
 * @version v1.0
 * @date：2011-11-10 下午11:52:13
 */
public class AppUtil {

    Context        context;
    PackageManager pm;

    public AppUtil(Context context) {
        this.context=context;
        pm=context.getPackageManager();
    }

    /**
     * 描述：打开并安装APK文件.
     *
     * @param context
     *         the context
     * @param file
     *         apk文件路径
     */
    public static void installApk(Context context,File file) {
        Intent intent=new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(
                Uri.fromFile(file),
                "application/vnd.android.package-archive"
        );
        context.startActivity(intent);
    }

    /**
     * 描述：卸载程序.
     *
     * @param context
     *         the context
     * @param packageName
     *         包名
     */
    public static void uninstallApk(Context context,String packageName) {
        Intent intent    =new Intent(Intent.ACTION_DELETE);
        Uri    packageURI=Uri.parse("package:"+packageName);
        intent.setData(packageURI);
        context.startActivity(intent);
    }

    /**
     * 用来判断服务是否运行.
     *
     * @param ctx
     *         the ctx
     * @param className
     *         判断的服务名字 "com.xxx.xx..XXXService"
     *
     * @return true 在运行 false 不在运行
     */
    public static boolean isServiceRunning(Context ctx,String className) {
        boolean isRunning=false;
        ActivityManager activityManager=(ActivityManager)ctx
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> servicesList=activityManager
                .getRunningServices(Integer.MAX_VALUE);
        Iterator<RunningServiceInfo> l=servicesList.iterator();
        while (l.hasNext()) {
            RunningServiceInfo si=(RunningServiceInfo)l.next();
            if (className.equals(si.service.getClassName())) {
                isRunning=true;
            }
        }
        return isRunning;
    }

    /**
     * 停止服务.
     *
     * @param ctx
     *         the ctx
     * @param className
     *         the class name
     *
     * @return true, if successful
     */
    public static boolean stopRunningService(Context ctx,String className) {
        Intent  intent_service=null;
        boolean ret           =false;
        try {
            intent_service=new Intent(ctx,Class.forName(className));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (intent_service!=null) {
            ret=ctx.stopService(intent_service);
        }
        return ret;
    }

    /**
     * Gets the number of cores available in this device, across all processors.
     * Requires: Ability to peruse the filesystem at "/sys/devices/system/cpu"
     *
     * @return The number of cores, or 1 if failed to get result
     */
    public static int getNumCores() {
        try {
            // Get directory containing CPU info
            File dir=new File("/sys/devices/system/cpu/");
            // Filter to only list the devices we care about
            File[] files=dir.listFiles(new FileFilter() {

                @Override
                public boolean accept(File pathname) {
                    // Check if filename is "cpu", followed by a single digit
                    // number
                    if (Pattern.matches("cpu[0-9]",pathname.getName())) {
                        return true;
                    }
                    return false;
                }

            });
            // Return the number of cores (virtual CPU devices)
            return files.length;
        } catch (Exception e) {
            // Default to return 1 core
            return 1;
        }
    }

    /**
     * 描述：判断网络是否有效.
     *
     * @param context
     *         the context
     *
     * @return true, if is network available
     */
    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager connectivity=(ConnectivityManager)context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity!=null) {
                NetworkInfo info=connectivity.getActiveNetworkInfo();
                if (info!=null&&info.isConnected()) {
                    if (info.getState()==NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * Gps是否打开 需要<uses-permission
     * android:name="android.permission.ACCESS_FINE_LOCATION" />权限
     *
     * @param context
     *         the context
     *
     * @return true, if is gps enabled
     */
    public static boolean isGpsEnabled(Context context) {
        LocationManager lm=(LocationManager)context
                .getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 判断当前网络是否是移动数据网络.
     *
     * @param context
     *         the context
     *
     * @return boolean
     */
    public static boolean isMobile(Context context) {
        ConnectivityManager connectivityManager=(ConnectivityManager)context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo=connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo!=null
            &&activeNetInfo.getType()==ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }

    /**
     * 导入数据库.
     *
     * @param context
     *         the context
     * @param dbName
     *         the db name
     * @param rawRes
     *         the raw res
     *
     * @return true, if successful
     */
    public static boolean importDatabase(
            Context context,String dbName,
            int rawRes
    ) {
        int              buffer_size=1024;
        InputStream      is         =null;
        FileOutputStream fos        =null;
        boolean          flag       =false;

        try {
            String dbPath="/data/data/"+context.getPackageName()
                          +"/databases/"+dbName;
            File dbfile=new File(dbPath);
            // 判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
            if (!dbfile.exists()) {
                // 欲导入的数据库
                if (!dbfile.getParentFile().exists()) {
                    dbfile.getParentFile().mkdirs();
                }
                dbfile.createNewFile();
                is=context.getResources().openRawResource(rawRes);
                fos=new FileOutputStream(dbfile);
                byte[] buffer=new byte[buffer_size];
                int    count =0;
                while ((count=is.read(buffer))>0) {
                    fos.write(buffer,0,count);
                }
                fos.flush();
            }
            flag=true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos!=null) {
                try {
                    fos.close();
                } catch (Exception e) {
                }
            }
            if (is!=null) {
                try {
                    is.close();
                } catch (Exception e) {
                }
            }
        }
        return flag;
    }

    /**
     * 获取屏幕尺寸与密度.
     *
     * @param context
     *         the context
     *
     * @return mDisplayMetrics
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        Resources mResources;
        if (context==null) {
            mResources=Resources.getSystem();

        } else {
            mResources=context.getResources();
        }
        // DisplayMetrics{density=1.5, width=480, height=854, scaledDensity=1.5,
        // xdpi=160.421, ydpi=159.497}
        // DisplayMetrics{density=2.0, width=720, height=1280,
        // scaledDensity=2.0, xdpi=160.42105, ydpi=160.15764}
        DisplayMetrics mDisplayMetrics=mResources.getDisplayMetrics();
        return mDisplayMetrics;
    }

    /**
     * 打开键盘.
     *
     * @param context
     *         the context
     */
    public static void showSoftInput(Context context) {
        InputMethodManager inputMethodManager=(InputMethodManager)context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(
                0,
                InputMethodManager.HIDE_NOT_ALWAYS
        );
    }

    /**
     * 关闭键盘事件.
     *
     * @param context
     *         the context
     */
    public static void closeSoftInput(Context context) {
        InputMethodManager inputMethodManager=(InputMethodManager)context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager!=null
            &&((Activity)context).getCurrentFocus()!=null) {
            inputMethodManager.hideSoftInputFromWindow(
                    ((Activity)context)
                            .getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS
            );
        }
    }

    /**
     * 获取包信息.
     *
     * @param context
     *         the context
     */
    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo info=null;
        try {
            String packageName=context.getPackageName();
            info=context.getPackageManager().getPackageInfo(packageName,0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }

    /**
     * 获取本程序包名.
     *
     * @param context
     *         the context
     */
    public static String getPackageName(Context context) {
        String packageName=null;
        try {
            packageName=context.getPackageName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return packageName;
    }

    /**
     * 获取版本号 vercode type:int
     *
     * @param context
     *
     * @return
     *
     * @author Sogrey
     * @date 2015年4月24日
     */
    public static int getVerCode(Context context) {
        String pkgName=getPackageName(context);
        int    verCode=-1;
        try {
            verCode=context.getPackageManager().getPackageInfo(pkgName,0).versionCode;
        } catch (NameNotFoundException e) {
            LogUtil.e("err",e.getMessage());
            e.printStackTrace();
        }
        return verCode;
    }

    /**
     * 获取版本号 vername type:String
     *
     * @param context
     *
     * @return
     *
     * @author Sogrey
     * @date 2015年4月24日
     */
    public static String getVerName(Context context) {
        String pkgName=getPackageName(context);
        String verName="";
        try {
            verName=context.getPackageManager().getPackageInfo(pkgName,0).versionName;
        } catch (NameNotFoundException e) {
            LogUtil.e("err",e.getMessage());
            e.printStackTrace();
        }
        return verName;
    }

    /**
     * 获取唯一设备号(UUID 加密)
     *
     * @return
     *
     * @author Sogrey
     * @date 2015-11-12下午12:54:18
     */
    public static String getDeviceId(Context context) {

        TelephonyManager tm=(TelephonyManager)context
                .getSystemService(Context.TELEPHONY_SERVICE);

        String tmDevice, tmSerial, androidId;
        tmDevice=""+tm.getDeviceId();
        tmSerial=""+tm.getSimSerialNumber();
        androidId=""
                  +android.provider.Settings.Secure.getString(
                context.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID
        );

        UUID deviceUuid=new UUID(
                androidId.hashCode(),
                ((long)tmDevice.hashCode()<<32)|tmSerial.hashCode()
        );
        return deviceUuid.toString();
    }

    /**
     * 获取唯一设备号(不 加密)
     *
     * @param context
     *
     * @return
     *
     * @author Sogrey
     * @date 2015-11-12下午12:56:58
     */
    public static String getDeviceIdNoEncrypt(Context context) {

        TelephonyManager tm=(TelephonyManager)context
                .getSystemService(Context.TELEPHONY_SERVICE);

        String tmSerial=""+tm.getSimSerialNumber();

        String androidId=""
                         +android.provider.Settings.Secure.getString(
                context.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID
        );

        UUID uuid=new UUID(androidId.hashCode(),tmSerial.hashCode());

        return TextUtils.equals(null,tm.getDeviceId()) ? uuid.toString() : tm.getDeviceId();
    }

    /**
     * 获取IP
     *
     * @return
     *
     * @author Sogrey
     * @date 2015-11-13下午5:44:23
     */
    public static String getLocalIpAddress() {
        try {
            for (
                    Enumeration<NetworkInterface> en=NetworkInterface
                            .getNetworkInterfaces();en.hasMoreElements();) {
                NetworkInterface intf=en.nextElement();
                for (
                        Enumeration<InetAddress> enumIpAddr=intf
                                .getInetAddresses();enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress=enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            LogUtil.e(ex.toString());
        }
        return null;
    }

    /*
     * 获取程序 图标
     */
    public Drawable getAppIcon(String packname) {
        try {
            ApplicationInfo info=pm.getApplicationInfo(packname,0);
            return info.loadIcon(pm);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * 获取程序的版本号
     */
    public String getAppVersion(String packname) {

        try {
            PackageInfo packinfo=pm.getPackageInfo(packname,0);
            return packinfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();

        }
        return "";
    }

    /*
     * 获取程序的名字
     */
    public String getAppName(String packname) {
        try {
            ApplicationInfo info=pm.getApplicationInfo(packname,0);
            return info.loadLabel(pm).toString();
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /*
     * 获取程序的权限
     */
    public String[] getAppPremission(String packname) {
        try {
            PackageInfo packinfo=pm.getPackageInfo(
                    packname,
                    PackageManager.GET_PERMISSIONS
            );
            // 获取到所有的权限
            return packinfo.requestedPermissions;

        } catch (NameNotFoundException e) {
            e.printStackTrace();

        }
        return null;
    }

    /*
     * 获取程序的签名
     */
    public String getAppSignature(String packname) {
        try {
            PackageInfo packinfo=pm.getPackageInfo(
                    packname,
                    PackageManager.GET_SIGNATURES
            );
            // 获取到所有的权限
            return packinfo.signatures[0].toCharsString();

        } catch (NameNotFoundException e) {
            e.printStackTrace();

        }
        return "";
    }
}
