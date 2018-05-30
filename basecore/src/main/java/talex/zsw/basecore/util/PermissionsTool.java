package talex.zsw.basecore.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限工具
 */

public class PermissionsTool
{
	public static int REQUEST_CODE = 1203;

	// 联系人权限组
	/** 修改您的通讯录 */
	public static String WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS;
	/** 查找设备上的帐户 */
	public static String GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;
	/** 读取您的通讯录 */
	public static String READ_CONTACTS = Manifest.permission.READ_CONTACTS;


	// 电话权限组
	/** 读取通话记录 */
	@SuppressLint("InlinedApi") public static String READ_CALL_LOG = Manifest.permission.READ_CALL_LOG;
	/** 读取手机状态和身份 */
	public static String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
	/** 直接拨打电话号码 */
	public static String CALL_PHONE = Manifest.permission.CALL_PHONE;
	/** 写入通话记录 */
	@SuppressLint("InlinedApi") public static String WRITE_CALL_LOG = Manifest.permission.WRITE_CALL_LOG;
	/** 拨打/接听SIP电话 */
	public static String USE_SIP = Manifest.permission.USE_SIP;
	/** 重新设置外拨电话的路径 */
	public static String PROCESS_OUTGOING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS;
	/** 添加语音邮件 */
	public static String ADD_VOICEMAIL = Manifest.permission.ADD_VOICEMAIL;


	// 日历权限组
	/** 读取日历活动和机密信息 */
	public static String READ_CALENDAR = Manifest.permission.READ_CALENDAR;
	/** 添加或修改日历活动，并在所有者不知情的情况下向邀请对象发送电子邮件 */
	public static String WRITE_CALENDAR = Manifest.permission.WRITE_CALENDAR;


	// 相机权限组
	/** 拍摄照片和录制视频 */
	public static String CAMERA = Manifest.permission.CAMERA;


	// 人体传感器权限组
	/** 人体传感器（如心跳速率检测器） */
	@SuppressLint("InlinedApi") public static String BODY_SENSORS = Manifest.permission.BODY_SENSORS;


	// 地理位置权限组
	/** 精确位置（基于GPS和网络） */
	public static String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
	/** 大致位置（基于网络） */
	public static String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;


	// 内存卡权限组
	/** 读取您的SD卡中的内容 */
	@SuppressLint("InlinedApi") public static String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
	/** 修改或删除您的SD卡中的内容 */
	public static String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;


	// 麦克风权限组
	/** 录音 */
	public static String RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;


	// 短信权限组
	/** 读取您的讯息（短信或彩信） */
	public static String READ_SMS = Manifest.permission.READ_SMS;
	/** 接收讯息 (WAP) */
	public static String RECEIVE_WAP_PUSH = Manifest.permission.RECEIVE_WAP_PUSH;
	/** 接收讯息（彩信） */
	public static String RECEIVE_MMS = Manifest.permission.RECEIVE_MMS;
	/** 接收讯息（短信） */
	public static String RECEIVE_SMS = Manifest.permission.RECEIVE_SMS;
	/** 发送和查看短信 */
	public static String SEND_SMS = Manifest.permission.SEND_SMS;
	/** 读取小区广播消息 */
	public static String READ_CELL_BROADCASTS = "android.permission.READ_CELL_BROADCASTS";

	public static PermissionsTool.Builder with(Activity activity)
	{
		return new Builder(activity);
	}

	public static class Builder
	{
		private Activity mActivity;
		private List<String> permissionList;

		public Builder(@NonNull Activity activity)
		{
			mActivity = activity;
			permissionList = new ArrayList<>();
		}

		/**
		 * Determine whether <em>you</em> have been granted a particular permission.
		 *
		 * @param permission The name of the permission being checked.
		 * @return {@link PackageManager#PERMISSION_GRANTED} if you have the
		 * permission, or {@link PackageManager#PERMISSION_DENIED} if not.
		 * @see PackageManager#checkPermission(String, String)
		 */
		public Builder addPermission(@NonNull String permission)
		{
			if(!permissionList.contains(permission))
			{
				permissionList.add(permission);
			}
			return this;
		}

		public List<String> initPermission()
		{
			List<String> list = new ArrayList<>();
			for(String permission : permissionList)
			{
				if(ActivityCompat.checkSelfPermission(mActivity, permission) != PackageManager.PERMISSION_GRANTED)
				{
					list.add(permission);
				}
			}
			if(list.size() > 0)
			{
				ActivityCompat.requestPermissions(mActivity, list.toArray(new String[list.size()]), REQUEST_CODE);
			}
			return list;
		}
	}

	public static boolean checkPermission(Activity activity, String permission)
	{
		return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
	}
}
