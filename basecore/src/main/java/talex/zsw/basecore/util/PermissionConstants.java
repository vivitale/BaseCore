package talex.zsw.basecore.util;

import android.Manifest;
import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * 权限列表
 */
@SuppressLint("InlinedApi")
public final class PermissionConstants
{
	/** 日历权限组 */
	public static final String CALENDAR = Manifest.permission_group.CALENDAR;
	/** 相机权限组 */
	public static final String CAMERA = Manifest.permission_group.CAMERA;
	/** 联系人权限组 */
	public static final String CONTACTS = Manifest.permission_group.CONTACTS;
	/** 地理位置权限组 */
	public static final String LOCATION = Manifest.permission_group.LOCATION;
	/** 麦克风权限组 */
	public static final String MICROPHONE = Manifest.permission_group.MICROPHONE;
	/** 电话权限组 */
	public static final String PHONE = Manifest.permission_group.PHONE;
	/** 人体传感器权限组 */
	public static final String SENSORS = Manifest.permission_group.SENSORS;
	/** 短信权限组 */
	public static final String SMS = Manifest.permission_group.SMS;
	/** 内存卡权限组 */
	public static final String STORAGE = Manifest.permission_group.STORAGE;

	/** 日历权限组 */
	private static final String[] GROUP_CALENDAR = {
		// 读取日历活动和机密信息
		permission.READ_CALENDAR,
		// 添加或修改日历活动，并在所有者不知情的情况下向邀请对象发送电子邮件
		permission.WRITE_CALENDAR};
	/** 相机权限组 */
	private static final String[] GROUP_CAMERA = {
		// 拍摄照片和录制视频
		permission.CAMERA};
	/** 联系人权限组 */
	private static final String[] GROUP_CONTACTS = {
		// 读取您的通讯录
		permission.READ_CONTACTS,
		// 修改您的通讯录
		permission.WRITE_CONTACTS,
		// 查找设备上的帐户
		permission.GET_ACCOUNTS};
	/** 地理位置权限组 */
	private static final String[] GROUP_LOCATION = {
		// 精确位置（基于GPS和网络）
		permission.ACCESS_FINE_LOCATION,
		// 大致位置（基于网络）
		permission.ACCESS_COARSE_LOCATION};
	/** 麦克风权限组 */
	private static final String[] GROUP_MICROPHONE = {
		// 录音
		permission.RECORD_AUDIO};
	/** 电话权限组 */
	private static final String[] GROUP_PHONE = {
		// 读取手机状态和身份
		permission.READ_PHONE_STATE,
		// 读取电话号码
		permission.READ_PHONE_NUMBERS,
		// 直接拨打电话号码
		permission.CALL_PHONE,
		// 允许该应用接听来电
		permission.ANSWER_PHONE_CALLS,
		// 读取通话记录
		permission.READ_CALL_LOG,
		// 写入通话记录
		permission.WRITE_CALL_LOG,
		// 添加语音邮件
		permission.ADD_VOICEMAIL,
		// 拨打/接听SIP电话
		permission.USE_SIP,
		// 重新设置外拨电话的路径
		permission.PROCESS_OUTGOING_CALLS};
	/** 人体传感器权限组 */
	private static final String[] GROUP_SENSORS = {
		// 人体传感器（如心跳速率检测器
		permission.BODY_SENSORS};
	/** 短信权限组 */
	private static final String[] GROUP_SMS = {
		// 发送和查看短信
		permission.SEND_SMS,
		// 接收讯息（短信）
		permission.RECEIVE_SMS,
		// 读取您的讯息（短信或彩信）
		permission.READ_SMS,
		// 接收讯息 (WAP)
		permission.RECEIVE_WAP_PUSH,
		// 接收讯息（彩信）
		permission.RECEIVE_MMS,};
	/** 内存卡权限组 */
	private static final String[] GROUP_STORAGE = {
		// 读取您的SD卡中的内容
		permission.READ_EXTERNAL_STORAGE,
		// 修改或删除您的SD卡中的内容
		permission.WRITE_EXTERNAL_STORAGE};

	@StringDef({CALENDAR, CAMERA, CONTACTS, LOCATION, MICROPHONE, PHONE, SENSORS, SMS, STORAGE,})
	@Retention(RetentionPolicy.SOURCE)
	public @interface Permission
	{}

	public static String[] getPermissions(@Permission final String permission)
	{
		switch(permission)
		{
			case CALENDAR:
				return GROUP_CALENDAR;
			case CAMERA:
				return GROUP_CAMERA;
			case CONTACTS:
				return GROUP_CONTACTS;
			case LOCATION:
				return GROUP_LOCATION;
			case MICROPHONE:
				return GROUP_MICROPHONE;
			case PHONE:
				return GROUP_PHONE;
			case SENSORS:
				return GROUP_SENSORS;
			case SMS:
				return GROUP_SMS;
			case STORAGE:
				return GROUP_STORAGE;
		}
		return new String[]{permission};
	}
}
