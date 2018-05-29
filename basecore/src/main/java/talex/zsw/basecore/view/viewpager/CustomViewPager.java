package talex.zsw.basecore.view.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 作用: 自动适应高度的ViewPager
 * 作者: 赵小白 email:edisonzsw@icloud.com 
 * 日期: 2017/3/22 21:30 
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class CustomViewPager extends ViewPager
{
	private int current;
	private int height = 0;
	/**
	 * 保存position与对于的View
	 */
	private HashMap<Integer, View> mChildrenViews = new LinkedHashMap<Integer, View>();

	public CustomViewPager(Context context)
	{
		this(context, null);
	}

	public CustomViewPager(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		if (mChildrenViews.size() > current)
		{
			View child = mChildrenViews.get(current);
			child
				.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			height = child.getMeasuredHeight();
		}
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	/**
	 * 在切换tab的时候，重置ViewPager的高度
	 */
	public void resetHeight(int current)
	{
		this.current = current;
		if (mChildrenViews.size() > current)
		{

			LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
			if (layoutParams == null)
			{
				layoutParams =
					new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
			}
			else
			{
				layoutParams.height = height;
			}
			setLayoutParams(layoutParams);
		}
	}

	public void setObjectForPosition(View view, int position)
	{
		mChildrenViews.put(position, view);
	}
}
