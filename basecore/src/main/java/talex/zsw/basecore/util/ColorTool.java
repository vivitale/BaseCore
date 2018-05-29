package talex.zsw.basecore.util;

import android.graphics.Color;

/**
 * 作用: 颜色工具类
 * 作者: 赵小白 email:edisonzsw@icloud.com 
 * 日期: 2018/5/17 14:12 
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ColorTool
{

	/**
	 * 通过color的资源id获取Color的int
	 *
	 * @param id 资源id
	 */
	public static int getColorById(int id)
	{
		return Tool.getContext().getResources().getColor(id);
	}

	/**
	 * @param colorInt Color的Int
	 */
	public static int getColorByInt(int colorInt)
	{
		return colorInt | -16777216;
	}

	/**
	 * 修改颜色透明度
	 *
	 * @param color Color的Int
	 * @param alpha 透明度 0~255
	 */
	public static int changeColorAlpha(int color, int alpha)
	{
		int red = Color.red(color);
		int green = Color.green(color);
		int blue = Color.blue(color);

		return Color.argb(alpha, red, green, blue);
	}

	/**
	 * 获取透明度 0.0 ~ 1.0
	 *
	 * @param argb Color的Int
	 */
	public static float getAlphaPercent(int argb)
	{
		return Color.alpha(argb)/255f;
	}

	public static int alphaValueAsInt(float alpha)
	{
		return Math.round(alpha*255);
	}

	/**
	 * 调整颜色的透明度
	 *
	 * @param alpha 新的透明度 0.0 ~1.0
	 * @param color Color的Int
	 */
	public static int adjustAlpha(float alpha, int color)
	{
		return alphaValueAsInt(alpha) << 24 | (0x00ffffff & color);
	}

	/**
	 * 更加明亮的颜色
	 *
	 * @param color     Color的Int
	 * @param lightness 明亮度
	 */
	public static int colorAtLightness(int color, float lightness)
	{
		float[] hsv = new float[3];
		Color.colorToHSV(color, hsv);
		hsv[2] = lightness;
		return Color.HSVToColor(hsv);
	}

	/**
	 * 更加明亮的颜色
	 *
	 * @param color Color的Int
	 */
	public static float lightnessOfColor(int color)
	{
		float[] hsv = new float[3];
		Color.colorToHSV(color, hsv);
		return hsv[2];
	}

	/**
	 * 16进制的颜色值
	 *
	 * @param color     Color的Int
	 * @param showAlpha 是否显示透明度
	 */
	public static String getHexString(int color, boolean showAlpha)
	{
		int base = showAlpha ? 0xFFFFFFFF : 0xFFFFFF;
		String format = showAlpha ? "#%08X" : "#%06X";
		return String.format(format, (base & color)).toUpperCase();
	}
}
