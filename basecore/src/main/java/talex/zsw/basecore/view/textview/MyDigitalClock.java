package talex.zsw.basecore.view.textview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.widget.DigitalClock;
import android.widget.TextView;

import java.util.Calendar;

/**
 * 作用: 时间控件
 * 作者: 赵小白 email:edisonzsw@icloud.com 
 * 日期: 2017/8/15 17:10 
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
@SuppressLint("AppCompatCustomView")
public class MyDigitalClock extends TextView
{
	Calendar mCalendar;
	@SuppressWarnings("FieldCanBeLocal") // We must keep a reference to this observer
	private FormatChangeObserver mFormatChangeObserver;

	private Runnable mTicker;
	private Handler mHandler;

	private boolean mTickerStopped = false;

	String mFormat = "yyyy-MM-dd HH:mm:ss";

	public MyDigitalClock(Context context)
	{
		super(context);
		initClock();
	}

	public MyDigitalClock(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initClock();
	}

	private void initClock()
	{
		if(mCalendar == null)
		{
			mCalendar = Calendar.getInstance();
		}
	}

	@Override protected void onAttachedToWindow()
	{
		mTickerStopped = false;
		super.onAttachedToWindow();

		mFormatChangeObserver = new FormatChangeObserver();
		getContext().getContentResolver()
		            .registerContentObserver(Settings.System.CONTENT_URI, true, mFormatChangeObserver);
		setFormat("yyyy-MM-dd HH:mm:ss");

		mHandler = new Handler();

		/**
		 * requests a tick on the next hard-second boundary
		 */
		mTicker = new Runnable()
		{
			public void run()
			{
				if(mTickerStopped)
				{
					return;
				}
				mCalendar.setTimeInMillis(System.currentTimeMillis());
				setText(DateFormat.format(mFormat, mCalendar));
				invalidate();
				long now = SystemClock.uptimeMillis();
				long next = now+(1000-now%1000);
				mHandler.postAtTime(mTicker, next);
			}
		};
		mTicker.run();
	}

	@Override protected void onDetachedFromWindow()
	{
		super.onDetachedFromWindow();
		mTickerStopped = true;
		getContext().getContentResolver()
		            .unregisterContentObserver(mFormatChangeObserver);
	}

	public void setFormat(String string)
	{
		mFormat = string;
	}

	private class FormatChangeObserver extends ContentObserver
	{
		public FormatChangeObserver()
		{
			super(new Handler());
		}

		@Override public void onChange(boolean selfChange)
		{
			setFormat(mFormat);
		}
	}

	@Override public CharSequence getAccessibilityClassName()
	{
		//noinspection deprecation
		return DigitalClock.class.getName();
	}
}
