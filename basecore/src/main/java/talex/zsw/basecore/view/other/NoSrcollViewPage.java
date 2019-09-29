package talex.zsw.basecore.view.other;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 作用：TabLayout+ViewPager撤消左右滑动切换功能
 * 作者：赵小白 email:vvtale@gmail.com  
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class NoSrcollViewPage extends ViewPager
{
	private boolean isCanScroll = false;

	public NoSrcollViewPage(Context context)
	{
		super(context);
	}

	public NoSrcollViewPage(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public void setScanScroll(boolean isCanScroll)
	{
		this.isCanScroll = isCanScroll;
	}

	@Override public void scrollTo(int x, int y)
	{
		super.scrollTo(x, y);
	}

	@Override public boolean onTouchEvent(MotionEvent arg0)
	{
		if(isCanScroll)
		{
			return super.onTouchEvent(arg0);
		}
		else
		{
			return false;
		}
	}

	@Override public void setCurrentItem(int item, boolean smoothScroll)
	{
		super.setCurrentItem(item, smoothScroll);
	}

	@Override public void setCurrentItem(int item)
	{
		super.setCurrentItem(item);
	}

	@Override public boolean onInterceptTouchEvent(MotionEvent arg0)
	{
		if(isCanScroll)
		{
			return super.onInterceptTouchEvent(arg0);
		}
		else
		{
			return false;
		}
	}
}
