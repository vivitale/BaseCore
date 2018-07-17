package talex.zsw.basecore.view.other;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import talex.zsw.basecore.R;

public class TitleBar2 extends RelativeLayout
{
	private ImageView vIvBack;
	private ImageView vIvCenter;
	private ImageView vIvRight2;
	private ImageView vIvRight;
	private TextView vTvLeft;
	private TextView vTvBack;
	private TextView vTvTitle;
	private TextView vTvRight;
	private RelativeLayout layout;

	public TitleBar2(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		initView(context);
	}

	public TitleBar2(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initView(context);
	}

	public TitleBar2(Context context)
	{
		super(context);
		initView(context);
	}

	private void initView(final Context context)
	{
		View rootView = LayoutInflater.from(context).inflate(R.layout.view_titlebar2, this);

		vIvBack = (ImageView) rootView.findViewById(R.id.vIvBack);
		vIvCenter = (ImageView) rootView.findViewById(R.id.vIvCenter);
		vIvRight2 = (ImageView) rootView.findViewById(R.id.vIvRight2);
		vIvRight = (ImageView) rootView.findViewById(R.id.vIvRight);
		vTvLeft = (TextView) rootView.findViewById(R.id.vTvLeft);
		vTvBack = (TextView) rootView.findViewById(R.id.vTvBack);
		vTvTitle = (TextView) rootView.findViewById(R.id.vTvTitle);
		vTvRight = (TextView) rootView.findViewById(R.id.vTvRight);
		layout = (RelativeLayout) rootView.findViewById(R.id.layout);

		vIvBack.setOnClickListener(new OnClickListener()
		{
			@Override public void onClick(View v)
			{
				((Activity) context).finish();
			}
		});

		vTvBack.setOnClickListener(new OnClickListener()
		{
			@Override public void onClick(View v)
			{
				((Activity) context).finish();
			}
		});
	}

	/**
	 * 设置标题
	 */
	public void setTitle(String string)
	{
		vTvTitle.setText(string);
	}

	/**
	 * 设置标题
	 */
	public void setTitle(int resid)
	{
		vTvTitle.setText(resid);
	}

	/**
	 * 设置左侧返回按钮点击事件
	 */
	public void setBackOnclickListener(OnClickListener listener)
	{
		if(listener == null)
		{
			vIvBack.setClickable(false);
			vTvBack.setClickable(false);
		}
		else
		{
			vIvBack.setOnClickListener(listener);
			vTvBack.setOnClickListener(listener);
		}
	}

	/**
	 * 隐藏左侧返回按钮
	 */
	public void hideBackButton()
	{
		vIvBack.setVisibility(View.GONE);
		vTvBack.setVisibility(View.GONE);
	}

	/**
	 * 设置左侧返回按钮图片
	 */
	public void setBackSrc(int resid)
	{
		vIvBack.setVisibility(View.VISIBLE);
		vTvBack.setVisibility(View.GONE);
		vIvBack.setImageResource(resid);
	}

	/**
	 * 设置左侧返回按钮图片
	 */
	public void setBackText(String string)
	{
		vTvBack.setVisibility(View.VISIBLE);
		vIvBack.setVisibility(View.GONE);
		vTvBack.setText(string);
	}

	/**
	 * 设置右边按钮点击事件
	 */
	public void setRightIV(int resid, OnClickListener listener)
	{
		vIvRight.setVisibility(View.VISIBLE);
		vIvRight.setImageResource(resid);
		vIvRight.setOnClickListener(listener);
	}

	public void disBadge()
	{

	}

	/**
	 * 设置右边按钮点击事件
	 */
	public void setRightIV2(int resid, OnClickListener listener)
	{
		vIvRight2.setVisibility(View.VISIBLE);
		vIvRight2.setImageResource(resid);
		vIvRight2.setOnClickListener(listener);
	}

	/**
	 * 设置中间按钮点击事件
	 */
	public void setCenterIV(int resid, OnClickListener listener)
	{
		vIvCenter.setVisibility(View.VISIBLE);
		vIvCenter.setImageResource(resid);
		vIvCenter.setOnClickListener(listener);
	}


	/**
	 * 设置右边文字和点击事件
	 */
	public void setRightTV(String string, OnClickListener listener)
	{
		vTvRight.setVisibility(View.VISIBLE);
		vTvRight.setText(string);
		vTvRight.setOnClickListener(listener);
	}

	/**
	 * 设置右边文字和点击事件
	 */
	public void setRightTV(int string, OnClickListener listener)
	{
		vTvRight.setVisibility(View.VISIBLE);
		vTvRight.setText(string);
		vTvRight.setOnClickListener(listener);
	}

	/**
	 * 设置左边文字和点击事件
	 */
	public void setLeftTV(String string, OnClickListener listener)
	{
		vIvBack.setVisibility(View.GONE);
		vTvBack.setVisibility(View.GONE);
		vTvLeft.setVisibility(View.VISIBLE);
		vTvLeft.setText(string);
		vTvLeft.setOnClickListener(listener);
	}

	/**
	 * 设置左边文字和点击事件
	 */
	public void setLeftTV(int string, OnClickListener listener)
	{
		vIvBack.setVisibility(View.GONE);
		vTvBack.setVisibility(View.GONE);
		vTvLeft.setVisibility(View.VISIBLE);
		vTvLeft.setText(string);
		vTvLeft.setOnClickListener(listener);
	}

	public void setBackgroundColor(int color)
	{
		layout.setBackgroundColor(color);
	}

	public ImageView getvIvBack()
	{
		return vIvBack;
	}

	public ImageView getvIvCenter()
	{
		return vIvCenter;
	}

	public ImageView getvIvRight2()
	{
		return vIvRight2;
	}

	public ImageView getvIvRight()
	{
		return vIvRight;
	}

	public TextView getvTvLeft()
	{
		return vTvLeft;
	}

	public TextView getvTvBack()
	{
		return vTvBack;
	}

	public TextView getvTvTitle()
	{
		return vTvTitle;
	}

	public TextView getvTvRight()
	{
		return vTvRight;
	}

	public RelativeLayout getLayout()
	{
		return layout;
	}
	// -------------------------------------------------------
}
