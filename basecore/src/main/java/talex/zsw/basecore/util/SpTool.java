package talex.zsw.basecore.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

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
}
