package talex.zsw.basecore.util;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.net.Uri;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static talex.zsw.basecore.util.ConstTool.BYTE;
import static talex.zsw.basecore.util.ConstTool.GB;
import static talex.zsw.basecore.util.ConstTool.KB;
import static talex.zsw.basecore.util.ConstTool.MB;
import static talex.zsw.basecore.util.RegTool.isNullString;

/**
 * 数据处理相关
 * <p>
 * ┌───┐   ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┐
 * │Esc│   │ F1│ F2│ F3│ F4│ │ F5│ F6│ F7│ F8│ │ F9│F10│F11│F12│ │P/S│S L│P/B│  ┌┐    ┌┐    ┌┐
 * └───┘   └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┘  └┘    └┘    └┘
 * ┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───────┐ ┌───┬───┬───┐ ┌───┬───┬───┬───┐
 * │~ `│! 1│@ 2│# 3│$ 4│% 5│^ 6│& 7│* 8│( 9│) 0│_ -│+ =│ BacSp │ │Ins│Hom│PUp│ │N L│ / │ * │ - │
 * ├───┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─────┤ ├───┼───┼───┤ ├───┼───┼───┼───┤
 * │ Tab │ Q │ W │ E │ R │ T │ Y │ U │ I │ O │ P │{ [│} ]│ | \ │ │Del│End│PDn│ │ 7 │ 8 │ 9 │   │
 * ├─────┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴─────┤ └───┴───┴───┘ ├───┼───┼───┤ + │
 * │ Caps │ A │ S │ D │ F │ G │ H │ J │ K │ L │: ;│" '│ Enter  │               │ 4 │ 5 │ 6 │   │
 * ├──────┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴────────┤     ┌───┐     ├───┼───┼───┼───┤
 * │ Shift  │ Z │ X │ C │ V │ B │ N │ M │< ,│> .│? /│  Shift   │     │ ↑ │     │ 1 │ 2 │ 3 │   │
 * ├─────┬──┴─┬─┴──┬┴───┴───┴───┴───┴───┴──┬┴───┼───┴┬────┬────┤ ┌───┼───┼───┐ ├───┴───┼───┤ E││
 * │ Ctrl│    │Alt │         Space         │ Alt│    │    │Ctrl│ │ ← │ ↓ │ → │ │   0   │ . │←─┘│
 * └─────┴────┴────┴───────────────────────┴────┴────┴────┴────┘ └───┴───┴───┘ └───────┴───┴───┘
 */

public class DataTool
{
	private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
		'F'};

	/**
	 * 金额 格式化
	 */
	private static final DecimalFormat AMOUNT_FORMAT = new DecimalFormat("###,###,###,##0.00");

	/**
	 * 根据日期判断星座
	 *
	 * @param month
	 * @param day
	 * @return
	 */
	public static String getAstro(int month, int day)
	{
		String[] starArr = {"魔羯座", "水瓶座", "双鱼座", "牡羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座"};
		int[] DayArr = {22, 20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22};  // 两个星座分割日

		if(month <= 0 || day <= 0)
		{
			return "猴年马月座";
		}
		else if(month > 12 || day > 31)
		{
			return "猴年马月座";
		}

		int index = month;
		// 所查询日期在分割日之前，索引-1，否则不变
		if(day < DayArr[month-1])
		{
			index = index-1;
		}
		// 返回索引指向的星座string
		return starArr[index];
	}

	/**
	 * 格式化银行卡 加*
	 * 3749 **** **** 330
	 *
	 * @param cardNo 银行卡
	 * @return 3749 **** **** 330
	 */
	public static String formatCard(String cardNo)
	{
		if(cardNo == null || cardNo.length() < 8)
		{
			return "银行卡号有误";
		}
		String card = "";
		card = cardNo.substring(0, 4)+" **** **** ";
		card += cardNo.substring(cardNo.length()-4);
		return card;
	}

	/**
	 * 格式化身份证 加*
	 * 33042419901101****
	 *
	 * @param certNo 身份证号
	 * @return 33042419901101****
	 */
	public static String formatCert(String certNo)
	{
		if(certNo == null || certNo.length() < 15)
		{
			return "无效身份证号";
		}
		String card = "";
		card = certNo.substring(0, certNo.length()-8)+"****";
		card += certNo.substring(certNo.length()-4);
		return card;
	}

	/**
	 * 格式化身份证 加*
	 * 33042419901101****
	 *
	 * @param name 姓名
	 * @return 赵*
	 */
	public static String formatName(String name)
	{
		if(name == null || name.length() < 1)
		{
			return "无姓名";
		}
		String card = "";
		card = name.substring(0, name.length()-1)+"*";
		return card;
	}

	/**
	 * 隐藏手机中间4位号码
	 * 130****0000
	 *
	 * @param mobile_phone 手机号码
	 * @return 130****0000
	 */
	public static String formatPhone(String mobile_phone)
	{
		if(mobile_phone == null || mobile_phone.length() != 11)
		{
			return mobile_phone;
		}
		return mobile_phone.substring(0, 3)+"****"+mobile_phone.substring(7, 11);
	}

	/**
	 * 银行卡后四位
	 *
	 * @param cardNo
	 * @return
	 */
	public static String formatCardEnd4(String cardNo)
	{
		if(cardNo == null || cardNo.length() < 8)
		{
			return "银行卡号有误";
		}
		String card = "";
		card += cardNo.substring(cardNo.length()-4);
		return card;
	}

	/**
	 * 字符串转换成整数 ,转换失败将会 return 0;
	 *
	 * @param str 字符串
	 */
	public static int string2Int(String str)
	{
		return string2Int(str, 0);
	}

	/**
	 * 字符串转换成整数 ,转换失败将会 return 0;
	 *
	 * @param str 字符串
	 * @param def 默认返回
	 */
	public static int string2Int(String str, int def)
	{
		if(isNullString(str))
		{
			return def;
		}
		else
		{
			try
			{
				return Integer.parseInt(str);
			}
			catch(NumberFormatException e)
			{
				return def;
			}
		}
	}

	/**
	 * 字符串转换成整型数组
	 *
	 * @param s
	 * @return
	 */
	public static int[] string2Ints(String s)
	{
		if(isNullString(s))
		{
			return null;
		}
		else
		{
			int[] n = new int[s.length()];
			for(int i = 0; i < s.length(); i++)
			{
				n[i] = Integer.parseInt(s.substring(i, i+1));
			}
			return n;
		}
	}

	/**
	 * 整型数组求和
	 *
	 * @param ints
	 * @return
	 */
	public static int intsGetSum(int[] ints)
	{
		int sum = 0;

		for(int i = 0, len = ints.length; i < len; i++)
		{
			sum += ints[i];
		}

		return sum;
	}

	/**
	 * 字符串转换成long ,转换失败将会 return 0;
	 *
	 * @param str 字符串
	 */
	public static long string2Long(String str)
	{
		return string2Long(str, 0);
	}

	/**
	 * 字符串转换成long ,转换失败将会 return 0;
	 *
	 * @param str 字符串
	 * @param def 默认返回
	 */
	public static long string2Long(String str, long def)
	{
		if(isNullString(str))
		{
			return def;
		}
		else
		{
			try
			{
				return Long.parseLong(str);
			}
			catch(NumberFormatException e)
			{
				return def;
			}
		}
	}

	/**
	 * 字符串转换成double ,转换失败将会 return 0;
	 *
	 * @param str 字符串
	 */
	public static double string2Double(String str)
	{
		return string2Double(str, 0);
	}

	/**
	 * 字符串转换成double ,转换失败将会 return 0;
	 *
	 * @param str 字符串
	 * @param def 默认返回
	 */
	public static double string2Double(String str, double def)
	{
		if(isNullString(str))
		{
			return def;
		}
		else
		{
			try
			{
				return Double.parseDouble(str);
			}
			catch(NumberFormatException e)
			{
				return def;
			}
		}
	}

	/**
	 * 字符串转换成浮点型 Float
	 *
	 * @param str 待转换的字符串
	 * @return 转换后的 float
	 */
	public static float string2Float(String str)
	{
		return string2Float(str, 0f);
	}

	/**
	 * 字符串转换成浮点型 Float
	 *
	 * @param str 待转换的字符串
	 * @param def 默认返回
	 * @return 转换后的 float
	 */
	public static float string2Float(String str, float def)
	{
		if(isNullString(str))
		{
			return def;
		}
		else
		{
			try
			{
				return Float.parseFloat(str);
			}
			catch(NumberFormatException e)
			{
				return def;
			}
		}
	}

	/**
	 * 将字符串格式化为带两位小数的字符串
	 *
	 * @param str 字符串
	 * @return
	 */
	public static String format2Decimals(String str)
	{
		DecimalFormat df = new DecimalFormat("#.00");
		if(df.format(string2Double(str)).startsWith("."))
		{
			return "0"+df.format(string2Double(str));
		}
		else
		{
			return df.format(string2Double(str));
		}
	}

	/**
	 * 字符串转InputStream
	 *
	 * @param str
	 * @return
	 */
	public static InputStream string2InputStream(String str)
	{
		InputStream in_nocode = new ByteArrayInputStream(str.getBytes());
		//InputStream   in_withcode   =   new ByteArrayInputStream(str.getBytes("UTF-8"));
		return in_nocode;
	}

	/**
	 * 首字母大写
	 *
	 * @param s 待转字符串
	 * @return 首字母大写字符串
	 */
	public static String upperFirstLetter(String s)
	{
		if(isNullString(s) || !Character.isLowerCase(s.charAt(0)))
		{
			return s;
		}
		return String.valueOf((char) (s.charAt(0)-32))+s.substring(1);
	}

	/**
	 * 首字母小写
	 *
	 * @param s 待转字符串
	 * @return 首字母小写字符串
	 */
	public static String lowerFirstLetter(String s)
	{
		if(isNullString(s) || !Character.isUpperCase(s.charAt(0)))
		{
			return s;
		}
		return String.valueOf((char) (s.charAt(0)+32))+s.substring(1);
	}

	/**
	 * 反转字符串
	 *
	 * @param s 待反转字符串
	 * @return 反转字符串
	 */
	public static String reverse(String s)
	{
		int len = s.length();
		if(len <= 1)
		{
			return s;
		}
		int mid = len >> 1;
		char[] chars = s.toCharArray();
		char c;
		for(int i = 0; i < mid; ++i)
		{
			c = chars[i];
			chars[i] = chars[len-i-1];
			chars[len-i-1] = c;
		}
		return new String(chars);
	}

	/**
	 * 转化为半角字符
	 *
	 * @param s 待转字符串
	 * @return 半角字符串
	 */
	public static String toDBC(String s)
	{
		if(isNullString(s))
		{
			return s;
		}
		char[] chars = s.toCharArray();
		for(int i = 0, len = chars.length; i < len; i++)
		{
			if(chars[i] == 12288)
			{
				chars[i] = ' ';
			}
			else if(65281 <= chars[i] && chars[i] <= 65374)
			{
				chars[i] = (char) (chars[i]-65248);
			}
			else
			{
				chars[i] = chars[i];
			}
		}
		return new String(chars);
	}

	/**
	 * 转化为全角字符
	 *
	 * @param s 待转字符串
	 * @return 全角字符串
	 */
	public static String toSBC(String s)
	{
		if(isNullString(s))
		{
			return s;
		}
		char[] chars = s.toCharArray();
		for(int i = 0, len = chars.length; i < len; i++)
		{
			if(chars[i] == ' ')
			{
				chars[i] = (char) 12288;
			}
			else if(33 <= chars[i] && chars[i] <= 126)
			{
				chars[i] = (char) (chars[i]+65248);
			}
			else
			{
				chars[i] = chars[i];
			}
		}
		return new String(chars);
	}

	/**
	 * 单个汉字转成ASCII码
	 *
	 * @param s 单个汉字字符串
	 * @return 如果字符串长度是1返回的是对应的ascii码，否则返回-1
	 */
	public static int oneCn2ASCII(String s)
	{
		if(s.length() != 1)
		{
			return -1;
		}
		int ascii = 0;
		try
		{
			byte[] bytes = s.getBytes("GB2312");
			if(bytes.length == 1)
			{
				ascii = bytes[0];
			}
			else if(bytes.length == 2)
			{
				int highByte = 256+bytes[0];
				int lowByte = 256+bytes[1];
				ascii = (256*highByte+lowByte)-256*256;
			}
			else
			{
				throw new IllegalArgumentException("Illegal resource string");
			}
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return ascii;
	}

	/**
	 * 单个汉字转成拼音
	 *
	 * @param s 单个汉字字符串
	 * @return 如果字符串长度是1返回的是对应的拼音，否则返回{@code null}
	 */
	public static String oneCn2PY(String s)
	{
		int ascii = oneCn2ASCII(s);
		if(ascii == -1)
		{
			return null;
		}
		String ret = null;
		if(0 <= ascii && ascii <= 127)
		{
			ret = String.valueOf((char) ascii);
		}
		else
		{
			for(int i = pyValue.length-1; i >= 0; i--)
			{
				if(pyValue[i] <= ascii)
				{
					ret = pyStr[i];
					break;
				}
			}
		}
		return ret;
	}

	/**
	 * 获得第一个汉字首字母
	 *
	 * @param s 单个汉字字符串
	 * @return 拼音
	 */
	public static String getPYFirstLetter(String s)
	{
		if(isNullString(s))
		{
			return "";
		}
		String first, py;
		first = s.substring(0, 1);
		py = oneCn2PY(first);
		if(py == null)
		{
			return null;
		}
		return py.substring(0, 1);
	}

	/**
	 * 中文转拼音
	 *
	 * @param s 汉字字符串
	 * @return 拼音
	 */
	public static String cn2PY(String s)
	{
		String hz, py;
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < s.length(); i++)
		{
			hz = s.substring(i, i+1);
			py = oneCn2PY(hz);
			if(py == null)
			{
				py = "?";
			}
			sb.append(py);
		}
		return sb.toString();
	}

	/**
	 * byteArr转hexString
	 * <p>例如：</p>
	 * bytes2HexString(new byte[] { 0, (byte) 0xa8 }) returns 00A8
	 *
	 * @param bytes byte数组
	 * @return 16进制大写字符串
	 */
	public static String bytes2HexString(byte[] bytes)
	{
		if(bytes == null)
		{
			return "";
		}
		char[] ret = new char[bytes.length << 1];
		for(int i = 0, j = 0; i < bytes.length; i++)
		{
			ret[j++] = HEX_DIGITS[bytes[i] >>> 4 & 0x0f];
			ret[j++] = HEX_DIGITS[bytes[i] & 0x0f];
		}
		return new String(ret);
	}

	/**
	 * byteArr转hexString
	 * <p>例如：</p>
	 * bytes2HexString(new byte[] { 0, (byte) 0xa8 }) returns 00 A8
	 *
	 * @param bytes byte数组
	 * @return 16进制大写字符串
	 */
	public static String bytes2HexStringWithBlank(byte[] bytes)
	{
		if(bytes == null)
		{
			return "";
		}
		String string = "";
		for(int j = 0; j < bytes.length; j++)
		{
			int count = (int) bytes[j];
			if(count < 0)
			{
				count = count+256;
			}
			if(count <= 15)
			{
				string += "0";
			}
			string += Integer.toHexString(count)+" ";
		}
		return string;
	}

	/**
	 * byteArr转hexString
	 * <p>例如：</p>
	 * bytes2HexString(new byte[] { 0, (byte) 0xa8 }) returns 00A8
	 *
	 * @param bytes byte数组
	 * @return 16进制大写字符串
	 */
	public static String bytes2HexString(byte[] bytes, int start, int end)
	{
		if(bytes == null)
		{
			return "";
		}
		String string = "";
		for(int j = start; j < end; j++)
		{
			// 超界
			int count = (int) bytes[j];
			if(count < 0)
			{
				count = count+256;
			}
			if(count <= 15)
			{
				string += "0";
			}
			string += Integer.toHexString(count);
		}
		return string;
	}

	/**
	 * hexString转byteArr
	 * <p>例如：</p>
	 * hexString2Bytes("00A8") returns { 0, (byte) 0xA8 }
	 *
	 * @param hexString 十六进制字符串
	 * @return 字节数组
	 */
	public static byte[] hexString2Bytes(String hexString)
	{
		int len = hexString.length();
		if(len%2 != 0)
		{
			throw new IllegalArgumentException("长度不是偶数");
		}
		char[] hexBytes = hexString.toUpperCase().toCharArray();
		byte[] ret = new byte[len >>> 1];
		for(int i = 0; i < len; i += 2)
		{
			ret[i >> 1] = (byte) (hex2Dec(hexBytes[i]) << 4 | hex2Dec(hexBytes[i+1]));
		}
		return ret;
	}


	/**
	 * byteArr 转 对应内容
	 */
	public static String bytes2String(byte[] bytes)
	{
		if(bytes == null)
		{
			return "";
		}
		String string = "";
		int start = 0;
		int end = bytes.length;

		byte[] by = new byte[end-start];

		for(int i = start, j = 0; i < end; i++, j++)
		{
			by[j] = bytes[i];
		}
		try
		{
			string = new String(by, "GBK");
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return string;
	}

	/**
	 * byteArr 转 对应内容
	 */
	public static String bytes2String(byte[] bytes, int start, int end)
	{
		if(bytes == null)
		{
			return "";
		}
		String string = "";
		byte[] by = new byte[end-start];

		for(int i = start, j = 0; i < end; i++, j++)
		{
			by[j] = bytes[i];
		}
		try
		{
			string = new String(by, "GBK");
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return string;
	}

	private final static String mHexStr = "0123456789ABCDEF";

	/**
	 * 十六进制字符串转换成 ASCII字符串
	 *
	 * @param hexStr String Byte字符串
	 * @return String 对应的字符串
	 */
	public static String hexStr2Str(String hexStr)
	{
		hexStr = hexStr.toString().trim().replace(" ", "").toUpperCase(Locale.US);
		char[] hexs = hexStr.toCharArray();
		byte[] bytes = new byte[hexStr.length()/2];
		int iTmp = 0x00;
		;

		for(int i = 0; i < bytes.length; i++)
		{
			iTmp = mHexStr.indexOf(hexs[2*i]) << 4;
			iTmp |= mHexStr.indexOf(hexs[2*i+1]);
			bytes[i] = (byte) (iTmp & 0xFF);
		}
		return new String(bytes);
	}


	/**
	 * hexChar转int
	 *
	 * @param hexChar hex单个字节
	 * @return 0..15
	 */
	private static int hex2Dec(char hexChar)
	{
		if(hexChar >= '0' && hexChar <= '9')
		{
			return hexChar-'0';
		}
		else if(hexChar >= 'A' && hexChar <= 'F')
		{
			return hexChar-'A'+10;
		}
		else
		{
			throw new IllegalArgumentException();
		}
	}

	/**
	 * charArr转byteArr
	 *
	 * @param chars 字符数组
	 * @return 字节数组
	 */
	public static byte[] chars2Bytes(char[] chars)
	{
		int len = chars.length;
		byte[] bytes = new byte[len];
		for(int i = 0; i < len; i++)
		{
			bytes[i] = (byte) (chars[i]);
		}
		return bytes;
	}

	/**
	 * byteArr转charArr
	 *
	 * @param bytes 字节数组
	 * @return 字符数组
	 */
	public static char[] bytes2Chars(byte[] bytes)
	{
		int len = bytes.length;
		char[] chars = new char[len];
		for(int i = 0; i < len; i++)
		{
			chars[i] = (char) (bytes[i] & 0xff);
		}
		return chars;
	}

	/**
	 * 字节数转以unit为单位的size
	 *
	 * @param byteNum 字节数
	 * @param unit    <ul>
	 *                <li>{@link ConstTool.MemoryUnit#BYTE}: 字节</li>
	 *                <li>{@link ConstTool.MemoryUnit#KB}  : 千字节</li>
	 *                <li>{@link ConstTool.MemoryUnit#MB}  : 兆</li>
	 *                <li>{@link ConstTool.MemoryUnit#GB}  : GB</li>
	 *                </ul>
	 * @return 以unit为单位的size
	 */
	public static double byte2Size(long byteNum, ConstTool.MemoryUnit unit)
	{
		if(byteNum < 0)
		{
			return -1;
		}
		switch(unit)
		{
			default:
			case BYTE:
				return (double) byteNum/BYTE;
			case KB:
				return (double) byteNum/KB;
			case MB:
				return (double) byteNum/MB;
			case GB:
				return (double) byteNum/GB;
		}
	}

	/**
	 * 以unit为单位的size转字节数
	 *
	 * @param size 大小
	 * @param unit <ul>
	 *             <li>{@link ConstTool.MemoryUnit#BYTE}: 字节</li>
	 *             <li>{@link ConstTool.MemoryUnit#KB}  : 千字节</li>
	 *             <li>{@link ConstTool.MemoryUnit#MB}  : 兆</li>
	 *             <li>{@link ConstTool.MemoryUnit#GB}  : GB</li>
	 *             </ul>
	 * @return 字节数
	 */
	public static long size2Byte(long size, ConstTool.MemoryUnit unit)
	{
		if(size < 0)
		{
			return -1;
		}
		switch(unit)
		{
			default:
			case BYTE:
				return size*BYTE;
			case KB:
				return size*KB;
			case MB:
				return size*MB;
			case GB:
				return size*GB;
		}
	}

	/**
	 * 字节数转合适大小
	 * <p>保留3位小数</p>
	 *
	 * @param byteNum 字节数
	 * @return 1...1024 unit
	 */
	public static String byte2FitSize(long byteNum)
	{
		if(byteNum < 0)
		{
			return "shouldn't be less than zero!";
		}
		else if(byteNum < KB)
		{
			return String.format(Locale.getDefault(), "%.3fB", (double) byteNum);
		}
		else if(byteNum < MB)
		{
			return String.format(Locale.getDefault(), "%.3fKB", (double) byteNum/KB);
		}
		else if(byteNum < GB)
		{
			return String.format(Locale.getDefault(), "%.3fMB", (double) byteNum/MB);
		}
		else
		{
			return String.format(Locale.getDefault(), "%.3fGB", (double) byteNum/GB);
		}
	}

	/**
	 * inputStream转outputStream
	 *
	 * @param is 输入流
	 * @return outputStream子类
	 */
	public static ByteArrayOutputStream input2OutputStream(InputStream is)
	{
		if(is == null)
		{
			return null;
		}
		try
		{
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			byte[] b = new byte[KB];
			int len;
			while((len = is.read(b, 0, KB)) != -1)
			{
				os.write(b, 0, len);
			}
			return os;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
		finally
		{
			FileTool.closeIO(is);
		}
	}

	/**
	 * inputStream转byteArr
	 *
	 * @param is 输入流
	 * @return 字节数组
	 */
	public static byte[] inputStream2Bytes(InputStream is)
	{
		return input2OutputStream(is).toByteArray();
	}

	/**
	 * byteArr转inputStream
	 *
	 * @param bytes 字节数组
	 * @return 输入流
	 */
	public static InputStream bytes2InputStream(byte[] bytes)
	{
		return new ByteArrayInputStream(bytes);
	}

	/**
	 * outputStream转byteArr
	 *
	 * @param out 输出流
	 * @return 字节数组
	 */
	public static byte[] outputStream2Bytes(OutputStream out)
	{
		if(out == null)
		{
			return null;
		}
		return ((ByteArrayOutputStream) out).toByteArray();
	}

	/**
	 * outputStream转byteArr
	 *
	 * @param bytes 字节数组
	 * @return 字节数组
	 */
	public static OutputStream bytes2OutputStream(byte[] bytes)
	{
		ByteArrayOutputStream os = null;
		try
		{
			os = new ByteArrayOutputStream();
			os.write(bytes);
			return os;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
		finally
		{
			FileTool.closeIO(os);
		}
	}

	/**
	 * inputStream转string按编码
	 *
	 * @param is          输入流
	 * @param charsetName 编码格式
	 * @return 字符串
	 */
	public static String inputStream2String(InputStream is, String charsetName)
	{
		if(is == null || isNullString(charsetName))
		{
			return null;
		}
		try
		{
			return new String(inputStream2Bytes(is), charsetName);
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * string转inputStream按编码
	 *
	 * @param string      字符串
	 * @param charsetName 编码格式
	 * @return 输入流
	 */
	public static InputStream string2InputStream(String string, String charsetName)
	{
		if(string == null || isNullString(charsetName))
		{
			return null;
		}
		try
		{
			return new ByteArrayInputStream(string.getBytes(charsetName));
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * outputStream转string按编码
	 *
	 * @param out         输出流
	 * @param charsetName 编码格式
	 * @return 字符串
	 */
	public static String outputStream2String(OutputStream out, String charsetName)
	{
		if(out == null)
		{
			return null;
		}
		try
		{
			return new String(outputStream2Bytes(out), charsetName);
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * string转outputStream按编码
	 *
	 * @param string      字符串
	 * @param charsetName 编码格式
	 * @return 输入流
	 */
	public static OutputStream string2OutputStream(String string, String charsetName)
	{
		if(string == null || isNullString(charsetName))
		{
			return null;
		}
		try
		{
			return bytes2OutputStream(string.getBytes(charsetName));
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 金额格式化
	 *
	 * @param value 数值
	 * @return
	 */
	public static String getAmountValue(double value)
	{
		return AMOUNT_FORMAT.format(value);
	}

	/**
	 * 金额格式化
	 *
	 * @param value 数值
	 * @return
	 */
	public static String getAmountValue(String value)
	{
		if(isNullString(value))
		{
			return "0";
		}
		return AMOUNT_FORMAT.format(Double.parseDouble(value));
	}

	/**
	 * 四舍五入
	 *
	 * @param value 数值
	 * @param digit 保留小数位
	 * @return
	 */
	public static String getRoundUp(BigDecimal value, int digit)
	{
		return value.setScale(digit, BigDecimal.ROUND_HALF_UP).toString();
	}

	/**
	 * 四舍五入
	 *
	 * @param value 数值
	 * @param digit 保留小数位
	 * @return
	 */
	public static String getRoundUp(double value, int digit)
	{
		BigDecimal result = new BigDecimal(value);
		return result.setScale(digit, BigDecimal.ROUND_HALF_UP).toString();
	}

	/**
	 * 四舍五入
	 *
	 * @param value 数值
	 * @param digit 保留小数位
	 * @return
	 */
	public static String getRoundUp(String value, int digit)
	{
		if(isNullString(value))
		{
			return "0";
		}
		BigDecimal result = new BigDecimal(Double.parseDouble(value));
		return result.setScale(digit, BigDecimal.ROUND_HALF_UP).toString();
	}

	/**
	 * 获取百分比（乘100）
	 *
	 * @param value 数值
	 * @param digit 保留小数位
	 * @return
	 */
	public static String getPercentValue(BigDecimal value, int digit)
	{
		BigDecimal result = value.multiply(new BigDecimal(100));
		return getRoundUp(result, digit)+"%";
	}

	/**
	 * 获取百分比（乘100）
	 *
	 * @param value 数值
	 * @param digit 保留小数位
	 * @return
	 */
	public static String getPercentValue(double value, int digit)
	{
		BigDecimal result = new BigDecimal(value);
		return getPercentValue(result, digit)+"%";
	}

	/**
	 * 获取百分比（乘100,保留两位小数）
	 *
	 * @param value 数值
	 * @return
	 */
	public static String getPercentValue(double value)
	{
		BigDecimal result = new BigDecimal(value);
		return getPercentValue(result, 2)+"%";
	}

	/**
	 * outputStream转inputStream
	 *
	 * @param out 输出流
	 * @return inputStream子类
	 */
	public ByteArrayInputStream output2InputStream(OutputStream out)
	{
		if(out == null)
		{
			return null;
		}
		return new ByteArrayInputStream(((ByteArrayOutputStream) out).toByteArray());
	}

	private static int[] pyValue = new int[]{-20319, -20317, -20304, -20295, -20292, -20283, -20265, -20257, -20242,
		-20230, -20051, -20036, -20032, -20026, -20002, -19990, -19986, -19982, -19976, -19805, -19784, -19775, -19774,
		-19763, -19756, -19751, -19746, -19741, -19739, -19728, -19725, -19715, -19540, -19531, -19525, -19515, -19500,
		-19484, -19479, -19467, -19289, -19288, -19281, -19275, -19270, -19263, -19261, -19249, -19243, -19242, -19238,
		-19235, -19227, -19224, -19218, -19212, -19038, -19023, -19018, -19006, -19003, -18996, -18977, -18961, -18952,
		-18783, -18774, -18773, -18763, -18756, -18741, -18735, -18731, -18722, -18710, -18697, -18696, -18526, -18518,
		-18501, -18490, -18478, -18463, -18448, -18447, -18446, -18239, -18237, -18231, -18220, -18211, -18201, -18184,
		-18183, -18181, -18012, -17997, -17988, -17970, -17964, -17961, -17950, -17947, -17931, -17928, -17922, -17759,
		-17752, -17733, -17730, -17721, -17703, -17701, -17697, -17692, -17683, -17676, -17496, -17487, -17482, -17468,
		-17454, -17433, -17427, -17417, -17202, -17185, -16983, -16970, -16942, -16915, -16733, -16708, -16706, -16689,
		-16664, -16657, -16647, -16474, -16470, -16465, -16459, -16452, -16448, -16433, -16429, -16427, -16423, -16419,
		-16412, -16407, -16403, -16401, -16393, -16220, -16216, -16212, -16205, -16202, -16187, -16180, -16171, -16169,
		-16158, -16155, -15959, -15958, -15944, -15933, -15920, -15915, -15903, -15889, -15878, -15707, -15701, -15681,
		-15667, -15661, -15659, -15652, -15640, -15631, -15625, -15454, -15448, -15436, -15435, -15419, -15416, -15408,
		-15394, -15385, -15377, -15375, -15369, -15363, -15362, -15183, -15180, -15165, -15158, -15153, -15150, -15149,
		-15144, -15143, -15141, -15140, -15139, -15128, -15121, -15119, -15117, -15110, -15109, -14941, -14937, -14933,
		-14930, -14929, -14928, -14926, -14922, -14921, -14914, -14908, -14902, -14894, -14889, -14882, -14873, -14871,
		-14857, -14678, -14674, -14670, -14668, -14663, -14654, -14645, -14630, -14594, -14429, -14407, -14399, -14384,
		-14379, -14368, -14355, -14353, -14345, -14170, -14159, -14151, -14149, -14145, -14140, -14137, -14135, -14125,
		-14123, -14122, -14112, -14109, -14099, -14097, -14094, -14092, -14090, -14087, -14083, -13917, -13914, -13910,
		-13907, -13906, -13905, -13896, -13894, -13878, -13870, -13859, -13847, -13831, -13658, -13611, -13601, -13406,
		-13404, -13400, -13398, -13395, -13391, -13387, -13383, -13367, -13359, -13356, -13343, -13340, -13329, -13326,
		-13318, -13147, -13138, -13120, -13107, -13096, -13095, -13091, -13076, -13068, -13063, -13060, -12888, -12875,
		-12871, -12860, -12858, -12852, -12849, -12838, -12831, -12829, -12812, -12802, -12607, -12597, -12594, -12585,
		-12556, -12359, -12346, -12320, -12300, -12120, -12099, -12089, -12074, -12067, -12058, -12039, -11867, -11861,
		-11847, -11831, -11798, -11781, -11604, -11589, -11536, -11358, -11340, -11339, -11324, -11303, -11097, -11077,
		-11067, -11055, -11052, -11045, -11041, -11038, -11024, -11020, -11019, -11018, -11014, -10838, -10832, -10815,
		-10800, -10790, -10780, -10764, -10587, -10544, -10533, -10519, -10331, -10329, -10328, -10322, -10315, -10309,
		-10307, -10296, -10281, -10274, -10270, -10262, -10260, -10256, -10254};
	private static String[] pyStr = new String[]{
		/*A*/
		"a", "ai", "an", "ang", "ao", "ba", "bai", "ban", "bang", "bao",

		/*B*/
		"bei", "ben", "beng", "bi", "bian", "biao", "bie", "bin", "bing", "bo", "bu",

		/*C*/
		"ca", "cai", "can", "cang", "cao", "ce", "ceng", "cha", "chai", "chan", "chang", "chao", "che", "chen", "cheng",
		"chi", "chong", "chou", "chu", "chuai", "chuan", "chuang", "chui", "chun", "chuo", "ci", "cong", "cou", "cu",
		"cuan", "cui", "cun", "cuo",

		/*D*/
		"da", "dai", "dan", "dang", "dao", "de", "deng", "di", "dian", "diao", "die", "ding", "diu", "dong", "dou",
		"du", "duan", "dui", "dun", "duo",

		/*E*/
		"e", "en", "er",

		/*F*/
		"fa", "fan", "fang", "fei", "fen", "feng", "fo", "fou", "fu",

		/*G*/
		"ga", "gai", "gan", "gang", "gao", "ge", "gei", "gen", "geng", "gong", "gou", "gu", "gua", "guai", "guan",
		"guang", "gui", "gun", "guo",

		/*H*/
		"ha", "hai", "han", "hang", "hao", "he", "hei", "hen", "heng", "hong", "hou", "hu", "hua", "huai", "huan",
		"huang", "hui", "hun", "huo",

		/*J*/
		"ji", "jia", "jian", "jiang", "jiao", "jie", "jin", "jing", "jiong", "jiu", "ju", "juan", "jue", "jun",

		/*K*/
		"ka", "kai", "kan", "kang", "kao", "ke", "ken", "keng", "kong", "kou", "ku", "kua", "kuai", "kuan", "kuang",
		"kui", "kun", "kuo",

		/*L*/
		"la", "lai", "lan", "lang", "lao", "le", "lei", "leng", "li", "lia", "lian", "liang", "liao", "lie", "lin",
		"ling", "liu", "long", "lou", "lu", "lv", "luan", "lue", "lun", "luo",

		/*M*/
		"ma", "mai", "man", "mang", "mao", "me", "mei", "men", "meng", "mi", "mian", "miao", "mie", "min", "ming",
		"miu", "mo", "mou", "mu",

		/*N*/
		"na", "nai", "nan", "nang", "nao", "ne", "nei", "nen", "neng", "ni", "nian", "niang", "niao", "nie", "nin",
		"ning", "niu", "nong", "nu", "nv", "nuan", "nue", "nuo",

		/*O*/
		"o", "ou",

		/*P*/
		"pa", "pai", "pan", "pang", "pao", "pei", "pen", "peng", "pi", "pian", "piao", "pie", "pin", "ping", "po", "pu",

		/*Q*/
		"qi", "qia", "qian", "qiang", "qiao", "qie", "qin", "qing", "qiong", "qiu", "qu", "quan", "que", "qun",

		/*R*/
		"ran", "rang", "rao", "re", "ren", "reng", "ri", "rong", "rou", "ru", "ruan", "rui", "run", "ruo",

		/*S*/
		"sa", "sai", "san", "sang", "sao", "se", "sen", "seng", "sha", "shai", "shan", "shang", "shao", "she", "shen",
		"sheng", "shi", "shou", "shu", "shua", "shuai", "shuan", "shuang", "shui", "shun", "shuo", "si", "song", "sou",
		"su", "suan", "sui", "sun", "suo",

		/*T*/
		"ta", "tai", "tan", "tang", "tao", "te", "teng", "ti", "tian", "tiao", "tie", "ting", "tong", "tou", "tu",
		"tuan", "tui", "tun", "tuo",

		/*W*/
		"wa", "wai", "wan", "wang", "wei", "wen", "weng", "wo", "wu",

		/*X*/
		"xi", "xia", "xian", "xiang", "xiao", "xie", "xin", "xing", "xiong", "xiu", "xu", "xuan", "xue", "xun",

		/*Y*/
		"ya", "yan", "yang", "yao", "ye", "yi", "yin", "ying", "yo", "yong", "you", "yu", "yuan", "yue", "yun",

		/*Z*/
		"za", "zai", "zan", "zang", "zao", "ze", "zei", "zen", "zeng", "zha", "zhai", "zhan", "zhang", "zhao", "zhe",
		"zhen", "zheng", "zhi", "zhong", "zhou", "zhu", "zhua", "zhuai", "zhuan", "zhuang", "zhui", "zhun", "zhuo",
		"zi", "zong", "zou", "zu", "zuan", "zui", "zun", "zuo"};

	// -----------------------------------------------------

	/**
	 * 若输入为null，则返回空字符串；否则返回字符串自身
	 */
	public static String getNotNull(String input)
	{
		return (input == null ? "" : input);
	}

	public static String getNotNull(String input, String defaultValue)
	{
		return (RegTool.isNullString(input) ? defaultValue : input);
	}

	/**
	 * 如果为空则返回默认值
	 */
	public static <T> T getOrDefault(final T object, final T defaultObject)
	{
		if(object == null)
		{
			return defaultObject;
		}
		return object;
	}

	/**
	 * 字符串的转义(处理特殊字符)
	 */
	public static String stringToString(String input)
	{
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < input.length(); i++)
		{
			char c = input.toCharArray()[i];
			switch(c)
			{
				case '\'':
					sb.append("\\\'");
					break;
				case '\"':
					sb.append("\\\"");
					break;
				case '\\':
					sb.append("\\\\");
					break;
				case '/':
					sb.append("\\/");
					break;
				case '\b':
					sb.append("\\b");
					break;
				case '\f':
					sb.append("\\f");
					break;
				case '\n':
					sb.append("\\n");
					break;
				case '\r':
					sb.append("\\r");
					break;
				case '\t':
					sb.append("\\t");
					break;
				default:
					sb.append(c);
					break;
			}
		}
		return sb.toString();
	}

	/**
	 * 去除字符串中的空格，回车制表符等。。。
	 *
	 * @param str 目标字符串
	 */
	public static String deleteBlank(String str)
	{
		String dest = "";
		if(str != null)
		{
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/**
	 * 去除String中的HTML标签
	 */
	public static String deleteHTMLTag(String htmlStr)
	{
		String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
		String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
		String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
		String regEx_my = "&nbsp;";
		Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签
		Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll(""); // 过滤style标签
		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll(""); // 过滤html标签
		Pattern p_my = Pattern.compile(regEx_my, Pattern.CASE_INSENSITIVE);
		Matcher m_my = p_my.matcher(htmlStr);
		htmlStr = m_my.replaceAll(""); // 过滤html标签
		return htmlStr.trim(); // 返回文本字符串
	}

	/**
	 * 将16进制转为区位码
	 */
	public static String[] hexString2BitCode(String str_16)
	{
		String[] result = null;
		if(str_16 == null || str_16 == "" || str_16.length()%2 != 0)
		{
			return result;
		}
		result = new String[str_16.length()/2];
		for(int i = 0; i < str_16.length()/2; i++)
		{
			result[i] = "0x"+str_16.charAt(i*2)+str_16.charAt(i*2+1);
		}
		return result;
	}

	/**
	 * 将汉字转Unicode
	 * 汉字 -> \u6c49\u5b57
	 *
	 * @param s 汉字
	 * @return String sb.toString();
	 */
	public static String string2Unicode(String s)
	{
		if(s == null)
		{
			return s;
		}
		char[] chars = s.toCharArray();
		char c;
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < chars.length; i++)
		{
			c = chars[i];
			if(c > 0xff)
			{
				sb.append("\\u").append(Integer.toHexString(c));
			}
			else
			{
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 将Unicode转汉字
	 * \u6c49\u5b57 -> 汉字
	 */
	public static String unicode2String(String unicodeStr)
	{
		int start = 0;
		int end = 0;
		final StringBuffer buffer = new StringBuffer();
		while(start > -1)
		{
			end = unicodeStr.indexOf("\\u", start+2);
			String charStr = "";
			if(end == -1)
			{
				charStr = unicodeStr.substring(start+2, unicodeStr.length());
			}
			else
			{
				charStr = unicodeStr.substring(start+2, end);
			}
			char letter = (char) Integer.parseInt(charStr, 16);
			buffer.append(new Character(letter).toString());
			start = end;
		}
		return buffer.toString();
	}

	/**
	 * 字符串 转 URL字符串
	 * 汉字 -> %e6%b1%89%e5%ad%97
	 *
	 * @param string 原字符串
	 */
	public static String string2URL(String string)
	{
		string = URLEncoder.encode(string);
		return string;
	}

	/**
	 * URL字符串 转 字符串(中文)
	 * %e6%b1%89%e5%ad%97 -> 汉字
	 *
	 * @param string 原字符串
	 */
	public static String URL2String(String string)
	{
		string = URLDecoder.decode(string);
		return string;
	}

	/** 7位ASCII字符，也叫作ISO646-US、Unicode字符集的基本拉丁块 */
	public static final String US_ASCII = "US-ASCII";

	/** ISO 拉丁字母表 No.1，也叫作 ISO-LATIN-1 */
	public static final String ISO_8859_1 = "ISO-8859-1";

	/** 8 位 UCS 转换格式 */
	public static final String UTF_8 = "UTF-8";

	/** 16 位 UCS 转换格式，Big Endian（最低地址存放高位字节）字节顺序 */
	public static final String UTF_16BE = "UTF-16BE";

	/** 16 位 UCS 转换格式，Little-endian（最高地址存放低位字节）字节顺序 */
	public static final String UTF_16LE = "UTF-16LE";

	/** 16 位 UCS 转换格式，字节顺序由可选的字节顺序标记来标识 */
	public static final String UTF_16 = "UTF-16";

	/** 中文超大字符集 */
	public static final String GBK = "GBK";

	/**
	 * 将字符编码转换成US-ASCII码
	 */
	public String toASCII(String str) throws UnsupportedEncodingException
	{
		return this.changeCharset(str, US_ASCII);
	}

	/**
	 * 将字符编码转换成ISO-8859-1码
	 */
	public String toISO_8859_1(String str) throws UnsupportedEncodingException
	{
		return this.changeCharset(str, ISO_8859_1);
	}

	/**
	 * 将字符编码转换成UTF-8码
	 */
	public String toUTF_8(String str) throws UnsupportedEncodingException
	{
		return this.changeCharset(str, UTF_8);
	}

	/**
	 * 将字符编码转换成UTF-16BE码
	 */
	public String toUTF_16BE(String str) throws UnsupportedEncodingException
	{
		return this.changeCharset(str, UTF_16BE);
	}

	/**
	 * 将字符编码转换成UTF-16LE码
	 */
	public String toUTF_16LE(String str) throws UnsupportedEncodingException
	{
		return this.changeCharset(str, UTF_16LE);
	}

	/**
	 * 将字符编码转换成UTF-16码
	 */
	public String toUTF_16(String str) throws UnsupportedEncodingException
	{
		return this.changeCharset(str, UTF_16);
	}

	/**
	 * 将字符编码转换成GBK码
	 */
	public String toGBK(String str) throws UnsupportedEncodingException
	{
		return this.changeCharset(str, GBK);
	}

	/**
	 * 字符串编码转换的实现方法
	 *
	 * @param str        待转换编码的字符串
	 * @param newCharset 目标编码
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String changeCharset(String str, String newCharset) throws UnsupportedEncodingException
	{
		if(str != null)
		{
			// 用默认字符编码解码字符串。
			byte[] bs = str.getBytes();
			// 用新的字符编码生成字符串
			return new String(bs, newCharset);
		}
		return null;
	}

	/**
	 * 字符串编码转换的实现方法
	 *
	 * @param str        待转换编码的字符串
	 * @param oldCharset 原编码
	 * @param newCharset 目标编码
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String changeCharset(String str, String oldCharset, String newCharset) throws UnsupportedEncodingException
	{
		if(str != null)
		{
			// 用旧的字符编码解码字符串。解码可能会出现异常。
			byte[] bs = str.getBytes(oldCharset);
			// 用新的字符编码生成s字符串
			return new String(bs, newCharset);
		}
		return null;
	}

	/**
	 * int到byte[]
	 *
	 * @param i
	 * @return
	 */
	public static byte[] int2Bytes(int i)
	{
		byte[] result = new byte[4];
		// 由高位到低位
		result[0] = (byte) ((i >> 24) & 0xFF);
		result[1] = (byte) ((i >> 16) & 0xFF);
		result[2] = (byte) ((i >> 8) & 0xFF);
		result[3] = (byte) (i & 0xFF);
		return result;
	}

	/**
	 * byte[]转int
	 *
	 * @param bytes
	 * @return
	 */
	public static int bytes2Int(byte[] bytes)
	{
		int value = 0;
		// 由高位到低位
		for(int i = 0; i < 4; i++)
		{
			int shift = (4-1-i)*8;
			value += (bytes[i] & 0x000000FF) << shift;// 往高位游
		}
		return value;
	}

	// ----------------------------------------------------------

	/**
	 * 资源id 转 Uri
	 */
	public static Uri resId2Uri(int resId)
	{
		Resources r = Tool.getContext().getResources();
		return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+"://"+r.getResourcePackageName(resId)+"/"+
			                 r.getResourceTypeName(resId)+"/"+r.getResourceEntryName(resId));
	}

	/**
	 * 从资源获取字符串
	 */
	public static String getString(int resId)
	{
		String string = Tool.getContext().getResources().getString(resId);
		if(RegTool.isEmpty(string))
		{
			return "";
		}
		else
		{
			return Tool.getContext().getResources().getString(resId);
		}
	}
}
