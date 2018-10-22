package talex.zsw.basecore.view.other.nicespinner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.nineoldandroids.animation.ObjectAnimator;

import java.util.ArrayList;

import talex.zsw.basecore.R;
import talex.zsw.basecore.util.DimenTool;


/**
 * @author angelo.marchesin
 */
@SuppressLint("AppCompatCustomView")
@SuppressWarnings("unused")
public class NiceSpinner extends TextView
{

	private static final int MAX_LEVEL = 10000;
	private static final int DEFAULT_ELEVATION = 16;
	private static final String INSTANCE_STATE = "instance_state";
	private static final String SELECTED_INDEX = "selected_index";
	private static final String IS_POPUP_SHOWING = "is_popup_showing";

	private int mSelectedIndex;
	private Drawable mDrawable;
	private PopupWindow mPopup;
	private ListView mListView;
	private NiceSpinnerBaseAdapter mAdapter;
	private AdapterView.OnItemClickListener mOnItemClickListener;
	private AdapterView.OnItemSelectedListener mOnItemSelectedListener;

	@SuppressWarnings("ConstantConditions") public NiceSpinner(Context context)
	{
		super(context);
		init(context, null);
	}

	public NiceSpinner(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init(context, attrs);
	}

	public NiceSpinner(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	@Override public Parcelable onSaveInstanceState()
	{
		Bundle bundle = new Bundle();
		bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
		bundle.putInt(SELECTED_INDEX, mSelectedIndex);

		if(mPopup != null)
		{
			bundle.putBoolean(IS_POPUP_SHOWING, mPopup.isShowing());
			dismissDropDown();
		}

		return bundle;
	}

	@Override public void onRestoreInstanceState(Parcelable savedState)
	{
		if(savedState instanceof Bundle)
		{
			Bundle bundle = (Bundle) savedState;

			mSelectedIndex = bundle.getInt(SELECTED_INDEX);

			if(mAdapter != null)
			{
				setText(mAdapter.getItemInDataset(mSelectedIndex).toString());
				mAdapter.notifyItemSelected(mSelectedIndex);
			}

			if(bundle.getBoolean(IS_POPUP_SHOWING))
			{
				if(mPopup != null)
				{
					// Post the show request into the looper to avoid bad token
					// exception
					post(new Runnable()
					{
						@Override public void run()
						{
							showDropDown();
						}
					});
				}
			}

			savedState = bundle.getParcelable(INSTANCE_STATE);
		}

		super.onRestoreInstanceState(savedState);
	}

	@SuppressLint("NewApi") private void init(Context context, AttributeSet attrs)
	{
		Resources resources = getResources();
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NiceSpinner);
		int defaultPadding = DimenTool.getPxById(R.dimen.dp_12);
		int halfPadding = DimenTool.getPxById(R.dimen.dp_6);

		setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		setMaxLines(1);
		setEllipsize(TextUtils.TruncateAt.END);
		setPadding(defaultPadding, halfPadding, defaultPadding, halfPadding);
		setClickable(true);
		setBackgroundResource(R.color.transparent);
		setTextSize(getTextSize()+1);

		mListView = new ListView(context);
		mListView.setDivider(null);
		mListView.setItemsCanFocus(true);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				if(position >= mSelectedIndex && position < mAdapter.getCount())
				{
					position++;
				}

				if(mOnItemClickListener != null)
				{
					mOnItemClickListener.onItemClick(parent, view, position, id);
				}

				if(mOnItemSelectedListener != null)
				{
					mOnItemSelectedListener.onItemSelected(parent, view, position, id);
				}

				mAdapter.notifyItemSelected(position);
				mSelectedIndex = position;
				setText(mAdapter.getItemInDataset(position).toString());
				dismissDropDown();
			}
		});

		mPopup = new PopupWindow(context);
		mPopup.setContentView(mListView);
		mPopup.setOutsideTouchable(true);
		mPopup.setFocusable(true);

		if(Build.VERSION.SDK_INT >= 21)
		{
			mPopup.setElevation(16);
			mPopup.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.nice_spinner_drawable));
		}
		else
		{
			mPopup.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.nice_spinner_drop_down_shadow));
		}

		mPopup.setOnDismissListener(new PopupWindow.OnDismissListener()
		{
			@Override public void onDismiss()
			{
				animateArrow(false);
			}
		});

		Drawable basicDrawable = ContextCompat.getDrawable(context, R.drawable.nice_spinner_arrow);
		int resId = typedArray.getColor(R.styleable.NiceSpinner_NS_arrowTint, -1);

		if(basicDrawable != null)
		{
			mDrawable = DrawableCompat.wrap(basicDrawable);

			if(resId != -1)
			{
				DrawableCompat.setTint(mDrawable, resId);
			}
		}
		setCompoundDrawablesWithIntrinsicBounds(null, null, mDrawable, null);

		typedArray.recycle();
	}

	public <T> void attachDataSource(@NonNull ArrayList<T> dataset)
	{
		mAdapter = new NiceSpinnerAdapter<T>(getContext(), dataset);
		setAdapterInternal(mAdapter);
	}

	private void setAdapterInternal(@NonNull NiceSpinnerBaseAdapter adapter)
	{
		mListView.setAdapter(adapter);
		setText(adapter.getItemInDataset(mSelectedIndex).toString());
	}

	@Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		mPopup.setWidth(MeasureSpec.getSize(widthMeasureSpec));
		mPopup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override public boolean onTouchEvent(@NonNull MotionEvent event)
	{
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			if(!mPopup.isShowing())
			{
				showDropDown();
			}
			else
			{
				dismissDropDown();
			}
		}

		return super.onTouchEvent(event);
	}

	@SuppressLint("NewApi") private void animateArrow(boolean shouldRotateUp)
	{
		int start = shouldRotateUp ? 0 : MAX_LEVEL;
		int end = shouldRotateUp ? MAX_LEVEL : 0;
		ObjectAnimator animator = ObjectAnimator.ofInt(mDrawable, "level", start, end);
		animator.setInterpolator(new LinearOutSlowInInterpolator());
		animator.start();
	}

	public void dismissDropDown()
	{
		animateArrow(false);
		mPopup.dismiss();
	}

	public void showDropDown()
	{
		animateArrow(true);
		mPopup.showAsDropDown(this);
	}

	public void setTintColor(@ColorRes int resId)
	{
		if(mDrawable != null)
		{
			DrawableCompat.setTint(mDrawable, getResources().getColor(resId));
		}
	}

	public int getSelectedIndex()
	{
		return mSelectedIndex;
	}

	public void setSelectedIndex(int position)
	{
		if(mAdapter != null)
		{
			if(position >= 0 && position <= mAdapter.getCount())
			{
				mAdapter.notifyItemSelected(position);
				mSelectedIndex = position;
				setText(mAdapter.getItemInDataset(position).toString());
			}
			else
			{
				throw new IllegalArgumentException("Position must be lower than adapter count!");
			}
		}
	}

	public void addOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener)
	{
		mOnItemClickListener = onItemClickListener;
	}

	public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener onItemSelectedListener)
	{
		mOnItemSelectedListener = onItemSelectedListener;
	}

	public void setAdapter(@NonNull ListAdapter adapter)
	{
		mAdapter = new NiceSpinnerAdapterWrapper(getContext(), adapter);
		setAdapterInternal(mAdapter);
	}

	public ListView getmListView()
	{
		return mListView;
	}

	public void setmListView(ListView mListView)
	{
		this.mListView = mListView;
	}
}
