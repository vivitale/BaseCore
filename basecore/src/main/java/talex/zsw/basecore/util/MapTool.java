package talex.zsw.basecore.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * 作用: 导航
 * 作者: 赵小白 email:edisonzsw@icloud.com 
 * 日期: 2017/9/15 14:22 
 * 修改人：
 * 修改时间：
 * 修改备注：
 * <p>
 * 高德调起app传送门：http://lbs.amap.com/api/amap-mobile/summary/
 * 百度调起app传送门：http://lbsyun.baidu.com/index.php?title=uri/api/android
 * 百度网页版传送门：http://lbsyun.baidu.com/index.php?title=uri/api/web
 * 谷歌地图app传送门：https://developers.google.com/maps/documentation/android-api/intents
 */
public class MapTool
{
	/**
	 * 启动高德App进行导航
	 *
	 * @param lat   必填 纬度
	 * @param lon   必填 经度
	 * @param dev   必填 是否偏移(0:lat 和 lon 是已经加密后的,不需要国测加密; 1:需要国测加密)
	 * @param style 必填 导航方式(0 速度快; 1 费用少; 2 路程短; 3 不走高速；4 躲避拥堵；5 不走高速且避免收费；6 不走高速且躲避拥堵；7 躲避收费和拥堵；8 不走高速躲避收费和拥堵))
	 */
	public static void gaodeNaviActivity(Context context, String lat, String lon, String dev, String style)
	{
		StringBuffer stringBuffer = new StringBuffer("androidamap://navi?");
		stringBuffer.append("lat=").append(lat).append("&lon=").append(lon).append("&dev=").append(dev).append("&style=").append(style);

		Intent intent = new Intent("android.intent.action.VIEW", android.net.Uri.parse(stringBuffer.toString()));
		intent.setPackage("com.autonavi.minimap");
		context.startActivity(intent);
	}

	/**
	 * 启动高德地图Web
	 *
	 * @param lat 必填 纬度
	 * @param lon 必填 经度
	 */
	public static void gaodeWeb(Context context, String lat, String lon)
	{
		String url = "http://mo.amap.com/?dev=0&q="+lat+","+lon;
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(url));
		context.startActivity(intent);
	}

	/**
	 * 启动百度App进行导航
	 *
	 * @param lat 必填 纬度
	 * @param lon 必填 经度
	 */
	public static void baiduNaviActivity(Context context, String lat, String lon)
	{
		StringBuffer stringBuffer = new StringBuffer("baidumap://map/direction?destination=");
		stringBuffer.append(lat).append(",").append(lon).append("&mode=driving").append("&sy=0");

		Intent intent = new Intent("android.intent.action.VIEW", android.net.Uri.parse(stringBuffer.toString()));
		intent.setPackage("com.baidu.BaiduMap");
		context.startActivity(intent);
	}

	/**
	 * 启动百度App进行导航
	 *
	 * @param lat 必填 纬度
	 * @param lon 必填 经度
	 */
	public static void baiduMapActivity(Context context, String lat, String lon)
	{
		Intent i1 = new Intent();
		i1.setData(Uri.parse("baidumap://map/show?center="+lat+","+lon+"&zoom=11"));
		context.startActivity(i1);
	}

	/**
	 * 启动百度Web
	 *
	 * @param lat 必填 纬度
	 * @param lon 必填 经度
	 */
	public static void baiduMapWeb(Context context, String lat, String lon)
	{
		String url = "http://api.map.baidu.com/marker?location="+lat+","+lon;
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(url));
		context.startActivity(intent);
	}

	/**
	 * 启动谷歌地图App进行导航
	 *
	 * @param lat 必填 纬度
	 * @param lon 必填 经度
	 */
	public void googleNaviActivity(Context context, String lat, String lon)
	{
		Uri gmmIntentUri = Uri.parse("google.navigation:q="+lat+","+lon);
		Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
		mapIntent.setPackage("com.google.android.apps.maps");
		context.startActivity(mapIntent);
	}

	/**
	 * 启动谷歌地图App
	 *
	 * @param lat 必填 纬度
	 * @param lon 必填 经度
	 */
	public void googleMapActivity(Context context, String lat, String lon)
	{
		Uri gmmIntentUri = Uri.parse("geo:"+lat+","+lon+"");
		Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
		mapIntent.setPackage("com.google.android.apps.maps");
		context.startActivity(mapIntent);
	}

	/**
	 * 打开google Web地图导航
	 *
	 * @param lat 必填 纬度
	 * @param lon 必填 经度
	 */
	private void googleNaviWeb(Context context, String lat, String lon)
	{
		StringBuffer stringBuffer
			= new StringBuffer("http://ditu.google.cn/maps?hl=zh&mrt=loc&q=").append(lat).append(",").append(lon);
		Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(stringBuffer.toString()));
		context.startActivity(i);
	}

	/**
	 * 根据包名检测某个APP是否安装
	 *
	 * @param packageName 包名
	 * @return true 安装 false 没有安装
	 */
	public static boolean isInstallByRead(String packageName)
	{
		return new File("/data/data/"+packageName).exists();
	}

	public static boolean isInstallGaode()
	{
		return isInstallByRead("com.autonavi.minimap");
	}

	public static boolean isInstallBaidu()
	{
		return isInstallByRead("com.baidu.BaiduMap");
	}

	public static boolean isInstallGoogle()
	{
		return isInstallByRead("com.google.android.apps.maps");
	}
}
