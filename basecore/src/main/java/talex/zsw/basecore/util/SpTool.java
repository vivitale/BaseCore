package talex.zsw.basecore.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.tencent.mmkv.MMKV;

import java.util.Map;

/**
 * MMKV 取代原生SharedPreferences
 * SharedPreferences的常规使用
 */
public class SpTool
{
	private static MMKV prefs;

	/**
	 * 单进程存储数据
	 */
	public static void init(Context context)
	{
		MMKV.initialize(context);
		prefs = MMKV.mmkvWithID(context.getPackageName());
	}


	/**
	 * 多进程共享数据
	 */
	public static void initMulitProcess(Context context)
	{
		MMKV.initialize(context);
		prefs = MMKV.mmkvWithID(context.getPackageName(), MMKV.MULTI_PROCESS_MODE);
	}

	public static String getString(String TAG, String def)
	{
		return prefs.getString(TAG, def);
	}

	public static String getString(String TAG)
	{
		return prefs.getString(TAG, "");
	}

	public static void saveString(String TAG, String data)
	{
		prefs.edit().putString(TAG, data);
	}

	public static void saveObject(String TAG, Object obj)
	{
		SharedPreferences.Editor editor = prefs.edit();
		if(obj != null)
		{
			editor.putString(TAG, JsonTool.getJsonString(obj));
		}
		else
		{
			editor.putString(TAG, "");
		}
	}

	public static <T> T getObject(String TAG, Class<T> t)
	{
		return JsonTool.getObject(prefs.getString(TAG, ""), t);
	}

	public static Boolean getBoolean(String TAG, boolean def)
	{
		return prefs.getBoolean(TAG, def);
	}

	public static Boolean getBoolean(String TAG)
	{
		return prefs.getBoolean(TAG, false);
	}

	public static void saveBoolean(String TAG, boolean flag)
	{
		prefs.edit().putBoolean(TAG, flag);
	}

	public static int getInt(String TAG, int def)
	{
		return prefs.getInt(TAG, def);
	}

	public static int getInt(String TAG)
	{
		return prefs.getInt(TAG, -1);
	}

	public static void saveInt(String TAG, int data)
	{
		prefs.edit().putInt(TAG, data);
	}

	public static long getLong(String TAG, long def)
	{
		return prefs.getLong(TAG, def);
	}

	public static long getLong(String TAG)
	{
		return prefs.getLong(TAG, -1);
	}

	public static void saveLong(String TAG, long data)
	{
		prefs.edit().putLong(TAG, data);
	}

	public static float getFloat(String TAG, float def)
	{
		return prefs.getFloat(TAG, def);
	}

	public static float getFloat(String TAG)
	{
		return prefs.getFloat(TAG, -1f);
	}

	public static void saveFloat(String TAG, float data)
	{
		prefs.edit().putFloat(TAG, data);
	}


	/**
	 * 获取所有键值对
	 */
	public static Map<String, ?> getAll()
	{
		return prefs.getAll();
	}

	/**
	 * 是否存在该 key
	 */
	public static boolean contains(@NonNull final String key)
	{
		return prefs.contains(key);
	}

	/**
	 * 移除该 key
	 */
	public static void remove(@NonNull final String key)
	{
		remove(key, false);
	}

	/**
	 * 移除该 key
	 */
	public static void remove(@NonNull final String key, final boolean isCommit)
	{
		if(isCommit)
		{
			prefs.edit().remove(key).commit();
		}
		else
		{
			prefs.edit().remove(key);
		}
	}

	/**
	 * 清除所有数据
	 */
	public static void clear()
	{
		clear(false);
	}

	/**
	 * 清除所有数据
	 */
	public static void clear(final boolean isCommit)
	{
		if(isCommit)
		{
			prefs.edit().clear().commit();
		}
		else
		{
			prefs.edit().clear();
		}
	}
}
