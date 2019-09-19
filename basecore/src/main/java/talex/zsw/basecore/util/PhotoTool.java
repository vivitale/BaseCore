package talex.zsw.basecore.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import talex.zsw.basecore.view.other.RxToast;

/**
 * 相册,相机常用方法
 */

public class PhotoTool
{
	public static final int GET_IMAGE_BY_CAMERA = 5001;
	public static final int GET_IMAGE_FROM_PHONE = 5002;
	public static final int CROP_IMAGE = 5003;
	public static Uri imageUriFromCamera;
	public static Uri cropImageUri;

	/**
	 * 调用系统相机
	 */
	public static void openCameraImage(final Activity activity)
	{
		imageUriFromCamera = createImagePathUri(activity);

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// MediaStore.EXTRA_OUTPUT参数不设置时,系统会自动生成一个uri,但是只会返回一个缩略图
		// 返回图片在onActivityResult中通过以下代码获取
		// Bitmap bitmap = (Bitmap) data.getExtras().get("data");
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriFromCamera);
		activity.startActivityForResult(intent, GET_IMAGE_BY_CAMERA);
	}

	/**
	 * 调用系统相机
	 */
	public static void openCameraImage(final Fragment fragment)
	{
		imageUriFromCamera = createImagePathUri(fragment.getContext());

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// MediaStore.EXTRA_OUTPUT参数不设置时,系统会自动生成一个uri,但是只会返回一个缩略图
		// 返回图片在onActivityResult中通过以下代码获取
		// Bitmap bitmap = (Bitmap) data.getExtras().get("data");
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriFromCamera);
		fragment.startActivityForResult(intent, GET_IMAGE_BY_CAMERA);
	}

	/**
	 * 调用系统相册
	 */
	public static void openLocalImage(final Activity activity)
	{
		Intent intent = null;
		if(Build.VERSION.SDK_INT > 19)
		{

			intent
				= new Intent(Intent.ACTION_OPEN_DOCUMENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			intent.addCategory(Intent.CATEGORY_OPENABLE);
		}
		else
		{

			intent
				= new Intent(Intent.ACTION_GET_CONTENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		}
		intent.setType("image/*");
		activity.startActivityForResult(intent, GET_IMAGE_FROM_PHONE);
	}

	/**
	 * 调用系统相册
	 */
	public static void openLocalImage(final Fragment fragment)
	{
		Intent intent = null;
		if(Build.VERSION.SDK_INT > 19)
		{

			intent
				= new Intent(Intent.ACTION_OPEN_DOCUMENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			intent.addCategory(Intent.CATEGORY_OPENABLE);
		}
		else
		{

			intent
				= new Intent(Intent.ACTION_GET_CONTENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		}
		intent.setType("image/*");
		fragment.startActivityForResult(intent, GET_IMAGE_FROM_PHONE);
	}

	/**
	 * 裁剪图片
	 *
	 * @param activity 调用的Activity
	 * @param srcUri   图片的uri
	 * @param aspectX  裁剪框宽高的比例
	 * @param aspectY  裁剪框宽高的比例
	 * @param outputX  裁剪后生成图片的宽高
	 * @param outputY  裁剪后生成图片的宽高
	 */
	public static void cropImage(Activity activity, Uri srcUri, int aspectX, int aspectY, int outputX, int outputY)
	{
		cropImageUri = createImagePathUri(activity);

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
		intent.setDataAndType(srcUri, "image/*");
		intent.putExtra("crop", "true");

		////////////////////////////////////////////////////////////////
		// 1.宽高和比例都不设置时,裁剪框可以自行调整(比例和大小都可以随意调整)
		////////////////////////////////////////////////////////////////
		// 2.只设置裁剪框宽高比(aspect)后,裁剪框比例固定不可调整,只能调整大小
		////////////////////////////////////////////////////////////////
		// 3.裁剪后生成图片宽高(output)的设置和裁剪框无关,只决定最终生成图片大小
		////////////////////////////////////////////////////////////////
		// 4.裁剪框宽高比例(aspect)可以和裁剪后生成图片比例(output)不同,此时,
		//	会以裁剪框的宽为准,按照裁剪宽高比例生成一个图片,该图和框选部分可能不同,
		//  不同的情况可能是截取框选的一部分,也可能超出框选部分,向下延伸补足
		////////////////////////////////////////////////////////////////

		// aspectX aspectY 是裁剪框宽高的比例
		intent.putExtra("aspectX", aspectX);
		intent.putExtra("aspectY", aspectY);
		// outputX outputY 是裁剪后生成图片的宽高
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);

		// return-data为true时,会直接返回bitmap数据,但是大图裁剪时会出现问题,推荐下面为false时的方式
		// return-data为false时,不会返回bitmap,但需要指定一个MediaStore.EXTRA_OUTPUT保存图片uri
//		intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImageUri);
//		intent.putExtra("return-data", true);

		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImageUri);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true);

		activity.startActivityForResult(intent, CROP_IMAGE);
	}

	/**
	 * 裁剪图片 返回200*200的图片
	 */
	public static void cropImage(Activity activity, Uri srcUri)
	{
		cropImage(activity, srcUri, 1, 1, 200, 200);
	}

	/**
	 * 裁剪图片 返回200*200的图片
	 */
	public static void cropImage(Fragment fragment, Uri srcUri)
	{
		cropImage(fragment, srcUri, 1, 1, 200, 200);
	}

	/**
	 * 裁剪图片
	 *
	 * @param fragment 调用裁剪的Fagment
	 * @param srcUri   图片的uri
	 * @param aspectX  裁剪框宽高的比例
	 * @param aspectY  裁剪框宽高的比例
	 * @param outputX  裁剪后生成图片的宽高
	 * @param outputY  裁剪后生成图片的宽高
	 */
	public static void cropImage(Fragment fragment, Uri srcUri, int aspectX, int aspectY, int outputX, int outputY)
	{
		cropImageUri = createImagePathUri(fragment.getContext());

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
		intent.setDataAndType(srcUri, "image/*");
		intent.putExtra("crop", "true");

		////////////////////////////////////////////////////////////////
		// 1.宽高和比例都不设置时,裁剪框可以自行调整(比例和大小都可以随意调整)
		////////////////////////////////////////////////////////////////
		// 2.只设置裁剪框宽高比(aspect)后,裁剪框比例固定不可调整,只能调整大小
		////////////////////////////////////////////////////////////////
		// 3.裁剪后生成图片宽高(output)的设置和裁剪框无关,只决定最终生成图片大小
		////////////////////////////////////////////////////////////////
		// 4.裁剪框宽高比例(aspect)可以和裁剪后生成图片比例(output)不同,此时,
		//	会以裁剪框的宽为准,按照裁剪宽高比例生成一个图片,该图和框选部分可能不同,
		//  不同的情况可能是截取框选的一部分,也可能超出框选部分,向下延伸补足
		////////////////////////////////////////////////////////////////

		// aspectX aspectY 是裁剪框宽高的比例
		intent.putExtra("aspectX", aspectX);
		intent.putExtra("aspectY", aspectY);
		// outputX outputY 是裁剪后生成图片的宽高
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);

		// return-data为true时,会直接返回bitmap数据,但是大图裁剪时会出现问题,推荐下面为false时的方式
		// return-data为false时,不会返回bitmap,但需要指定一个MediaStore.EXTRA_OUTPUT保存图片uri
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImageUri);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true);

		fragment.startActivityForResult(intent, CROP_IMAGE);
	}

	/**
	 * 创建一条图片地址uri,用于保存拍照后的照片
	 *
	 * @param context 上下文
	 * @return 图片的uri
	 */
	public static Uri createImagePathUri(final Context context)
	{
		final Uri[] imageFilePath = {null};

		if(ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
			PackageManager.PERMISSION_GRANTED)
		{
			ActivityCompat.requestPermissions((Activity) context, new String[]{
				Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
			imageFilePath[0] = Uri.parse("");
			RxToast.error("请先获取写入SDCard权限");
		}
		else
		{
			String status = Environment.getExternalStorageState();
			SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
			long time = System.currentTimeMillis();
			String imageName = timeFormatter.format(new Date(time));
			// ContentValues是我们希望这条记录被创建时包含的数据信息
			ContentValues values = new ContentValues(3);
			values.put(MediaStore.Images.Media.DISPLAY_NAME, imageName);
			values.put(MediaStore.Images.Media.DATE_TAKEN, time);
			values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

			if(status.equals(Environment.MEDIA_MOUNTED))
			{// 判断是否有SD卡,优先使用SD卡存储,当没有SD卡时使用手机存储
				imageFilePath[0] = context
					.getContentResolver()
					.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
			}
			else
			{
				imageFilePath[0] = context
					.getContentResolver()
					.insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, values);
			}
		}

		Log.i("", "生成的照片输出路径："+imageFilePath[0].toString());
		return imageFilePath[0];
	}


	//此方法 只能用于4.4以下的版本
	public static String getRealFilePath(final Context context, final Uri uri)
	{
		if(null == uri)
		{
			return null;
		}
		final String scheme = uri.getScheme();
		String data = null;
		if(scheme == null)
		{
			data = uri.getPath();
		}
		else if(ContentResolver.SCHEME_FILE.equals(scheme))
		{
			data = uri.getPath();
		}
		else if(ContentResolver.SCHEME_CONTENT.equals(scheme))
		{
			String[] projection = {MediaStore.Images.ImageColumns.DATA};
			Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

			//            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
			if(null != cursor)
			{
				if(cursor.moveToFirst())
				{
					int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
					if(index > -1)
					{
						data = cursor.getString(index);
					}
				}
				cursor.close();
			}
		}
		return data;
	}


	/**
	 * 根据Uri获取图片绝对路径，解决Android4.4以上版本Uri转换
	 *
	 * @param context
	 * @param imageUri
	 * @author yaoxing
	 * @date 2014-10-12
	 */
	@TargetApi(19) public static String getImageAbsolutePath(Context context, Uri imageUri)
	{
		if(context == null || imageUri == null)
		{
			return null;
		}
		if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT &&
			DocumentsContract.isDocumentUri(context, imageUri))
		{
			if(FileTool.isExternalStorageDocument(imageUri))
			{
				String docId = DocumentsContract.getDocumentId(imageUri);
				String[] split = docId.split(":");
				String type = split[0];
				if("primary".equalsIgnoreCase(type))
				{
					return Environment.getExternalStorageDirectory()+"/"+split[1];
				}
			}
			else if(FileTool.isDownloadsDocument(imageUri))
			{
				String id = DocumentsContract.getDocumentId(imageUri);
				Uri contentUri
					= ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long
					.valueOf(id));
				return FileTool.getDataColumn(context, contentUri, null, null);
			}
			else if(FileTool.isMediaDocument(imageUri))
			{
				String docId = DocumentsContract.getDocumentId(imageUri);
				String[] split = docId.split(":");
				String type = split[0];
				Uri contentUri = null;
				if("image".equals(type))
				{
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				}
				else if("video".equals(type))
				{
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				}
				else if("audio".equals(type))
				{
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}
				String selection = MediaStore.Images.Media._ID+"=?";
				String[] selectionArgs = new String[]{split[1]};
				return FileTool.getDataColumn(context, contentUri, selection, selectionArgs);
			}
		} // MediaStore (and general)
		else if("content".equalsIgnoreCase(imageUri.getScheme()))
		{
			// Return the remote address
			if(FileTool.isGooglePhotosUri(imageUri))
			{
				return imageUri.getLastPathSegment();
			}
			return FileTool.getDataColumn(context, imageUri, null, null);
		}
		// File
		else if("file".equalsIgnoreCase(imageUri.getScheme()))
		{
			return imageUri.getPath();
		}
		return null;
	}
}
