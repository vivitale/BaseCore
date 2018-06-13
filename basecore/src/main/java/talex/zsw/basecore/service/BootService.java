package talex.zsw.basecore.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import org.greenrobot.eventbus.EventBus;

import talex.zsw.basecore.model.BootEvent;

/**
 * 作用: 开机启动服务
 * 作者: 赵小白 email:edisonzsw@icloud.com 
 * 日期: 2018/6/13 15:07 
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class BootService extends Service
{
	private final IBinder mBinder = new MyBinder();

	@Override public int onStartCommand(Intent intent, final int flags, int startId)
	{
		EventBus.getDefault().post(new BootEvent());
		return Service.START_STICKY;
	}

	@Override public IBinder onBind(Intent arg0)
	{
		return mBinder;
	}

	public class MyBinder extends Binder
	{
		public BootService getService()
		{
			return BootService.this;
		}
	}
}