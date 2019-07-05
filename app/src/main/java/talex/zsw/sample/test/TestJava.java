package talex.zsw.sample.test;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
		Date now = new Date(System.currentTimeMillis());
		Format FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS ", Locale.getDefault());
		String format = FORMAT.format(now);
		String date = format.substring(0, 5);
		String hour = format.substring(6, 8);
		String ten = format.substring(9, 10);

		System.out.println("date = "+date+"  hour = "+hour+"  ten = "+ten);
	}
}
