package talex.zsw.basecore.view.recyleview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import talex.zsw.basecore.R;

public class SampleHeader extends RelativeLayout
{


	private int width, height;

	public SampleHeader(Context context)
	{
		super(context);
		init(context);
	}

	public SampleHeader(Context context, int width, int height)
	{
		super(context);
		this.width = width;
		this.height = height;
		init(context);
	}

	public SampleHeader(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init(context);
	}

	public SampleHeader(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		init(context);
	}

	public void init(Context context)
	{
		inflate(context, R.layout.sample_header, this);

		if(width == 0 || height == 0)
		{
			setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		}
		else
		{
			setLayoutParams(new ViewGroup.LayoutParams(width, height));
		}
	}
}