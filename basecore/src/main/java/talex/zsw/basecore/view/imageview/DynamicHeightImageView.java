package talex.zsw.basecore.view.imageview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 保持一定宽高比的图片
 */
@SuppressLint("AppCompatCustomView")
public class DynamicHeightImageView extends ImageView
{

	private double mHeightRatio= 1;

	public DynamicHeightImageView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public DynamicHeightImageView(Context context)
	{
		super(context);
	}

	public DynamicHeightImageView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	public void setHeightRatio(double ratio)
	{
		if (ratio != mHeightRatio)
		{
			mHeightRatio = ratio;
			requestLayout();
		}
	}

	public double getHeightRatio()
	{
		return mHeightRatio;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		if (mHeightRatio > 0.0)
		{
			// set the image views size
			int width = MeasureSpec.getSize(widthMeasureSpec);
			int height = (int) (width * mHeightRatio);
			setMeasuredDimension(width, height);
		}
		else
		{
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}

	@Override
	protected void onDetachedFromWindow()
	{
		super.onDetachedFromWindow();
		setImageDrawable(null);
	}
}
