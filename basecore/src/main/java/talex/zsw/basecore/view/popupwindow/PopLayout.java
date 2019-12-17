package talex.zsw.basecore.view.popupwindow;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import talex.zsw.basecore.R;

/**
 * 简单弹出一个指定layout,不传则弹出简单TextView
 */
public class PopLayout extends PopupWindow
{
	// 坐标的位置（x、y）
	private final int[] mLocation = new int[2];
	private Context mContext;
	// 实例化一个矩形
	private Rect mRect = new Rect();

	// 定义弹窗
	private TextView tv_imply;

	public PopLayout(Context context, String str)
	{
		// 设置布局的参数
		this(context, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, str);
	}

	public PopLayout(Context context, int width, int height, String str)
	{
		this.mContext = context;

		// 设置可以获得焦点
		setFocusable(true);
		// 设置弹窗内可点击
		setTouchable(true);
		// 设置弹窗外可点击
		setOutsideTouchable(true);

		// 设置弹窗的宽度和高度
		setWidth(width);
		setHeight(height);

		setBackgroundDrawable(new BitmapDrawable());

		// 设置弹窗的布局界面
		setContentView(LayoutInflater.from(mContext).inflate(R.layout.pop_layout, null));

		initUI(str);
	}

	public PopLayout(Context context, int width, int height, int layout)
	{
		this.mContext = context;

		// 设置可以获得焦点
		setFocusable(true);
		// 设置弹窗内可点击
		setTouchable(true);
		// 设置弹窗外可点击
		setOutsideTouchable(true);

		// 设置弹窗的宽度和高度
		setWidth(width);
		setHeight(height);

		setBackgroundDrawable(new BitmapDrawable());

		// 设置弹窗的布局界面
		setContentView(LayoutInflater.from(mContext).inflate(layout, null));
	}

	/**
	 * 默认视图下的文本数据设置
	 */
	private void initUI(String str)
	{
		tv_imply = (TextView) getContentView().findViewById(R.id.tv_imply);
		tv_imply.setText(str);
	}

	public TextView getSimpleTextView()
	{
		return tv_imply;
	}

	/**
	 * 显示弹窗列表界面
	 */
	public void show(View view)
	{
		// 获得点击屏幕的位置坐标
		view.getLocationOnScreen(mLocation);
		// 设置矩形的大小
		mRect.set(mLocation[0], mLocation[1],
		          mLocation[0]+view.getWidth(), mLocation[1]+view.getHeight());
		// 显示弹窗的位置
		// showAtLocation(view, popupGravity, mScreenWidth - LIST_PADDING - (getWidth() / 2), mRect.bottom + VonUtils.dip2px(mContext, 7.5f));
		showAsDropDown(view);
	}

	/**
	 * 显示弹窗列表界面
	 *
	 * @param x x轴的偏移量
	 */
	public void show(View view, int x)
	{
		// 获得点击屏幕的位置坐标
		view.getLocationOnScreen(mLocation);
		// 设置矩形的大小
		mRect.set(mLocation[0], mLocation[1],
		          mLocation[0]+view.getWidth(), mLocation[1]+view.getHeight());
		// 显示弹窗的位置
		// showAtLocation(view, popupGravity, mScreenWidth - LIST_PADDING - (getWidth() / 2), mRect.bottom + VonUtils.dip2px(mContext, 7.5f));
		showAsDropDown(view, x, 0);
	}

	/**
	 * 显示弹窗列表界面
	 *
	 * @param x x轴的偏移量
	 * @param y y轴的偏移量
	 */
	public void show(View view, int x, int y)
	{
		// 获得点击屏幕的位置坐标
		view.getLocationOnScreen(mLocation);
		// 设置矩形的大小
		mRect.set(mLocation[0], mLocation[1],
		          mLocation[0]+view.getWidth(), mLocation[1]+view.getHeight());
		// 显示弹窗的位置
		// showAtLocation(view, popupGravity, mScreenWidth - LIST_PADDING - (getWidth() / 2), mRect.bottom + VonUtils.dip2px(mContext, 7.5f));
		showAsDropDown(view, x, y);
	}
}
