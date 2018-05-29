package talex.zsw.basecore.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import talex.zsw.basecore.BuildConfig;
import talex.zsw.basecore.interfaces.OnSimpleListener;
import talex.zsw.basecore.util.cockroach.Cockroach;

/**
 * RxTools的常用工具类
 */
public class Tool
{

	@SuppressLint("StaticFieldLeak") private static Context context;
	private static long lastClickTime;

	/**
	 * 初始化工具类
	 *
	 * @param context 上下文
	 */
	public static void init(Context context)
	{
		Tool.context = context.getApplicationContext();
		CrashTool.init(context);
		SpTool.init(context);
		if(!BuildConfig.DEBUG)
		{
			LogTool.getConfig().setConsoleSwitch(false);
			Cockroach.install(new Cockroach.ExceptionHandler()
			{
				@Override public void handlerException(final Thread thread, final Throwable throwable)
				{
					new Handler(Looper.getMainLooper()).post(new Runnable()
					{
						@Override public void run()
						{
							try
							{
								LogTool.e(thread+"\n"+throwable.toString());
								throwable.printStackTrace();
							}
							catch(Throwable ignored)
							{
							}
						}
					});
				}
			});
		}
	}

	/**
	 * 结束工具类
	 */
	public static void uninstall()
	{
		Cockroach.uninstall();
	}

	/**
	 * 在某种获取不到 Context 的情况下，即可以使用才方法获取 Context
	 * <p>
	 * 获取ApplicationContext
	 *
	 * @return ApplicationContext
	 */
	public static Context getContext()
	{
		if(context != null)
		{
			return context;
		}
		throw new NullPointerException("请先调用init()方法");
	}
	//==============================================================================================延时任务封装 end

	//----------------------------------------------------------------------------------------------延时任务封装 start
	public static void delayToDo(long delayTime, final OnSimpleListener onSimpleListener)
	{
		new Handler().postDelayed(new Runnable()
		{
			@Override public void run()
			{
				//execute the task
				onSimpleListener.doSomething();
			}
		}, delayTime);
	}

	/**
	 * 倒计时
	 *
	 * @param textView 控件
	 * @param waitTime 倒计时总时长
	 * @param interval 倒计时的间隔时间
	 * @param hint     倒计时完毕时显示的文字
	 */
	public static void countDown(final TextView textView, long waitTime, long interval, final String hint)
	{
		textView.setEnabled(false);
		android.os.CountDownTimer timer = new android.os.CountDownTimer(waitTime, interval)
		{

			@SuppressLint("DefaultLocale") @Override public void onTick(long millisUntilFinished)
			{
				textView.setText(String.format("剩下 %d S", millisUntilFinished/1000));
			}

			@Override public void onFinish()
			{
				textView.setEnabled(true);
				textView.setText(hint);
			}
		};
		timer.start();
	}

	/**
	 * 手动计算出listView的高度，但是不再具有滚动效果
	 *
	 * @param listView
	 */
	public static void fixListViewHeight(ListView listView)
	{
		// 如果没有设置数据适配器，则ListView没有子项，返回。
		ListAdapter listAdapter = listView.getAdapter();
		if(listAdapter == null)
		{
			return;
		}

		int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
		for(int i = 0; i < listAdapter.getCount(); i++)
		{
			View listItem = listAdapter.getView( i, null, listView );
			if(listItem instanceof ViewGroup)
			{
				listItem.setLayoutParams(
					new ViewGroup.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT,
					                            ViewGroup.LayoutParams.WRAP_CONTENT ) );
			}
			listItem.measure( 0, 0 );
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams( params );
	}

	//---------------------------------------------MD5加密-------------------------------------------

	/**
	 * 生成MD5加密32位字符串
	 *
	 * @param MStr :需要加密的字符串
	 * @return
	 */
	public static String Md5(String MStr)
	{
		try
		{
			final MessageDigest mDigest = MessageDigest.getInstance("MD5");
			mDigest.update(MStr.getBytes());
			return bytesToHexString(mDigest.digest());
		}
		catch(NoSuchAlgorithmException e)
		{
			return String.valueOf(MStr.hashCode());
		}
	}

	// MD5内部算法---------------不能修改!
	private static String bytesToHexString(byte[] bytes)
	{
		// http://stackoverflow.com/questions/332079
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < bytes.length; i++)
		{
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if(hex.length() == 1)
			{
				sb.append('0');
			}
			sb.append(hex);
		}
		return sb.toString();
	}
	//============================================MD5加密============================================

	/**
	 * 根据资源名称获取资源 id
	 * <p>
	 * 不提倡使用这个方法获取资源,比其直接获取ID效率慢
	 * <p>
	 * 例如
	 * getResources().getIdentifier("ic_launcher", "drawable", getPackageName());
	 *
	 * @param context
	 * @param name
	 * @param defType
	 * @return
	 */
	public final static int getResIdByName(Context context, String name, String defType)
	{
		return context.getResources().getIdentifier("ic_launcher", "drawable", context.getPackageName());
	}

	public static boolean isFastClick(int millisecond)
	{
		long curClickTime = System.currentTimeMillis();
		long interval = (curClickTime-lastClickTime);

		if(0 < interval && interval < millisecond)
		{
			// 超过点击间隔后再将lastClickTime重置为当前点击时间
			return true;
		}
		lastClickTime = curClickTime;
		return false;
	}

	/**
	 * 获取
	 *
	 * @return
	 */
	public static Handler getBackgroundHandler()
	{
		HandlerThread thread = new HandlerThread("background");
		thread.start();
		return new Handler(thread.getLooper());
	}
}
