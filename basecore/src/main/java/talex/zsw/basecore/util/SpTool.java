package talex.zsw.basecore.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import java.util.Map;

/**
 * SharedPreferences的常规使用
 */
public class SpTool
{
	private static SharedPreferences prefs;

	public static void init(Context context)
	{
		prefs = PreferenceManager.getDefaultSharedPreferences(context);
	}

	public static String getString(String TAG)
	{
		return prefs.getString(TAG, "");
	}

	public static void saveString(String TAG, String data)
	{
		prefs.edit().putString(TAG, data).apply();
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
		editor.apply();
	}

	public static <T> T getObject(String TAG, Class<T> t)
	{
		return JsonTool.getObject(prefs.getString(TAG, ""), t);
	}

	public static Boolean getBoolean(String TAG)
	{
		return prefs.getBoolean(TAG, false);
	}

	public static void saveBoolean(String TAG, boolean flag)
	{
		prefs.edit().putBoolean(TAG, flag).apply();
	}

	public static int getInt(String TAG)
	{
		return prefs.getInt(TAG, 0);
	}

	public static void saveInt(String TAG, int data)
	{
		prefs.edit().putInt(TAG, data).apply();
	}

	public static long getLong(String TAG)
	{
		return prefs.getLong(TAG, 0);
	}

	public static void saveLong(String TAG, long data)
	{
		prefs.edit().putLong(TAG, data).apply();
	}

	public static float getFloat(String TAG)
	{
		return prefs.getFloat(TAG, 0f);
	}

	public static void saveFloat(String TAG, float data)
	{
		prefs.edit().putFloat(TAG, data).apply();
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
			prefs.edit().remove(key).apply();
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
			prefs.edit().clear().apply();
		}
	}
}
