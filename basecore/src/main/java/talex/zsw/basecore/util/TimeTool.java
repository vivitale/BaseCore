package talex.zsw.basecore.util;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import static talex.zsw.basecore.util.ConstTool.DAY;
import static talex.zsw.basecore.util.ConstTool.HOUR;
import static talex.zsw.basecore.util.ConstTool.MIN;
import static talex.zsw.basecore.util.ConstTool.MSEC;
import static talex.zsw.basecore.util.ConstTool.SEC;

/**
 * 时间相关工具类
 */
public class TimeTool
{
	/**
	 * <p>在工具类中经常使用到工具类的格式化描述，这个主要是一个日期的操作类，所以日志格式主要使用 SimpleDateFormat的定义格式.</p>
	 * 格式的意义如下： 日期和时间模式 <br>
	 * <p>日期和时间格式由日期和时间模式字符串指定。在日期和时间模式字符串中，未加引号的字母 'A' 到 'Z' 和 'a' 到 'z'
	 * 被解释为模式字母，用来表示日期或时间字符串元素。文本可以使用单引号 (') 引起来，以免进行解释。"''"
	 * 表示单引号。所有其他字符均不解释；只是在格式化时将它们简单复制到输出字符串，或者在分析时与输入字符串进行匹配。
	 * </p>
	 * 定义了以下模式字母（所有其他字符 'A' 到 'Z' 和 'a' 到 'z' 都被保留）： <br>
	 * HH:mm    15:44
	 * h:mm a    3:44 下午
	 * HH:mm z    15:44 CST
	 * HH:mm Z    15:44 +0800
	 * HH:mm zzzz    15:44 中国标准时间
	 * HH:mm:ss    15:44:40
	 * yyyy-MM-dd    2016-08-12
	 * yyyy-MM-dd HH:mm    2016-08-12 15:44
	 * yyyy-MM-dd HH:mm:ss    2016-08-12 15:44:40
	 * yyyy-MM-dd HH:mm:ss zzzz    2016-08-12 15:44:40 中国标准时间
	 * EEEE yyyy-MM-dd HH:mm:ss zzzz    星期五 2016-08-12 15:44:40 中国标准时间
	 * yyyy-MM-dd HH:mm:ss.SSSZ    2016-08-12 15:44:40.461+0800
	 * yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
	 * yyyy.MM.dd G 'at' HH:mm:ss z    2016.08.12 公元 at 15:44:40 CST
	 * K:mm a    3:44 下午
	 * EEE, MMM d, ''yy    星期五, 八月 12, '16
	 * hh 'o''clock' a, zzzz    03 o'clock 下午, 中国标准时间
	 * yyyyy.MMMMM.dd GGG hh:mm aaa    02016.八月.12 公元 03:44 下午
	 * EEE, d MMM yyyy HH:mm:ss Z    星期五, 12 八月 2016 15:44:40 +0800
	 * yyMMddHHmmssZ    160812154440+0800
	 * yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
	 * EEEE 'DATE('yyyy-MM-dd')' 'TIME('HH:mm:ss')' zzzz    星期五 DATE(2016-08-12) TIME(15:44:40) 中国标准时间
	 * </pre>
	 */
	public static final SimpleDateFormat DEFAULT_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
	public static String STR_FOMATER_DATA_TIME = "yyyy-MM-dd HH:mm:ss";
	public static String STR_FOMATER_DATA = "yyyy-MM-dd";

	/**
	 * 将时间戳转为时间字符串
	 * <p>格式为yyyy-MM-dd HH:mm:ss</p>
	 *
	 * @param milliseconds 毫秒时间戳
	 * @return 时间字符串
	 */
	public static String milliseconds2String(long milliseconds)
	{
		return milliseconds2String(milliseconds, DEFAULT_SDF);
	}

	/**
	 * 将时间戳转为时间字符串
	 * <p>格式为用户自定义</p>
	 *
	 * @param milliseconds 毫秒时间戳
	 * @param format       时间格式
	 * @return 时间字符串
	 */
	public static String milliseconds2String(long milliseconds, SimpleDateFormat format)
	{
		return format.format(new Date(milliseconds));
	}

	/**
	 * 将时间戳转为时间字符串
	 * <p>格式为用户自定义</p>
	 *
	 * @param milliseconds 毫秒时间戳
	 * @param formatStr    时间格式
	 * @return 时间字符串
	 */
	public static String milliseconds2String(long milliseconds, String formatStr)
	{
		SimpleDateFormat format = new SimpleDateFormat(formatStr, Locale.getDefault());
		return format.format(new Date(milliseconds));
	}

	/**
	 * 将时间字符串转为时间戳
	 * <p>格式为yyyy-MM-dd HH:mm:ss</p>
	 *
	 * @param time 时间字符串
	 * @return 毫秒时间戳
	 */
	public static long string2Milliseconds(String time)
	{
		return string2Milliseconds(time, DEFAULT_SDF);
	}

	/**
	 * 将时间字符串转为时间戳
	 * <p>格式为用户自定义</p>
	 *
	 * @param time   时间字符串
	 * @param format 时间格式
	 * @return 毫秒时间戳
	 */
	public static long string2Milliseconds(String time, SimpleDateFormat format)
	{
		try
		{
			return format.parse(time).getTime();
		}
		catch(ParseException e)
		{
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * 将时间字符串转为时间戳
	 * <p>格式为用户自定义</p>
	 *
	 * @param time      时间字符串
	 * @param formatStr 时间格式
	 * @return 毫秒时间戳
	 */
	public static long string2Milliseconds(String time, String formatStr)
	{
		SimpleDateFormat format = new SimpleDateFormat(formatStr, Locale.getDefault());
		try
		{
			return format.parse(time).getTime();
		}
		catch(ParseException e)
		{
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * 将时间字符串转为Date类型
	 * <p>格式为yyyy-MM-dd HH:mm:ss</p>
	 *
	 * @param time 时间字符串
	 * @return Date类型
	 */
	public static Date string2Date(String time)
	{
		return string2Date(time, DEFAULT_SDF);
	}

	/**
	 * 将时间字符串转为Date类型
	 * <p>格式为用户自定义</p>
	 *
	 * @param time   时间字符串
	 * @param format 时间格式
	 * @return Date类型
	 */
	public static Date string2Date(String time, SimpleDateFormat format)
	{
		return new Date(string2Milliseconds(time, format));
	}

	/**
	 * 将时间字符串转为Date类型
	 * <p>格式为用户自定义</p>
	 *
	 * @param time      时间字符串
	 * @param formatStr 时间格式
	 * @return Date类型
	 */
	public static Date string2Date(String time, String formatStr)
	{
		SimpleDateFormat format = new SimpleDateFormat(formatStr, Locale.getDefault());
		return new Date(string2Milliseconds(time, format));
	}

	/**
	 * 将Date类型转为时间字符串
	 * <p>格式为yyyy-MM-dd HH:mm:ss</p>
	 *
	 * @param time Date类型时间
	 * @return 时间字符串
	 */
	public static String date2String(Date time)
	{
		return date2String(time, DEFAULT_SDF);
	}

	/**
	 * 将Date类型转为时间字符串
	 * <p>格式为用户自定义</p>
	 *
	 * @param time   Date类型时间
	 * @param format 时间格式
	 * @return 时间字符串
	 */
	public static String date2String(Date time, SimpleDateFormat format)
	{
		return format.format(time);
	}

	/**
	 * 将Date类型转为时间字符串
	 * <p>格式为用户自定义</p>
	 *
	 * @param time      Date类型时间
	 * @param formatStr 时间格式
	 * @return 时间字符串
	 */
	public static String date2String(Date time, String formatStr)
	{
		SimpleDateFormat format = new SimpleDateFormat(formatStr, Locale.getDefault());
		return format.format(time);
	}

	/**
	 * 将Date类型转为时间戳
	 *
	 * @param time Date类型时间
	 * @return 毫秒时间戳
	 */
	public static long date2Milliseconds(Date time)
	{
		return time.getTime();
	}

	/**
	 * 将时间戳转为Date类型
	 *
	 * @param milliseconds 毫秒时间戳
	 * @return Date类型时间
	 */
	public static Date milliseconds2Date(long milliseconds)
	{
		return new Date(milliseconds);
	}

	/**
	 * 毫秒时间戳单位转换（单位：unit）
	 *
	 * @param milliseconds 毫秒时间戳
	 * @param unit         <ul>
	 *                     <li>{@link ConstTool.TimeUnit#MSEC}: 毫秒</li>
	 *                     <li>{@link ConstTool.TimeUnit#SEC }: 秒</li>
	 *                     <li>{@link ConstTool.TimeUnit#MIN }: 分</li>
	 *                     <li>{@link ConstTool.TimeUnit#HOUR}: 小时</li>
	 *                     <li>{@link ConstTool.TimeUnit#DAY }: 天</li>
	 *                     </ul>
	 * @return unit时间戳
	 */
	private static long milliseconds2Unit(long milliseconds, ConstTool.TimeUnit unit)
	{
		switch(unit)
		{
			case MSEC:
				return milliseconds/MSEC;
			case SEC:
				return milliseconds/SEC;
			case MIN:
				return milliseconds/MIN;
			case HOUR:
				return milliseconds/HOUR;
			case DAY:
				return milliseconds/DAY;
		}
		return -1;
	}

	/**
	 * 获取两个时间差（单位：unit）
	 * <p>time1和time2格式都为yyyy-MM-dd HH:mm:ss</p>
	 *
	 * @param time0 时间字符串1
	 * @param time1 时间字符串2
	 * @param unit  <ul>
	 *              <li>{@link ConstTool.TimeUnit#MSEC}: 毫秒</li>
	 *              <li>{@link ConstTool.TimeUnit#SEC }: 秒</li>
	 *              <li>{@link ConstTool.TimeUnit#MIN }: 分</li>
	 *              <li>{@link ConstTool.TimeUnit#HOUR}: 小时</li>
	 *              <li>{@link ConstTool.TimeUnit#DAY }: 天</li>
	 *              </ul>
	 * @return unit时间戳
	 */
	public static long getIntervalTime(String time0, String time1, ConstTool.TimeUnit unit)
	{
		return getIntervalTime(time0, time1, unit, DEFAULT_SDF);
	}

	/**
	 * 获取两个时间差（单位：unit）
	 * <p>time1和time2格式都为format</p>
	 *
	 * @param time0  时间字符串1
	 * @param time1  时间字符串2
	 * @param unit   <ul>
	 *               <li>{@link ConstTool.TimeUnit#MSEC}: 毫秒</li>
	 *               <li>{@link ConstTool.TimeUnit#SEC }: 秒</li>
	 *               <li>{@link ConstTool.TimeUnit#MIN }: 分</li>
	 *               <li>{@link ConstTool.TimeUnit#HOUR}: 小时</li>
	 *               <li>{@link ConstTool.TimeUnit#DAY }: 天</li>
	 *               </ul>
	 * @param format 时间格式
	 * @return unit时间戳
	 */
	public static long getIntervalTime(String time0, String time1, ConstTool.TimeUnit unit, SimpleDateFormat format)
	{
		return Math.abs(milliseconds2Unit(string2Milliseconds(time0, format)-string2Milliseconds(time1, format), unit));
	}

	/**
	 * 获取两个时间差（单位：unit）
	 * <p>time1和time2格式都为format</p>
	 *
	 * @param time0     时间字符串1
	 * @param time1     时间字符串2
	 * @param unit      <ul>
	 *                  <li>{@link ConstTool.TimeUnit#MSEC}: 毫秒</li>
	 *                  <li>{@link ConstTool.TimeUnit#SEC }: 秒</li>
	 *                  <li>{@link ConstTool.TimeUnit#MIN }: 分</li>
	 *                  <li>{@link ConstTool.TimeUnit#HOUR}: 小时</li>
	 *                  <li>{@link ConstTool.TimeUnit#DAY }: 天</li>
	 *                  </ul>
	 * @param formatStr 时间格式
	 * @return unit时间戳
	 */
	public static long getIntervalTime(String time0, String time1, ConstTool.TimeUnit unit, String formatStr)
	{
		SimpleDateFormat format = new SimpleDateFormat(formatStr, Locale.getDefault());
		return Math.abs(milliseconds2Unit(string2Milliseconds(time0, format)-string2Milliseconds(time1, format), unit));
	}

	/**
	 * 获取两个时间差（单位：unit）
	 * <p>time1和time2都为Date类型</p>
	 *
	 * @param time0 Date类型时间1
	 * @param time1 Date类型时间2
	 * @param unit  <ul>
	 *              <li>{@link ConstTool.TimeUnit#MSEC}: 毫秒</li>
	 *              <li>{@link ConstTool.TimeUnit#SEC }: 秒</li>
	 *              <li>{@link ConstTool.TimeUnit#MIN }: 分</li>
	 *              <li>{@link ConstTool.TimeUnit#HOUR}: 小时</li>
	 *              <li>{@link ConstTool.TimeUnit#DAY }: 天</li>
	 *              </ul>
	 * @return unit时间戳
	 */
	public static long getIntervalTime(Date time0, Date time1, ConstTool.TimeUnit unit)
	{
		return Math.abs(milliseconds2Unit(date2Milliseconds(time1)-date2Milliseconds(time0), unit));
	}

	/**
	 * 获取当前时间
	 *
	 * @return 毫秒时间戳
	 */
	public static long getCurTimeMills()
	{
		return System.currentTimeMillis();
	}

	/**
	 * 获取当前时间
	 * <p>格式为yyyy-MM-dd HH:mm:ss</p>
	 *
	 * @return 时间字符串
	 */
	public static String getCurTimeString()
	{
		return date2String(new Date());
	}

	/**
	 * 获取当前时间
	 * <p>格式为用户自定义</p>
	 *
	 * @param format 时间格式
	 * @return 时间字符串
	 */
	public static String getCurTimeString(SimpleDateFormat format)
	{
		return date2String(new Date(), format);
	}

	/**
	 * 获取当前时间
	 * <p>格式为用户自定义</p>
	 *
	 * @param formatStr 时间格式
	 * @return 时间字符串
	 */
	public static String getCurTimeString(String formatStr)
	{
		SimpleDateFormat format = new SimpleDateFormat(formatStr, Locale.getDefault());
		return date2String(new Date(), format);
	}

	/**
	 * 获取当前时间
	 * <p>Date类型</p>
	 *
	 * @return Date类型时间
	 */
	public static Date getCurTimeDate()
	{
		return new Date();
	}

	/**
	 * 获取与当前时间的差（单位：unit）
	 * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
	 *
	 * @param time 时间字符串
	 * @param unit <ul>
	 *             <li>{@link ConstTool.TimeUnit#MSEC}:毫秒</li>
	 *             <li>{@link ConstTool.TimeUnit#SEC }:秒</li>
	 *             <li>{@link ConstTool.TimeUnit#MIN }:分</li>
	 *             <li>{@link ConstTool.TimeUnit#HOUR}:小时</li>
	 *             <li>{@link ConstTool.TimeUnit#DAY }:天</li>
	 *             </ul>
	 * @return unit时间戳
	 */
	public static long getIntervalByNow(String time, ConstTool.TimeUnit unit)
	{
		return getIntervalByNow(time, unit, DEFAULT_SDF);
	}

	/**
	 * 获取与当前时间的差（单位：unit）
	 * <p>time格式为format</p>
	 *
	 * @param time   时间字符串
	 * @param unit   <ul>
	 *               <li>{@link ConstTool.TimeUnit#MSEC}: 毫秒</li>
	 *               <li>{@link ConstTool.TimeUnit#SEC }: 秒</li>
	 *               <li>{@link ConstTool.TimeUnit#MIN }: 分</li>
	 *               <li>{@link ConstTool.TimeUnit#HOUR}: 小时</li>
	 *               <li>{@link ConstTool.TimeUnit#DAY }: 天</li>
	 *               </ul>
	 * @param format 时间格式
	 * @return unit时间戳
	 */
	public static long getIntervalByNow(String time, ConstTool.TimeUnit unit, SimpleDateFormat format)
	{
		return getIntervalTime(getCurTimeString(), time, unit, format);
	}

	/**
	 * 获取与当前时间的差（单位：unit）
	 * <p>time格式为format</p>
	 *
	 * @param time      时间字符串
	 * @param unit      <ul>
	 *                  <li>{@link ConstTool.TimeUnit#MSEC}: 毫秒</li>
	 *                  <li>{@link ConstTool.TimeUnit#SEC }: 秒</li>
	 *                  <li>{@link ConstTool.TimeUnit#MIN }: 分</li>
	 *                  <li>{@link ConstTool.TimeUnit#HOUR}: 小时</li>
	 *                  <li>{@link ConstTool.TimeUnit#DAY }: 天</li>
	 *                  </ul>
	 * @param formatStr 时间格式
	 * @return unit时间戳
	 */
	public static long getIntervalByNow(String time, ConstTool.TimeUnit unit, String formatStr)
	{
		SimpleDateFormat format = new SimpleDateFormat(formatStr, Locale.getDefault());
		return getIntervalTime(getCurTimeString(), time, unit, format);
	}

	/**
	 * 获取与当前时间的差（单位：unit）
	 * <p>time为Date类型</p>
	 *
	 * @param time Date类型时间
	 * @param unit <ul>
	 *             <li>{@link ConstTool.TimeUnit#MSEC}: 毫秒</li>
	 *             <li>{@link ConstTool.TimeUnit#SEC }: 秒</li>
	 *             <li>{@link ConstTool.TimeUnit#MIN }: 分</li>
	 *             <li>{@link ConstTool.TimeUnit#HOUR}: 小时</li>
	 *             <li>{@link ConstTool.TimeUnit#DAY }: 天</li>
	 *             </ul>
	 * @return unit时间戳
	 */
	public static long getIntervalByNow(Date time, ConstTool.TimeUnit unit)
	{
		return getIntervalTime(getCurTimeDate(), time, unit);
	}

	/**
	 * 判断闰年
	 *
	 * @param year 年份
	 * @return {@code true}: 闰年<br>{@code false}: 平年
	 */
	public static boolean isLeapYear(int year)
	{
		return year%4 == 0 && year%100 != 0 || year%400 == 0;
	}

	/**
	 * 将date转换成format格式的日期
	 *
	 * @param format 格式
	 * @param date   日期
	 * @return
	 */
	public static String simpleDateFormat(String format, Date date)
	{
		if(RegTool.isNullString(format))
		{
			format = "yyyy-MM-dd HH:mm:ss";
		}
		String content = new SimpleDateFormat(format).format(date);
		return content;
	}

	/**
	 * 获取当前日期时间 / 得到今天的日期
	 * str yyyyMMddhhMMss 之类的
	 *
	 * @return
	 */
	@SuppressLint("SimpleDateFormat") public static String getCurrentDateTime(String format)
	{
		return simpleDateFormat(format, new Date());
	}

	/**
	 * 得到昨天的日期
	 *
	 * @param format 日期格式 yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getYestoryDate(String format)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		return simpleDateFormat(format, calendar.getTime());
	}

	/**
	 * 视频时间 转换成 "mm:ss"
	 *
	 * @param milliseconds
	 * @return
	 */
	public static String formatTime(long milliseconds)
	{
		String format = "mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+0"));
		String video_time = sdf.format(milliseconds);
		return video_time;
	}

	/**
	 * "mm:ss" 转换成 视频时间
	 *
	 * @param time
	 * @return
	 */
	public static long formatSeconds(String time)
	{
		String format = "mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+0"));
		Date date;
		long times = 0;
		try
		{
			date = sdf.parse(time);
			long l = date.getTime();
			times = l;
			Log.d("时间戳", times+"");
		}
		catch(ParseException e)
		{
			e.printStackTrace();
		}
		return times;
	}

	/**
	 * 根据年 月 获取对应的月份 天数
	 */
	public static int getDaysByYearMonth(int year, int month)
	{
		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month-1);
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 判断当前日期是星期几
	 *
	 * @param strDate 修要判断的时间 yyyy-MM-dd
	 * @return dayForWeek 判断结果
	 */
	public static int stringForWeek(String strDate) throws Exception
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(format.parse(strDate));
		if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
		{
			return 7;
		}
		else
		{
			return c.get(Calendar.DAY_OF_WEEK)-1;
		}
	}

	/**
	 * 判断当前日期是星期几
	 *
	 * @param date 修要判断的时间
	 * @return dayForWeek 判断结果
	 */
	public static int stringForWeek(Date date) throws Exception
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
		{
			return 7;
		}
		else
		{
			return c.get(Calendar.DAY_OF_WEEK)-1;
		}
	}

	/**
	 * 判断当前日期是星期几
	 *
	 * @param strDate 修要判断的时间
	 * @return dayForWeek 判断结果
	 */
	public static int stringForWeek(String strDate, SimpleDateFormat simpleDateFormat) throws Exception
	{
		Calendar c = Calendar.getInstance();
		c.setTime(simpleDateFormat.parse(strDate));
		if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
		{
			return 7;
		}
		else
		{
			return c.get(Calendar.DAY_OF_WEEK)-1;
		}
	}

	/**
	 * 判断当前日期是星期几
	 *
	 * @param strDate   修要判断的时间字符串
	 * @param formatStr 时间字符串的格式
	 */
	public static int stringForWeek(String strDate, String formatStr) throws Exception
	{
		SimpleDateFormat format = new SimpleDateFormat(formatStr, Locale.getDefault());
		Calendar c = Calendar.getInstance();
		c.setTime(format.parse(strDate));
		if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
		{
			return 7;
		}
		else
		{
			return c.get(Calendar.DAY_OF_WEEK)-1;
		}
	}

	// -----------------------------------------------------------------

	/**
	 * 按格式取得日期时间字符串
	 */
	public static String getDateString(Date value, String format)
	{
		String result = null;
		if(null != value)
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());

			try
			{
				result = dateFormat.format(value);
			}
			catch(Exception e)
			{

				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 按格式取得日期时间字符串
	 */
	public static Calendar getCalendar(String value, String format)
	{
		Calendar result = null;
		if(!RegTool.isNullString(value))
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());

			try
			{
				Date date = dateFormat.parse(value);
				result = Calendar.getInstance();
				result.setTime(date);
			}
			catch(ParseException e)
			{

				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 按格 式取得日期时间字符串
	 *
	 * @param c Calendar日期
	 */
	public static String getCalDateString(Calendar c, String format)
	{
		String result = null;
		try
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
			if(null == c)
			{

				c = Calendar.getInstance();
			}
			Date date = c.getTime();
			result = dateFormat.format(date);
		}
		catch(Exception e)
		{
			Log.e("TAG", e.toString());
		}
		return result;
	}

	/**
	 * 修改日期格式
	 *
	 * @param value     日期
	 * @param oldFormat 旧格式
	 * @param newFormat 新格式
	 */
	public static String changeFormat(String value, String oldFormat, String newFormat)
	{
		Calendar calendar = getCalendar(value, oldFormat);
		String result = getCalDateString(calendar, newFormat);
		return result;
	}

	/**
	 * 返回int型的日期 yyyyMMdd
	 *
	 * @param date 日期
	 */
	public static int getDateInt(Date date)
	{
		String string = getDateString(date, "yyyyMMdd");
		int dateInt = Integer.valueOf(string);
		return dateInt;
	}

	/**
	 * 返回int型的时间 HHmmss
	 *
	 * @param date 日期
	 */
	public static int getTimeInt(Date date)
	{
		String string = getDateString(date, "HHmmss");
		int dateInt = Integer.valueOf(string);
		return dateInt;
	}

	/**
	 * 给定一个日期型字符串，返回加减n天后的日期型字符串
	 *
	 * @param basicDate yyyy-MM-dd格式的日期
	 * @param n         多少天以后
	 */
	public static String nDaysAfter(String basicDate, int n)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date tmpDate = null;
		try
		{
			tmpDate = df.parse(basicDate);
		}
		catch(Exception e)
		{
			// 日期型字符串格式错误
		}
		long nDay = (tmpDate.getTime()/(24*60*60*1000)+1+n)*(24*60*60*1000);
		tmpDate.setTime(nDay);

		return df.format(tmpDate);
	}

	/**
	 * 推迟的天数
	 */
	public static String nDaysAfter(int day)
	{
		String dateString;
		Date date = new Date();// 取时间
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);// 把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		dateString = formatter.format(date);
		// System.out.println(dateString);
		return dateString;
	}

	/**
	 * 推迟的天数
	 */
	public static Date nDaysAfter(Date date, int day)
	{
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);// 把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
		return date;
	}

	/**
	 * 判断时间是否在时间段内
	 *
	 * @param strDate      当前时间 yyyy-MM-dd HH:mm:ss
	 * @param strDateBegin 开始时间 yyyy-MM-dd HH:mm:ss
	 * @param strDateEnd   结束时间 yyyy-MM-dd HH:mm:ss
	 */
	public static boolean isInDate(String strDate, String strDateBegin, String strDateEnd)
	{
		// 截取当前时间时分秒
		int strDateY = Integer.parseInt(strDate.substring(0, 4));
		int strDateM = Integer.parseInt(strDate.substring(5, 7));
		int strDateD = Integer.parseInt(strDate.substring(8, 10));
		// 截取开始时间时分秒
		int strDateBeginY = Integer.parseInt(strDateBegin.substring(0, 4));
		int strDateBeginM = Integer.parseInt(strDateBegin.substring(5, 7));
		int strDateBeginD = Integer.parseInt(strDateBegin.substring(8, 10));
		// 截取结束时间时分秒
		int strDateEndY = Integer.parseInt(strDateEnd.substring(0, 4));
		int strDateEndM = Integer.parseInt(strDateEnd.substring(5, 7));
		int strDateEndD = Integer.parseInt(strDateEnd.substring(8, 10));

		Date date = new Date(strDateY, strDateM, strDateD, 12, 0);
		Date dateBegin = new Date(strDateBeginY, strDateBeginM, strDateBeginD, 0, 0);
		Date dateEnd = new Date(strDateEndY, strDateEndM, strDateEndD, 23, 59);

		if(date.getTime() > dateBegin.getTime() && date.getTime() < dateEnd.getTime())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * 修改日期的时间段格式
	 *
	 * @param date 日期
	 * @param hms  新的时间 HH:mm:ss
	 */
	public static Date changeDateHMS(Date date, String hms)
	{
		String dateStr = getDateString(date, STR_FOMATER_DATA)+" "+hms;
		return string2Date(dateStr, STR_FOMATER_DATA_TIME);
	}

	/**
	 * 获取某日的开始时间
	 *
	 * @param date 日期
	 */
	public static Date getDateStart(Date date)
	{
		String dateStr = getDateString(date, STR_FOMATER_DATA)+" 00:00:00";
		return string2Date(dateStr, STR_FOMATER_DATA_TIME);
	}

	/**
	 * 获取某日的结束时间
	 *
	 * @param date 日期
	 */
	public static Date getDateEnd(Date date)
	{
		String dateStr = getDateString(date, STR_FOMATER_DATA)+" 23:59:59";
		return string2Date(dateStr, STR_FOMATER_DATA_TIME);
	}

	/**
	 * 获取指定日期所在月份开始的时间
	 *
	 * @param date 日期
	 */
	public static Date getMonthStart(Date date)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		//设置为1号,当前日期既为本月第一天
		c.set(Calendar.DAY_OF_MONTH, 1);
		//将小时至0
		c.set(Calendar.HOUR_OF_DAY, 0);
		//将分钟至0
		c.set(Calendar.MINUTE, 0);
		//将秒至0
		c.set(Calendar.SECOND, 0);
		//将毫秒至0
		c.set(Calendar.MILLISECOND, 0);
		// 获取本月第一天的时间戳
		return c.getTime();
	}

	/**
	 * 获取指定日期所在月份结束的时
	 *
	 * @param date 指定日期
	 * @return
	 */
	public static Date getMonthEnd(Date date)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		//设置为当月最后一天
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		//将小时至23
		c.set(Calendar.HOUR_OF_DAY, 23);
		//将分钟至59
		c.set(Calendar.MINUTE, 59);
		//将秒至59
		c.set(Calendar.SECOND, 59);
		//将毫秒至999
		c.set(Calendar.MILLISECOND, 999);
		// 获取本月最后一天的时间戳
		return c.getTime();
	}

	/**
	 * A的时间是不是比B早
	 *
	 * @param A 日期A
	 * @param B 日期B
	 */
	public static boolean isAEarlier(Date A, Date B)
	{
		long a = A.getTime();
		long b = B.getTime();
		return a >= b;
	}
}