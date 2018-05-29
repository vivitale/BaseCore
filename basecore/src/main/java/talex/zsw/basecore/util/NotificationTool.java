package talex.zsw.basecore.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * 作用: 系统消息
 * 作者: 赵小白 email:vvtale@gmail.com  
 * 日期: 2018/5/22 16:46 
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class NotificationTool
{
	/**
	 * 发送系统消息的消息
	 *
	 * @param icon       图标资源id
	 * @param title      标题
	 * @param content    内容
	 * @param mainIntent 意图
	 */
	public static void sendSysNotification(int icon, String title, String content, Intent mainIntent)
	{
		sendSysNotification(icon, title, content, mainIntent, 1101);
	}

	/**
	 * 发送系统消息的消息
	 *
	 * @param icon       图标资源id
	 * @param title      标题
	 * @param content    内容
	 * @param mainIntent 意图
	 * @param id         Notification指定id
	 */
	public static void sendSysNotification(int icon, String title, String content, Intent mainIntent, int id)
	{
		//获取NotificationManager实例
		NotificationManager mNotifyManager = (NotificationManager) Tool
			.getContext()
			.getSystemService(Context.NOTIFICATION_SERVICE);
		//获取PendingIntent
		PendingIntent mainPendingIntent
			= PendingIntent.getActivity(Tool.getContext(), 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		//创建 Notification.Builder 对象
		NotificationCompat.Builder builder = new NotificationCompat.Builder(Tool.getContext()).setSmallIcon(icon)
		                                                                                      //点击通知后自动清除
		                                                                                      .setAutoCancel(true)
		                                                                                      .setContentTitle(title)
		                                                                                      .setContentText(content)
		                                                                                      .setDefaults(Notification.DEFAULT_ALL)
		                                                                                      .setContentIntent(mainPendingIntent);
		//发送通知
		mNotifyManager.notify(id, builder.build());
	}

	/**
	 * 取消指定id的消息
	 *
	 * @param id Notification指定id
	 */
	public static void clearNotification(int id)
	{
		//获取NotificationManager实例
		NotificationManager mNotifyManager = (NotificationManager) Tool
			.getContext()
			.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotifyManager.cancel(id);
	}

	/**
	 * 取消默认id的消息
	 */
	public static void clearNotification()
	{
		//获取NotificationManager实例
		NotificationManager mNotifyManager = (NotificationManager) Tool
			.getContext()
			.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotifyManager.cancel(1101);
	}
}
