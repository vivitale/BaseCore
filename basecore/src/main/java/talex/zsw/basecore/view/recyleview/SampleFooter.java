package talex.zsw.basecore.view.recyleview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import talex.zsw.basecore.R;

public class SampleFooter extends RelativeLayout
{

	public SampleFooter(Context context)
	{
		super(context);
		init(context);
	}

	public SampleFooter(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init(context);
	}

	public SampleFooter(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		init(context);
	}

	public void init(Context context)
	{

		inflate(context, R.layout.sample_footer, this);
	}
}