package talex.zsw.basecore.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import talex.zsw.basecore.util.LogTool;
import talex.zsw.basecore.util.NetPingTool;

public class PingService extends Service
{
	NetPingTool netPing = null;

	@Override public void onCreate()
	{
		super.onCreate();
	}

	@Override public ComponentName startService(Intent service)
	{
		return super.startService(service);
	}

	@Nullable @Override public IBinder onBind(Intent intent)
	{
		return new PingBinder();
	}

	@Override public void onDestroy()
	{
		if(netPing != null)
		{
			netPing.release();
			netPing = null;
		}
		super.onDestroy();
	}

	public void startPing(String s, NetPingTool.IOnNetPingListener listener)
	{
		startPing(s, 80, listener);
	}

	public void startPing(String s, int port, NetPingTool.IOnNetPingListener listener)
	{
		LogTool.d("startPing --> IP = "+s+"   port = "+port);
		if(netPing != null)
		{
			netPing.setmDomain(s);
			netPing.setmPort(port);
		}
		else
		{
			netPing = new NetPingTool(this, s, port, listener);
		}
		netPing.startGetDelay();
	}

	public class PingBinder extends Binder
	{
		public PingService getService()
		{
			return PingService.this;
		}
	}

	// ---------------- 使用方法 ----------------
	//	private PingService mPingService;
	//
	//	private ServiceConnection mPingServiceConnection = new ServiceConnection()
	//	{
	//		@Override public void onServiceConnected(ComponentName name, IBinder service)
	//		{
	//			LogTool.nv("onServiceConnected");
	//			mPingService = ((PingService.PingBinder) service).getService();
	//			mPingService.startPing("www.baidu.com", new NetPingTool.IOnNetPingListener()
	//			{
	//				@Override public void ontDelay(long log)
	//				{
	//					LogTool.ni("延迟 "+log+" ms");
	//				}
	//
	//				@Override public void onError()
	//				{
	//					LogTool.ne("错误，网络不通");
	//				}
	//			});
	//		}
	//
	//		@Override public void onServiceDisconnected(ComponentName name)
	//		{
	//			LogTool.nv("onServiceDisconnected");
	//			mPingService = null;
	//		}
	//	};
	//
	//	private void bindPingService()
	//	{
	//		LogTool.nv("bindPingService");
	//		Intent intent = new Intent(this, PingService.class);
	//		bindService(intent, mPingServiceConnection, Context.BIND_AUTO_CREATE);
	//	}
	//
	//	private void unBindPingService()
	//	{
	//		unbindService(mPingServiceConnection);
	//	}
}
