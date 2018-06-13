package talex.zsw.basecore.util;

import android.app.ActivityManager;
import android.content.Context;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 服务工具
 */

public class ServiceTool
{
	/**
	 * 判断服务是否运行
	 *
	 * @param cls The service class.
	 * @return {@code true}: yes<br>{@code false}: no
	 */
	public static boolean isServiceRunning(final Class<?> cls) {
		return isServiceRunning(cls.getName());
	}

	/**
	 * 判断服务是否运行
	 *
	 * @param className The name of class.
	 * @return {@code true}: yes<br>{@code false}: no
	 */
	public static boolean isServiceRunning(final String className) {
		ActivityManager am =
			(ActivityManager) Tool.getContext().getSystemService(Context.ACTIVITY_SERVICE);
		if (am == null) return false;
		List<ActivityManager.RunningServiceInfo> info = am.getRunningServices(0x7FFFFFFF);
		if (info == null || info.size() == 0) return false;
		for (ActivityManager.RunningServiceInfo aInfo : info) {
			if (className.equals(aInfo.service.getClassName())) return true;
		}
		return false;
	}

	/**
	 * 获取所有运行的服务
	 *
	 * @return all of the services are running
	 */
	public static Set getAllRunningServices()
	{
		ActivityManager am = (ActivityManager) Tool.getContext().getSystemService(Context.ACTIVITY_SERVICE);
		if(am == null)
		{
			return Collections.emptySet();
		}
		List<ActivityManager.RunningServiceInfo> info = am.getRunningServices(0x7FFFFFFF);
		Set<String> names = new HashSet<>();
		if(info == null || info.size() == 0)
		{
			return null;
		}
		for(ActivityManager.RunningServiceInfo aInfo : info)
		{
			names.add(aInfo.service.getClassName());
		}
		return names;
	}
}
