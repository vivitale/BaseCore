package talex.zsw.basecore.util;

/**
 * 作用: 尺寸相关转换类
 * 作者: 赵小白 email:edisonzsw@icloud.com 
 * 日期: 2018/5/17 14:08 
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class DimenTool
{
	/**
	 * dip转px
	 *
	 * @param dpValue dp值
	 * @return px值
	 */
	public static int dip2px(float dpValue)
	{
		return dp2px(dpValue);
	}

	/**
	 * dp转px
	 *
	 * @param dpValue dp值
	 * @return px值
	 */
	public static int dp2px(float dpValue)
	{
		final float scale = Tool.getContext().getResources().getDisplayMetrics().density;
		return (int) (dpValue*scale+0.5f);
	}

	/**
	 * px转dip
	 *
	 * @param pxValue px值
	 * @return dip值
	 */
	public static int px2dip(float pxValue)
	{
		return px2dp(pxValue);
	}

	/**
	 * px转dp
	 *
	 * @param pxValue px值
	 * @return dp值
	 */
	public static int px2dp(float pxValue)
	{
		final float scale = Tool.getContext().getResources().getDisplayMetrics().density;
		return (int) (pxValue/scale+0.5f);
	}

	/**
	 * sp转px
	 *
	 * @param spValue sp值
	 * @return px值
	 */
	public static int sp2px(float spValue)
	{
		final float fontScale = Tool.getContext().getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue*fontScale+0.5f);
	}

	/**
	 * px转sp
	 *
	 * @param pxValue px值
	 * @return sp值
	 */
	public static int px2sp(float pxValue)
	{
		final float fontScale = Tool.getContext().getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue/fontScale+0.5f);
	}

	/**
	 * 从资源文件获取尺寸
	 *
	 * @param id 资源ID
	 * @return sp值
	 */
	public static int getPxById(int id)
	{
		return Tool.getContext().getResources().getDimensionPixelSize(id);
	}
}
