package talex.zsw.basecore.view.other.dropdownmenu;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import talex.zsw.basecore.R;
import talex.zsw.basecore.util.DimenTool;
import talex.zsw.basecore.view.textview.autofit.AutofitTextView;

public class DropDownMenu extends LinearLayout
{
	//顶部菜单布局
	private LinearLayout tabMenuView;
	//底部容器，包含popupMenuViews，maskView
	private FrameLayout containerView;
	//弹出菜单父布局
	private FrameLayout popupMenuViews;
	//遮罩半透明View，点击可关闭DropDownMenu
	private View maskView;
	//tabMenuView里面选中的tab位置，-1表示未选中
	private int current_tab_position = -1;

	//分割线颜色
	private int dividerColor = 0xffE0E0E0;
	//tab选中颜色
	private int textSelectedColor = 0xffEB6D27;
	//tab未选中颜色
	private int textUnselectedColor = 0xff444444;
	//遮罩颜色
	private int maskColor = 0x488888;
	//tab字体大小88
	private int menuTextSize = 14;

	//tab选中图标
	private int menuSelectedIcon = R.mipmap.drop_down_selected_icon;
	//tab未选中图标
	private int menuUnselectedIcon = R.mipmap.drop_down_unselected_icon;

	private List<TextView> tabs = new ArrayList<>();
	private List<TextView> weights = new ArrayList<>();
	private List<Boolean> booleans = new ArrayList<>();//设置未选中时,是否变色

	private onCloseMenuListener listener;
	private Context mContext;

	public void setOnCloseMenuListener(onCloseMenuListener listener)
	{
		this.listener = listener;
	}


	public DropDownMenu(Context context)
	{
		super(context, null);
	}

	public DropDownMenu(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init(context, attrs);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB) public DropDownMenu(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	/**
	 * 修改某个tab是否会变色 true:自动修改 false 始终选中
	 */
	public void changeSelect(int position, boolean flag)
	{
		booleans.remove(position);
		booleans.add(position, flag);
		if(flag)
		{
			tabs.get(position).setTextColor(textUnselectedColor);
			tabs
				.get(position)
				.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(menuUnselectedIcon), null);
		}
		else
		{
			tabs.get(position).setTextColor(textSelectedColor);
			tabs
				.get(position)
				.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(menuSelectedIcon), null);
		}
	}

	private void init(Context context, AttributeSet attrs)
	{
		this.mContext = context;
		setOrientation(VERTICAL);

		//为DropDownMenu添加自定义属性
		int menuBackgroundColor = 0xffffffff;
		int underlineColor = 0xffffffff;
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DropDownMenu);
		underlineColor = a.getColor(R.styleable.DropDownMenu_DD_underlineColor, underlineColor);
		dividerColor = a.getColor(R.styleable.DropDownMenu_DD_dividerColor, dividerColor);
		textSelectedColor = a.getColor(R.styleable.DropDownMenu_DD_textSelectedColor, textSelectedColor);
		textUnselectedColor = a.getColor(R.styleable.DropDownMenu_DD_textUnselectedColor, textUnselectedColor);
		menuBackgroundColor = a.getColor(R.styleable.DropDownMenu_DD_menuBackgroundColor, menuBackgroundColor);
		maskColor = a.getColor(R.styleable.DropDownMenu_DD_maskColor, maskColor);
		menuTextSize = a.getDimensionPixelSize(R.styleable.DropDownMenu_DD_menuTextSize, menuTextSize);
		menuSelectedIcon = a.getResourceId(R.styleable.DropDownMenu_DD_menuSelectedIcon, menuSelectedIcon);
		menuUnselectedIcon = a.getResourceId(R.styleable.DropDownMenu_DD_menuUnselectedIcon, menuUnselectedIcon);
		a.recycle();

		//初始化tabMenuView并添加到tabMenuView
		tabMenuView = new LinearLayout(context);
		LayoutParams params
			= new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		tabMenuView.setOrientation(HORIZONTAL);
		tabMenuView.setBackgroundColor(menuBackgroundColor);
		tabMenuView.setLayoutParams(params);
		addView(tabMenuView, 0);

		//为tabMenuView添加下划线
		View underLine = new View(getContext());
		underLine.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
		underLine.setBackgroundColor(underlineColor);
		addView(underLine, 1);

		//初始化containerView并将其添加到DropDownMenu
		containerView = new FrameLayout(context);
		containerView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
		addView(containerView, 2);
	}

	/**
	 * 初始化DropDownMenu
	 *
	 * @param tabTexts
	 * @param popupViews
	 * @param contentView
	 */
	public void setDropDownMenu(List<String> tabTexts, List<View> popupViews, View contentView)
	{
		if(tabTexts.size() != popupViews.size())
		{
			throw new IllegalArgumentException("params not match, tabTexts.size() should be equal popupViews.size()");
		}

		for(int i = 0; i < tabTexts.size(); i++)
		{
			addTab(tabTexts, i);
		}
		containerView.addView(contentView, 0);

		maskView = new View(getContext());
		maskView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
		maskView.setBackgroundColor(maskColor);
		maskView.setOnClickListener(new OnClickListener()
		{
			@Override public void onClick(View v)
			{
				if(popupMenuViews.getVisibility() == VISIBLE)
				{
					closeMenu();
				}
			}
		});
		containerView.addView(maskView, 1);
		maskView.setVisibility(GONE);

		popupMenuViews = new FrameLayout(getContext());
		popupMenuViews.setVisibility(GONE);
		containerView.addView(popupMenuViews, 2);

		for(int i = 0; i < popupViews.size(); i++)
		{
			popupViews
				.get(i)
				.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
			popupMenuViews.addView(popupViews.get(i), i);
		}
	}

	private void addTab(List<String> tabTexts, int i)
	{
		tabMenuView.addView(getWeightView());

		final AutofitTextView tab = new AutofitTextView(getContext());
		tab.setSingleLine();
		tab.setEllipsize(TextUtils.TruncateAt.END);
		tab.setGravity(Gravity.CENTER);
		tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, menuTextSize);
		tab.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, DimenTool.dip2px(45), 1.0f));
		tab.setTextColor(textUnselectedColor);
		tab.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(menuUnselectedIcon), null);
		tab.setText(tabTexts.get(i));
		tab.setPadding(dpTpPx(5), dpTpPx(0), dpTpPx(5), dpTpPx(0));
		//添加点击事件
		tab.setOnClickListener(new OnClickListener()
		{
			@Override public void onClick(View v)
			{
				switchMenu(tab);
			}
		});

		tabMenuView.addView(tab);
		tabs.add(tab);
		booleans.add(true);

		tabMenuView.addView(getWeightView());
		//添加分割线
		if(i < tabTexts.size()-1)
		{
			View view = new View(getContext());
			view.setLayoutParams(new LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT));
			view.setBackgroundColor(dividerColor);
			tabMenuView.addView(view);
		}
	}

	private TextView getWeightView()
	{
		TextView view = new TextView(getContext());
		view.setLayoutParams(new LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT));
		weights.add(view);
		return view;
	}

	/**
	 * 改变tab文字
	 *
	 * @param text
	 */
	public void setTabText(String text)
	{
		((TextView) tabMenuView.getChildAt(current_tab_position)).setText(text);
	}

	/**
	 * 改变tab文字颜色
	 */
	public void setTabTextSelect()
	{
		((TextView) tabMenuView.getChildAt(current_tab_position)).setTextColor(textSelectedColor);
	}

	/**
	 * 改变tab文字颜色
	 */
	public void setTabTextCommon()
	{
		((TextView) tabMenuView.getChildAt(current_tab_position)).setTextColor(textUnselectedColor);
	}

	/**
	 * 改变tab文字
	 * 第几个块
	 */
	public void setTabText(String text, int position)
	{
		int pos = (position-1)*4+1;
		((TextView) tabMenuView.getChildAt(pos)).setText(text);
	}

	/**
	 * 设置第几个Tab为选中文字颜色
	 */
	public void setTabTextSelect(int position)
	{
		int pos = (position-1)*4+1;
		((TextView) tabMenuView.getChildAt(pos)).setTextColor(textSelectedColor);
	}

	/**
	 * 设置第几个Tab为默认文字颜色
	 */
	public void setTabTextCommon(int position)
	{
		int pos = (position-1)*4+1;
		((TextView) tabMenuView.getChildAt(pos)).setTextColor(textUnselectedColor);
	}

	/**
	 * 设置是否可点击
	 */
	public void setTabClickable(boolean clickable)
	{
		for(int i = 0; i < tabMenuView.getChildCount(); i = i+2)
		{
			tabMenuView.getChildAt(i).setClickable(clickable);
		}
	}

	/**
	 * 关闭菜单
	 */
	public void closeMenu()
	{
		for(int i = 0; i < weights.size(); i++)
		{
			weights.get(i).setBackgroundColor(Color.WHITE);
		}
		for(int i = 0; i < tabs.size(); i++)
		{
			tabs.get(i).setBackgroundColor(Color.WHITE);
		}

		int position = (current_tab_position-1)/4;
		if(booleans.get(position))
		{
			tabs.get(position).setTextColor(textUnselectedColor);
			tabs
				.get(position)
				.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(menuUnselectedIcon), null);
		}
		else
		{
			tabs.get(position).setTextColor(textSelectedColor);
			tabs
				.get(position)
				.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(menuSelectedIcon), null);
		}

		popupMenuViews.setVisibility(View.GONE);
		popupMenuViews.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_menu_out));
		maskView.setVisibility(GONE);
		maskView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_mask_out));
		current_tab_position = -1;
		if(listener != null)
		{
			listener.onClose();
		}
	}

	/**
	 * DropDownMenu是否处于可见状态
	 *
	 * @return
	 */
	public boolean isShowing()
	{
		return current_tab_position != -1;
	}

	/**
	 * 切换菜单
	 *
	 * @param target
	 */
	private void switchMenu(View target)
	{
		//		System.out.println(current_tab_position);
		//		for (int i = 1; i < tabMenuView.getChildCount(); i = i + 4)
		//		{
		//			if (target == tabMenuView.getChildAt(i))
		//			{
		//				if (current_tab_position == i)
		//				{
		//					closeMenu();
		//				}
		//				else
		//				{
		//					if (current_tab_position == -1)
		//					{
		//						popupMenuViews.setVisibility(View.VISIBLE);
		//						popupMenuViews.setAnimation(
		//							AnimationUtils.loadAnimation(getContext(), R.anim.dd_menu_in));
		//						maskView.setVisibility(VISIBLE);
		//						maskView.setAnimation(
		//							AnimationUtils.loadAnimation(getContext(), R.anim.dd_mask_in));
		//						popupMenuViews.getChildAt((i - 1) / 4).setVisibility(View.VISIBLE);
		//					}
		//					else
		//					{
		//						popupMenuViews.getChildAt((i - 1) / 4).setVisibility(View.VISIBLE);
		//					}
		//					current_tab_position = i;
		//					((TextView) tabMenuView.getChildAt(i)).setTextColor(textSelectedColor);
		//					((TextView) tabMenuView.getChildAt(i))
		//						.setCompoundDrawablesWithIntrinsicBounds(null, null,
		//							getResources().getDrawable(menuSelectedIcon), null);
		//				}
		//			}
		//			else
		//			{
		//				((TextView) tabMenuView.getChildAt(i)).setTextColor(textUnselectedColor);
		//				((TextView) tabMenuView.getChildAt(i))
		//					.setCompoundDrawablesWithIntrinsicBounds(null, null,
		//						getResources().getDrawable(menuUnselectedIcon), null);
		//				popupMenuViews.getChildAt((i - 1) / 4).setVisibility(View.GONE);
		//			}
		//		}

		for(int i = 0; i < weights.size(); i++)
		{
			weights.get(i).setBackgroundColor(Color.WHITE);
		}

		for(int i = 0; i < tabs.size(); i++)
		{
			tabs.get(i).setBackgroundColor(Color.WHITE);
			if(target == tabs.get(i))
			{
				if(current_tab_position == (i*4+1))
				{
					closeMenu();
				}
				else
				{
					if(current_tab_position == -1)
					{
						popupMenuViews.setVisibility(View.VISIBLE);
						popupMenuViews.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_menu_in));
						maskView.setVisibility(VISIBLE);
						maskView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_mask_in));
						popupMenuViews.getChildAt(i).setVisibility(View.VISIBLE);
					}
					else
					{
						popupMenuViews.getChildAt(i).setVisibility(View.VISIBLE);
					}
					current_tab_position = i*4+1;
					tabs.get(i).setTextColor(textSelectedColor);
					tabs
						.get(i)
						.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(menuSelectedIcon), null);
					weights.get(i*2).setBackgroundColor(Color.WHITE);
					weights.get(i*2+1).setBackgroundColor(Color.WHITE);
					tabs.get(i).setBackgroundColor(Color.WHITE);
				}
			}
			else
			{
				if(booleans.get(i))
				{
					tabs.get(i).setTextColor(textUnselectedColor);
					tabs
						.get(i)
						.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(menuUnselectedIcon), null);
				}
				else
				{
					tabs.get(i).setTextColor(textSelectedColor);
					tabs
						.get(i)
						.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(menuSelectedIcon), null);
				}
				popupMenuViews.getChildAt(i).setVisibility(View.GONE);
			}
		}
	}

	public int dpTpPx(float value)
	{
		DisplayMetrics dm = getResources().getDisplayMetrics();
		return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, dm)+0.5);
	}

	public interface onCloseMenuListener
	{
		void onClose();
	}
}
