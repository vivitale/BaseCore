package talex.zsw.basecore.view.recyleview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;


/**
 * 作用: 简易的计算显示RecyclerView间隔
 * 作者: TALE(赵小白) email:vvtale@gmail.com  
 * 日期: 2015-09-08 9:59 
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class DividerGridItemDecoration extends RecyclerView.ItemDecoration
{
	private Drawable mDivider;
	private int dividerHeight = 1;
	private int dividerWidth = 1;

	/** 显示一个纯色的间隔符 */
	public DividerGridItemDecoration(int resource)
	{
		mDivider = new ColorDrawable(resource);
	}

	public DividerGridItemDecoration(Context context, int resource, int height, int width,
									 boolean isColor)
	{
		this.dividerHeight = height;
		this.dividerWidth = width;
		if (isColor)
		{
			mDivider = new ColorDrawable(resource);
		}
		else
		{
			Resources resources = context.getResources();
			mDivider = resources.getDrawable(resource);
		}
	}

	@Override
	public void onDraw(Canvas c, RecyclerView parent, State state)
	{
		drawHorizontal(c, parent);
		drawVertical(c, parent);
	}

	private int getSpanCount(RecyclerView parent)
	{
		// 列数
		int spanCount = -1;
		LayoutManager layoutManager = parent.getLayoutManager();
		if (layoutManager instanceof GridLayoutManager)
		{
			spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
		}
		else if (layoutManager instanceof StaggeredGridLayoutManager)
		{
			spanCount = ((StaggeredGridLayoutManager) layoutManager)
				.getSpanCount();
		}
		return spanCount;
	}

	public void drawHorizontal(Canvas c, RecyclerView parent)
	{
		int childCount = parent.getChildCount();
		for (int i = 0; i < childCount; i++)
		{
			final View child = parent.getChildAt(i);
			final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
				.getLayoutParams();
			final int left = child.getLeft() - params.leftMargin;
			final int right = child.getRight() + params.rightMargin
				+ dividerWidth;
			final int top = child.getBottom() + params.bottomMargin;
			final int bottom = top + dividerHeight;
			mDivider.setBounds(left, top, right, bottom);
			mDivider.draw(c);
		}
	}

	public void drawVertical(Canvas c, RecyclerView parent)
	{
		final int childCount = parent.getChildCount();
		for (int i = 0; i < childCount; i++)
		{
			final View child = parent.getChildAt(i);
			final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
				.getLayoutParams();
			final int top = child.getTop() - params.topMargin;
			final int bottom = child.getBottom() + params.bottomMargin;
			final int left = child.getRight() + params.rightMargin;
			final int right = left + dividerWidth;
			mDivider.setBounds(left, top, right, bottom);
			mDivider.draw(c);
		}
	}

	private boolean isLastColum(RecyclerView parent, int pos, int spanCount,
								int childCount)
	{
		LayoutManager layoutManager = parent.getLayoutManager();
		if (layoutManager instanceof GridLayoutManager)
		{
			if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
			{
				return true;
			}
		}
		else if (layoutManager instanceof StaggeredGridLayoutManager)
		{
			int orientation = ((StaggeredGridLayoutManager) layoutManager)
				.getOrientation();
			if (orientation == StaggeredGridLayoutManager.VERTICAL)
			{
				if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
				{
					return true;
				}
			}
			else
			{
				childCount = childCount - childCount % spanCount;
				if (pos >= childCount)// 如果是最后一列，则不需要绘制右边
				{
					return true;
				}
			}
		}
		return false;
	}

	private boolean isLastRaw(RecyclerView parent, int pos, int spanCount,
							  int childCount)
	{
		LayoutManager layoutManager = parent.getLayoutManager();
		if (layoutManager instanceof GridLayoutManager)
		{
			childCount = childCount - childCount % spanCount;
			if (pos >= childCount)// 如果是最后一行，则不需要绘制底部
			{
				return true;
			}
		}
		else if (layoutManager instanceof StaggeredGridLayoutManager)
		{
			int orientation = ((StaggeredGridLayoutManager) layoutManager)
				.getOrientation();
			// StaggeredGridLayoutManager 且纵向滚动
			if (orientation == StaggeredGridLayoutManager.VERTICAL)
			{
				childCount = childCount - childCount % spanCount;
				// 如果是最后一行，则不需要绘制底部
				if (pos >= childCount)
				{
					return true;
				}
			}
			else
			// StaggeredGridLayoutManager 且横向滚动
			{
				// 如果是最后一行，则不需要绘制底部
				if ((pos + 1) % spanCount == 0)
				{
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void getItemOffsets(Rect outRect, int itemPosition,
							   RecyclerView parent)
	{
		int spanCount = getSpanCount(parent);
		int childCount = parent.getAdapter().getItemCount();
		if (isLastRaw(parent, itemPosition, spanCount, childCount))// 如果是最后一行，则不需要绘制底部
		{
			outRect.set(0, 0, dividerWidth, 0);
		}
		else if (isLastColum(parent, itemPosition, spanCount, childCount))// 如果是最后一列，则不需要绘制右边
		{
			outRect.set(0, 0, 0, dividerHeight);
		}
		else
		{
			outRect.set(0, 0, dividerWidth, dividerHeight);
		}
	}
}
