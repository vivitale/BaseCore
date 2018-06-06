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
	private List<LinearLayout> tabLayouts = new ArrayList<>();
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
			tabs.get(position).setCompoundDrawablePadding(3);
		}
		else
		{
			tabs.get(position).setTextColor(textSelectedColor);
			tabs
				.get(position)
				.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(menuSelectedIcon), null);
			tabs.get(position).setCompoundDrawablePadding(3);
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
	 */
	public void setDropDownMenu(List<String> tabTexts, List<View> popupViews, View contentView)
	{
		if(tabTexts.size() != popupViews.size())
		{
			throw new IllegalArgumentException("params not match, tabTexts.size() should be equal popupViews.size()");
		}

		setTabs(tabTexts);

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

	private void setTabs(List<String> tabTexts)
	{
		for(int i = 0; i < tabTexts.size(); i++)
		{
			final LinearLayout tabLayout = new LinearLayout(getContext());
			LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
			tabLayout.setOrientation(HORIZONTAL);
			tabLayout.setLayoutParams(params);
			//添加点击事件
			tabLayout.setOnClickListener(new OnClickListener()
			{
				@Override public void onClick(View v)
				{
					switchMenu(tabLayout);
				}
			});


			tabLayout.addView(getWeightView());

			final AutofitTextView tab = new AutofitTextView(getContext());
			tab.setSingleLine();
			tab.setEllipsize(TextUtils.TruncateAt.END);
			tab.setGravity(Gravity.CENTER);
			tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, menuTextSize);
			tab.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, DimenTool.dip2px(45)));
			tab.setTextColor(textUnselectedColor);
			tab.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(menuUnselectedIcon), null);
			tab.setCompoundDrawablePadding(3);
			tab.setText(tabTexts.get(i));
			tab.setPadding(dpTpPx(5), dpTpPx(0), dpTpPx(5), dpTpPx(0));

			tabLayout.addView(tab);
			tabs.add(tab);
			booleans.add(false);

			tabLayout.addView(getWeightView());
			//添加分割线
			if(i < tabTexts.size()-1)
			{
				View view = new View(getContext());
				view.setLayoutParams(new LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT));
				view.setBackgroundColor(dividerColor);
				tabMenuView.addView(view);
			}

			tabLayouts.add(tabLayout);
			tabMenuView.addView(tabLayout);
		}
	}

	private TextView getWeightView()
	{
		TextView view = new TextView(getContext());
		view.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
		return view;
	}

	/**
	 * 设置当前Tab的文字,需要在closeMenu前调用
	 */
	public void setCurTabText(String text)
	{
		if(current_tab_position > 0 && current_tab_position < tabs.size())
		{
			tabs.get(current_tab_position).setText(text);
		}
	}

	/**
	 * 设置当前Tab为选中状态,需要在closeMenu前调用
	 */
	public void setCurTabSelect()
	{
		if(current_tab_position > 0 && current_tab_position < tabs.size())
		{
			tabs.get(current_tab_position).setTextColor(textSelectedColor);
			tabs
				.get(current_tab_position)
				.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(menuSelectedIcon), null);
			tabs.get(current_tab_position).setCompoundDrawablePadding(3);
		}
	}

	/**
	 * 设置当前Tab为普通需要在closeMenu前调用
	 */
	public void setCurTabCommon()
	{
		if(current_tab_position > 0 && current_tab_position < tabs.size())
		{
			tabs.get(current_tab_position).setTextColor(textUnselectedColor);
			tabs
				.get(current_tab_position)
				.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(menuUnselectedIcon), null);
			tabs.get(current_tab_position).setCompoundDrawablePadding(3);
		}
	}

	/**
	 * 改变第 position 个Tab的文字
	 */
	public void setTabText(String text, int position)
	{
		if(position > 0 && position < tabs.size())
		{
			tabs.get(position).setText(text);
		}
	}

	/**
	 * 设置第 position 个Tab为选中状态
	 */
	public void setTabTextSelect(int position)
	{
		if(position > 0 && position < tabs.size())
		{
			tabs.get(position).setTextColor(textSelectedColor);
			tabs
				.get(position)
				.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(menuSelectedIcon), null);
			tabs.get(position).setCompoundDrawablePadding(3);
		}
	}

	/**
	 * 设置第 position 个Tab为普通状态
	 */
	public void setTabTextCommon(int position)
	{
		if(position > 0 && position < tabs.size())
		{
			tabs.get(position).setTextColor(textUnselectedColor);
			tabs
				.get(position)
				.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(menuUnselectedIcon), null);
			tabs.get(position).setCompoundDrawablePadding(3);
		}
	}

	/**
	 * 设置是否可点击
	 */
	public void setTabClickable(boolean clickable)
	{
		for(int i = 0; i < tabLayouts.size(); i = i+2)
		{
			tabLayouts.get(i).setClickable(clickable);
		}
	}

	/**
	 * 设置第 position 个Tab是否可点击
	 */
	public void setTabClickable(boolean clickable, int position)
	{
		if(position > 0 && position < tabLayouts.size())
		{
			tabLayouts.get(position).setClickable(clickable);
		}
	}

	/**
	 * 关闭菜单
	 */
	public void closeMenu()
	{
		for(int i = 0; i < tabLayouts.size(); i++)
		{
			tabLayouts.get(i).setBackgroundColor(Color.WHITE);
		}

		setTabColorByBooleans(current_tab_position);

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
	 */
	public boolean isShowing()
	{
		return current_tab_position != -1;
	}

	private void setTabColorByBooleans(int position)
	{
		if(booleans.get(position))
		{
			tabs.get(position).setTextColor(textSelectedColor);
			tabs
				.get(position)
				.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(menuSelectedIcon), null);
			tabs.get(position).setCompoundDrawablePadding(3);
		}
		else
		{
			tabs.get(position).setTextColor(textUnselectedColor);
			tabs
				.get(position)
				.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(menuUnselectedIcon), null);
			tabs.get(position).setCompoundDrawablePadding(3);
		}
	}

	/**
	 * 切换菜单
	 */
	private void switchMenu(LinearLayout target)
	{
		for(int i = 0; i < tabLayouts.size(); i++)
		{
			tabLayouts.get(i).setBackgroundColor(Color.WHITE);
			tabs.get(i).setBackgroundColor(Color.WHITE);

			if(target == tabLayouts.get(i))
			{
				if(current_tab_position == i)
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
					}
					popupMenuViews.getChildAt(i).setVisibility(View.VISIBLE);

					current_tab_position = i;
					tabs.get(i).setTextColor(textSelectedColor);
					tabs
						.get(i)
						.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(menuSelectedIcon), null);
					tabs.get(i).setCompoundDrawablePadding(3);
					tabs.get(i).setBackgroundColor(Color.WHITE);
				}
			}
			else
			{
				setTabColorByBooleans(i);
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
