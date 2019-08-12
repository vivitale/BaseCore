package talex.zsw.sample.test;

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
		String string = "一二三四五  - / abc 阿 ABC 哦";
		String string2 = "abc - ABC";
		System.out.println("原数据 : "+string);
		System.out.println("新数据 : "+string.replaceAll("[^A-Za-z]", ""));
		System.out.println("新数据 : "+string.replaceAll("[^A-Za-z -]", ""));
		System.out.println("新数据 : "+string.replaceAll("[A-Za-z]", ""));
		System.out.println("新数据 : "+string.replaceAll("[A-Za-z -]", ""));
		System.out.println(string+" : "+string.matches("[A-Za-z -]+"));
		System.out.println(string2+" : "+string2.matches("[A-Za-z -]+"));
	}
}
