package talex.zsw.basecore.view.dialog.rxdialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import talex.zsw.basecore.R;
import talex.zsw.basecore.util.glide.GlideTool;
import talex.zsw.basecore.view.imageview.scaleimage.ImageSource;
import talex.zsw.basecore.view.imageview.scaleimage.ScaleImageView;


/**
 * 查看图片并支持手势缩放
 */
public class RxDialogScaleView extends RxDialog
{

	private ScaleImageView mScaleImageView;
	private String filePath;
	private Uri fileUri;
	private String fileAssetName;
	private String imageUrl;
	private Bitmap fileBitmap;
	private int resId;
	private int maxScale = 100;

	public RxDialogScaleView(Context context)
	{
		super(context);
		initView();
	}

	public RxDialogScaleView(Activity context)
	{
		super(context);
		initView();
	}


	public RxDialogScaleView(Context context, String filePath, boolean isAssets)
	{
		super(context);
		initView();
		setImage(filePath, isAssets);
	}

	public RxDialogScaleView(Context context, Uri uri)
	{
		super(context);
		initView();
		setImage(uri);
	}

	public RxDialogScaleView(Context context, int resId, boolean isResId)
	{
		super(context);
		initView();
		if(isResId)
		{
			setImage(resId);
		}
	}

	public RxDialogScaleView(Context context, Bitmap bitmap)
	{
		super(context);
		initView();
		setImage(bitmap);
	}

	public RxDialogScaleView(Context context, String imageUrl)
	{
		super(context);
		initView();
		setImage(imageUrl);
	}

	public RxDialogScaleView(Context context, int themeResId)
	{
		super(context, themeResId);
		initView();
	}

	public RxDialogScaleView(Context context, boolean cancelable, OnCancelListener cancelListener)
	{
		super(context, cancelable, cancelListener);
		initView();
	}


	public RxDialogScaleView(Context context, float alpha, int gravity)
	{
		super(context, alpha, gravity);
		initView();
	}

	public ScaleImageView getScaleImageView()
	{
		return mScaleImageView;
	}

	public void setImage(String filePath, boolean isAssets)
	{
		if(isAssets)
		{
			this.fileAssetName = filePath;
			mScaleImageView.setImage(ImageSource.asset(filePath));
		}
		else
		{
			this.filePath = filePath;
			mScaleImageView.setImage(ImageSource.uri(filePath));
		}
	}

	public void setImage(String imageUrl)
	{
		this.imageUrl = imageUrl;
		GlideTool.loadImageSimpleTarget(mScaleImageView.getContext(), imageUrl, new SimpleTarget<Bitmap>()
		{
			@Override
			public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition)
			{
				mScaleImageView.setImage(ImageSource.bitmap(resource));
			}
		});
	}

	public void setImage(Uri uri)
	{
		this.fileUri = uri;
		mScaleImageView.setImage(ImageSource.uri(uri));
	}

	public void setImage(int resId)
	{
		this.resId = resId;
		mScaleImageView.setImage(ImageSource.resource(resId));
	}

	public void setImage(Bitmap bitmap)
	{
		this.fileBitmap = bitmap;
		mScaleImageView.setImage(ImageSource.bitmap(fileBitmap));
	}

	private void initView()
	{
		View dialogView = LayoutInflater.from(mContext).inflate(R.layout.dialog_scaleview, null);
		mScaleImageView = dialogView.findViewById(R.id.rx_scale_view);
		mScaleImageView.setMaxScale(maxScale);
		ImageView ivClose = dialogView.findViewById(R.id.iv_close);
		ivClose.setOnClickListener(new View.OnClickListener()
		{
			@Override public void onClick(View view)
			{
				cancel();
			}
		});
		setFullScreen();
		setContentView(dialogView);
	}

	public int getMaxScale()
	{
		return maxScale;
	}

	public void setMaxScale(int maxScale)
	{
		this.maxScale = maxScale;
		initView();
	}

	public String getFilePath()
	{
		return filePath;
	}

	public Uri getFileUri()
	{
		return fileUri;
	}

	public String getFileAssetName()
	{
		return fileAssetName;
	}

	public Bitmap getFileBitmap()
	{
		return fileBitmap;
	}

	public int getResId()
	{
		return resId;
	}

	public String getImageUrl()
	{
		return imageUrl;
	}

	public ScaleImageView getmScaleImageView()
	{
		return mScaleImageView;
	}
}
