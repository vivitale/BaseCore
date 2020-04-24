package talex.zsw.sample.test;

import java.math.BigInteger;

/**
 * 作用：测试Java方法
 * 作者：赵小白 email:vvtale@gmail.com  
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class TestJava
{
	public static void main(String[] args)
	{
		String code = "[A47.97.236.153,2347,1111,1,1,8208,2222,3333]";
		String init = "47.97.236.153,2347,1111,1,1,8208,2222,3333";
		if (init.isEmpty())
		{
			System.out.println( "0");
		}
		try {
			String[] arr= init.split(",");
			if (arr.length!=0){
				System.out.println( 	arr[arr.length-1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		Map<String, String> map = new HashMap<>();
//		map.put("ticket", "成人票");
//		map.put("price", "27.00");
//		StringSubstitutor stringSubstitutor = new StringSubstitutor(map);
//		String temp = "${ticket} 的价格是 ${price}";
//		String result = stringSubstitutor.replace(temp);
//		System.out.println(result);
	}

	/** 判断字符串是否是整数 */
	public static boolean isInteger(String value)
	{
		try
		{
			Integer.parseInt(value);
			return true;
		}
		catch(NumberFormatException e)
		{
			return false;
		}
	}

	/** 十进制转换成二进制 */
	public static String decimalToBinary(int decimalSource)
	{
		BigInteger bi = new BigInteger(String.valueOf(decimalSource));    //转换成BigInteger类型
		return bi.toString(2);    //参数2指定的是转化成X进制，默认10进制
	}
}
