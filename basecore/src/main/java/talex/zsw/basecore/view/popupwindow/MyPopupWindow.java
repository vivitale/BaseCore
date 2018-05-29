package talex.zsw.basecore.view.popupwindow;

import android.view.View;
import android.widget.PopupWindow;

/**
 * 作用: 可以监听消失事件的POP
 * 作者: 赵小白 email:edisonzsw@icloud.com 
 * 日期: 2017/8/31 16:50 
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MyPopupWindow extends PopupWindow
{
	private popListener popListener;

	public MyPopupWindow(View view, int matchParent, int wrapContent, boolean b)
	{
		super(view, matchParent, wrapContent, b);
	}

	@Override public void dismiss()
	{
		if(popListener != null)
		{
			popListener.onDismiss();
		}
		super.dismiss();
	}

	@Override public void showAsDropDown(View anchor)
	{
		if(popListener != null)
		{
			popListener.onShow();
		}
		super.showAsDropDown(anchor);
	}

	@Override public void showAsDropDown(View anchor, int xoff, int yoff)
	{
		if(popListener != null)
		{
			popListener.onShow();
		}
		super.showAsDropDown(anchor, xoff, yoff);
	}

	@Override public void showAsDropDown(View anchor, int xoff, int yoff, int gravity)
	{
		if(popListener != null)
		{
			popListener.onShow();
		}
		super.showAsDropDown(anchor, xoff, yoff, gravity);
	}

	@Override public void showAtLocation(View parent, int gravity, int x, int y)
	{
		if(popListener != null)
		{
			popListener.onShow();
		}
		super.showAtLocation(parent, gravity, x, y);
	}

	public MyPopupWindow.popListener getPopListener()
	{
		return popListener;
	}

	public void setPopListener(MyPopupWindow.popListener onDismissListener)
	{
		this.popListener = onDismissListener;
	}

	public interface popListener
	{
		public void onDismiss();

		public void onShow();
	}
}
