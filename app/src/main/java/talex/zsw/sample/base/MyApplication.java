package talex.zsw.sample.base;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.commonsdk.UMConfigure;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import talex.zsw.basecore.util.Tool;
import talex.zsw.sample.module.main.ui.MainActivity;

/**
 * 作用：使用的Application
 * 作者：赵小白 email:vvtale@gmail.com  
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MyApplication extends BaseApplication
{
	@Override public void onCreate()
	{
		super.onCreate();
		initHttp();
		Tool.initCaoc(0, MainActivity.class);
		UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "");
		//Bugly.init(this, "", false);// 调试时，将第三个参数改为true
		if(LeakCanary.isInAnalyzerProcess(this))
		{
			return;
		}
		LeakCanary.install(this);
	}

	@Override protected void attachBaseContext(Context base)
	{
		super.attachBaseContext(base);
		// 安装tinker
		//		Beta.installTinker();
		//		Beta.canNotifyUserRestart = false;
		//		Beta.betaPatchListener = new BetaPatchListener()
		//		{
		//			@Override public void onPatchReceived(String patchFile)
		//			{
		//				Log.d("Tinker", "补丁下载地址");
		//			}
		//
		//			@Override public void onDownloadReceived(long savedLength, long totalLength)
		//			{
		//				Log.d("Tinker", String.format(Locale.getDefault(), "%s %d%%", Beta.strNotificationDownloading, (int) (
		//					totalLength == 0 ? 0 : savedLength*100/totalLength)));
		//			}
		//
		//			@Override public void onDownloadSuccess(String msg)
		//			{
		//				Log.d("Tinker", "补丁下载成功");
		//			}
		//
		//			@Override public void onDownloadFailure(String msg)
		//			{
		//				Log.d("Tinker", "补丁下载失败");
		//			}
		//
		//			@Override public void onApplySuccess(String msg)
		//			{
		//				Log.d("Tinker", "补丁应用成功");
		//			}
		//
		//			@Override public void onApplyFailure(String msg)
		//			{
		//				Log.d("Tinker", "补丁应用失败");
		//			}
		//
		//			@Override public void onPatchRollback()
		//			{
		//				Log.d("Tinker", "补丁回滚");
		//			}
		//		};
	}

	@Override public void exit()
	{
		OkGo.getInstance().cancelAll();
		super.exit();
	}

	// ================= 网络请求 ========================
	private void initHttp()
	{
		//---------这里给出的是示例代码,告诉你可以这么传,实际使用的时候,根据需要传,不需要就不传-------------//
		HttpHeaders headers = new HttpHeaders();
		//		headers.put("commonHeaderKey1", "commonHeaderValue1");    //header不支持中文，不允许有特殊字符
		//		headers.put("commonHeaderKey2", "commonHeaderValue2");
		HttpParams params = new HttpParams();
		//		params.put("commonParamsKey1", "commonParamsValue1");     //param支持中文,直接传,不要自己编码
		//		params.put("commonParamsKey2", "这里支持中文参数");
		//-------------------------------------------------------------------------------------//

		OkHttpClient.Builder builder = new OkHttpClient.Builder();

		//全局的读取超时时间
		builder.readTimeout(10*1000, TimeUnit.MILLISECONDS);
		//全局的写入超时时间
		builder.writeTimeout(10*1000, TimeUnit.MILLISECONDS);
		//全局的连接超时时间
		builder.connectTimeout(10*1000, TimeUnit.MILLISECONDS);

		//使用sp保持cookie，如果cookie不过期，则一直有效
		builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));
		builder.retryOnConnectionFailure(false);

		//方法一：信任所有证书,不安全有风险
		HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();

		OkGo.getInstance().init(this)                       //必须调用初始化
		    .setOkHttpClient(builder.build())               //必须设置OkHttpClient
		    .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
		    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
		    .setRetryCount(0)                               //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
		    .addCommonHeaders(headers)                      //全局公共头
		    .addCommonParams(params);                       //全局公共参数
		OkGo.getInstance().cancelAll();
	}
}
