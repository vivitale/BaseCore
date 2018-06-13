package talex.zsw.basecore.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


/**
 * 权限工具类
 */
//PermissionTool
//			.permission(PermissionConstants.PHONE, PermissionConstants.STORAGE)
//			.rationale(new PermissionTool.OnRationaleListener()
//			{
//@Override public void rationale(final ShouldRequest shouldRequest)
//	{
//	PermissionHelper.showRationaleDialog(shouldRequest);
//	}
//	})
//	.callback(new PermissionTool.FullCallback()
//	{
//@Override public void onGranted(List<String> permissionsGranted)
//	{
//	}
//
//@Override public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied)
//	{
//	if(!permissionsDeniedForever.isEmpty())
//	{
//	PermissionHelper.showOpenAppSettingDialog();
//	}
//	}
//	})
//	.theme(new PermissionTool.ThemeCallback()
//	{
//@Override public void onActivityCreate(Activity activity)
//	{
//	PermissionHelper.setFullScreen(activity);
//	}
//	})
//	.request();
public final class PermissionTool
{

	private static final List<String> PERMISSIONS = getPermissions();

	private static PermissionTool sInstance;

	private OnRationaleListener mOnRationaleListener;
	private SimpleCallback mSimpleCallback;
	private FullCallback mFullCallback;
	private ThemeCallback mThemeCallback;
	private Set<String> mPermissions;
	private List<String> mPermissionsRequest;
	private List<String> mPermissionsGranted;
	private List<String> mPermissionsDenied;
	private List<String> mPermissionsDeniedForever;

	/**
	 * 获取程序中使用的权限列表
	 */
	public static List<String> getPermissions()
	{
		return getPermissions(Tool.getContext().getPackageName());
	}

	/**
	 * 获取程序中使用的权限列表
	 */
	public static List<String> getPermissions(final String packageName)
	{
		PackageManager pm = Tool.getContext().getPackageManager();
		try
		{
			return Arrays.asList(pm.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS).requestedPermissions);
		}
		catch(PackageManager.NameNotFoundException e)
		{
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	/**
	 * 返回某些权限是否已经获取
	 */
	public static boolean isGranted(final String... permissions)
	{
		for(String permission : permissions)
		{
			if(!isGranted(permission))
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * 返回某个权限是否已经获取
	 */
	private static boolean isGranted(final String permission)
	{
		return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
			PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(Tool.getContext(), permission);
	}

	/**
	 * 申请某些权限
	 */
	public static PermissionTool permission(@PermissionConstants.Permission final String... permissions)
	{
		return new PermissionTool(permissions);
	}

	private PermissionTool(final String... permissions)
	{
		mPermissions = new LinkedHashSet<>();
		for(String permission : permissions)
		{
			for(String aPermission : PermissionConstants.getPermissions(permission))
			{
				if(PERMISSIONS.contains(aPermission))
				{
					mPermissions.add(aPermission);
				}
			}
		}
		sInstance = this;
	}

	/**
	 * Set rationale listener.
	 *
	 * @param listener The rationale listener.
	 * @return the single {@link PermissionTool} instance
	 */
	public PermissionTool rationale(final OnRationaleListener listener)
	{
		mOnRationaleListener = listener;
		return this;
	}

	/**
	 * Set the simple call back.
	 *
	 * @param callback the simple call back
	 * @return the single {@link PermissionTool} instance
	 */
	public PermissionTool callback(final SimpleCallback callback)
	{
		mSimpleCallback = callback;
		return this;
	}

	/**
	 * Set the full call back.
	 *
	 * @param callback the full call back
	 * @return the single {@link PermissionTool} instance
	 */
	public PermissionTool callback(final FullCallback callback)
	{
		mFullCallback = callback;
		return this;
	}

	/**
	 * Set the theme callback.
	 *
	 * @param callback The theme callback.
	 * @return the single {@link PermissionTool} instance
	 */
	public PermissionTool theme(final ThemeCallback callback)
	{
		mThemeCallback = callback;
		return this;
	}

	/**
	 * Start request.
	 */
	public void request()
	{
		mPermissionsGranted = new ArrayList<>();
		mPermissionsRequest = new ArrayList<>();
		if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
		{
			mPermissionsGranted.addAll(mPermissions);
			requestCallback();
		}
		else
		{
			for(String permission : mPermissions)
			{
				if(isGranted(permission))
				{
					mPermissionsGranted.add(permission);
				}
				else
				{
					mPermissionsRequest.add(permission);
				}
			}
			if(mPermissionsRequest.isEmpty())
			{
				requestCallback();
			}
			else
			{
				startPermissionActivity();
			}
		}
	}

	@RequiresApi(api = Build.VERSION_CODES.M) private void startPermissionActivity()
	{
		mPermissionsDenied = new ArrayList<>();
		mPermissionsDeniedForever = new ArrayList<>();
		PermissionActivity.start(Tool.getContext());
	}

	@RequiresApi(api = Build.VERSION_CODES.M) private boolean rationale(final Activity activity)
	{
		boolean isRationale = false;
		if(mOnRationaleListener != null)
		{
			for(String permission : mPermissionsRequest)
			{
				if(activity.shouldShowRequestPermissionRationale(permission))
				{
					getPermissionsStatus(activity);
					mOnRationaleListener.rationale(new OnRationaleListener.ShouldRequest()
					{
						@Override public void again(boolean again)
						{
							if(again)
							{
								startPermissionActivity();
							}
							else
							{
								requestCallback();
							}
						}
					});
					isRationale = true;
					break;
				}
			}
			mOnRationaleListener = null;
		}
		return isRationale;
	}

	private void getPermissionsStatus(final Activity activity)
	{
		for(String permission : mPermissionsRequest)
		{
			if(isGranted(permission))
			{
				mPermissionsGranted.add(permission);
			}
			else
			{
				mPermissionsDenied.add(permission);
				if(!activity.shouldShowRequestPermissionRationale(permission))
				{
					mPermissionsDeniedForever.add(permission);
				}
			}
		}
	}

	private void requestCallback()
	{
		if(mSimpleCallback != null)
		{
			if(mPermissionsRequest.size() == 0 || mPermissions.size() == mPermissionsGranted.size())
			{
				mSimpleCallback.onGranted();
			}
			else
			{
				if(!mPermissionsDenied.isEmpty())
				{
					mSimpleCallback.onDenied();
				}
			}
			mSimpleCallback = null;
		}
		if(mFullCallback != null)
		{
			if(mPermissionsRequest.size() == 0 || mPermissions.size() == mPermissionsGranted.size())
			{
				mFullCallback.onGranted(mPermissionsGranted);
			}
			else
			{
				if(!mPermissionsDenied.isEmpty())
				{
					mFullCallback.onDenied(mPermissionsDeniedForever, mPermissionsDenied);
				}
			}
			mFullCallback = null;
		}
		mOnRationaleListener = null;
		mThemeCallback = null;
	}

	private void onRequestPermissionsResult(final Activity activity)
	{
		getPermissionsStatus(activity);
		requestCallback();
	}


	@RequiresApi(api = Build.VERSION_CODES.M)
	public static class PermissionActivity extends Activity
	{

		public static void start(final Context context)
		{
			Intent starter = new Intent(context, PermissionActivity.class);
			starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(starter);
		}

		@Override protected void onCreate(@Nullable Bundle savedInstanceState)
		{
			getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
			if(sInstance == null)
			{
				super.onCreate(savedInstanceState);
				Log.e("PermissionUtils", "request permissions failed");
				finish();
				return;
			}
			if(sInstance.mThemeCallback != null)
			{
				sInstance.mThemeCallback.onActivityCreate(this);
			}
			super.onCreate(savedInstanceState);

			if(sInstance.rationale(this))
			{
				finish();
				return;
			}
			if(sInstance.mPermissionsRequest != null)
			{
				int size = sInstance.mPermissionsRequest.size();
				if(size <= 0)
				{
					finish();
					return;
				}
				requestPermissions(sInstance.mPermissionsRequest.toArray(new String[size]), 1);
			}
		}

		@Override public void onRequestPermissionsResult(int requestCode,
			@NonNull String[] permissions, @NonNull int[] grantResults)
		{
			sInstance.onRequestPermissionsResult(this);
			finish();
		}

		@Override public boolean dispatchTouchEvent(MotionEvent ev)
		{
			finish();
			return true;
		}
	}


	public interface OnRationaleListener
	{

		void rationale(ShouldRequest shouldRequest);

		interface ShouldRequest
		{
			void again(boolean again);
		}
	}

	public interface SimpleCallback
	{
		void onGranted();

		void onDenied();
	}

	public interface FullCallback
	{
		void onGranted(List<String> permissionsGranted);

		void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied);
	}

	public interface ThemeCallback
	{
		void onActivityCreate(Activity activity);
	}
}