package talex.zsw.basecore.view.other;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import talex.zsw.basecore.R;
import talex.zsw.basecore.interfaces.ScrollToScreenCallback;


/**
 * @author zhaosw email:vvtale@gmail.com
 * PageControlView 
 * 图片下面小圆圈的设置
 * 2013-12-4 下午1:48:12  
 */
public class PageControlView extends LinearLayout implements ScrollToScreenCallback
{
	/** Context对象 **/
	private Context context;
	/** 圆圈的数量 **/
	private int count;

	public PageControlView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.context = context;
	}

	/** 回调函数 **/
	@Override public void callback(int currentIndex)
	{
		generatePageControl(currentIndex);
	}

	/** 设置被选中圆圈 **/
	public void generatePageControl(int currentIndex)
	{
		this.removeAllViews();
		for(int i = 0; i < this.count; i++)
		{
			ImageView iv = new ImageView(context);
			if(currentIndex == i)
			{
				iv.setImageResource(R.drawable.bg_oval_blue);
			}
			else
			{
				iv.setImageResource(R.drawable.bg_oval_gray);
			}
			iv.setLayoutParams(new LayoutParams(1, 0));
			LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			layoutParams.leftMargin = 8;
			layoutParams.rightMargin = 8;
			iv.setLayoutParams(layoutParams);
			this.addView(iv);
		}
	}

	/** 设置圆圈数量 **/
	public void setCount(int count)
	{
		this.count = count;
	}
}