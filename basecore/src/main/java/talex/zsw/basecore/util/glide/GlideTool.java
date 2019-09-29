package talex.zsw.basecore.util.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.util.concurrent.ExecutionException;

import talex.zsw.basecore.util.LogTool;
import talex.zsw.basecore.util.Tool;

/**
 * 作用: GlideTool
 * 作者: 赵小白 email:vvtale@gmail.com  
 * 日期: 2018/5/30 02:03 
 * 文档: https://muyangmin.github.io/glide-docs-cn/
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class GlideTool
{
	/**
	 * 加载图片
	 *
	 * @param v   ImageView
	 * @param url 图片URL
	 */
	public static void loadImg(ImageView v, String url)
	{
		LogTool.i("IMG", url);
		GlideApp
			.with(v.getContext())
			.load(url)
			.placeholder(new ColorDrawable(0xffF0F0F0))
			.error(new ColorDrawable(0xffF0F0F0))
			.fallback(new ColorDrawable(0xffF0F0F0))
			.transition(DrawableTransitionOptions.withCrossFade())
			.fitCenter()
			.into(v);
	}

	/**
	 * 加载图片,设置占位图
	 *
	 * @param v      ImageView
	 * @param url    图片URL
	 * @param holder 占位图
	 */
	public static void loadImg(ImageView v, String url, int holder)
	{
		LogTool.i("IMG", url);
		GlideApp
			.with(v.getContext())
			.load(url)
			.placeholder(holder)
			.error(holder)
			.fallback(holder)
			.transition(DrawableTransitionOptions.withCrossFade())
			.fitCenter()
			.into(v);
	}

	/**
	 * 加载图片,设置占位图
	 *
	 * @param v      ImageView
	 * @param url    图片URL
	 * @param holder 占位图
	 */
	public static void loadImg(ImageView v, String url, Drawable holder)
	{
		LogTool.i("IMG", url);
		GlideApp
			.with(v.getContext())
			.load(url)
			.placeholder(holder)
			.error(holder)
			.fallback(holder)
			.transition(DrawableTransitionOptions.withCrossFade())
			.fitCenter()
			.into(v);
	}

	/**
	 * 加载图片,重新调整图片大小
	 *
	 * @param v      ImageView
	 * @param url    图片URL
	 * @param width  图片的宽
	 * @param height 图片的高
	 */
	public static void loadImg(ImageView v, String url, int width, int height)
	{
		LogTool.i("IMG", url);
		GlideApp
			.with(v.getContext())
			.load(url)
			.placeholder(new ColorDrawable(0xffF0F0F0))
			.error(new ColorDrawable(0xffF0F0F0))
			.fallback(new ColorDrawable(0xffF0F0F0))
			.transition(DrawableTransitionOptions.withCrossFade())
			.override(width, height)
			.into(v);
	}

	/**
	 * 加载图片,为图片加入一些变化
	 *
	 * @param v              ImageView
	 * @param url            图片URL
	 * @param transformation 变化
	 */
	public static void loadImg(ImageView v, String url, Transformation<Bitmap> transformation)
	{
		LogTool.i("IMG", url);
		GlideApp
			.with(v.getContext())
			.asBitmap()
			.load(url)
			.placeholder(new ColorDrawable(0xffF0F0F0))
			.error(new ColorDrawable(0xffF0F0F0))
			.fallback(new ColorDrawable(0xffF0F0F0))
			.transition(BitmapTransitionOptions.withCrossFade())
			.transform(transformation)
			.into(v);
	}

	/**
	 * 图片可能不完整
	 * 缩放宽和高都到达View的边界，有一个参数在边界上，另一个参数可能在边界上，也可能超过边界
	 *
	 * @param v   ImageView
	 * @param url 图片URL
	 */
	public static void loadImgCenterCrop(ImageView v, String url)
	{
		LogTool.i("IMG", url);
		GlideApp
			.with(v.getContext())
			.load(url)
			.placeholder(new ColorDrawable(0xffF0F0F0))
			.error(new ColorDrawable(0xffF0F0F0))
			.fallback(new ColorDrawable(0xffF0F0F0))
			.transition(DrawableTransitionOptions.withCrossFade())
			.centerCrop()
			.into(v);
	}

	/**
	 * 图片完整
	 * 如果宽和高都在View的边界内，那就不缩放，否则缩放宽和高都进入View的边界，有一个参数在边界上，另一个参数可能在边界上，也可能在边界内
	 *
	 * @param v   ImageView
	 * @param url 图片URL
	 */
	public static void loadImgCenterInside(ImageView v, String url)
	{
		LogTool.i("IMG", url);
		GlideApp
			.with(v.getContext())
			.load(url)
			.placeholder(new ColorDrawable(0xffF0F0F0))
			.error(new ColorDrawable(0xffF0F0F0))
			.fallback(new ColorDrawable(0xffF0F0F0))
			.transition(DrawableTransitionOptions.withCrossFade())
			.centerInside()
			.into(v);
	}

	/**
	 * 图片完整
	 * 缩放宽和高都进入View的边界，有一个参数在边界上，另一个参数可能在边界上，也可能在边界内
	 *
	 * @param v   ImageView
	 * @param url 图片URL
	 */
	public static void loadImgFitCenter(ImageView v, String url)
	{
		LogTool.i("IMG", url);
		GlideApp
			.with(v.getContext())
			.load(url)
			.placeholder(new ColorDrawable(0xffF0F0F0))
			.error(new ColorDrawable(0xffF0F0F0))
			.fallback(new ColorDrawable(0xffF0F0F0))
			.transition(DrawableTransitionOptions.withCrossFade())
			.fitCenter()
			.into(v);
	}

	/**
	 * 加载圆形图片
	 *
	 * @param v   ImageView
	 * @param url 图片URL
	 */
	public static void loadImgCircleCrop(ImageView v, String url)
	{
		LogTool.i("IMG", url);
		GlideApp
			.with(v.getContext())
			.load(url)
			.transition(DrawableTransitionOptions.withCrossFade())
			.circleCrop()
			.into(v);
	}

	/**
	 * 加载圆形图片
	 *
	 * @param v      ImageView
	 * @param url    图片URL
	 * @param holder 占位图
	 */
	public static void loadImgCircleCrop(ImageView v, String url, int holder)
	{
		LogTool.i("IMG", url);
		GlideApp
			.with(v.getContext())
			.load(url)
			.placeholder(holder)
			.error(holder)
			.fallback(holder)
			.transition(DrawableTransitionOptions.withCrossFade())
			.circleCrop()
			.into(v);
	}

	/**
	 * 加载圆形图片
	 *
	 * @param v      ImageView
	 * @param url    图片URL
	 * @param holder 占位图
	 */
	public static void loadImgCircleCrop(ImageView v, String url, Drawable holder)
	{
		LogTool.i("IMG", url);
		GlideApp
			.with(v.getContext())
			.load(url)
			.placeholder(holder)
			.error(holder)
			.fallback(holder)
			.transition(DrawableTransitionOptions.withCrossFade())
			.circleCrop()
			.into(v);
	}

	/**
	 * 加载圆角图片
	 *
	 * @param v   ImageView
	 * @param url 图片URL
	 */
	public static void loadImgRoundedCorners(ImageView v, String url)
	{
		LogTool.i("IMG", url);
		GlideApp
			.with(v.getContext())
			.load(url)
			.placeholder(new ColorDrawable(0xffF0F0F0))
			.error(new ColorDrawable(0xffF0F0F0))
			.fallback(new ColorDrawable(0xffF0F0F0))
			.transition(DrawableTransitionOptions.withCrossFade())
			.transform(new RoundedCorners(11))
			.into(v);
	}

	/**
	 * 加载圆角图片
	 *
	 * @param v              ImageView
	 * @param url            图片URL
	 * @param roundingRadius 元角度数(px)
	 */
	public static void loadImgRoundedCorners(ImageView v, String url, int roundingRadius)
	{
		LogTool.i("IMG", url);
		GlideApp
			.with(v.getContext())
			.load(url)
			.placeholder(new ColorDrawable(0xffF0F0F0))
			.error(new ColorDrawable(0xffF0F0F0))
			.fallback(new ColorDrawable(0xffF0F0F0))
			.transition(DrawableTransitionOptions.withCrossFade())
			.transform(new RoundedCorners(roundingRadius))
			.into(v);
	}

	/**
	 * 加载圆角图片
	 *
	 * @param v              ImageView
	 * @param url            图片URL
	 * @param roundingRadius 元角度数(px)
	 */
	public static void loadImgRoundedCornersLeft(ImageView v, String url, int roundingRadius)
	{
		GlideCircleTransform transform
			= new GlideCircleTransform(v.getContext(), Color.TRANSPARENT, roundingRadius);
		transform.setExceptCorner(true, false, true, false);
		loadImg(v, url, transform);
	}

	/**
	 * 加载圆角图片
	 *
	 * @param v              ImageView
	 * @param url            图片URL
	 * @param roundingRadius 元角度数(px)
	 */
	public static void loadImgRoundedCornersRight(ImageView v, String url, int roundingRadius)
	{
		GlideCircleTransform transform
			= new GlideCircleTransform(v.getContext(), Color.TRANSPARENT, roundingRadius);
		transform.setExceptCorner(false, true, false, true);
		loadImg(v, url, transform);
	}

	/**
	 * 加载圆角图片
	 *
	 * @param v              ImageView
	 * @param url            图片URL
	 * @param roundingRadius 元角度数(px)
	 */
	public static void loadImgRoundedCornersTop(ImageView v, String url, int roundingRadius)
	{
		GlideCircleTransform transform
			= new GlideCircleTransform(v.getContext(), Color.TRANSPARENT, roundingRadius);
		transform.setExceptCorner(true, true, false, false);
		loadImg(v, url, transform);
	}

	/**
	 * 加载圆角图片
	 *
	 * @param v              ImageView
	 * @param url            图片URL
	 * @param roundingRadius 元角度数(px)
	 */
	public static void loadImgRoundedCornersBottom(ImageView v, String url, int roundingRadius)
	{
		GlideCircleTransform transform
			= new GlideCircleTransform(v.getContext(), Color.TRANSPARENT, roundingRadius);
		transform.setExceptCorner(false, false, true, true);
		loadImg(v, url, transform);
	}


	/**
	 * 加载图片并且不缓存
	 *
	 * @param v   ImageView
	 * @param url 图片URL
	 */
	public static void loadImgNoCache(ImageView v, String url)
	{
		LogTool.i("IMG", url);
		GlideApp
			.with(v.getContext())
			.load(url)
			.placeholder(new ColorDrawable(0xffF0F0F0))
			.error(new ColorDrawable(0xffF0F0F0))
			.fallback(new ColorDrawable(0xffF0F0F0))
			.transition(DrawableTransitionOptions.withCrossFade())
			.skipMemoryCache(true)
			.diskCacheStrategy(DiskCacheStrategy.NONE)
			.into(v);
	}

	/**
	 * 加载图片返回Bitmap
	 *
	 * @param context 上下文
	 * @param url     图片URL
	 * @param target  目标
	 */
	public static void loadImageSimpleTarget(Context context, String url, SimpleTarget<Bitmap> target)
	{
		GlideApp
			.with(context)
			.asBitmap()
			.load(url)
			.placeholder(new ColorDrawable(0xffF0F0F0))
			.error(new ColorDrawable(0xffF0F0F0))
			.fallback(new ColorDrawable(0xffF0F0F0))
			.transition(BitmapTransitionOptions.withCrossFade())
			.into(target);
	}

	public static Bitmap getBitmap(String url)
	{
		LogTool.i("IMG", url);
		Bitmap bitmap = null;
		try
		{
			bitmap = GlideApp
				.with(Tool.getContext())
				.asBitmap()
				.load(url)
				.centerCrop()
				.into(500, 500)
				.get();
		}
		catch(InterruptedException | ExecutionException e)
		{
			e.printStackTrace();
		}
		return bitmap;
	}

	public static String getBitmapPath(String url)
	{
		LogTool.i("IMG", url);
		String path = "";
		FutureTarget<File> future = GlideApp
			.with(Tool.getContext())
			.load(url)
			.downloadOnly(500, 500);
		try
		{
			File cacheFile = future.get();
			path = cacheFile.getAbsolutePath();
		}
		catch(InterruptedException | ExecutionException e)
		{
			e.printStackTrace();
		}
		return path;
	}

	/**
	 * 清空图片缓存
	 */
	public static void clean()
	{
		//磁盘缓存清理（子线程）
		GlideApp.get(Tool.getContext()).clearDiskCache();
		//内存缓存清理（主线程）
		GlideApp.get(Tool.getContext()).clearMemory();
	}
}
