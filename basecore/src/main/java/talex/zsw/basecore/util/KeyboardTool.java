package talex.zsw.basecore.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.Field;

import talex.zsw.basecore.interfaces.OnSoftInputChangedListener;

/**
 * 软键盘工具
 */
public class KeyboardTool
{

	/**
	 * 避免输入法面板遮挡
	 * <p>在manifest.xml中activity中设置</p>
	 * <p>android:windowSoftInputMode="stateVisible|adjustResize"</p>
	 */

	/**
	 * 动态隐藏软键盘
	 */
	public static void hideSoftInput(Activity activity)
	{
		View view = activity.getWindow().peekDecorView();
		if(view != null)
		{
			InputMethodManager inputmanger
				= (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	/**
	 * 点击隐藏软键盘
	 */
	public static void hideKeyboard(Activity activity, View view)
	{
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	/**
	 * 动态隐藏软键盘
	 *
	 * @param context 上下文
	 * @param edit    输入框
	 */
	public static void hideSoftInput(Context context, EditText edit)
	{
		edit.clearFocus();
		InputMethodManager inputmanger = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputmanger.hideSoftInputFromWindow(edit.getWindowToken(), 0);
	}

	/**
	 * 点击屏幕空白区域隐藏软键盘（方法1）
	 * <p>在onTouch中处理，未获焦点则隐藏</p>
	 * <p>参照以下注释代码</p>
	 */
	public static void clickBlankArea2HideSoftInput()
	{
		Log.i("tips", "U should copy the following code.");
        /*
        @Override
        public boolean onTouchEvent (MotionEvent event){
            if (null != this.getCurrentFocus()) {
                InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
            }
            return super.onTouchEvent(event);
        }
        */
	}

	/**
	 * 点击屏幕空白区域隐藏软键盘（方法2）
	 * <p>根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘</p>
	 * <p>需重写dispatchTouchEvent</p>
	 * <p>参照以下注释代码</p>
	 */
	public static void clickBlankArea2HideSoftInput1()
	{
		Log.i("tips", "U should copy the following code.");
        /*
        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                View v = getCurrentFocus();
                if (isShouldHideKeyboard(v, ev)) {
                    hideKeyboard(v.getWindowToken());
                }
            }
            return super.dispatchTouchEvent(ev);
        }

        // 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
        private boolean isShouldHideKeyboard(View v, MotionEvent event) {
            if (v != null && (v instanceof EditText)) {
                int[] l = {0, 0};
                v.getLocationInWindow(l);
                int left = l[0],
                        top = l[1],
                        bottom = top + v.getHeight(),
                        right = left + v.getWidth();
                return !(event.getX() > left && event.getX() < right
                        && event.getY() > top && event.getY() < bottom);
            }
            return false;
        }

        // 获取InputMethodManager，隐藏软键盘
        private void hideKeyboard(IBinder token) {
            if (token != null) {
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        */
	}

	/**
	 * 动态显示软键盘
	 *
	 * @param context 上下文
	 * @param edit    输入框
	 */
	public static void showSoftInput(Context context, EditText edit)
	{
		edit.setFocusable(true);
		edit.setFocusableInTouchMode(true);
		edit.requestFocus();
		InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.showSoftInput(edit, 0);
	}

	/**
	 * 动态显示软键盘
	 *
	 * @param activity The activity.
	 */
	public static void showSoftInput(final Activity activity)
	{
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
		if(imm == null)
		{
			return;
		}
		View view = activity.getCurrentFocus();
		if(view == null)
		{
			view = new View(activity);
			view.setFocusable(true);
			view.setFocusableInTouchMode(true);
			view.requestFocus();
		}
		imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
	}

	/**
	 * 动态显示软键盘
	 *
	 * @param view The view.
	 */
	public static void showSoftInput(final View view)
	{
		InputMethodManager imm = (InputMethodManager) Tool.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		if(imm == null)
		{
			return;
		}
		view.setFocusable(true);
		view.setFocusableInTouchMode(true);
		view.requestFocus();
		imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
	}

	/**
	 * 切换键盘显示与否状态
	 *
	 * @param context 上下文
	 * @param edit    输入框
	 */
	public static void toggleSoftInput(Context context, EditText edit)
	{
		edit.setFocusable(true);
		edit.setFocusableInTouchMode(true);
		edit.requestFocus();
		InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
	}

	/**
	 * 切换键盘显示与否状态
	 */
	public static void toggleSoftInput()
	{
		InputMethodManager imm = (InputMethodManager) Tool.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		if(imm == null)
		{
			return;
		}
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
	}

	/**
	 * 判断软键盘是否可见
	 *
	 * @param activity The activity.
	 * @return {@code true}: yes<br>{@code false}: no
	 */
	public static boolean isSoftInputVisible(final Activity activity)
	{
		return isSoftInputVisible(activity, 200);
	}

	/**
	 * 判断软键盘是否可见
	 *
	 * @param activity             The activity.
	 * @param minHeightOfSoftInput The minimum height of soft input.
	 * @return {@code true}: yes<br>{@code false}: no
	 */
	public static boolean isSoftInputVisible(final Activity activity, final int minHeightOfSoftInput)
	{
		return getContentViewInvisibleHeight(activity) >= minHeightOfSoftInput;
	}

	private static int getContentViewInvisibleHeight(final Activity activity)
	{
		final View contentView = activity.findViewById(android.R.id.content);
		final Rect outRect = new Rect();
		contentView.getWindowVisibleDisplayFrame(outRect);
		LogTool.d("BaseCore",contentView.getTop(), contentView.getBottom(), outRect.top, outRect.bottom);
		return contentView.getBottom()-outRect.bottom;
	}


	private static int sContentViewInvisibleHeightPre;
	private static ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener;
	private static OnSoftInputChangedListener onSoftInputChangedListener;

	/**
	 * 注册软键盘改变监听器
	 *
	 * @param activity The activity.
	 * @param listener The soft input changed listener.
	 */
	public static void registerSoftInputChangedListener(final Activity activity, final OnSoftInputChangedListener listener)
	{
		final int flags = activity.getWindow().getAttributes().flags;
		if((flags & WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS) != 0)
		{
			activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		}
		final View contentView = activity.findViewById(android.R.id.content);
		sContentViewInvisibleHeightPre = getContentViewInvisibleHeight(activity);
		onSoftInputChangedListener = listener;
		onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener()
		{
			@Override public void onGlobalLayout()
			{
				if(onSoftInputChangedListener != null)
				{
					int height = getContentViewInvisibleHeight(activity);
					if(sContentViewInvisibleHeightPre != height)
					{
						onSoftInputChangedListener.onSoftInputChanged(height);
						sContentViewInvisibleHeightPre = height;
					}
				}
			}
		};
		contentView.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
	}

	/**
	 * 注销软键盘改变监听器
	 *
	 * @param activity The activity.
	 */
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public static void unregisterSoftInputChangedListener(final Activity activity)
	{
		final View contentView = activity.findViewById(android.R.id.content);
		contentView.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
		onSoftInputChangedListener = null;
		onGlobalLayoutListener = null;
	}

	/**
	 * 修复软键盘内存泄漏
	 * <p>在{@link Activity#onDestroy()} 中调用.</p>
	 *
	 * @param context The context.
	 */
	public static void fixSoftInputLeaks(final Context context)
	{
		if(context == null)
		{
			return;
		}
		InputMethodManager imm = (InputMethodManager) Tool.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		if(imm == null)
		{
			return;
		}
		String[] strArr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
		for(int i = 0; i < 3; i++)
		{
			try
			{
				Field declaredField = imm.getClass().getDeclaredField(strArr[i]);
				if(declaredField == null)
				{
					continue;
				}
				if(!declaredField.isAccessible())
				{
					declaredField.setAccessible(true);
				}
				Object obj = declaredField.get(imm);
				if(obj == null || !(obj instanceof View))
				{
					continue;
				}
				View view = (View) obj;
				if(view.getContext() == context)
				{
					declaredField.set(imm, null);
				}
				else
				{
					return;
				}
			}
			catch(Throwable th)
			{
				th.printStackTrace();
			}
		}
	}
}