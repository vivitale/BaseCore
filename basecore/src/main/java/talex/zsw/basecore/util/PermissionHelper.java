package talex.zsw.basecore.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;

import java.util.List;

import talex.zsw.basecore.R;

/**
 * 权限简单获取工具类
 */
//PermissionHelper.requestStorage(new PermissionTool.FullCallback()
//	{
//@Override public void onGranted(List<String> permissionsGranted)
//	{
//
//	}
//
//@Override public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied)
//	{
//	if(!permissionsDeniedForever.isEmpty())
//	{
//	PermissionHelper.showOpenAppSettingDialog();
//	}
//	}
//	});
public class PermissionHelper
{
	/** 获取相机权限 */
	public static void requestCamera(final PermissionTool.FullCallback listener)
	{
		request(listener, PermissionConstants.CAMERA);
	}

	/** 获取内存卡权限 */
	public static void requestStorage(final PermissionTool.FullCallback listener)
	{
		request(listener, PermissionConstants.STORAGE);
	}

	/** 获取电话权限 */
	public static void requestPhone(final PermissionTool.FullCallback listener)
	{
		request(listener, PermissionConstants.PHONE);
	}

	/** 获取短信权限 */
	public static void requestSms(final PermissionTool.FullCallback listener)
	{
		request(listener, PermissionConstants.SMS);
	}

	/** 获取地理位置权限 */
	public static void requestLocation(final PermissionTool.FullCallback listener)
	{
		request(listener, PermissionConstants.LOCATION);
	}

	/** 获取日历权限 */
	public static void requestCalendar(final PermissionTool.FullCallback listener)
	{
		request(listener, PermissionConstants.CALENDAR);
	}

	/** 获取联系人权限 */
	public static void requestContacts(final PermissionTool.FullCallback listener)
	{
		request(listener, PermissionConstants.CONTACTS);
	}

	/** 获取麦克风权限 */
	public static void requestMicrophone(final PermissionTool.FullCallback listener)
	{
		request(listener, PermissionConstants.MICROPHONE);
	}

	/** 获取人体传感器权限 */
	public static void requestSensors(final PermissionTool.FullCallback listener)
	{
		request(listener, PermissionConstants.SENSORS);
	}

	/** 获取权限 */
	public static void request(final PermissionTool.FullCallback listener, final @PermissionConstants.Permission
		String... permissions)
	{
		PermissionTool.permission(permissions).callback(new PermissionTool.FullCallback()
		{
			@Override public void onGranted(List<String> permissionsGranted)
			{
				if(listener != null)
				{
					listener.onGranted(permissionsGranted);
				}
				LogTool.d("BaseCore", permissionsGranted);
			}

			@Override public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied)
			{
				if(listener != null)
				{
					listener.onDenied(permissionsDeniedForever, permissionsDenied);
				}
				LogTool.d("BaseCore", permissionsDeniedForever, permissionsDenied);
			}
		}).theme(new PermissionTool.ThemeCallback()
		{
			@Override public void onActivityCreate(Activity activity)
			{
				setFullScreen(activity);
			}
		}).request();
	}

	// --------------------------- Dialog -----------------------------

	/** 显示一个打开APP设置来获取权限的Dialog */
	public static void showOpenAppSettingDialog()
	{
		showOpenAppSettingDialog(new DialogInterface.OnClickListener()
		{
			@Override public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
		});
	}

	/** 显示一个打开APP设置来获取权限的Dialog */
	public static void showOpenAppSettingDialog(DialogInterface.OnClickListener cancelListener)
	{
		String message = Tool.getContext().getResources().getString(R.string.permission_denied_forever_message);
		showOpenAppSettingDialog(message, new DialogInterface.OnClickListener()
		{
			@Override public void onClick(DialogInterface dialog, int which)
			{
				launchAppDetailsSettings();
			}
		}, cancelListener);
	}

	/** 显示一个打开APP设置来获取权限的Dialog */
	public static void showOpenAppSettingDialog(String message)
	{
		showOpenAppSettingDialog(message, new DialogInterface.OnClickListener()
		{
			@Override public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
		});
	}

	/** 显示一个打开APP设置来获取权限的Dialog */
	public static void showOpenAppSettingDialog(String message, DialogInterface.OnClickListener cancelListener)
	{
		showOpenAppSettingDialog(message, new DialogInterface.OnClickListener()
		{
			@Override public void onClick(DialogInterface dialog, int which)
			{
				launchAppDetailsSettings();
			}
		}, cancelListener);
	}

	/** 显示一个打开APP设置来获取权限的Dialog */
	public static void showOpenAppSettingDialog(DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancelListener)
	{
		String message = Tool.getContext().getResources().getString(R.string.permission_denied_forever_message);
		showOpenAppSettingDialog(message, okListener, cancelListener);
	}

	/** 显示一个打开APP设置来获取权限的Dialog */
	public static void showOpenAppSettingDialog(String message, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancelListener)
	{
		Activity topActivity = ActivityTool.currentActivity();
		if(topActivity == null)
		{
			return;
		}
		new AlertDialog.Builder(topActivity)
			.setTitle(android.R.string.dialog_alert_title)
			.setMessage(message)
			.setPositiveButton(android.R.string.ok, okListener)
			.setNegativeButton(android.R.string.cancel, cancelListener)
			.setCancelable(false)
			.create()
			.show();
	}

	public static void showRationaleDialog(final PermissionTool.OnRationaleListener.ShouldRequest shouldRequest) {
		Activity topActivity = ActivityTool.currentActivity();
		if (topActivity == null) return;
		new AlertDialog.Builder(topActivity)
			.setTitle(android.R.string.dialog_alert_title)
			.setMessage(R.string.permission_rationale_message)
			.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					shouldRequest.again(true);
				}
			})
			.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					shouldRequest.again(false);
				}
			})
			.setCancelable(false)
			.create()
			.show();

	}

	/** 打开App的设置界面 */
	public static void launchAppDetailsSettings()
	{
		Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
		intent.setData(Uri.parse("package:"+Tool.getContext().getPackageName()));
		Tool.getContext().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
	}

	/**
	 * activity设置全屏
	 */
	public static void setFullScreen(@NonNull final Activity activity)
	{
		activity
			.getWindow()
			.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
	}
}
