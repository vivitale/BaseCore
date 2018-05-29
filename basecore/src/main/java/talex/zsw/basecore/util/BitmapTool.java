package talex.zsw.basecore.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;

/**
 * 图像工具类
 */

public class BitmapTool
{

	/**
	 * 得到本地或者网络上的bitmap url - 网络或者本地图片的绝对路径,比如:
	 * <p/>
	 * A.网络路径: url="http://blog.foreverlove.us/girl2.png" ;
	 * <p/>
	 * B.本地路径:url="file://mnt/sdcard/photo/image.png";
	 * <p/>
	 * C.支持的图片格式 ,png, jpg,bmp,gif等等
	 *
	 * @param url
	 * @return
	 */
	public static Bitmap getLocalOrNetBitmap(String url)
	{
		Bitmap bitmap = null;
		InputStream in = null;
		BufferedOutputStream out = null;
		try
		{
			in = new BufferedInputStream(new URL(url).openStream(), 1024);
			final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
			out = new BufferedOutputStream(dataStream, 1024);
			copy(in, out);
			out.flush();
			byte[] data = dataStream.toByteArray();
			bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			data = null;
			return bitmap;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	private static void copy(InputStream in, OutputStream out) throws IOException
	{
		byte[] b = new byte[1024];
		int read;
		while((read = in.read(b)) != -1)
		{
			out.write(b, 0, read);
		}
	}

	/**
	 * bitmap转byteArr
	 *
	 * @param bitmap bitmap对象
	 * @param format 格式
	 * @return 字节数组
	 */
	public static byte[] bitmap2Bytes(Bitmap bitmap, CompressFormat format)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(format, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * byteArr转bitmap
	 *
	 * @param bytes 字节数组
	 * @return bitmap对象
	 */
	public static Bitmap bytes2Bitmap(byte[] bytes)
	{
		if(bytes.length != 0)
		{
			return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		}
		else
		{
			return null;
		}
	}

	/**
	 * drawable转bitmap
	 *
	 * @param drawable drawable对象
	 * @return bitmap对象
	 */
	public static Bitmap drawable2Bitmap(Drawable drawable)
	{
		// 取 drawable 的长宽
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();

		// 取 drawable 的颜色格式
		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 :
			Bitmap.Config.RGB_565;
		// 建立对应 bitmap
		Bitmap bitmap = Bitmap.createBitmap(w, h, config);
		// 建立对应 bitmap 的画布
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		// 把 drawable 内容画到画布中
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * bitmap转drawable
	 *
	 * @param res    resources对象
	 * @param bitmap bitmap对象
	 * @return drawable对象
	 */
	public static Drawable bitmap2Drawable(Resources res, Bitmap bitmap)
	{
		return new BitmapDrawable(res, bitmap);
	}

	public static Drawable bitmap2Drawable(Bitmap bitmap)
	{
		return new BitmapDrawable(bitmap);
	}

	/**
	 * drawable转byteArr
	 *
	 * @param drawable drawable对象
	 * @param format   格式
	 * @return 字节数组
	 */
	public static byte[] drawable2Bytes(Drawable drawable, CompressFormat format)
	{
		Bitmap bitmap = drawable2Bitmap(drawable);
		return bitmap2Bytes(bitmap, format);
	}

	/**
	 * byteArr转drawable
	 *
	 * @param res   resources对象
	 * @param bytes 字节数组
	 * @return drawable对象
	 */
	public static Drawable bytes2Drawable(Resources res, byte[] bytes)
	{
		Bitmap bitmap = bytes2Bitmap(bytes);
		Drawable drawable = bitmap2Drawable(res, bitmap);
		return drawable;
	}

	public static Drawable bytes2Drawable(byte[] bytes)
	{
		Bitmap bitmap = bytes2Bitmap(bytes);
		Drawable drawable = bitmap2Drawable(bitmap);
		return drawable;
	}

	/**
	 * 计算采样大小
	 *
	 * @param options   选项
	 * @param maxWidth  最大宽度
	 * @param maxHeight 最大高度
	 * @return 采样大小
	 */
	private static int calculateInSampleSize(BitmapFactory.Options options, int maxWidth, int maxHeight)
	{
		if(maxWidth == 0 || maxHeight == 0)
		{
			return 1;
		}
		int height = options.outHeight;
		int width = options.outWidth;
		int inSampleSize = 1;
		while((height >>= 1) >= maxHeight && (width >>= 1) >= maxWidth)
		{
			inSampleSize <<= 1;
		}
		return inSampleSize;
	}

	/**
	 * 获取bitmap
	 *
	 * @param file 文件
	 * @return bitmap
	 */
	public static Bitmap getBitmap(File file)
	{
		if(file == null)
		{
			return null;
		}
		InputStream is = null;
		try
		{
			is = new BufferedInputStream(new FileInputStream(file));
			return BitmapFactory.decodeStream(is);
		}
		catch(FileNotFoundException e)
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
	 * 获取bitmap
	 *
	 * @param file      文件
	 * @param maxWidth  最大宽度
	 * @param maxHeight 最大高度
	 * @return bitmap
	 */
	public static Bitmap getBitmap(File file, int maxWidth, int maxHeight)
	{
		if(file == null)
		{
			return null;
		}
		InputStream is = null;
		try
		{
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			is = new BufferedInputStream(new FileInputStream(file));
			BitmapFactory.decodeStream(is, null, options);
			options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
			options.inJustDecodeBounds = false;
			return BitmapFactory.decodeStream(is, null, options);
		}
		catch(FileNotFoundException e)
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
	 * 获取bitmap
	 *
	 * @param filePath 文件路径
	 * @return bitmap
	 */
	public static Bitmap getBitmap(String filePath)
	{
		if(RegTool.isNullString(filePath))
		{
			return null;
		}
		return BitmapFactory.decodeFile(filePath);
	}

	/**
	 * 获取bitmap
	 *
	 * @param filePath  文件路径
	 * @param maxWidth  最大宽度
	 * @param maxHeight 最大高度
	 * @return bitmap
	 */
	public static Bitmap getBitmap(String filePath, int maxWidth, int maxHeight)
	{
		if(RegTool.isNullString(filePath))
		{
			return null;
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(filePath, options);
	}

	/**
	 * 获取bitmap
	 *
	 * @param is        输入流
	 * @param maxWidth  最大宽度
	 * @param maxHeight 最大高度
	 * @return bitmap
	 */
	public static Bitmap getBitmap(InputStream is, int maxWidth, int maxHeight)
	{
		if(is == null)
		{
			return null;
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(is, null, options);
		options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeStream(is, null, options);
	}

	/**
	 * 获取bitmap
	 *
	 * @param data      数据
	 * @param offset    偏移量
	 * @param maxWidth  最大宽度
	 * @param maxHeight 最大高度
	 * @return bitmap
	 */
	public static Bitmap getBitmap(byte[] data, int offset, int maxWidth, int maxHeight)
	{
		if(data.length == 0)
		{
			return null;
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(data, offset, data.length, options);
		options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeByteArray(data, offset, data.length, options);
	}

	/**
	 * 获取bitmap
	 *
	 * @param resId 资源id
	 * @return bitmap
	 */
	public static Bitmap getBitmap(int resId)
	{
		if(Tool.getContext() == null)
		{
			return null;
		}
		InputStream is = Tool.getContext().getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is);
	}

	/**
	 * 获取bitmap
	 *
	 * @param resId     资源id
	 * @param maxWidth  最大宽度
	 * @param maxHeight 最大高度
	 * @return bitmap
	 */
	public static Bitmap getBitmap(int resId, int maxWidth, int maxHeight)
	{
		if(Tool.getContext() == null)
		{
			return null;
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		InputStream is = Tool.getContext().getResources().openRawResource(resId);
		BitmapFactory.decodeStream(is, null, options);
		options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeStream(is, null, options);
	}

	/**
	 * 获取bitmap
	 *
	 * @param res 资源对象
	 * @param id  资源id
	 * @return bitmap
	 */
	public static Bitmap getBitmap(Resources res, int id)
	{
		if(res == null)
		{
			return null;
		}
		return BitmapFactory.decodeResource(res, id);
	}

	/**
	 * 获取bitmap
	 *
	 * @param res       资源对象
	 * @param id        资源id
	 * @param maxWidth  最大宽度
	 * @param maxHeight 最大高度
	 * @return bitmap
	 */
	public static Bitmap getBitmap(Resources res, int id, int maxWidth, int maxHeight)
	{
		if(res == null)
		{
			return null;
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, id, options);
		options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, id, options);
	}

	/**
	 * 获取bitmap
	 *
	 * @param fd 文件描述
	 * @return bitmap
	 */
	public static Bitmap getBitmap(FileDescriptor fd)
	{
		if(fd == null)
		{
			return null;
		}
		return BitmapFactory.decodeFileDescriptor(fd);
	}

	/**
	 * 获取bitmap
	 *
	 * @param fd        文件描述
	 * @param maxWidth  最大宽度
	 * @param maxHeight 最大高度
	 * @return bitmap
	 */
	public static Bitmap getBitmap(FileDescriptor fd, int maxWidth, int maxHeight)
	{
		if(fd == null)
		{
			return null;
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFileDescriptor(fd, null, options);
		options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFileDescriptor(fd, null, options);
	}

	/**
	 * 缩放图片
	 *
	 * @param src       源图片
	 * @param newWidth  新宽度
	 * @param newHeight 新高度
	 * @return 缩放后的图片
	 */
	public static Bitmap scale(Bitmap src, int newWidth, int newHeight)
	{
		return scale(src, newWidth, newHeight, false);
	}

	/**
	 * 缩放图片
	 *
	 * @param src       源图片
	 * @param newWidth  新宽度
	 * @param newHeight 新高度
	 * @param recycle   是否回收
	 * @return 缩放后的图片
	 */
	public static Bitmap scale(Bitmap src, int newWidth, int newHeight, boolean recycle)
	{
		if(isEmptyBitmap(src))
		{
			return null;
		}
		Bitmap ret = Bitmap.createScaledBitmap(src, newWidth, newHeight, true);
		if(recycle && !src.isRecycled())
		{
			src.recycle();
		}
		return ret;
	}

	/**
	 * 缩放图片
	 *
	 * @param src         源图片
	 * @param scaleWidth  缩放宽度倍数
	 * @param scaleHeight 缩放高度倍数
	 * @return 缩放后的图片
	 */
	public static Bitmap scale(Bitmap src, float scaleWidth, float scaleHeight)
	{
		return scale(src, scaleWidth, scaleHeight, false);
	}

	/**
	 * 缩放图片
	 *
	 * @param src         源图片
	 * @param scaleWidth  缩放宽度倍数
	 * @param scaleHeight 缩放高度倍数
	 * @param recycle     是否回收
	 * @return 缩放后的图片
	 */
	public static Bitmap scale(Bitmap src, float scaleWidth, float scaleHeight, boolean recycle)
	{
		if(isEmptyBitmap(src))
		{
			return null;
		}
		Matrix matrix = new Matrix();
		matrix.setScale(scaleWidth, scaleHeight);
		Bitmap ret = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
		if(recycle && !src.isRecycled())
		{
			src.recycle();
		}
		return ret;
	}

	/**
	 * 裁剪图片
	 *
	 * @param src    源图片
	 * @param x      开始坐标x
	 * @param y      开始坐标y
	 * @param width  裁剪宽度
	 * @param height 裁剪高度
	 * @return 裁剪后的图片
	 */
	public static Bitmap clip(Bitmap src, int x, int y, int width, int height)
	{
		return clip(src, x, y, width, height, false);
	}

	/**
	 * 裁剪图片
	 *
	 * @param src     源图片
	 * @param x       开始坐标x
	 * @param y       开始坐标y
	 * @param width   裁剪宽度
	 * @param height  裁剪高度
	 * @param recycle 是否回收
	 * @return 裁剪后的图片
	 */
	public static Bitmap clip(Bitmap src, int x, int y, int width, int height, boolean recycle)
	{
		if(isEmptyBitmap(src))
		{
			return null;
		}
		Bitmap ret = Bitmap.createBitmap(src, x, y, width, height);
		if(recycle && !src.isRecycled())
		{
			src.recycle();
		}
		return ret;
	}

	/**
	 * 倾斜图片
	 *
	 * @param src 源图片
	 * @param kx  倾斜因子x
	 * @param ky  倾斜因子y
	 * @return 倾斜后的图片
	 */
	public static Bitmap skew(Bitmap src, float kx, float ky)
	{
		return skew(src, kx, ky, 0, 0, false);
	}

	/**
	 * 倾斜图片
	 *
	 * @param src     源图片
	 * @param kx      倾斜因子x
	 * @param ky      倾斜因子y
	 * @param recycle 是否回收
	 * @return 倾斜后的图片
	 */
	public static Bitmap skew(Bitmap src, float kx, float ky, boolean recycle)
	{
		return skew(src, kx, ky, 0, 0, recycle);
	}

	/**
	 * 倾斜图片
	 *
	 * @param src 源图片
	 * @param kx  倾斜因子x
	 * @param ky  倾斜因子y
	 * @param px  平移因子x
	 * @param py  平移因子y
	 * @return 倾斜后的图片
	 */
	public static Bitmap skew(Bitmap src, float kx, float ky, float px, float py)
	{
		return skew(src, kx, ky, 0, 0, false);
	}

	/**
	 * 倾斜图片
	 *
	 * @param src     源图片
	 * @param kx      倾斜因子x
	 * @param ky      倾斜因子y
	 * @param px      平移因子x
	 * @param py      平移因子y
	 * @param recycle 是否回收
	 * @return 倾斜后的图片
	 */
	public static Bitmap skew(Bitmap src, float kx, float ky, float px, float py, boolean recycle)
	{
		if(isEmptyBitmap(src))
		{
			return null;
		}
		Matrix matrix = new Matrix();
		matrix.setSkew(kx, ky, px, py);
		Bitmap ret = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
		if(recycle && !src.isRecycled())
		{
			src.recycle();
		}
		return ret;
	}

	/**
	 * 旋转图片
	 *
	 * @param src     源图片
	 * @param degrees 旋转角度
	 * @param px      旋转点横坐标
	 * @param py      旋转点纵坐标
	 * @return 旋转后的图片
	 */
	public static Bitmap rotate(Bitmap src, int degrees, float px, float py)
	{
		return rotate(src, degrees, px, py, false);
	}

	/**
	 * 旋转图片
	 *
	 * @param src     源图片
	 * @param degrees 旋转角度
	 * @param px      旋转点横坐标
	 * @param py      旋转点纵坐标
	 * @param recycle 是否回收
	 * @return 旋转后的图片
	 */
	public static Bitmap rotate(Bitmap src, int degrees, float px, float py, boolean recycle)
	{
		if(isEmptyBitmap(src))
		{
			return null;
		}
		if(degrees == 0)
		{
			return src;
		}
		Matrix matrix = new Matrix();
		matrix.setRotate(degrees, px, py);
		Bitmap ret = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
		if(recycle && !src.isRecycled())
		{
			src.recycle();
		}
		return ret;
	}

	/**
	 * 获取图片旋转角度
	 *
	 * @param filePath 文件路径
	 * @return 旋转角度
	 */
	public static int getRotateDegree(String filePath)
	{
		int degree = 0;
		try
		{
			ExifInterface exifInterface = new ExifInterface(filePath);
			int orientation
				= exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			switch(orientation)
			{
				default:
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return degree;
	}

	/**
	 * 转为圆形图片
	 *
	 * @param src 源图片
	 * @return 圆形图片
	 */
	public static Bitmap toRound(Bitmap src)
	{
		return toRound(src, false);
	}

	/**
	 * 转为圆形图片
	 *
	 * @param src     源图片
	 * @param recycle 是否回收
	 * @return 圆形图片
	 */
	public static Bitmap toRound(Bitmap src, boolean recycle)
	{
		if(isEmptyBitmap(src))
		{
			return null;
		}
		int width = src.getWidth();
		int height = src.getHeight();
		int radius = Math.min(width, height) >> 1;
		Bitmap ret = src.copy(src.getConfig(), true);
		Paint paint = new Paint();
		Canvas canvas = new Canvas(ret);
		Rect rect = new Rect(0, 0, width, height);
		paint.setAntiAlias(true);
		paint.setColor(Color.TRANSPARENT);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawARGB(0, 0, 0, 0);
		canvas.drawCircle(width >> 1, height >> 1, radius, paint);
		canvas.drawBitmap(src, rect, rect, paint);
		if(recycle && !src.isRecycled())
		{
			src.recycle();
		}
		return ret;
	}

	/**
	 * 转为圆角图片
	 *
	 * @param src    源图片
	 * @param radius 圆角的度数
	 * @return 圆角图片
	 */
	public static Bitmap toRoundCorner(Bitmap src, float radius)
	{
		return toRoundCorner(src, radius, false);
	}

	/**
	 * 转为圆角图片
	 *
	 * @param src     源图片
	 * @param radius  圆角的度数
	 * @param recycle 是否回收
	 * @return 圆角图片
	 */
	public static Bitmap toRoundCorner(Bitmap src, float radius, boolean recycle)
	{
		if(null == src)
		{
			return null;
		}
		int width = src.getWidth();
		int height = src.getHeight();
		Bitmap ret = src.copy(src.getConfig(), true);
		BitmapShader bitmapShader = new BitmapShader(src, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
		Paint paint = new Paint();
		Canvas canvas = new Canvas(ret);
		RectF rectf = new RectF(0, 0, width, height);
		paint.setAntiAlias(true);
		paint.setShader(bitmapShader);
		canvas.drawRoundRect(rectf, radius, radius, paint);
		if(recycle && !src.isRecycled())
		{
			src.recycle();
		}
		return ret;
	}

	/**
	 * 快速模糊
	 * <p>先缩小原图，对小图进行模糊，再放大回原先尺寸</p>
	 *
	 * @param src    源图片
	 * @param scale  缩小倍数(0...1)
	 * @param radius 模糊半径
	 * @return 模糊后的图片
	 */
	public static Bitmap fastBlur(Bitmap src, float scale, float radius)
	{
		return fastBlur(src, scale, radius, false);
	}

	/**
	 * 快速模糊
	 * <p>先缩小原图，对小图进行模糊，再放大回原先尺寸</p>
	 *
	 * @param src     源图片
	 * @param scale   缩小倍数(0...1)
	 * @param radius  模糊半径
	 * @param recycle 是否回收
	 * @return 模糊后的图片
	 */
	public static Bitmap fastBlur(Bitmap src, float scale, float radius, boolean recycle)
	{
		if(isEmptyBitmap(src))
		{
			return null;
		}
		int width = src.getWidth();
		int height = src.getHeight();
		int scaleWidth = (int) (width*scale+0.5f);
		int scaleHeight = (int) (height*scale+0.5f);
		if(scaleWidth == 0 || scaleHeight == 0)
		{
			return null;
		}
		Bitmap scaleBitmap = Bitmap.createScaledBitmap(src, scaleWidth, scaleHeight, true);
		Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG | Paint.ANTI_ALIAS_FLAG);
		Canvas canvas = new Canvas();
		PorterDuffColorFilter filter = new PorterDuffColorFilter(Color.TRANSPARENT, PorterDuff.Mode.SRC_ATOP);
		paint.setColorFilter(filter);
		canvas.scale(scale, scale);
		canvas.drawBitmap(scaleBitmap, 0, 0, paint);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
		{
			scaleBitmap = renderScriptBlur(scaleBitmap, radius);
		}
		else
		{
			scaleBitmap = stackBlur(scaleBitmap, (int) radius, true);
		}
		if(scale == 1)
		{
			return scaleBitmap;
		}
		Bitmap ret = Bitmap.createScaledBitmap(scaleBitmap, width, height, true);
		if(scaleBitmap != null && !scaleBitmap.isRecycled())
		{
			scaleBitmap.recycle();
		}
		if(recycle && !src.isRecycled())
		{
			src.recycle();
		}
		return ret;
	}

	/**
	 * renderScript模糊图片
	 * <p>API大于17</p>
	 *
	 * @param src    源图片
	 * @param radius 模糊度(0...25)
	 * @return 模糊后的图片
	 */
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1) public static Bitmap renderScriptBlur(Bitmap src, float radius)
	{
		if(isEmptyBitmap(src))
		{
			return null;
		}
		RenderScript rs = null;
		try
		{
			rs = RenderScript.create(Tool.getContext());
			rs.setMessageHandler(new RenderScript.RSMessageHandler());
			Allocation input
				= Allocation.createFromBitmap(rs, src, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
			Allocation output = Allocation.createTyped(rs, input.getType());
			ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
			if(radius > 25)
			{
				radius = 25.0f;
			}
			else if(radius <= 0)
			{
				radius = 1.0f;
			}
			blurScript.setInput(input);
			blurScript.setRadius(radius);
			blurScript.forEach(output);
			output.copyTo(src);
		}
		finally
		{
			if(rs != null)
			{
				rs.destroy();
			}
		}
		return src;
	}

	/**
	 * stack模糊图片
	 *
	 * @param src     源图片
	 * @param radius  模糊半径
	 * @param recycle 是否回收
	 * @return stackBlur模糊图片
	 */
	public static Bitmap stackBlur(Bitmap src, int radius, boolean recycle)
	{
		Bitmap ret;
		if(recycle)
		{
			ret = src;
		}
		else
		{
			ret = src.copy(src.getConfig(), true);
		}

		if(radius < 1)
		{
			return (null);
		}

		int w = ret.getWidth();
		int h = ret.getHeight();

		int[] pix = new int[w*h];
		ret.getPixels(pix, 0, w, 0, 0, w, h);

		int wm = w-1;
		int hm = h-1;
		int wh = w*h;
		int div = radius+radius+1;

		int r[] = new int[wh];
		int g[] = new int[wh];
		int b[] = new int[wh];
		int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
		int vmin[] = new int[Math.max(w, h)];

		int divsum = (div+1) >> 1;
		divsum *= divsum;
		int dv[] = new int[256*divsum];
		for(i = 0; i < 256*divsum; i++)
		{
			dv[i] = (i/divsum);
		}

		yw = yi = 0;

		int[][] stack = new int[div][3];
		int stackpointer;
		int stackstart;
		int[] sir;
		int rbs;
		int r1 = radius+1;
		int routsum, goutsum, boutsum;
		int rinsum, ginsum, binsum;

		for(y = 0; y < h; y++)
		{
			rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
			for(i = -radius; i <= radius; i++)
			{
				p = pix[yi+Math.min(wm, Math.max(i, 0))];
				sir = stack[i+radius];
				sir[0] = (p & 0xff0000) >> 16;
				sir[1] = (p & 0x00ff00) >> 8;
				sir[2] = (p & 0x0000ff);
				rbs = r1-Math.abs(i);
				rsum += sir[0]*rbs;
				gsum += sir[1]*rbs;
				bsum += sir[2]*rbs;
				if(i > 0)
				{
					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];
				}
				else
				{
					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];
				}
			}
			stackpointer = radius;

			for(x = 0; x < w; x++)
			{

				r[yi] = dv[rsum];
				g[yi] = dv[gsum];
				b[yi] = dv[bsum];

				rsum -= routsum;
				gsum -= goutsum;
				bsum -= boutsum;

				stackstart = stackpointer-radius+div;
				sir = stack[stackstart%div];

				routsum -= sir[0];
				goutsum -= sir[1];
				boutsum -= sir[2];

				if(y == 0)
				{
					vmin[x] = Math.min(x+radius+1, wm);
				}
				p = pix[yw+vmin[x]];

				sir[0] = (p & 0xff0000) >> 16;
				sir[1] = (p & 0x00ff00) >> 8;
				sir[2] = (p & 0x0000ff);

				rinsum += sir[0];
				ginsum += sir[1];
				binsum += sir[2];

				rsum += rinsum;
				gsum += ginsum;
				bsum += binsum;

				stackpointer = (stackpointer+1)%div;
				sir = stack[(stackpointer)%div];

				routsum += sir[0];
				goutsum += sir[1];
				boutsum += sir[2];

				rinsum -= sir[0];
				ginsum -= sir[1];
				binsum -= sir[2];

				yi++;
			}
			yw += w;
		}
		for(x = 0; x < w; x++)
		{
			rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
			yp = -radius*w;
			for(i = -radius; i <= radius; i++)
			{
				yi = Math.max(0, yp)+x;

				sir = stack[i+radius];

				sir[0] = r[yi];
				sir[1] = g[yi];
				sir[2] = b[yi];

				rbs = r1-Math.abs(i);

				rsum += r[yi]*rbs;
				gsum += g[yi]*rbs;
				bsum += b[yi]*rbs;

				if(i > 0)
				{
					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];
				}
				else
				{
					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];
				}

				if(i < hm)
				{
					yp += w;
				}
			}
			yi = x;
			stackpointer = radius;
			for(y = 0; y < h; y++)
			{
				// Preserve alpha channel: ( 0xff000000 & pix[yi] )
				pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

				rsum -= routsum;
				gsum -= goutsum;
				bsum -= boutsum;

				stackstart = stackpointer-radius+div;
				sir = stack[stackstart%div];

				routsum -= sir[0];
				goutsum -= sir[1];
				boutsum -= sir[2];

				if(x == 0)
				{
					vmin[y] = Math.min(y+r1, hm)*w;
				}
				p = x+vmin[y];

				sir[0] = r[p];
				sir[1] = g[p];
				sir[2] = b[p];

				rinsum += sir[0];
				ginsum += sir[1];
				binsum += sir[2];

				rsum += rinsum;
				gsum += ginsum;
				bsum += binsum;

				stackpointer = (stackpointer+1)%div;
				sir = stack[stackpointer];

				routsum += sir[0];
				goutsum += sir[1];
				boutsum += sir[2];

				rinsum -= sir[0];
				ginsum -= sir[1];
				binsum -= sir[2];

				yi += w;
			}
		}
		ret.setPixels(pix, 0, w, 0, 0, w, h);
		return (ret);
	}

	/**
	 * 添加颜色边框
	 *
	 * @param src         源图片
	 * @param borderWidth 边框宽度
	 * @param color       边框的颜色值
	 * @return 带颜色边框图
	 */
	public static Bitmap addFrame(Bitmap src, int borderWidth, int color)
	{
		return addFrame(src, borderWidth, color);
	}

	/**
	 * 添加颜色边框
	 *
	 * @param src         源图片
	 * @param borderWidth 边框宽度
	 * @param color       边框的颜色值
	 * @param recycle     是否回收
	 * @return 带颜色边框图
	 */
	public static Bitmap addFrame(Bitmap src, int borderWidth, int color, boolean recycle)
	{
		if(isEmptyBitmap(src))
		{
			return null;
		}
		int newWidth = src.getWidth()+borderWidth >> 1;
		int newHeight = src.getHeight()+borderWidth >> 1;
		Bitmap ret = Bitmap.createBitmap(newWidth, newHeight, src.getConfig());
		Canvas canvas = new Canvas(ret);
		Rect rec = canvas.getClipBounds();
		Paint paint = new Paint();
		paint.setColor(color);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(borderWidth);
		canvas.drawRect(rec, paint);
		canvas.drawBitmap(src, borderWidth, borderWidth, null);
		if(recycle && !src.isRecycled())
		{
			src.recycle();
		}
		return ret;
	}

	/**
	 * 添加倒影
	 *
	 * @param src              源图片的
	 * @param reflectionHeight 倒影高度
	 * @return 带倒影图片
	 */
	public static Bitmap addReflection(Bitmap src, int reflectionHeight)
	{
		return addReflection(src, reflectionHeight, false);
	}

	/**
	 * 添加倒影
	 *
	 * @param src              源图片的
	 * @param reflectionHeight 倒影高度
	 * @param recycle          是否回收
	 * @return 带倒影图片
	 */
	public static Bitmap addReflection(Bitmap src, int reflectionHeight, boolean recycle)
	{
		if(isEmptyBitmap(src))
		{
			return null;
		}
		final int REFLECTION_GAP = 0;
		int srcWidth = src.getWidth();
		int srcHeight = src.getHeight();
		if(0 == srcWidth || srcHeight == 0)
		{
			return null;
		}
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);
		Bitmap reflectionBitmap = Bitmap.createBitmap(src, 0, srcHeight-
			reflectionHeight, srcWidth, reflectionHeight, matrix, false);
		if(null == reflectionBitmap)
		{
			return null;
		}
		Bitmap ret = Bitmap.createBitmap(srcWidth, srcHeight+reflectionHeight, src.getConfig());
		Canvas canvas = new Canvas(ret);
		canvas.drawBitmap(src, 0, 0, null);
		canvas.drawBitmap(reflectionBitmap, 0, srcHeight+REFLECTION_GAP, null);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		LinearGradient shader = new LinearGradient(0, srcHeight, 0, ret.getHeight()+
			REFLECTION_GAP, 0x70FFFFFF, 0x00FFFFFF, Shader.TileMode.MIRROR);
		paint.setShader(shader);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		canvas.save();
		canvas.drawRect(0, srcHeight, srcWidth, ret.getHeight()+REFLECTION_GAP, paint);
		canvas.restore();
		if(!reflectionBitmap.isRecycled())
		{
			reflectionBitmap.recycle();
		}
		if(recycle && !src.isRecycled())
		{
			src.recycle();
		}
		return ret;
	}

	/**
	 * 添加文字水印
	 *
	 * @param src      源图片
	 * @param content  水印文本
	 * @param textSize 水印字体大小
	 * @param color    水印字体颜色
	 * @param alpha    水印字体透明度
	 * @param x        起始坐标x
	 * @param y        起始坐标y
	 * @return 带有文字水印的图片
	 */
	public static Bitmap addTextWatermark(Bitmap src, String content, int textSize, int color, int alpha, float x, float y)
	{
		return addTextWatermark(src, content, textSize, color, alpha, x, y, false);
	}

	/**
	 * 添加文字水印
	 *
	 * @param src      源图片
	 * @param content  水印文本
	 * @param textSize 水印字体大小
	 * @param color    水印字体颜色
	 * @param alpha    水印字体透明度
	 * @param x        起始坐标x
	 * @param y        起始坐标y
	 * @param recycle  是否回收
	 * @return 带有文字水印的图片
	 */
	public static Bitmap addTextWatermark(Bitmap src, String content, int textSize, int color, int alpha, float x, float y, boolean recycle)
	{
		if(isEmptyBitmap(src) || content == null)
		{
			return null;
		}
		Bitmap ret = src.copy(src.getConfig(), true);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		Canvas canvas = new Canvas(ret);
		paint.setAlpha(alpha);
		paint.setColor(color);
		paint.setTextSize(textSize);
		Rect bounds = new Rect();
		paint.getTextBounds(content, 0, content.length(), bounds);
		canvas.drawText(content, x, y, paint);
		if(recycle && !src.isRecycled())
		{
			src.recycle();
		}
		return ret;
	}

	/**
	 * 添加图片水印
	 *
	 * @param src       源图片
	 * @param watermark 图片水印
	 * @param x         起始坐标x
	 * @param y         起始坐标y
	 * @param alpha     透明度
	 * @return 带有图片水印的图片
	 */
	public static Bitmap addImageWatermark(Bitmap src, Bitmap watermark, int x, int y, int alpha)
	{
		return addImageWatermark(src, watermark, x, y, alpha, false);
	}

	/**
	 * 添加图片水印
	 *
	 * @param src       源图片
	 * @param watermark 图片水印
	 * @param x         起始坐标x
	 * @param y         起始坐标y
	 * @param alpha     透明度
	 * @param recycle   是否回收
	 * @return 带有图片水印的图片
	 */
	public static Bitmap addImageWatermark(Bitmap src, Bitmap watermark, int x, int y, int alpha, boolean recycle)
	{
		if(isEmptyBitmap(src))
		{
			return null;
		}
		Bitmap ret = src.copy(src.getConfig(), true);
		if(!isEmptyBitmap(watermark))
		{
			Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
			Canvas canvas = new Canvas(ret);
			paint.setAlpha(alpha);
			canvas.drawBitmap(watermark, x, y, paint);
		}
		if(recycle && !src.isRecycled())
		{
			src.recycle();
		}
		return ret;
	}

	/**
	 * 转为alpha位图
	 *
	 * @param src 源图片
	 * @return alpha位图
	 */
	public static Bitmap toAlpha(Bitmap src)
	{
		return toAlpha(src);
	}

	/**
	 * 转为alpha位图
	 *
	 * @param src     源图片
	 * @param recycle 是否回收
	 * @return alpha位图
	 */
	public static Bitmap toAlpha(Bitmap src, Boolean recycle)
	{
		if(isEmptyBitmap(src))
		{
			return null;
		}
		Bitmap ret = src.extractAlpha();
		if(recycle && !src.isRecycled())
		{
			src.recycle();
		}
		return ret;
	}

	/**
	 * 可以对该图的非透明区域着色
	 * <p>
	 * 有多种使用场景，常见如 Button 的 pressed 状态，View 的阴影状态等
	 *
	 * @param iv
	 * @param src
	 * @param radius
	 * @param color
	 * @return
	 */
	private static Bitmap getDropShadow(ImageView iv, Bitmap src, float radius, int color)
	{

		final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(color);

		final int width = src.getWidth(), height = src.getHeight();
		final Bitmap dest = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		final Canvas canvas = new Canvas(dest);
		final Bitmap alpha = src.extractAlpha();
		canvas.drawBitmap(alpha, 0, 0, paint);

		final BlurMaskFilter filter = new BlurMaskFilter(radius, BlurMaskFilter.Blur.OUTER);
		paint.setMaskFilter(filter);
		canvas.drawBitmap(alpha, 0, 0, paint);
		iv.setImageBitmap(dest);

		return dest;
	}

	/**
	 * 转为灰度图片
	 *
	 * @param src 源图片
	 * @return 灰度图
	 */
	public static Bitmap toGray(Bitmap src)
	{
		return toGray(src, false);
	}

	/**
	 * 转为灰度图片
	 *
	 * @param src     源图片
	 * @param recycle 是否回收
	 * @return 灰度图
	 */
	public static Bitmap toGray(Bitmap src, boolean recycle)
	{
		if(isEmptyBitmap(src))
		{
			return null;
		}
		Bitmap grayBitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(grayBitmap);
		Paint paint = new Paint();
		ColorMatrix colorMatrix = new ColorMatrix();
		colorMatrix.setSaturation(0);
		ColorMatrixColorFilter colorMatrixColorFilter = new ColorMatrixColorFilter(colorMatrix);
		paint.setColorFilter(colorMatrixColorFilter);
		canvas.drawBitmap(src, 0, 0, paint);
		if(recycle && !src.isRecycled())
		{
			src.recycle();
		}
		return grayBitmap;
	}

	/**
	 * 保存图片
	 *
	 * @param src      源图片
	 * @param filePath 要保存到的文件路径
	 * @param format   格式
	 * @return {@code true}: 成功<br>{@code false}: 失败
	 */
	public static boolean save(Bitmap src, String filePath, CompressFormat format)
	{
		return save(src, FileTool.getFileByPath(filePath), format, false);
	}

	/**
	 * 保存图片
	 *
	 * @param src    源图片
	 * @param file   要保存到的文件
	 * @param format 格式
	 * @return {@code true}: 成功<br>{@code false}: 失败
	 */
	public static boolean save(Bitmap src, File file, CompressFormat format)
	{
		return save(src, file, format, false);
	}

	/**
	 * 保存图片
	 *
	 * @param src      源图片
	 * @param filePath 要保存到的文件路径
	 * @param format   格式
	 * @param recycle  是否回收
	 * @return {@code true}: 成功<br>{@code false}: 失败
	 */
	public static boolean save(Bitmap src, String filePath, CompressFormat format, boolean recycle)
	{
		return save(src, FileTool.getFileByPath(filePath), format, recycle);
	}

	/**
	 * 保存图片
	 *
	 * @param src     源图片
	 * @param file    要保存到的文件
	 * @param format  格式
	 * @param recycle 是否回收
	 * @return {@code true}: 成功<br>{@code false}: 失败
	 */
	public static boolean save(Bitmap src, File file, CompressFormat format, boolean recycle)
	{
		if(isEmptyBitmap(src) || !FileTool.createOrExistsFile(file))
		{
			return false;
		}
		System.out.println(src.getWidth()+", "+src.getHeight());
		OutputStream os = null;
		boolean ret = false;
		try
		{
			os = new BufferedOutputStream(new FileOutputStream(file));
			ret = src.compress(format, 100, os);
			if(recycle && !src.isRecycled())
			{
				src.recycle();
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			FileTool.closeIO(os);
		}
		return ret;
	}

	/**
	 * 根据文件名判断文件是否为图片
	 *
	 * @param file 　文件
	 * @return {@code true}: 是<br>{@code false}: 否
	 */
	public static boolean isImage(File file)
	{
		return file != null && isImage(file.getPath());
	}

	/**
	 * 根据文件名判断文件是否为图片
	 *
	 * @param filePath 　文件路径
	 * @return {@code true}: 是<br>{@code false}: 否
	 */
	public static boolean isImage(String filePath)
	{
		String path = filePath.toUpperCase();
		return path.endsWith(".PNG") || path.endsWith(".JPG") || path.endsWith(".JPEG") || path.endsWith(".BMP") ||
			path.endsWith(".GIF");
	}

	/**
	 * 获取图片类型
	 *
	 * @param filePath 文件路径
	 * @return 图片类型
	 */
	public static String getImageType(String filePath)
	{
		return getImageType(FileTool.getFileByPath(filePath));
	}

	/**
	 * 获取图片类型
	 *
	 * @param file 文件
	 * @return 图片类型
	 */
	public static String getImageType(File file)
	{
		if(file == null)
		{
			return null;
		}
		InputStream is = null;
		try
		{
			is = new FileInputStream(file);
			return getImageType(is);
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
	 * 流获取图片类型
	 *
	 * @param is 图片输入流
	 * @return 图片类型
	 */
	public static String getImageType(InputStream is)
	{
		if(is == null)
		{
			return null;
		}
		try
		{
			byte[] bytes = new byte[8];
			return is.read(bytes, 0, 8) != -1 ? getImageType(bytes) : null;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取图片类型
	 *
	 * @param bytes bitmap的前8字节
	 * @return 图片类型
	 */
	public static String getImageType(byte[] bytes)
	{
		if(isJPEG(bytes))
		{
			return "JPEG";
		}
		if(isGIF(bytes))
		{
			return "GIF";
		}
		if(isPNG(bytes))
		{
			return "PNG";
		}
		if(isBMP(bytes))
		{
			return "BMP";
		}
		return null;
	}

	private static boolean isJPEG(byte[] b)
	{
		return b.length >= 2 && (b[0] == (byte) 0xFF) && (b[1] == (byte) 0xD8);
	}

	private static boolean isGIF(byte[] b)
	{
		return b.length >= 6 && b[0] == 'G' && b[1] == 'I' && b[2] == 'F' && b[3] == '8' &&
			(b[4] == '7' || b[4] == '9') && b[5] == 'a';
	}

	private static boolean isPNG(byte[] b)
	{
		return b.length >= 8 &&
			(b[0] == (byte) 137 && b[1] == (byte) 80 && b[2] == (byte) 78 && b[3] == (byte) 71 && b[4] == (byte) 13 &&
				b[5] == (byte) 10 && b[6] == (byte) 26 && b[7] == (byte) 10);
	}

	private static boolean isBMP(byte[] b)
	{
		return b.length >= 2 && (b[0] == 0x42) && (b[1] == 0x4d);
	}

	/**
	 * 判断bitmap对象是否为空
	 *
	 * @param src 源图片
	 * @return {@code true}: 是<br>{@code false}: 否
	 */
	private static boolean isEmptyBitmap(Bitmap src)
	{
		return src == null || src.getWidth() == 0 || src.getHeight() == 0;
	}

	/**
	 * 按缩放压缩
	 *
	 * @param src       源图片
	 * @param newWidth  新宽度
	 * @param newHeight 新高度
	 * @return 缩放压缩后的图片
	 */
	public static Bitmap compressByScale(Bitmap src, int newWidth, int newHeight)
	{
		return scale(src, newWidth, newHeight, false);
	}

	/**
	 * 按缩放压缩
	 *
	 * @param src       源图片
	 * @param newWidth  新宽度
	 * @param newHeight 新高度
	 * @param recycle   是否回收
	 * @return 缩放压缩后的图片
	 */
	public static Bitmap compressByScale(Bitmap src, int newWidth, int newHeight, boolean recycle)
	{
		return scale(src, newWidth, newHeight, recycle);
	}

	/******************************~~~~~~~~~ 下方和压缩有关 ~~~~~~~~~******************************/

	/**
	 * 按缩放压缩
	 *
	 * @param src         源图片
	 * @param scaleWidth  缩放宽度倍数
	 * @param scaleHeight 缩放高度倍数
	 * @return 缩放压缩后的图片
	 */
	public static Bitmap compressByScale(Bitmap src, float scaleWidth, float scaleHeight)
	{
		return scale(src, scaleWidth, scaleHeight, false);
	}

	/**
	 * 按缩放压缩
	 *
	 * @param src         源图片
	 * @param scaleWidth  缩放宽度倍数
	 * @param scaleHeight 缩放高度倍数
	 * @param recycle     是否回收
	 * @return 缩放压缩后的图片
	 */
	public static Bitmap compressByScale(Bitmap src, float scaleWidth, float scaleHeight, boolean recycle)
	{
		return scale(src, scaleWidth, scaleHeight, recycle);
	}

	/**
	 * 按质量压缩
	 *
	 * @param src     源图片
	 * @param quality 质量
	 * @return 质量压缩后的图片
	 */
	public static Bitmap compressByQuality(Bitmap src, int quality)
	{
		return compressByQuality(src, quality, false);
	}

	/**
	 * 按质量压缩
	 *
	 * @param src     源图片
	 * @param quality 质量
	 * @param recycle 是否回收
	 * @return 质量压缩后的图片
	 */
	public static Bitmap compressByQuality(Bitmap src, int quality, boolean recycle)
	{
		if(isEmptyBitmap(src) || quality < 0 || quality > 100)
		{
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		src.compress(CompressFormat.JPEG, quality, baos);
		byte[] bytes = baos.toByteArray();
		if(recycle && !src.isRecycled())
		{
			src.recycle();
		}
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	}

	/**
	 * 按质量压缩
	 *
	 * @param src         源图片
	 * @param maxByteSize 允许最大值字节数
	 * @return 质量压缩压缩过的图片
	 */
	public static Bitmap compressByQuality(Bitmap src, long maxByteSize)
	{
		return compressByQuality(src, maxByteSize, false);
	}

	/**
	 * 按质量压缩
	 *
	 * @param src         源图片
	 * @param maxByteSize 允许最大值字节数
	 * @param recycle     是否回收
	 * @return 质量压缩压缩过的图片
	 */
	public static Bitmap compressByQuality(Bitmap src, long maxByteSize, boolean recycle)
	{
		if(isEmptyBitmap(src) || maxByteSize <= 0)
		{
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int quality = 100;
		src.compress(CompressFormat.JPEG, quality, baos);
		while(baos.toByteArray().length > maxByteSize && quality >= 0)
		{
			baos.reset();
			src.compress(CompressFormat.JPEG, quality -= 5, baos);
		}
		if(quality < 0)
		{
			return null;
		}
		byte[] bytes = baos.toByteArray();
		if(recycle && !src.isRecycled())
		{
			src.recycle();
		}
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	}

	/**
	 * 按采样大小压缩
	 *
	 * @param src        源图片
	 * @param sampleSize 采样率大小
	 * @return 按采样率压缩后的图片
	 */
	public static Bitmap compressBySampleSize(Bitmap src, int sampleSize)
	{
		return compressBySampleSize(src, sampleSize, false);
	}

	/**
	 * 按采样大小压缩
	 *
	 * @param src        源图片
	 * @param sampleSize 采样率大小
	 * @param recycle    是否回收
	 * @return 按采样率压缩后的图片
	 */
	public static Bitmap compressBySampleSize(Bitmap src, int sampleSize, boolean recycle)
	{
		if(isEmptyBitmap(src))
		{
			return null;
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = sampleSize;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		src.compress(CompressFormat.JPEG, 100, baos);
		byte[] bytes = baos.toByteArray();
		if(recycle && !src.isRecycled())
		{
			src.recycle();
		}
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
	}

	/**
	 * 缩略图工具类，
	 * 可以根据本地视频文件源、
	 * Bitmap 对象生成缩略图
	 *
	 * @param filePath
	 * @param kind
	 * @return
	 */
	public static Bitmap getThumb(String filePath, int kind)
	{
		return ThumbnailUtils.createVideoThumbnail(filePath, kind);
	}

	/**
	 * 缩略图工具
	 */
	public static Bitmap getThumb(Bitmap source, int width, int height)
	{
		return ThumbnailUtils.extractThumbnail(source, width, height);
	}

	/**
	 * 缩放图片
	 *
	 * @param bgimage   图片
	 * @param newWidth  宽度
	 * @param newHeight 高度
	 * @return 缩放之后的图片引用
	 */
	public static Bitmap zoomImage(Bitmap bgimage, double newWidth, double newHeight)
	{
		// 获取到bitmap的宽
		float width = bgimage.getWidth();

		float height = bgimage.getHeight();
		//
		Matrix matrix = new Matrix();
		// 设置尺寸
		float scaleWidth = ((float) newWidth)/width;
		float scaleHeight = ((float) newHeight)/height;

		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width, (int) height, matrix, true);
		Log.e("tag", bitmap.getHeight()+bitmap.getWidth()+"d");
		return bitmap;
	}

	/**
	 * 缩放图片
	 *
	 * @param bitmap 图片引用
	 * @param width  宽度
	 * @param height 高度
	 * @return 缩放之后的图片引用
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height)
	{
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) width/w);
		float scaleHeight = ((float) height/h);
		matrix.postScale(scaleWidth, scaleHeight);
		return Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
	}

	/**
	 * 获取bitmap
	 *
	 * @param is 输入流
	 * @return bitmap
	 */
	public Bitmap getBitmap(InputStream is)
	{
		if(is == null)
		{
			return null;
		}
		return BitmapFactory.decodeStream(is);
	}

	/**
	 * 获取bitmap
	 *
	 * @param data   数据
	 * @param offset 偏移量
	 * @return bitmap
	 */
	public Bitmap getBitmap(byte[] data, int offset)
	{
		if(data.length == 0)
		{
			return null;
		}
		return BitmapFactory.decodeByteArray(data, offset, data.length);
	}

	// ----------------------------------------------------------------------

	/**
	 * 下载图片
	 *
	 * @param url 图片地址
	 */
	public static Bitmap getBitmapByUrl(String url)
	{
		Bitmap bitmap = null;
		try
		{
			URL url2 = new URL(url);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url2.openConnection();
			httpURLConnection.setReadTimeout(3000);
			int code = httpURLConnection.getResponseCode();
			if(code == 200)
			{
				bitmap = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * bitmap转为base64
	 *
	 * @param bitmap 图片
	 */
	public static String bitmap2Base64(Bitmap bitmap)
	{

		String result = null;
		ByteArrayOutputStream baos = null;
		try
		{
			if(bitmap != null)
			{
				baos = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

				baos.flush();
				baos.close();

				byte[] bitmapBytes = baos.toByteArray();
				result = Base64.encodeToString(bitmapBytes, Base64.NO_WRAP);
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(baos != null)
				{
					baos.flush();
					baos.close();
				}
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		result = result.replaceAll("\\u003d", "=");
		return result;
	}

	/**
	 * base64转为bitmap
	 *
	 * @param base64Data base64的图片字符串
	 */
	public static Bitmap base642Bitmap(String base64Data)
	{
		byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	}

	/**
	 * 毛玻璃效果
	 *
	 * @param context    上下文
	 * @param sentBitmap 要添加毛玻璃效果的图片
	 * @param radius     毛玻璃的程度
	 * @return 毛玻璃图片
	 */
	@SuppressLint("NewApi")
	public static Bitmap fastBlur(Context context, Bitmap sentBitmap, int radius)
	{
		if(Build.VERSION.SDK_INT > 16)
		{
			Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

			final RenderScript rs = RenderScript.create(context);
			final Allocation input
				= Allocation.createFromBitmap(rs, sentBitmap, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
			final Allocation output = Allocation.createTyped(rs, input.getType());
			final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
			script.setRadius(radius /* e.g. 3.f */);
			script.setInput(input);
			script.forEach(output);
			output.copyTo(bitmap);
			return bitmap;
		}
		else
		{
			Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

			if(radius < 1)
			{
				return (null);
			}

			int w = bitmap.getWidth();
			int h = bitmap.getHeight();

			int[] pix = new int[w*h];
			//        Log.e("pix", w + " " + h + " " + pix.length);
			bitmap.getPixels(pix, 0, w, 0, 0, w, h);

			int wm = w-1;
			int hm = h-1;
			int wh = w*h;
			int div = radius+radius+1;

			int r[] = new int[wh];
			int g[] = new int[wh];
			int b[] = new int[wh];
			int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
			int vmin[] = new int[Math.max(w, h)];

			int divsum = (div+1) >> 1;
			divsum *= divsum;
			int temp = 256*divsum;
			int dv[] = new int[temp];
			for(i = 0; i < temp; i++)
			{
				dv[i] = (i/divsum);
			}

			yw = yi = 0;

			int[][] stack = new int[div][3];
			int stackpointer;
			int stackstart;
			int[] sir;
			int rbs;
			int r1 = radius+1;
			int routsum, goutsum, boutsum;
			int rinsum, ginsum, binsum;

			for(y = 0; y < h; y++)
			{
				rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
				for(i = -radius; i <= radius; i++)
				{
					p = pix[yi+Math.min(wm, Math.max(i, 0))];
					sir = stack[i+radius];
					sir[0] = (p & 0xff0000) >> 16;
					sir[1] = (p & 0x00ff00) >> 8;
					sir[2] = (p & 0x0000ff);
					rbs = r1-Math.abs(i);
					rsum += sir[0]*rbs;
					gsum += sir[1]*rbs;
					bsum += sir[2]*rbs;
					if(i > 0)
					{
						rinsum += sir[0];
						ginsum += sir[1];
						binsum += sir[2];
					}
					else
					{
						routsum += sir[0];
						goutsum += sir[1];
						boutsum += sir[2];
					}
				}
				stackpointer = radius;

				for(x = 0; x < w; x++)
				{

					r[yi] = dv[rsum];
					g[yi] = dv[gsum];
					b[yi] = dv[bsum];

					rsum -= routsum;
					gsum -= goutsum;
					bsum -= boutsum;

					stackstart = stackpointer-radius+div;
					sir = stack[stackstart%div];

					routsum -= sir[0];
					goutsum -= sir[1];
					boutsum -= sir[2];

					if(y == 0)
					{
						vmin[x] = Math.min(x+radius+1, wm);
					}
					p = pix[yw+vmin[x]];

					sir[0] = (p & 0xff0000) >> 16;
					sir[1] = (p & 0x00ff00) >> 8;
					sir[2] = (p & 0x0000ff);

					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];

					rsum += rinsum;
					gsum += ginsum;
					bsum += binsum;

					stackpointer = (stackpointer+1)%div;
					sir = stack[(stackpointer)%div];

					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];

					rinsum -= sir[0];
					ginsum -= sir[1];
					binsum -= sir[2];

					yi++;
				}
				yw += w;
			}
			for(x = 0; x < w; x++)
			{
				rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
				yp = -radius*w;
				for(i = -radius; i <= radius; i++)
				{
					yi = Math.max(0, yp)+x;

					sir = stack[i+radius];

					sir[0] = r[yi];
					sir[1] = g[yi];
					sir[2] = b[yi];

					rbs = r1-Math.abs(i);

					rsum += r[yi]*rbs;
					gsum += g[yi]*rbs;
					bsum += b[yi]*rbs;

					if(i > 0)
					{
						rinsum += sir[0];
						ginsum += sir[1];
						binsum += sir[2];
					}
					else
					{
						routsum += sir[0];
						goutsum += sir[1];
						boutsum += sir[2];
					}

					if(i < hm)
					{
						yp += w;
					}
				}
				yi = x;
				stackpointer = radius;
				for(y = 0; y < h; y++)
				{
					// Preserve alpha channel: ( 0xff000000 & pix[yi] )
					pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

					rsum -= routsum;
					gsum -= goutsum;
					bsum -= boutsum;

					stackstart = stackpointer-radius+div;
					sir = stack[stackstart%div];

					routsum -= sir[0];
					goutsum -= sir[1];
					boutsum -= sir[2];

					if(x == 0)
					{
						vmin[y] = Math.min(y+r1, hm)*w;
					}
					p = x+vmin[y];

					sir[0] = r[p];
					sir[1] = g[p];
					sir[2] = b[p];

					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];

					rsum += rinsum;
					gsum += ginsum;
					bsum += binsum;

					stackpointer = (stackpointer+1)%div;
					sir = stack[stackpointer];

					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];

					rinsum -= sir[0];
					ginsum -= sir[1];
					binsum -= sir[2];

					yi += w;
				}
			}
			//        Log.e("pix", w + " " + h + " " + pix.length);
			bitmap.setPixels(pix, 0, w, 0, 0, w, h);
			return (bitmap);
		}
	}

	/**
	 * 按指定宽高截取中间一段图片
	 *
	 * @param src 图片
	 * @param w   宽
	 * @param h   高
	 */
	public static Bitmap copyCenterBitmap(Bitmap src, int w, int h)
	{
		if(src == null)
		{
			return null;
		}
		int width = src.getWidth();
		int height = src.getHeight();
		if(width < w && height < h)
		{
			return src;
		}
		int x = 0;
		int y = 0;
		if(width > w)
		{
			x = (width-w)/2;
		}
		if(height > h)
		{
			y = (height-h)/2;
		}
		int cw = w; // crop width
		int ch = h; // crop height
		if(w > width)
		{
			cw = width;
		}
		if(h > height)
		{
			ch = height;
		}
		return Bitmap.createBitmap(src, x, y, cw, ch);
	}

	public static Bitmap getBitmap(Uri uri, Context context)
	{
		InputStream input = null;
		Bitmap bitmap = null;
		try
		{
			input = context.getContentResolver().openInputStream(uri);
			BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
			onlyBoundsOptions.inJustDecodeBounds = true;
			onlyBoundsOptions.inDither = true;//optional
			onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
			BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
			input.close();
			int originalWidth = onlyBoundsOptions.outWidth;
			int originalHeight = onlyBoundsOptions.outHeight;
			if((originalWidth == -1) || (originalHeight == -1))
			{
				return null;
			}
			//图片分辨率以480x800为标准
			float hh = 800f;//这里设置高度为800f
			float ww = 480f;//这里设置宽度为480f
			//缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
			int be = 1;//be=1表示不缩放
			if(originalWidth > originalHeight && originalWidth > ww)
			{//如果宽度大的话根据宽度固定大小缩放
				be = (int) (originalWidth/ww);
			}
			else if(originalWidth < originalHeight && originalHeight > hh)
			{//如果高度高的话根据宽度固定大小缩放
				be = (int) (originalHeight/hh);
			}
			if(be <= 0)
			{
				be = 1;
			}
			//比例压缩
			BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
			bitmapOptions.inSampleSize = be;//设置缩放比例
			bitmapOptions.inDither = true;//optional
			bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
			input = context.getContentResolver().openInputStream(uri);
			bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
			input.close();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return compressImage(bitmap);//再进行质量压缩
	}

	/**
	 * 从Uri中获取图片并重新设置图片的最大尺寸
	 *
	 * @param uri       图片的Uri
	 * @param maxHeight 目标图片的最大高
	 * @param maxWidth  目标图片的最大宽
	 */
	public static Bitmap getBitmap(Uri uri, Context context, int maxHeight, int maxWidth)
	{
		return zoomBitmap(getBitmap(uri, context), maxWidth, maxHeight);
	}


	public static final String ANDROID_RESOURCE = "android.resource://";
	public static final String FOREWARD_SLASH = "/";

	/**
	 * 将资源ID转为Uri
	 *
	 * @param context    上限文
	 * @param resourceId 资源id
	 */
	public static Uri resourceIdToUri(Context context, int resourceId)
	{
		return Uri.parse(ANDROID_RESOURCE+context.getPackageName()+FOREWARD_SLASH+resourceId);
	}

	/**
	 * 重新计算图片的最大尺寸
	 *
	 * @param src 图片
	 * @param max 最大尺寸
	 * @return
	 */
	public static Bitmap resizeBitmap(Bitmap src, int max)
	{
		if(src == null)
		{
			return null;
		}

		int width = src.getWidth();
		int height = src.getHeight();
		float rate = 0.0f;

		if(width > height)
		{
			rate = max/(float) width;
			height = (int) (height*rate);
			width = max;
		}
		else
		{
			rate = max/(float) height;
			width = (int) (width*rate);
			height = max;
		}

		return Bitmap.createScaledBitmap(src, width, height, true);
	}

	/**
	 * 保存图片
	 *
	 * @param bm       图片
	 * @param fileName 文件名
	 * @param max      最大尺寸
	 */
	public static File saveBitmap2FileZIP(Bitmap bm, String fileName, int max)
	{
		return saveBitmap2File(resizeBitmap(bm, max), fileName);
	}

	/**
	 * 保存图片
	 *
	 * @param uri      图片Uri
	 * @param fileName 文件名
	 * @param context  上下文
	 */
	public static File saveBitmap2FileZIP(Uri uri, String fileName, Context context)
	{
		return saveBitmap2File(getBitmap(uri, context), fileName);
	}

	/**
	 * @param bm       图片源
	 * @param fileName 文件名称
	 * @return
	 */
	public static File saveBitmap2File(Bitmap bm, String fileName)
	{
		Log.e(TAG, "保存图片");
		File dirFile = new File("/sdcard/cache/");
		if(!dirFile.exists())
		{
			dirFile.mkdir();
		}
		File f = new File("/sdcard/cache/", fileName);
		if(f.exists())
		{
			f.delete();
		}
		else
		{
			try
			{
				f.createNewFile();
			}
			catch(IOException e)
			{
			}
		}
		try
		{
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.PNG, 90, out);
			out.flush();
			out.close();
			Log.i(TAG, "已经保存");
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return f;
	}

	/**
	 * 压缩图片
	 *
	 * @param image 图片
	 */
	public static Bitmap compressImage(Bitmap image)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while(baos.toByteArray().length/1024 > 150)
		{//循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();//重置baos即清空baos
			options -= 10;//每次都减少10
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	public static final int ALL = 347120;
	public static final int TOP = 547120;
	public static final int LEFT = 647120;
	public static final int RIGHT = 747120;
	public static final int BOTTOM = 847120;

	/**
	 * 指定图片的切边，对图片进行圆角处理
	 *
	 * @param type    具体参见：{@link ALL} , {@link TOP} ,
	 *                {@link LEFT} , {@link RIGHT} , {@link BOTTOM}
	 * @param bitmap  需要被切圆角的图片
	 * @param roundPx 要切的像素大小
	 * @return
	 */
	public static Bitmap fillet(int type, Bitmap bitmap, int roundPx)
	{
		try
		{
			// 其原理就是：先建立一个与图片大小相同的透明的Bitmap画板
			// 然后在画板上画出一个想要的形状的区域。
			// 最后把源图片帖上。
			final int width = bitmap.getWidth();
			final int height = bitmap.getHeight();

			Bitmap paintingBoard = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(paintingBoard);
			canvas.drawARGB(Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT,
			                Color.TRANSPARENT);

			final Paint paint = new Paint();
			paint.setAntiAlias(true);
			paint.setColor(Color.BLACK);

			if (TOP == type)
			{
				clipTop(canvas, paint, roundPx, width, height);
			}
			else if (LEFT == type)
			{
				clipLeft(canvas, paint, roundPx, width, height);
			}
			else if (RIGHT == type)
			{
				clipRight(canvas, paint, roundPx, width, height);
			}
			else if (BOTTOM == type)
			{
				clipBottom(canvas, paint, roundPx, width, height);
			}
			else
			{
				clipAll(canvas, paint, roundPx, width, height);
			}

			paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
			//帖子图
			final Rect src = new Rect(0, 0, width, height);
			final Rect dst = src;
			canvas.drawBitmap(bitmap, src, dst, paint);
			return paintingBoard;
		} catch (Exception exp)
		{
			return bitmap;
		}
	}

	private static void clipLeft(final Canvas canvas, final Paint paint, int offset, int width,
		int height)
	{
		final Rect block = new Rect(offset, 0, width, height);
		canvas.drawRect(block, paint);
		final RectF rectF = new RectF(0, 0, offset * 2, height);
		canvas.drawRoundRect(rectF, offset, offset, paint);
	}

	private static void clipRight(final Canvas canvas, final Paint paint, int offset, int width,
		int height)
	{
		final Rect block = new Rect(0, 0, width - offset, height);
		canvas.drawRect(block, paint);
		final RectF rectF = new RectF(width - offset * 2, 0, width, height);
		canvas.drawRoundRect(rectF, offset, offset, paint);
	}

	private static void clipTop(final Canvas canvas, final Paint paint, int offset, int width,
		int height)
	{
		final Rect block = new Rect(0, offset, width, height);
		canvas.drawRect(block, paint);
		final RectF rectF = new RectF(0, 0, width, offset * 2);
		canvas.drawRoundRect(rectF, offset, offset, paint);
	}

	private static void clipBottom(final Canvas canvas, final Paint paint, int offset, int width,
		int height)
	{
		final Rect block = new Rect(0, 0, width, height - offset);
		canvas.drawRect(block, paint);
		final RectF rectF = new RectF(0, height - offset * 2, width, height);
		canvas.drawRoundRect(rectF, offset, offset, paint);
	}

	private static void clipAll(final Canvas canvas, final Paint paint, int offset, int width,
		int height)
	{
		final RectF rectF = new RectF(0, 0, width, height);
		canvas.drawRoundRect(rectF, offset, offset, paint);
	}
}
