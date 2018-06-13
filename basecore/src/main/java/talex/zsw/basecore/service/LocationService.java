package talex.zsw.basecore.service;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;

import talex.zsw.basecore.util.LocationTool;
import talex.zsw.basecore.util.LogTool;

public class LocationService extends Service
{
	private boolean isSuccess;
	private String lastLatitude = "";
	private String lastLongitude = "";
	private String latitude = "";
	private String longitude = "";
	private String country = "";
	private String locality = "";
	private String street = "";
	private OnGetLocationListener mOnGetLocationListener;
	private LocationTool.OnLocationChangeListener mOnLocationChangeListener
		= new LocationTool.OnLocationChangeListener()
	{
		@Override public void getLastKnownLocation(Location location)
		{
			lastLatitude = String.valueOf(location.getLatitude());
			lastLongitude = String.valueOf(location.getLongitude());
			if(mOnGetLocationListener != null)
			{
				mOnGetLocationListener.getLocation(lastLatitude, lastLongitude, latitude, longitude, country, locality, street);
			}
		}

		@Override public void onLocationChanged(final Location location)
		{
			latitude = String.valueOf(location.getLatitude());
			longitude = String.valueOf(location.getLongitude());
			if(mOnGetLocationListener != null)
			{
				mOnGetLocationListener.getLocation(lastLatitude, lastLongitude, latitude, longitude, country, locality, street);
			}
			country
				= LocationTool.getCountryName(getApplicationContext(), Double.parseDouble(latitude), Double.parseDouble(longitude));
			locality
				= LocationTool.getLocality(getApplicationContext(), Double.parseDouble(latitude), Double.parseDouble(longitude));
			street
				= LocationTool.getStreet(getApplicationContext(), Double.parseDouble(latitude), Double.parseDouble(longitude));
			if(mOnGetLocationListener != null)
			{
				mOnGetLocationListener.getLocation(lastLatitude, lastLongitude, latitude, longitude, country, locality, street);
			}
		}

		@Override public void onStatusChanged(String provider, int status, Bundle extras)
		{

		}
	};

	public void setOnGetLocationListener(OnGetLocationListener onGetLocationListener)
	{
		mOnGetLocationListener = onGetLocationListener;
	}

	@Override public void onCreate()
	{
		super.onCreate();
		new Thread(new Runnable()
		{
			@Override public void run()
			{
				Looper.prepare();
				isSuccess = LocationTool.registerLocation(getApplicationContext(),
				                                          60*1000, 10, mOnLocationChangeListener);
				if(isSuccess)
				{
					LogTool.v("BaseCore", "LocationService 初始化成功");
				}
				Looper.loop();
			}
		}).start();
	}

	@Nullable @Override public IBinder onBind(Intent intent)
	{
		return new LocationBinder();
	}

	@Override public void onDestroy()
	{
		LocationTool.unRegisterLocation();
		// 一定要制空，否则内存泄漏
		mOnGetLocationListener = null;
		super.onDestroy();
	}

	/**
	 * 获取位置监听器
	 */
	public interface OnGetLocationListener
	{
		void getLocation(String lastLatitude, String lastLongitude, String latitude, String longitude, String country, String locality, String street);
	}

	public class LocationBinder extends Binder
	{
		public LocationService getService()
		{
			return LocationService.this;
		}
	}

	//	// --------------------- 权限 -------------------
	//	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
	//	{
	//		super.onRequestPermissionsResult(requestCode, permissions, grantResults)
	//
	//		if (requestCode == PermissionsTool.REQUEST_CODE)
	//		{
	//			if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
	//			{
	//				// 权限成功
	//				bindService()
	//			}
	//			else
	//			{
	//				// 权限失败
	//			}
	//		}
	//	}
	//
	//	private fun checkPermissions(): Boolean
	//	{
	//		return PermissionsTool.checkPermission(this,
	//		                                       PermissionsTool.ACCESS_FINE_LOCATION) || PermissionsTool.checkPermission(
	//			this,
	//			PermissionsTool.ACCESS_COARSE_LOCATION)
	//	}
	//
	//	// --------------------- 定位服务 -------------------
	//	private var isBind = false
	//	private var mLocationService: LocationService? = null
	//
	//	private fun initLocation()
	//	{
	//		PermissionsTool.with(this)
	//		               .addPermission(PermissionsTool.ACCESS_FINE_LOCATION)
	//		               .addPermission(PermissionsTool.ACCESS_COARSE_LOCATION)
	//		               .initPermission()
	//
	//		if (checkPermissions())
	//		{
	//			bindService()
	//		}
	//	}
	//
	//	private val mLocationServiceConnection = object : ServiceConnection
	//	{
	//		override fun onServiceDisconnected(name:ComponentName)
	//		{
	//			mLocationService = null
	//		}
	//
	//		@SuppressLint("SetTextI18n")
	//		override fun onServiceConnected(name: ComponentName, service: IBinder)
	//		{
	//			isBind = true
	//			mLocationService = (service as LocationService.LocationBinder).getService()
	//			mLocationService?.setOnGetLocationListener { lastLatitude, lastLongitude, latitude, longitude, country, locality, street ->
	//			runOnUiThread {
	//			mTvLocation.text =
	//				"UI更新时间 = ${TimeTool.getCurTimeString()} \nlastLatitude = $lastLatitude \nlastLongitude = $lastLongitude \nlatitude = $latitude \nlongitude = $longitude \ncountry = $country \nlocality = $locality \nstreet = $street \n"
	//		}
	//		}
	//
	//		}
	//	}
	//
	//	private fun bindService()
	//	{
	//		if (checkPermissions())
	//		{
	//			if (isBind)
	//			{
	//				RxToast.warning("服务已启动")
	//			}
	//			else
	//			{
	//				val intent = Intent(applicationContext, LocationService::class.java)
	//				bindService(intent, mLocationServiceConnection, Context.BIND_AUTO_CREATE)
	//			}
	//		}
	//		else
	//		{
	//			RxToast.error("请设置权限")
	//			val intent = Intent("android.settings.APPLICATION_DETAILS_SETTINGS")
	//			val uri = Uri.fromParts("package", this.packageName, null)
	//			intent.data = uri
	//			start(intent)
	//		}
	//	}
	//
	//	private fun unBindService()
	//	{
	//		isBind = false
	//		unbindService(mLocationServiceConnection)
	//	}
}
