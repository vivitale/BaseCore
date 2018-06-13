package talex.zsw.basecore.view.textview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Chronometer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 作用: 倒计时器 
 * 作者: 赵小白 email:edisonzsw@icloud.com 
 * 日期: 16/5/22 22:01 
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class Anticlockwise extends Chronometer
{
	private long mTime;
	private long mNextTime;
	private OnTimeCompleteListener mListener;
	private SimpleDateFormat mTimeFormat;

	public Anticlockwise(Context context)
	{
		super(context);
		mTimeFormat = new SimpleDateFormat("HH:mm:ss");
		mTimeFormat.setTimeZone(TimeZone.getTimeZone("GMT+0"));
		this.setOnChronometerTickListener(listener);
	}

	public Anticlockwise(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		mTimeFormat = new SimpleDateFormat("HH:mm:ss");
		mTimeFormat.setTimeZone(TimeZone.getTimeZone("GMT+0"));
		this.setOnChronometerTickListener(listener);
	}

	/**
	 * 重新启动计时 秒
	 */
	public void reStart(long _time_s)
	{
		if(_time_s == -1)
		{
			mNextTime = mTime;
		}
		else
		{
			mTime = mNextTime = _time_s;
		}
		this.start();
	}

	public void reStart()
	{
		reStart(-1);
	}

	/**
	 * 继续计时
	 */
	public void onResume()
	{
		this.start();
	}

	/**
	 * 暂停计时
	 */
	public void onPause()
	{
		this.stop();
	}

	/**
	 * 设置时间格式
	 *
	 * @param pattern 计时格式
	 */
	public void setTimeFormat(String pattern)
	{
		mTimeFormat = new SimpleDateFormat(pattern);
		mTimeFormat.setTimeZone(TimeZone.getTimeZone("GMT+0"));
	}

	public void setOnTimeCompleteListener(OnTimeCompleteListener l)
	{
		mListener = l;
	}

	OnChronometerTickListener listener = new OnChronometerTickListener()
	{
		@Override public void onChronometerTick(Chronometer chronometer)
		{
			if(mNextTime <= 0)
			{
				if(mNextTime == 0)
				{
					Anticlockwise.this.stop();
					if(null != mListener)
					{
						mListener.onTimeComplete();
					}
				}
				mNextTime = 0;
				updateTimeText();
				return;
			}

			mNextTime--;

			updateTimeText();
		}
	};

	/**
	 * 初始化时间 秒
	 */
	public void initTime(long _time_s)
	{
		mTime = mNextTime = _time_s;
		updateTimeText();
	}

	/**
	 * 初始化时间 秒 , 设置时间格式
	 */
	public void initTime(long _time_s, String pattern)
	{
		mTime = mNextTime = _time_s;
		mTimeFormat = new SimpleDateFormat(pattern);
		mTimeFormat.setTimeZone(TimeZone.getTimeZone("GMT+0"));
		updateTimeText();
	}

	private void updateTimeText()
	{
		this.setText(mTimeFormat.format(new Date(mNextTime*1000)));
	}

	interface OnTimeCompleteListener
	{
		void onTimeComplete();
	}
}