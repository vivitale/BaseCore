package talex.zsw.basecore.util.glide;

import android.annotation.SuppressLint;

import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.request.RequestOptions;

/**
 * 作用: 为Glide添加新的方法
 * 作者: 赵小白 email:vvtale@gmail.com  
 * 日期: 2018/5/30 02:19 
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class CustomGlideExtension
{
	//缩略图的最小尺寸，单位：px
	private static final int MINI_THUMB_SIZE = 100;

	/**
	 * 将构造方法设为私有，作为工具类使用
	 */
	private CustomGlideExtension()
	{
	}

	/**
	 * 1.自己新增的方法的第一个参数必须是RequestOptions options
	 * 2.方法必须是静态的
	 */
	@SuppressLint("CheckResult") @GlideOption public static void miniThumb(RequestOptions options)
	{
		options.fitCenter().override(MINI_THUMB_SIZE);
	}
}
