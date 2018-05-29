package talex.zsw.basecore.util;

import android.Manifest;
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

	public static String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;//内存卡写入
	public static String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;//手机状态
	public static String CAMERA = Manifest.permission.CAMERA;//相机
	public static String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;//地理位置
	public static String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;//地理位置

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
				ActivityCompat.requestPermissions(mActivity, list.toArray(new String[list.size()]), 1);
			}
			return list;
		}
	}

	public static boolean checkPermission(Activity activity, String permission)
	{
		return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
	}
}
