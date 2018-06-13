package talex.zsw.basecore.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;

import java.io.File;
import java.io.IOException;

/**
 * Intent工具类
 */
public class IntentTool
{

	/**
	 * 获取安装App(支持7.0)的意图
	 *
	 * @param context
	 * @param filePath
	 * @return
	 */
	public static Intent getInstallAppIntent(Context context, String filePath)
	{
		//apk文件的本地路径
		File apkfile = new File(filePath);
		if(!apkfile.exists())
		{
			return null;
		}

		boolean canInstall = true;
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
		{
			canInstall = Tool.getContext().getPackageManager().canRequestPackageInstalls();
		}
		Intent intent = new Intent();
		if(canInstall)
		{
			intent = new Intent(Intent.ACTION_VIEW);
			Uri contentUri = FileTool.getUriForFile(context, apkfile);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
			{
				intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
			}
			intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
		}
		else
		{
			// android 8.0 跳转到显示是否可以安装应用的界面
			Uri packageUri = Uri.parse("package:"+Tool.getContext().getPackageName());
			intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageUri);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		}
		return intent;
	}

	/**
	 * 获取卸载App的意图
	 *
	 * @param packageName 包名
	 * @return 意图
	 */
	public static Intent getUninstallAppIntent(String packageName)
	{
		Intent intent = new Intent(Intent.ACTION_DELETE);
		intent.setData(Uri.parse("package:"+packageName));
		return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	}

	/**
	 * 获取打开App的意图
	 *
	 * @param context     上下文
	 * @param packageName 包名
	 * @return 意图
	 */
	public static Intent getLaunchAppIntent(Context context, String packageName)
	{
		return getIntentByPackageName(context, packageName);
	}

	/**
	 * 根据包名获取意图
	 *
	 * @param context     上下文
	 * @param packageName 包名
	 * @return 意图
	 */
	private static Intent getIntentByPackageName(Context context, String packageName)
	{
		return context.getPackageManager().getLaunchIntentForPackage(packageName);
	}

	/**
	 * 获取App信息的意图
	 *
	 * @param packageName 包名
	 * @return 意图
	 */
	public static Intent getAppInfoIntent(String packageName)
	{
		Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
		Uri uri = Uri.fromParts("package", packageName, null);
		return intent.setData(uri);
	}

	/**
	 * 获取App信息分享的意图
	 *
	 * @param info 分享信息
	 * @return 意图
	 */
	public static Intent getShareInfoIntent(String info)
	{
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		return intent.putExtra(Intent.EXTRA_TEXT, info);
	}

	/**
	 * 获取其他应用的Intent
	 *
	 * @param packageName 包名
	 * @param className   全类名
	 * @return 意图
	 */
	public static Intent getComponentNameIntent(String packageName, String className)
	{
		return getComponentNameIntent(packageName, className, null);
	}

	/**
	 * 获取其他应用的Intent
	 *
	 * @param packageName 包名
	 * @param className   全类名
	 * @return 意图
	 */
	public static Intent getComponentNameIntent(String packageName, String className, Bundle bundle)
	{
		Intent intent = new Intent(Intent.ACTION_VIEW);
		if(bundle != null)
		{
			intent.putExtras(bundle);
		}
		ComponentName cn = new ComponentName(packageName, className);
		intent.setComponent(cn);
		return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	}

	/**
	 * 获取应用详情页面具体设置 intent
	 *
	 * @return
	 */
	public static Intent getAppDetailsSettingsIntent(Context mContext)
	{
		Intent localIntent = new Intent();
		localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if(Build.VERSION.SDK_INT >= 9)
		{
			localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
			localIntent.setData(Uri.fromParts("package", mContext.getPackageName(), null));
		}
		else if(Build.VERSION.SDK_INT <= 8)
		{
			localIntent.setAction(Intent.ACTION_VIEW);
			localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
			localIntent.putExtra("com.android.settings.ApplicationPkgName", mContext.getPackageName());
		}
		return localIntent;
	}

	/**
	 * 获取应用详情页面具体设置 intent
	 *
	 * @param packageName 包名
	 * @return intent
	 */
	public static Intent getAppDetailsSettingsIntent(String packageName)
	{
		Intent localIntent = new Intent();
		localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if(Build.VERSION.SDK_INT >= 9)
		{
			localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
			localIntent.setData(Uri.fromParts("package", packageName, null));
		}
		else if(Build.VERSION.SDK_INT <= 8)
		{
			localIntent.setAction(Intent.ACTION_VIEW);
			localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
			localIntent.putExtra("com.android.settings.ApplicationPkgName", packageName);
		}
		return localIntent;
	}

	// ==================================================================================
	private static Intent getIntent(final Intent intent, final boolean isNewTask)
	{
		return isNewTask ? intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) : intent;
	}

	/**
	 * 获取分享文字的意图
	 *
	 * @param content 分享信息
	 * @return 意图
	 */
	public static Intent getShareTextIntent(final String content)
	{
		return getShareTextIntent(content, false);
	}

	/**
	 * 获取分享文字的意图
	 *
	 * @param content   分享信息
	 * @param isNewTask 是否在新的线程打开
	 * @return 意图
	 */

	public static Intent getShareTextIntent(final String content, final boolean isNewTask)
	{
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, content);
		return getIntent(intent, isNewTask);
	}

	/**
	 * 获取分享图片的意图
	 *
	 * @param content   文字说明
	 * @param imagePath 图片的本地地址
	 * @return 意图
	 */
	public static Intent getShareImageIntent(final String content, final String imagePath)
	{
		return getShareImageIntent(content, imagePath, false);
	}

	/**
	 * 获取分享图片的意图
	 *
	 * @param content   文字说明
	 * @param imagePath 图片的本地地址
	 * @param isNewTask 是否在新的线程打开
	 * @return intent
	 */
	public static Intent getShareImageIntent(final String content, final String imagePath, final boolean isNewTask)
	{
		if(imagePath == null || imagePath.length() == 0)
		{
			return null;
		}
		return getShareImageIntent(content, new File(imagePath), isNewTask);
	}

	/**
	 * 获取分享图片的意图
	 *
	 * @param content 文字说明
	 * @param image   图片的文件
	 * @return intent
	 */
	public static Intent getShareImageIntent(final String content, final File image)
	{
		return getShareImageIntent(content, image, false);
	}

	/**
	 * 获取分享图片的意图
	 *
	 * @param content   文字说明
	 * @param image     图片的文件
	 * @param isNewTask 是否在新的线程打开
	 * @return intent
	 */
	public static Intent getShareImageIntent(final String content, final File image, final boolean isNewTask)
	{
		if(image != null && image.isFile())
		{
			return null;
		}
		return getShareImageIntent(content, Uri.fromFile(image), isNewTask);
	}

	/**
	 * 获取分享图片的意图
	 *
	 * @param content 文字说明
	 * @param uri     图片的Uri
	 * @return intent
	 */
	public static Intent getShareImageIntent(final String content, final Uri uri)
	{
		return getShareImageIntent(content, uri, false);
	}

	/**
	 * 获取分享图片的意图
	 *
	 * @param content   文字说明
	 * @param uri       The uri of image.
	 * @param isNewTask 是否在新的线程打开
	 * @return intent
	 */
	public static Intent getShareImageIntent(final String content, final Uri uri, final boolean isNewTask)
	{
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_TEXT, content);
		intent.putExtra(Intent.EXTRA_STREAM, uri);
		intent.setType("image/*");
		return getIntent(intent, isNewTask);
	}

	/**
	 * 关机重启的意图
	 * <p>Requires root permission
	 * or hold {@code android:sharedUserId="android.uid.system"},
	 * {@code <uses-permission android:name="android.permission.SHUTDOWN/>}
	 * in manifest.</p>
	 */
	public static Intent getShutdownIntent()
	{
		return getShutdownIntent(false);
	}

	/**
	 * 关机重启的意图
	 * <p>Requires root permission
	 * or hold {@code android:sharedUserId="android.uid.system"},
	 * {@code <uses-permission android:name="android.permission.SHUTDOWN/>}
	 * in manifest.</p>
	 *
	 * @param isNewTask 是否在新的线程打开
	 */
	public static Intent getShutdownIntent(final boolean isNewTask)
	{
		Intent intent = new Intent("android.intent.action.ACTION_REQUEST_SHUTDOWN");
		intent.putExtra("android.intent.extra.KEY_CONFIRM", false);
		return getIntent(intent, isNewTask);
	}

	/**
	 * 拨号的意图
	 *
	 * @param phoneNumber 电话号码
	 */
	public static Intent getDialIntent(final String phoneNumber)
	{
		return getDialIntent(phoneNumber, false);
	}

	/**
	 * 拨号的意图
	 *
	 * @param phoneNumber 电话号码
	 * @param isNewTask   是否在新的线程打开
	 */
	public static Intent getDialIntent(final String phoneNumber, final boolean isNewTask)
	{
		Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phoneNumber));
		return getIntent(intent, isNewTask);
	}

	/**
	 * 打电话的意图
	 * <p>Must hold {@code <uses-permission android:name="android.permission.CALL_PHONE" />}</p>
	 *
	 * @param phoneNumber 电话号码
	 */
	public static Intent getCallIntent(final String phoneNumber)
	{
		return getCallIntent(phoneNumber, false);
	}

	/**
	 * 打电话的意图
	 * <p>Must hold {@code <uses-permission android:name="android.permission.CALL_PHONE" />}</p>
	 *
	 * @param phoneNumber 电话号码
	 * @param isNewTask   是否在新的线程打开
	 */
	public static Intent getCallIntent(final String phoneNumber, final boolean isNewTask)
	{
		Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:"+phoneNumber));
		return getIntent(intent, isNewTask);
	}

	/**
	 * 发送短信的意图
	 *
	 * @param phoneNumber 电话号码
	 * @param content     短信内容
	 */
	public static Intent getSendSmsIntent(final String phoneNumber, final String content)
	{
		return getSendSmsIntent(phoneNumber, content, false);
	}

	/**
	 * 发送短信的意图
	 *
	 * @param phoneNumber 电话号码
	 * @param content     短信内容
	 * @param isNewTask   是否在新的线程打开
	 */
	public static Intent getSendSmsIntent(final String phoneNumber, final String content, final boolean isNewTask)
	{
		Uri uri = Uri.parse("smsto:"+phoneNumber);
		Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
		intent.putExtra("sms_body", content);
		return getIntent(intent, isNewTask);
	}

	/**
	 * 调用系统相机的意图
	 */
	public static Intent getCaptureIntent()
	{
		return getCaptureIntent(null, false);
	}

	/**
	 * 调用系统相机的意图
	 *
	 * @param outUri 图片输出路径
	 */
	public static Intent getCaptureIntent(final Uri outUri)
	{
		return getCaptureIntent(outUri, false);
	}

	/**
	 * 调用系统相机的意图
	 *
	 * @param outUri    图片输出路径
	 * @param isNewTask 是否在新的线程打开
	 */
	public static Intent getCaptureIntent(final Uri outUri, final boolean isNewTask)
	{
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if(outUri != null)
		{
			intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
		}
		intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		return getIntent(intent, isNewTask);
	}

	/**
	 * 打开某个网页的意图
	 *
	 * @param url 网页URL
	 */
	public static Intent getWebIntent(String url)
	{
		Uri uri = Uri.parse(url);
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setData(uri);
		return intent;
	}


	/**
	 * 获取打开照程序界面的Intent
	 */
	public static Intent getOpenCameraIntent()
	{
		return new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	}

	/**
	 * 获取跳转至相册选择界面的Intent
	 */
	public static Intent getImagePickerIntent()
	{
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		return intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
	}

	/**
	 * 获取[跳转至相册选择界面,并跳转至裁剪界面，默认可缩放裁剪区域]的Intent
	 */
	public static Intent getImagePickerIntent(int outputX, int outputY, Uri fromFileURI, Uri saveFileURI)
	{
		return getImagePickerIntent(1, 1, outputX, outputY, true, fromFileURI, saveFileURI);
	}

	/**
	 * 获取[跳转至相册选择界面,并跳转至裁剪界面，默认可缩放裁剪区域]的Intent
	 */
	public static Intent getImagePickerIntent(int aspectX, int aspectY, int outputX, int outputY, Uri fromFileURI, Uri saveFileURI)
	{
		return getImagePickerIntent(aspectX, aspectY, outputX, outputY, true, fromFileURI, saveFileURI);
	}

	/**
	 * 获取[跳转至相册选择界面,并跳转至裁剪界面，可以指定是否缩放裁剪区域]的Intent
	 *
	 * @param aspectX     裁剪框尺寸比例X
	 * @param aspectY     裁剪框尺寸比例Y
	 * @param outputX     输出尺寸宽度
	 * @param outputY     输出尺寸高度
	 * @param canScale    是否可缩放
	 * @param fromFileURI 文件来源路径URI
	 * @param saveFileURI 输出文件路径URI
	 */
	public static Intent getImagePickerIntent(int aspectX, int aspectY, int outputX, int outputY, boolean canScale, Uri fromFileURI, Uri saveFileURI)
	{
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setDataAndType(fromFileURI, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", aspectX <= 0 ? 1 : aspectX);
		intent.putExtra("aspectY", aspectY <= 0 ? 1 : aspectY);
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("scale", canScale);
		// 图片剪裁不足黑边解决
		intent.putExtra("scaleUpIfNeeded", true);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, saveFileURI);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		// 去除人脸识别
		return intent.putExtra("noFaceDetection", true);
	}

	/**
	 * 获取[跳转至相册选择界面,并跳转至裁剪界面，默认可缩放裁剪区域]的Intent
	 */
	public static Intent getCameraIntent(Uri saveFileURI)
	{
		Intent mIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		return mIntent.putExtra(MediaStore.EXTRA_OUTPUT, saveFileURI);
	}

	/**
	 * 获取[跳转至裁剪界面,默认可缩放]的Intent
	 */
	public static Intent getCropImageIntent(int outputX, int outputY, Uri fromFileURI, Uri saveFileURI)
	{
		return getCropImageIntent(1, 1, outputX, outputY, true, fromFileURI, saveFileURI);
	}

	/**
	 * 获取[跳转至裁剪界面,默认可缩放]的Intent
	 */
	public static Intent getCropImageIntent(int aspectX, int aspectY, int outputX, int outputY, Uri fromFileURI, Uri saveFileURI)
	{
		return getCropImageIntent(aspectX, aspectY, outputX, outputY, true, fromFileURI, saveFileURI);
	}


	/**
	 * 获取[跳转至裁剪界面]的Intent
	 */
	public static Intent getCropImageIntent(int aspectX, int aspectY, int outputX, int outputY, boolean canScale, Uri fromFileURI, Uri saveFileURI)
	{
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(fromFileURI, "image/*");
		intent.putExtra("crop", "true");
		// X方向上的比例
		intent.putExtra("aspectX", aspectX <= 0 ? 1 : aspectX);
		// Y方向上的比例
		intent.putExtra("aspectY", aspectY <= 0 ? 1 : aspectY);
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("scale", canScale);
		// 图片剪裁不足黑边解决
		intent.putExtra("scaleUpIfNeeded", true);
		intent.putExtra("return-data", false);
		// 需要将读取的文件路径和裁剪写入的路径区分，否则会造成文件0byte
		intent.putExtra(MediaStore.EXTRA_OUTPUT, saveFileURI);
		// true-->返回数据类型可以设置为Bitmap，但是不能传输太大，截大图用URI，小图用Bitmap或者全部使用URI
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		// 取消人脸识别功能
		intent.putExtra("noFaceDetection", true);
		return intent;
	}

	/**
	 * 获得选中相册的图片
	 *
	 * @param context 上下文
	 * @param data    onActivityResult返回的Intent
	 * @return bitmap
	 */
	public static Bitmap getChoosedImage(Activity context, Intent data)
	{
		if(data == null)
		{
			return null;
		}
		Bitmap bm = null;
		ContentResolver cr = context.getContentResolver();
		Uri originalUri = data.getData();
		try
		{
			bm = MediaStore.Images.Media.getBitmap(cr, originalUri);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return bm;
	}

	/**
	 * 获得选中相册的图片路径
	 *
	 * @param context 上下文
	 * @param data    onActivityResult返回的Intent
	 * @return
	 */
	public static String getChoosedImagePath(Activity context, Intent data)
	{
		if(data == null)
		{
			return null;
		}
		String path = "";
		ContentResolver resolver = context.getContentResolver();
		Uri originalUri = data.getData();
		if(null == originalUri)
		{
			return null;
		}
		String[] projection = {MediaStore.Images.Media.DATA};
		Cursor cursor = resolver.query(originalUri, projection, null, null, null);
		if(null != cursor)
		{
			try
			{
				int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				cursor.moveToFirst();
				path = cursor.getString(column_index);
			}
			catch(IllegalArgumentException e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if(!cursor.isClosed())
					{
						cursor.close();
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return RegTool.isNullString(path) ? originalUri.getPath() : null;
	}

	/**
	 * 获取拍照之后的照片文件（JPG格式）
	 *
	 * @param data     onActivityResult回调返回的数据
	 * @param filePath 文件路径
	 * @return 文件
	 */
	public static File getTakePictureFile(Intent data, String filePath)
	{
		if(data == null)
		{
			return null;
		}
		Bundle extras = data.getExtras();
		if(extras == null)
		{
			return null;
		}
		Bitmap photo = extras.getParcelable("data");
		File file = new File(filePath);
		if(BitmapTool.save(photo, file, Bitmap.CompressFormat.JPEG))
		{
			return file;
		}
		return null;
	}
}
