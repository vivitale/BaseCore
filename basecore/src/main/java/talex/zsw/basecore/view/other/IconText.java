package talex.zsw.basecore.view.other;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.florent37.viewanimator.ViewAnimator;

import talex.zsw.basecore.R;
import talex.zsw.basecore.util.DimenTool;
import talex.zsw.basecore.view.textview.autofit.AutofitTextView;


/**
 * 带有icon的TextView
 */
public class IconText extends RelativeLayout
{
	private TextView mTextView;
	private AutofitTextView mTvBadge;
	private ImageView mImageView, mIvBadge;
	private LinearLayout mLinearLayout;

	public static final int DEFAULT_TEXT_SIZE = DimenTool.sp2px(14.5f);
	public static final int DEFAULT_TEXT_COLOR = 0xff404040;
	public static final int DEFAULT_SELECTED_COLOR = 0x00000000;
	public static final int DEFAULT_UNSELECTED_COLOR = 0x00000000;
	public static final int DEFAULT_PADDING = 6;

	// TextView属性
	private String text = "";
	private int textSelectedColor, textUnselectedColor;
	private float textSize = 14.f;
	// ImageView 属性
	private Drawable iconSelected, iconUnselected;
	private float iconWidth, iconHeight;
	private boolean iconWrap = true;
	// LinearLayout 属性
	private int selectedBG;
	private int unselectedBG;
	private int padding;
	private boolean layoutMatch = false;
	// Badge 属性
	private String badgeText = "";
	private boolean badgeTextShow, badgeIconShow;
	// 整体的属性
	private boolean selected = false;

	public IconText(Context context)
	{
		this(context, null);
	}

	public IconText(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public IconText(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs)
	{
		View rootView = LayoutInflater.from(context).inflate(R.layout.image_text_view, this);

		mTextView = (TextView) rootView.findViewById(R.id.mTextView);
		mTvBadge = (AutofitTextView) rootView.findViewById(R.id.mTvBadge);
		mImageView = (ImageView) rootView.findViewById(R.id.mImageView);
		mIvBadge = (ImageView) rootView.findViewById(R.id.mIvBadge);
		mLinearLayout = (LinearLayout) rootView.findViewById(R.id.mLinearLayout);

		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IconText);
		// TextView
		text = typedArray.getString(R.styleable.IconText_android_text);
		textSize = typedArray.getDimension(R.styleable.IconText_android_textSize, DEFAULT_TEXT_SIZE);
		textSelectedColor = typedArray.getColor(R.styleable.IconText_IT_text_selected_color, DEFAULT_TEXT_COLOR);
		textUnselectedColor = typedArray.getColor(R.styleable.IconText_IT_text_unselected_color, DEFAULT_TEXT_COLOR);
		// ImageView
		iconSelected = typedArray.getDrawable(R.styleable.IconText_IT_icon_selected);
		iconUnselected = typedArray.getDrawable(R.styleable.IconText_IT_icon_unselected);
		iconWidth = typedArray.getDimension(R.styleable.IconText_IT_icon_width, 0);
		iconHeight = typedArray.getDimension(R.styleable.IconText_IT_icon_height, 0);
		iconWrap = typedArray.getBoolean(R.styleable.IconText_IT_icon_wrap, true);
		// LinearLayout
		selectedBG = typedArray.getColor(R.styleable.IconText_IT_selected_background, DEFAULT_SELECTED_COLOR);
		unselectedBG = typedArray.getColor(R.styleable.IconText_IT_unselected_background, DEFAULT_UNSELECTED_COLOR);
		padding = typedArray.getDimensionPixelSize(R.styleable.IconText_IT_padding, DEFAULT_PADDING);
		layoutMatch = typedArray.getBoolean(R.styleable.IconText_IT_match, false);
		// Badge
		badgeText = typedArray.getString(R.styleable.IconText_IT_badge_text);
		badgeTextShow = typedArray.getBoolean(R.styleable.IconText_IT_badge_text_show, false);
		badgeIconShow = typedArray.getBoolean(R.styleable.IconText_IT_badge_icon_show, false);
		// 整体的属性
		selected = typedArray.getBoolean(R.styleable.IconText_IT_selected, false);
		// ------------------------------------------------------------------------
		refreshView();
		typedArray.recycle();
	}

	public void refreshView()
	{
		// TextView
		mTextView.setText(text);
		mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
		// ImageView
		ViewGroup.LayoutParams params = mImageView.getLayoutParams();
		if(iconWidth > 0)
		{
			params.width = (int) iconWidth;
		}
		else
		{
			params.width = LayoutParams.WRAP_CONTENT;
		}
		if(iconHeight > 0)
		{
			params.height = (int) iconHeight;
		}
		else if(iconWrap)
		{
			params.height = LayoutParams.WRAP_CONTENT;
		}
		else
		{
			params.height = LayoutParams.MATCH_PARENT;
		}
		mImageView.setLayoutParams(params);
		// LinearLayout
		mLinearLayout.setPadding(0, padding, 0, padding);
		if(layoutMatch)
		{
			ViewGroup.LayoutParams layoutParams = mLinearLayout.getLayoutParams();
			layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
			mLinearLayout.setLayoutParams(layoutParams);
		}
		// Badge
		mTvBadge.setText(badgeText);
		if(badgeTextShow)
		{
			mTvBadge.setVisibility(View.VISIBLE);
		}
		if(badgeIconShow)
		{
			mIvBadge.setVisibility(View.VISIBLE);
		}
		// 整体的属性
		setSelected(selected);
		if(selected)
		{
			mTextView.setTextColor(textSelectedColor);
			if(iconSelected != null)
			{
				mImageView.setImageDrawable(iconSelected);
			}
			mLinearLayout.setBackgroundColor(selectedBG);
		}
		else
		{
			mTextView.setTextColor(textUnselectedColor);
			if(iconUnselected != null)
			{
				mImageView.setImageDrawable(iconUnselected);
			}
			mLinearLayout.setBackgroundColor(unselectedBG);
		}
	}

	public void setText(String string)
	{
		mTextView.setText(string);
	}

	public void setTextSize(float size)
	{
		mTextView.setTextSize(size);
	}

	public void setPadding(int padding)
	{
		mLinearLayout.setPadding(0, padding, 0, padding);
	}

	public void setIcon(int res)
	{
		mImageView.setImageResource(res);
	}

	public boolean getSelected()
	{
		return selected;
	}

	public void toggle(boolean doAnim)
	{
		boolean flag = !selected;
		setSelected(flag);
	}

	public void setSelected(boolean selected)
	{
		this.selected = selected;
		if(selected)
		{
			if(iconSelected != null)
			{
				mImageView.setImageDrawable(iconSelected);
			}
			ViewAnimator
				.animate(mTextView)
				.textColor(textUnselectedColor, textSelectedColor)
				.andAnimate(mLinearLayout)
				.backgroundColor(unselectedBG, selectedBG)
				.andAnimate(mImageView)
				.scale(1.0f, 1.2f, 1.0f)
				.duration(200)
				.start();
		}
		else
		{
			if(iconUnselected != null)
			{
				mImageView.setImageDrawable(iconUnselected);
			}
			ViewAnimator
				.animate(mTextView)
				.textColor(textUnselectedColor, textUnselectedColor)
				.andAnimate(mLinearLayout)
				.backgroundColor(unselectedBG, unselectedBG)
				.andAnimate(mImageView)
				.scale(1.0f, 1.0f)
				.duration(200)
				.start();
		}
	}

	public void showBadge()
	{
		badgeIconShow = true;
		mIvBadge.setVisibility(View.VISIBLE);
		badgeIconShow = false;
		mIvBadge.setVisibility(View.GONE);
	}

	public void showBadge(String text)
	{
		badgeTextShow = true;
		mTvBadge.setVisibility(View.VISIBLE);
		badgeTextShow = false;
		mTvBadge.setVisibility(View.GONE);
		mTvBadge.setText(text);
	}

	public void hideBadge()
	{
		badgeIconShow = false;
		mIvBadge.setVisibility(View.GONE);
		badgeTextShow = false;
		mTvBadge.setVisibility(View.GONE);
	}

	public boolean isShowing()
	{
		return badgeIconShow || badgeTextShow;
	}

	// ------------------------ get set --------------------

	public TextView getmTextView()
	{
		return mTextView;
	}

	public void setmTextView(TextView mTextView)
	{
		this.mTextView = mTextView;
	}

	public AutofitTextView getmTvBadge()
	{
		return mTvBadge;
	}

	public void setmTvBadge(AutofitTextView mTvBadge)
	{
		this.mTvBadge = mTvBadge;
	}

	public ImageView getmImageView()
	{
		return mImageView;
	}

	public void setmImageView(ImageView mImageView)
	{
		this.mImageView = mImageView;
	}

	public ImageView getmIvBadge()
	{
		return mIvBadge;
	}

	public void setmIvBadge(ImageView mIvBadge)
	{
		this.mIvBadge = mIvBadge;
	}

	public LinearLayout getmLinearLayout()
	{
		return mLinearLayout;
	}

	public void setmLinearLayout(LinearLayout mLinearLayout)
	{
		this.mLinearLayout = mLinearLayout;
	}

	public String getText()
	{
		return text;
	}

	public int getTextSelectedColor()
	{
		return textSelectedColor;
	}

	public void setTextSelectedColor(int textSelectedColor)
	{
		this.textSelectedColor = textSelectedColor;
	}

	public int getTextUnselectedColor()
	{
		return textUnselectedColor;
	}

	public void setTextUnselectedColor(int textUnselectedColor)
	{
		this.textUnselectedColor = textUnselectedColor;
	}

	public float getTextSize()
	{
		return textSize;
	}

	public Drawable getIconSelected()
	{
		return iconSelected;
	}

	public void setIconSelected(Drawable iconSelected)
	{
		this.iconSelected = iconSelected;
	}

	public Drawable getIconUnselected()
	{
		return iconUnselected;
	}

	public void setIconUnselected(Drawable iconUnselected)
	{
		this.iconUnselected = iconUnselected;
	}

	public float getIconWidth()
	{
		return iconWidth;
	}

	public void setIconWidth(float iconWidth)
	{
		this.iconWidth = iconWidth;
	}

	public float getIconHeight()
	{
		return iconHeight;
	}

	public void setIconHeight(float iconHeight)
	{
		this.iconHeight = iconHeight;
	}

	public int getSelectedBG()
	{
		return selectedBG;
	}

	public void setSelectedBG(int selectedBG)
	{
		this.selectedBG = selectedBG;
	}

	public int getUnselectedBG()
	{
		return unselectedBG;
	}

	public void setUnselectedBG(int unselectedBG)
	{
		this.unselectedBG = unselectedBG;
	}

	public int getPadding()
	{
		return padding;
	}

	public String getBadgeText()
	{
		return badgeText;
	}

	public void setBadgeText(String badgeText)
	{
		this.badgeText = badgeText;
	}

	public boolean isBadgeTextShow()
	{
		return badgeTextShow;
	}

	public void setBadgeTextShow(boolean badgeTextShow)
	{
		this.badgeTextShow = badgeTextShow;
	}

	public boolean isBadgeIconShow()
	{
		return badgeIconShow;
	}

	public void setBadgeIconShow(boolean badgeIconShow)
	{
		this.badgeIconShow = badgeIconShow;
	}

	public boolean isIconWrap()
	{
		return iconWrap;
	}

	public void setIconWrap(boolean iconWrap)
	{
		this.iconWrap = iconWrap;
	}
}
