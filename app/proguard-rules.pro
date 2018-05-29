#-------------------------------------------定制化区域----------------------------------------------
#--------------------------------- 0.BASE ---------------------------------
-keep class talex.zsw.basecore.** {*;}
-dontwarn talex.zsw.basecore.**
-keep class talex.zsw.sample.database.** {*;}
-dontwarn talex.zsw.sample.database.**
-keep class talex.zsw.sample.entitys.** {*;}
-dontwarn talex.zsw.sample.entitys.**
-keep class talex.zsw.sample.mpush.** {*;}
-dontwarn talex.zsw.sample.mpush.**
-keep class talex.zsw.sample.test.** {*;}
-dontwarn talex.zsw.sample.test.**
-keep class talex.zsw.sample.widget.** {*;}
-dontwarn talex.zsw.sample.widget.**
-keep class talex.zsw.sample.update.** {*;}
-dontwarn talex.zsw.sample.update.**
-keep class talex.zsw.sample.util.** {*;}
-dontwarn talex.zsw.sample.util.**
-keep class talex.zsw.sample.zxing.** {*;}
-dontwarn talex.zsw.sample.zxing.**

#--------------------------------- 1.公司包 ---------------------------------

#Logsystem
-keep class com.sendinfo.loglib.** {*;}
-dontwarn com.sendinfo.loglib.**

#Headset
-keep class com.qrcode.** {*;}
-dontwarn com.qrcode.**
-keep class com.sam.sdticreader.** {*;}
-dontwarn com.sam.sdticreader.**
-keep class com.sendinfo.machinepower.** {*;}
-dontwarn com.sendinfo.machinepower.**
-keep class com.sendinfo.sendinfoheadsetlib.** {*;}
-dontwarn com.sendinfo.sendinfoheadsetlib.**
-keep class com.sendinfocard.** {*;}
-dontwarn com.sendinfocard.**
-keep class com.com.techsdk.wid.** {*;}
-dontwarn com.com.techsdk.wid.**
-keep class com.util.** {*;}
-dontwarn com.util.**
-keep class com.wellcom.** {*;}
-dontwarn com.wellcom.**
-keep class com.sendinfo.headsetcardutil.** {*;}
-dontwarn com.sendinfo.headsetcardutil.**
-keep class com.sendinfo.headsetcardlib.** {*;}
-dontwarn com.sendinfo.headsetcardlib.**

#哈尼维尔打印机
-dontwarn com.sendinfo.honeywellprintlib.**
-keep class com.sendinfo.honeywellprintlib.**{*;}

#新大陆手持机
-keep class com.newland.** {*;}
-dontwarn com.newland.**
-keep class android.newland.** {*;}
-dontwarn android.newland.**

#公司硬件包
-dontwarn android_serialport_api.**
-keep class android_serialport_api.**{*;}
-dontwarn com.sendinfo.padserialhelp.**
-keep class com.sendinfo.padserialhelp.**{*;}
-dontwarn com.sendinfo.readargslib.**
-keep class com.sendinfo.readargslib.**{*;}
-dontwarn com.sendinfo.devicehelp.**
-keep class com.sendinfo.devicehelp.**{*;}
-dontwarn com.sam.**
-keep class com.sam.**{*;}
-dontwarn com.sendinfo.machinepower.**
-keep class com.sendinfo.machinepower.**{*;}
-dontwarn com.techsdk.wid.**
-keep class com.techsdk.wid.**{*;}

#-------------------------------------------------------------------------

#---------------------------------2.第三方包-------------------------------

#unity3d
-keep class com.sendinfo.handsetbgssj.UnityManager {
  *;
}
-dontwarn com.sendinfo.handsetbgssj.UnityManager
-keep class com.sendinfo.handsetbgssjar.** {*;}
-dontwarn com.sendinfo.handsetbgssjar.**
-keep class bitter.jnibridge.** {*;}
-dontwarn bitter.jnibridge.**
-keep class com.unity3d.player.** {*;}
-dontwarn com.unity3d.player.**
-keep class org.fmod.** {*;}
-dontwarn org.fmod.**
-keep class com.vuforia.** {*;}
-dontwarn com.vuforia.**

#友盟
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
   }
-keep public class com.sendinfo.handsetbgssj.R$*{
public static final int *;
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class com.umeng.** {*;}
-dontwarn com.umeng.**

#微博
-keep class com.sina.** {*;}
-dontwarn com.sina.**

#腾讯系 - QQ,微信
-keep class com.sendinfo.handsetbgssj.wxapi.** {*;}
-dontwarn com.sendinfo.handsetbgssj.wxapi.**
-keep class com.tencent.** {*;}
-dontwarn com.tencent.**

#阿里系 - 支付宝,AndFix
-keep class com.alipay.** {*;}
-dontwarn com.alipay.**
-keep class com.ta.utdid2.** {*;}
-dontwarn com.ta.utdid2.**
-keep class com.ut.device.** {*;}
-dontwarn com.ut.device.**
-keep class org.json.alipay.** {*;}
-dontwarn org.json.alipay.**
-keep class * extends java.lang.annotation.Annotation
-keepclasseswithmembernames class * {
    native <methods>;
}

#gson
-keep class com.google.gson.** {*;}
-keep class com.google.**{*;}
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }
-keep class com.google.** {
    <fields>;
    <methods>;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-dontwarn com.google.gson.**
-dontwarn com.google.**
-keep class com.google.protobuf.** {*;}
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }

#universal-image-loader
-keep class com.nostra13.universalimageloader.** {*;}
-dontwarn com.nostra13.universalimageloader.**

#zxing
-keep class com.google.zxing.** {*;}
-dontwarn com.google.zxing.**

#Butter Knife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

#Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

#RxJava
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
-dontwarn com.trello.rxlifecycle2.**
-keep class com.trello.rxlifecycle2.** { *; }

#simpl-xml
-dontwarn org.simpleframework.**
-keep class org.simpleframework.** { *; }

#OkGO - okgo,okrx,okserver
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**
-keep class okio.**{*;}
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}

-dontwarn com.lzy.okgo.**
-keep class com.lzy.okgo.**{*;}

-dontwarn com.lzy.okrx.**
-keep class com.lzy.okrx.**{*;}

-dontwarn com.lzy.okserver.**
-keep class com.lzy.okserver.**{*;}

#Jackson
-dontwarn com.fasterxml.jackson.**
-keep class com.fasterxml.jackson.** { *;}

#ormlite
-dontwarn com.j256.**
-keep class com.j256.** { *;}
-keep class com.j256.**
-keepclassmembers class com.j256.** { *; }
-keep enum com.j256.**
-keepclassmembers enum com.j256.** { *; }
-keep interface com.j256.**
-keepclassmembers interface com.j256.** { *; }
-keepclassmembers class classpath.** {
  public *;
}

#dom4j
-dontwarn org.dom4j.**
-keep class org.dom4j.** { *;}

#xstream
-dontwarn com.thoughtworks.xstream.**
-keep class com.thoughtworks.xstream.** { *;}

#MPChart
-dontwarn com.github.mikephil.**
-keep class com.github.mikephil.** { *; }

#Picasso
-dontwarn com.squareup.picasso.**
-keep class com.squareup.picasso.** { *;}

#图片选择工具
-dontwarn io.valuesfeng.picker.**
-keep class io.valuesfeng.picker.** { *;}
-dontwarn com.bumptech.glide.**
-keep class com.bumptech.glide.** { *;}

#第三方登录分享工具
-dontwarn me.shaohui.shareutil.**
-keep class me.shaohui.shareutil.** { *;}

#极光推送
-dontoptimize
-dontpreverify

-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }

-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }

#讯飞语音
-dontwarn com.iflytek.**
-keep class com.iflytek.**{*;}

#Anko
-dontwarn org.jetbrains.anko.**
-keep class org.jetbrains.anko.**{*;}

#高德地图 - 3D 地图,定位,搜索,2D地图,导航
-keep class com.amap.api.mapcore.**{*;}
-keep class com.amap.api.maps.**{*;}
-keep class com.autonavi.amap.*{*;}
-dontwarn com.amap.api.mapcore.**
-dontwarn com.amap.api.maps.**
-dontwarn com.autonavi.amap.**

-keep class com.amap.api.location.**{*;}
-keep class com.amap.api.fence.**{*;}
-keep class com.autonavi.aps.amapapi.model.**{*;}
-dontwarn com.amap.api.location.**
-dontwarn com.amap.api.fence.**
-dontwarn com.autonavi.aps.amapapi.model.**

-keep class com.amap.api.services.**{*;}
-dontwarn com.amap.api.services.**

-keep class com.amap.api.maps2d.**{*;}
-keep class com.amap.api.mapcore2d.**{*;}
-dontwarn com.amap.api.maps2d.**
-dontwarn com.amap.api.mapcore2d.**

-keep class com.amap.api.navi.**{*;}
-keep class com.autonavi.**{*;}
-dontwarn com.amap.api.navi.**
-dontwarn com.autonavi.**

-dontwarn com.amap.apis.**
-dontwarn com.amap.api.**
-dontwarn com.a.a.**
-dontwarn com.autonavi.**
-keep class com.amap.api.**{*;}
-keep class com.autonavi.**{*;}
-keep class com.a.a.**{*;}

#EventBus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

#glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# for DexGuard only
-keepresourcexmlelements manifest/application/meta-data@value=GlideModule

#log4j
-dontwarn org.apache.log4j.**
-keep class  org.apache.log4j.** { *;}

#BaseRecyclerViewAdapterHelper
-keep class com.chad.library.adapter.** {
*;
}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
     <init>(...);
}
-keep class com.ch

#------------------ other ------------------
# slidingmenu 的混淆
-dontwarn com.jeremyfeinstein.slidingmenu.lib.**
-keep class com.jeremyfeinstein.slidingmenu.lib.** { *; }

# ActionBarSherlock混淆
-dontwarn com.actionbarsherlock.**
-keep class com.actionbarsherlock.** { *; }
-keep interface com.actionbarsherlock.** { *; }
-keep class * extends java.lang.annotation.Annotation { *; }
-keepclasseswithmembernames class * {
    native <methods>;
}

-keep class com.jph.android.entity.** {
    <fields>;
    <methods>;
}

-dontwarn android.support.**
-dontwarn com.slidingmenu.lib.app.SlidingMapActivity
-keep class android.support.** { *; }
-keep class com.actionbarsherlock.** { *; }
-keep interface com.actionbarsherlock.** { *; }
-keep class com.slidingmenu.** { *; }
-keep interface com.slidingmenu.** { *; }

#-optimizationpasses 7
#-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-dontoptimize
-dontusemixedcaseclassnames
-verbose
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontwarn dalvik.**
#-overloadaggressively

#@proguard_debug_start
# ------------------ Keep LineNumbers and properties ---------------- #
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod
-renamesourcefileattribute TbsSdkJava
-keepattributes SourceFile,LineNumberTable
#@proguard_debug_end

# --------------------------------------------------------------------------
# Addidional for x5.sdk classes for apps

-keep class com.tencent.smtt.export.external.**{
    *;
}

-keep class com.tencent.tbs.video.interfaces.IUserStateChangedListener {
  *;
}

-keep class com.tencent.smtt.sdk.CacheManager {
  public *;
}

-keep class com.tencent.smtt.sdk.CookieManager {
  public *;
}

-keep class com.tencent.smtt.sdk.WebHistoryItem {
  public *;
}

-keep class com.tencent.smtt.sdk.WebViewDatabase {
  public *;
}

-keep class com.tencent.smtt.sdk.WebBackForwardList {
  public *;
}

-keep public class com.tencent.smtt.sdk.WebView {
  public <fields>;
  public <methods>;
}

-keep public class com.tencent.smtt.sdk.WebView$HitTestResult {
  public static final <fields>;
  public java.lang.String getExtra();
  public int getType();
}

-keep public class com.tencent.smtt.sdk.WebView$WebViewTransport {
  public <methods>;
}

-keep public class com.tencent.smtt.sdk.WebView$PictureListener {
  public <fields>;
  public <methods>;
}


-keepattributes InnerClasses

-keep public enum com.tencent.smtt.sdk.WebSettings$** {
    *;
}

-keep public enum com.tencent.smtt.sdk.QbSdk$** {
    *;
}

-keep public class com.tencent.smtt.sdk.WebSettings {
    public *;
}


-keepattributes Signature
-keep public class com.tencent.smtt.sdk.ValueCallback {
  public <fields>;
  public <methods>;
}

-keep public class com.tencent.smtt.sdk.WebViewClient {
  public <fields>;
  public <methods>;
}

-keep public class com.tencent.smtt.sdk.DownloadListener {
  public <fields>;
  public <methods>;
}

-keep public class com.tencent.smtt.sdk.WebChromeClient {
  public <fields>;
  public <methods>;
}

-keep public class com.tencent.smtt.sdk.WebChromeClient$FileChooserParams {
  public <fields>;
  public <methods>;
}

-keep class com.tencent.smtt.sdk.SystemWebChromeClient{
  public *;
}
# 1. extension interfaces should be apparent
-keep public class com.tencent.smtt.export.external.extension.interfaces.* {
  public protected *;
}

# 2. interfaces should be apparent
-keep public class com.tencent.smtt.export.external.interfaces.* {
  public protected *;
}

-keep public class com.tencent.smtt.sdk.WebViewCallbackClient {
  public protected *;
}

-keep public class com.tencent.smtt.sdk.WebStorage$QuotaUpdater {
  public <fields>;
  public <methods>;
}

-keep public class com.tencent.smtt.sdk.WebIconDatabase {
  public <fields>;
  public <methods>;
}

-keep public class com.tencent.smtt.sdk.WebStorage {
  public <fields>;
  public <methods>;
}

-keep public class com.tencent.smtt.sdk.DownloadListener {
  public <fields>;
  public <methods>;
}

-keep public class com.tencent.smtt.sdk.QbSdk {
  public <fields>;
  public <methods>;
}

-keep public class com.tencent.smtt.sdk.QbSdk$PreInitCallback {
  public <fields>;
  public <methods>;
}
-keep public class com.tencent.smtt.sdk.CookieSyncManager {
  public <fields>;
  public <methods>;
}

-keep public class com.tencent.smtt.sdk.Tbs* {
  public <fields>;
  public <methods>;
}

-keep public class com.tencent.smtt.utils.LogFileUtils {
  public <fields>;
  public <methods>;
}

-keep public class com.tencent.smtt.utils.TbsLog {
  public <fields>;
  public <methods>;
}

-keep public class com.tencent.smtt.utils.TbsLogClient {
  public <fields>;
  public <methods>;
}

-keep public class com.tencent.smtt.sdk.CookieSyncManager {
  public <fields>;
  public <methods>;
}

# Added for game demos
-keep public class com.tencent.smtt.sdk.TBSGamePlayer {
  public <fields>;
  public <methods>;
}

-keep public class com.tencent.smtt.sdk.TBSGamePlayerClient* {
  public <fields>;
  public <methods>;
}

-keep public class com.tencent.smtt.sdk.TBSGamePlayerClientExtension {
  public <fields>;
  public <methods>;
}

-keep public class com.tencent.smtt.sdk.TBSGamePlayerService* {
  public <fields>;
  public <methods>;
}

-keep public class com.tencent.smtt.utils.Apn {
  public <fields>;
  public <methods>;
}
# end


-keep public class com.tencent.smtt.export.external.extension.proxy.ProxyWebViewClientExtension {
  public <fields>;
  public <methods>;
}

-keep class MTT.ThirdAppInfoNew {
  *;
}

-keep class com.tencent.mtt.MttTraceEvent {
  *;
}

# Game related
-keep public class com.tencent.smtt.gamesdk.* {
  public protected *;
}

-keep public class com.tencent.smtt.sdk.TBSGameBooter {
        public <fields>;
        public <methods>;
}

-keep public class com.tencent.smtt.sdk.TBSGameBaseActivity {
  public protected *;
}

-keep public class com.tencent.smtt.sdk.TBSGameBaseActivityProxy {
  public protected *;
}

-keep public class com.tencent.smtt.gamesdk.internal.TBSGameServiceClient {
  public *;
}

#-------------------------------------------------------------------------

#---------------------------------3.与js互相调用的类------------------------

#-keepclasseswithmembers class com.demo.login.bean.ui.MainActivity$JSInterface {
#      <methods>;
#}

#-------------------------------------------------------------------------

#---------------------------------4.反射相关的类和方法-----------------------



#----------------------------------------------------------------------------
#---------------------------------------------------------------------------------------------------

#-------------------------------------------基本不用动区域--------------------------------------------
#---------------------------------基本指令区----------------------------------
-optimizationpasses 5

# 混淆时不使用大小写混合，混淆后的类名为小写
# windows下的同学还是加入这个选项吧(windows大小写不敏感)
-dontusemixedcaseclassnames

# 指定不去忽略非公共的库的类
# 默认跳过，有些情况下编写的代码与类库中的类在同一个包下，并且持有包中内容的引用，此时就需要加入此条声明
-dontskipnonpubliclibraryclasses

# 指定不去忽略非公共的库的类的成员
-dontskipnonpubliclibraryclassmembers

# 不做预检验，preverify是proguard的四个步骤之一
# Android不需要preverify，去掉这一步可以加快混淆速度
-dontpreverify

# 有了verbose这句话，混淆后就会生成映射文件
# 包含有类名->混淆后类名的映射关系
# 然后使用printmapping指定映射文件的名称
-verbose
-printmapping priguardMapping.txt

# 指定混淆时采用的算法，后面的参数是一个过滤器
# 这个过滤器是谷歌推荐的算法，一般不改变
-optimizations !code/simplification/artithmetic,!field/*,!class/merging/*

# 保护代码中的Annotation不被混淆
# 这在JSON实体映射时非常重要，比如fastJson
-keepattributes *Annotation*

# 避免混淆泛型
# 这在JSON实体映射时非常重要，比如fastJson
-keepattributes Signature

# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable

# 保留所有的本地native方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

# 保留了继承自Activity、Application这些类的子类
# 因为这些子类有可能被外部调用
# 比如第一行就保证了所有Activity的子类不要被混淆
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * extends android.app.Fragment
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.annotation.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.**
-keep class android.support.** { *; }
-dontwarn android.support.**

# 如果有引用android-support-v4.jar包，可以添加下面这行
-keep public class com.null.test.ui.fragment.** {*;}

# 保留Activity中的方法参数是view的方法，
# 从而我们在layout里面编写onClick就不会影响
-keepclassmembers class * extends android.app.Activity {
    public void * (android.view.View);
}

# 枚举类不能被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# 保留自定义控件(继承自View)不能被混淆
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(***);
    *** get* ();
}

# 保留Parcelable序列化的类不能被混淆
-keep class * implements android.os.Parcelable{
    public static final android.os.Parcelable$Creator *;
}

# 保留Serializable 序列化的类不被混淆
-keepclassmembers class * implements java.io.Serializable {
   static final long serialVersionUID;
   private static final java.io.ObjectStreamField[] serialPersistentFields;
   !static !transient <fields>;
   private void writeObject(java.io.ObjectOutputStream);
   private void readObject(java.io.ObjectInputStream);
   java.lang.Object writeReplace();
   java.lang.Object readResolve();
}

# 对R文件下的所有类及其方法，都不能被混淆
-keepclassmembers class **.R$* {
    *;
}

# 对于带有回调函数onEvent的，不能混淆
-keepclassmembers class ** {
    public void onEvent*(**);
}
-keepclassmembers class ** {
public void onEvent(**);
}

# 保留实体类和成员不被混淆
-keep class com.null.test.entities.** {
    public void set*(***);
    public *** get*();
    public *** is*();
}
#----------------------------------------------------------------------------

#---------------------------------webview------------------------------------
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}

# 对WebView的处理
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String);
}
# 对JavaScript的处理
-keepclassmembers class com.null.test.MainActivity$JSInterfacel {
    <methods>;
}

#--------------------------------- JNI ------------------------------------

# 方法名中含有“JNI”字符的，认定是Java Native Interface方法，自动排除
# 方法名中含有“JRI”字符的，认定是Java Reflection Interface方法，自动排除

-keepclasseswithmembers class * {
    ... *JNI*(...);
}

-keepclasseswithmembernames class * {
  ... *JRI*(...);
}

-keep class **JNI* {*;}

#----------------------------------------------------------------------------
#---------------------------------------------------------------------------------------------------