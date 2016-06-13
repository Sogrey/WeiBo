# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Android\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-optimizationpasses 5          # 指定代码的压缩级别
-dontusemixedcaseclassnames   # 是否使用大小写混合
-dontpreverify           # 混淆时是否做预校验
-verbose                # 混淆时是否记录日志

-dontwarn android.support.**
-dontwarn org.dom4j.**
-dontwarn com.google.gson.**
-dontwarn org.apache.**
-dontwarn org.apache.log4j.**
-dontwarn org.apache.commons.logging.**
-dontwarn org.apache.commons.codec.binary.**
#-Xlint:deprecation
#-Xlint:unchecked

-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*  # 混淆时所采用的算法

-keep public class * extends android.app.Activity      # 保持哪些类不被混淆
-keep public class * extends android.app.Application   # 保持哪些类不被混淆
-keep public class * extends android.app.Service       # 保持哪些类不被混淆
-keep public class * extends android.content.BroadcastReceiver  # 保持哪些类不被混淆
-keep public class * extends android.content.ContentProvider    # 保持哪些类不被混淆
-keep public class * extends android.app.backup.BackupAgentHelper # 保持哪些类不被混淆
-keep public class * extends android.preference.Preference        # 保持哪些类不被混淆
-keep public class com.android.vending.licensing.ILicensingService    # 保持哪些类不被混淆

#保持 xutils 包不被混淆
-keep public class * extends org.apache.http.client.methods.HttpRequestBase        # 保持哪些类不被混淆
#-keep public class * org.apache.http.HttpEntityEnclosingRequest        # 保持哪些类不被混淆
-keep public class com.lidroid.xutils.http.client.HttpRequest    # 保持哪些类不被混淆
-keep public class org.apache.http.HttpEntityEnclosingRequest    # 保持哪些类不被混淆

-keep class com.lidroid.**{ *; }
-keep class org.apache.http.**{ *; }
-keep class com.lidroid.xutils.**{*;}
#保持 ksoap2-android-assembly-2.5.4-jar-with-dependencies.jar 包不被混淆
-keep class org.kxml2.**{*;}
-keep class org.xmlpull.v1.**{*;}
-keep class org.ksoap2.**{*;}
-keep class org.kobjects.**{*;}
-dontwarn  org.kxml2.**
-dontwarn  org.xmlpull.v1.**
-dontwarn  org.ksoap2.**
-dontwarn  org.kobjects.**
#-libraryjars libs/xUtils-3.1.14.jar
-keep class com.lidroid.** { *; }
-keepattributes Signature

#-keep class * extends java.lang.annotation.Annotation { *; }

-keepclasseswithmembernames class * {  # 保持 native 方法不被混淆
    native <methods>;
}
-keepclasseswithmembers class * {   # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {# 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity { # 保持自定义控件类不被混淆
    public void *(android.view.View);
}
-keepclassmembers enum * {     # 保持枚举 enum 类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable { # 保持 Parcelable 不被混淆
    public static final android.os.Parcelable$Creator *;
}