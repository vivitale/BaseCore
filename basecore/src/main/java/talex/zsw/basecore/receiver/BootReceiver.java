package talex.zsw.basecore.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import talex.zsw.basecore.service.BootService;

/**
 * 作用: 开机启动广播监听
 * 作者: 赵小白 email:edisonzsw@icloud.com 
 * 日期: 2018/6/13 15:07 
 * 修改人：
 * 修改时间：
 * 修改备注：
 */

public class BootReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
		Intent service = new Intent(context, BootService.class);
		context.startService(service);
	}
}