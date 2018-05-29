package sendinfo.com.basecore.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import sendinfo.com.basecore.R;
import sendinfo.com.basecore.mvp._Presenter;
import sendinfo.com.basecore.mvp._View;
import talex.zsw.basecore.BuildConfig;
import talex.zsw.basecore.util.ActivityTool;
import talex.zsw.basecore.util.KeyboardTool;
import talex.zsw.basecore.util.RegTool;
import talex.zsw.basecore.view.dialog.sweetalertdialog.SweetAlertDialog;

/**
 * 作用: 基于MVP架构的Activity基类
 * 作者: 赵小白 email:edisonzsw@icloud.com
 * 日期: 2016 16/3/3 10:46 
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public abstract class BaseMVPActivity<T extends _Presenter> extends RxAppCompatActivity
	implements _View
{
	protected T mPresenter;
	protected MyApplication mApplication;
	private boolean mViewInited = false;
	public SweetAlertDialog mSweetAlertDialog;
	private InputMethodManager mInputMethodManager;
	private String mac;
	private Handler mainHandler;
	public int activityState;// 0:onCreate 1:onStart 2:onResume -1:onPause -2:onStop -3:onDestroy

	protected abstract void initArgs(Intent intent);

	protected abstract void initView();

	protected abstract void initData();

	@Override protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// 严苛模式
		if(BuildConfig.DEBUG)
		{
			StrictMode.setThreadPolicy(
				new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
			StrictMode
				.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
		}

		mApplication = getAppApplication();
		ActivityTool.addActivity(this);
		mInputMethodManager = (InputMethodManager) this
			.getSystemService(Context.INPUT_METHOD_SERVICE);
		mSweetAlertDialog = new SweetAlertDialog(this);
		mSweetAlertDialog.setCancelable(false);

		try
		{
			initArgs(getIntent());
			initView();
			initData();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		activityState = 0;
		EventBus.getDefault().register(this);
		ButterKnife.bind(this);
	}

	protected MyApplication getAppApplication()
	{
		if(null == mApplication)
		{
			mApplication = (MyApplication) getApplication();
		}
		return mApplication;
	}

	@Override protected void onNewIntent(Intent intent)
	{
		super.onNewIntent(intent);
		setIntent(intent);
	}

	@Override protected void onStart()
	{
		activityState = 1;
		super.onStart();
	}

	@Override protected void onResume()
	{
		activityState = 2;
		super.onResume();
		MobclickAgent.onResume(this);
		if(mPresenter != null)
		{
			mPresenter.onResume();
		}
	}

	@Override protected void onPause()
	{
		activityState = -1;
		MobclickAgent.onPause(this);
		KeyboardTool.hideSoftInput(this);
		if(mPresenter != null)
		{
			mPresenter.onPause();
		}
		super.onPause();
	}

	@Override protected void onStop()
	{
		activityState = -2;
		super.onStop();
	}

	@Override protected void onDestroy()
	{
		activityState = -3;
		setContentView(R.layout.activity_empty);
		EventBus.getDefault().unregister(this);
		mInputMethodManager = null;
		if(mSweetAlertDialog != null && mSweetAlertDialog.isShowing())
		{
			mSweetAlertDialog.dismiss();
			mSweetAlertDialog = null;
		}
		//取消所有请求
		OkGo.getInstance().cancelTag(this);

		ActivityTool.removeActivity(this);

		if(mPresenter != null)
		{
			mPresenter.onDestroy();
		}
		super.onDestroy();
		System.runFinalization();
		Runtime.getRuntime().gc();
		System.gc();
	}

	@Override public void getData(boolean silence)
	{

	}

	@Override public void disLoading()
	{
	}

	/**
	 * @return 获取屏幕高度
	 */
	public int getScrnHeight()
	{
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
		return mDisplayMetrics.heightPixels;
	}

	/**
	 * @return 获取屏幕宽度
	 */
	public int getScrnWidth()
	{
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
		return mDisplayMetrics.widthPixels;
	}

	@Override public void showToast(String msg)
	{
		if(!RegTool.isNullString(msg))
		{
			Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		}
	}

	protected void showSnackbar(String msg)
	{
		Snackbar.make(getDecorView(), msg, Snackbar.LENGTH_SHORT).show();
	}

	protected void showSnackbar(int id)
	{
		Snackbar.make(getDecorView(), id, Snackbar.LENGTH_SHORT).show();
	}

	protected View getDecorView()
	{
		return getWindow().getDecorView();
	}

	@Override public Handler getHandler()
	{
		if(mainHandler == null)
		{
			mainHandler = new Handler();
		}
		return mainHandler;
	}

	@Override public Context getCont()
	{
		return this;
	}

	private static long mLastClickTime;
	public static final int MIN_CLICK_DELAY_TIME = 500;
	@Override public boolean isFastClick()
	{
		// 当前时间
		long currentTime = System.currentTimeMillis();
		// 两次点击的时间差
		long time = currentTime-mLastClickTime;
		if(0 < time && time < MIN_CLICK_DELAY_TIME)
		{
			return true;
		}
		mLastClickTime = currentTime;
		return false;
	}

	public int getStatusBarHeight()
	{
		try
		{
			Class<?> c = Class.forName("com.android.internal.R$dimen");
			Object obj = c.newInstance();
			Field field = c.getField("status_bar_height");
			int x = Integer.parseInt(field.get(obj).toString());
			return getResources().getDimensionPixelSize(x);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	public SweetAlertDialog.OnSweetClickListener finishListener
		= new SweetAlertDialog.OnSweetClickListener()
	{
		@Override public void onClick(SweetAlertDialog sweetAlertDialog)
		{
			sweetAlertDialog.dismissWithAnimation();
			finish();
		}
	};

	@Override public SweetAlertDialog.OnSweetClickListener getFinishListener()
	{
		return finishListener;
	}

	@Override public void disDialog()
	{
		if(mSweetAlertDialog != null)
		{
			mSweetAlertDialog.dismiss();
		}
	}

	@Override public void showDialog()
	{
		if(mSweetAlertDialog != null && mSweetAlertDialog.isShowing())
		{
			mSweetAlertDialog.setTitleText("正在加载数据");
			mSweetAlertDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
		}
		else
		{
			mSweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
				.setTitleText("正在加载数据");
			mSweetAlertDialog.setCancelable(false);
			mSweetAlertDialog.show();
		}
	}

	@Override public void showDialog(int type, String title, String content)
	{
		showDialog(type, title, content, "确定", null, new SweetAlertDialog.OnSweetClickListener()
		{
			@Override public void onClick(SweetAlertDialog sweetAlertDialog)
			{
				sweetAlertDialog.dismiss();
			}
		}, null);
	}

	@Override public void showDialog(int type, String title, String content, String confirmText,
		String cancelText, SweetAlertDialog.OnSweetClickListener confirmListener,
		SweetAlertDialog.OnSweetClickListener cancelListener)
	{
		if(mSweetAlertDialog != null && mSweetAlertDialog.isShowing())
		{
			mSweetAlertDialog.changeAlertType(type);
		}
		else
		{
			mSweetAlertDialog = new SweetAlertDialog(this, type);
			mSweetAlertDialog.setCancelable(false);
		}
		// Title
		if(!RegTool.isNullString(title))
		{
			mSweetAlertDialog.setTitleText(title);
		}
		else
		{
			mSweetAlertDialog.setTitleText("");
		}
		// content
		if(!RegTool.isNullString(content))
		{
			mSweetAlertDialog.setContentText(content);
		}
		else
		{
			mSweetAlertDialog.showContentText(false);
		}
		// confirmText
		if(!RegTool.isNullString(confirmText))
		{
			mSweetAlertDialog.setConfirmText(confirmText);
		}
		// cancelText
		if(!RegTool.isNullString(cancelText))
		{
			mSweetAlertDialog.setCancelText(cancelText);
		}
		else
		{
			mSweetAlertDialog.showCancelButton(false);
		}
		// confirmListener
		if(confirmListener != null)
		{
			mSweetAlertDialog.setConfirmClickListener(confirmListener);
		}
		else
		{
			mSweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
			{
				@Override public void onClick(SweetAlertDialog sweetAlertDialog)
				{
					sweetAlertDialog.dismiss();
				}
			});
		}
		// confirmListener
		if(confirmListener != null)
		{
			mSweetAlertDialog.setCancelClickListener(cancelListener);
		}
		else
		{
			mSweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener()
			{
				@Override public void onClick(SweetAlertDialog sweetAlertDialog)
				{
					sweetAlertDialog.dismiss();
				}
			});
		}
		mSweetAlertDialog.show();
	}

	@NonNull @Override public LifecycleTransformer bindLifecycle()
	{
		return this.bindUntilEvent(ActivityEvent.DESTROY);
	}

	public void start(Class<?> cls)
	{
		startActivity(new Intent(this, cls));
	}

	public void start(Intent intent)
	{
		startActivity(intent);
	}

	@Subscribe
	public void onEvent(NotingEvent event){
	}

	private class NotingEvent{}
}
