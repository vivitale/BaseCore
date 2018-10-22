package talex.zsw.sample.module.common.ui;

import android.content.Intent;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import talex.zsw.basecore.util.PermissionConstants;
import talex.zsw.basecore.util.PermissionHelper;
import talex.zsw.basecore.util.PermissionTool;
import talex.zsw.basecore.view.other.PageControlView;
import talex.zsw.sample.R;
import talex.zsw.sample.base.BaseMVPActivity;
import talex.zsw.sample.entitys.BaseResponse;
import talex.zsw.sample.module.main.ui.MainActivity;
import talex.zsw.sample.mvp.CommonPresenter;
import talex.zsw.sample.mvp.CommonView;
import talex.zsw.sample.mvp._Presenter;

/**
 * 作用：欢迎页
 * 作者：赵小白 email:vvtale@gmail.com  
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class WelcomeActivity extends BaseMVPActivity<_Presenter>
	implements CommonView.View//, ViewPager.OnPageChangeListener
{
	@BindView(R.id.layout) FrameLayout layout;
	@BindView(R.id.mViewPager) ViewPager mViewPager;
	@BindView(R.id.mRLViewPager) RelativeLayout mRLViewPager;
	@BindView(R.id.mPageControlView) PageControlView mPageControlView;

	private boolean isAnim = false;// 动画效果是否完成
	private boolean isDB = false;// 数据库是否初始化完毕
	private boolean isUpdate = false;// 检查更新是否完毕这个

	@Override protected void initArgs(Intent intent)
	{
	}

	@Override protected void initView()
	{
		setContentView(R.layout.activity_welcome);
		ButterKnife.bind(this);
		mPresenter = new CommonPresenter(this);

		//当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
		{
			//透明状态栏
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
	}

	@Override protected void initData()
	{
		PermissionHelper.request(new PermissionTool.FullCallback(){
			@Override public void onGranted(List<String> permissionsGranted)
			{

			}

			@Override public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied)
			{

			}
		}, PermissionConstants.PHONE, PermissionConstants.STORAGE);

		initDataBase();
		startWelcomeAnim();
	}

	private void startWelcomeAnim()
	{
//		ViewAnimator.animate(layout).alpha(0.5f, 1).duration(1000).start().onStop(new Animation.AnimationListener.Stop()
//		{
//			@Override public void onStop()
//			{
//				isAnim = true;
//				mIvSure.setVisibility(View.VISIBLE);
//				ViewAnimator.animate(mIvSure).alpha(0.0f, 1.0f).duration(300).start();
//			}
//		});
	}

	private void launchMain()
	{
		start(MainActivity.class);
		overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
	}


	@Override public void bindData(@NotNull BaseResponse response)
	{

	}

	// =================================== 数据库初始化 =====================================
	private void initDataBase()
	{
		isDB = true;
	}

	// =================================== 引导页 =====================================
	//	private View view1, view2, view3;
	//	private TextView mTvGo;
	//	private LayoutInflater inflater;
	//	private List<View> list;
	//
	//	@SuppressLint("InflateParams") public void initpage(LayoutInflater flater)
	//	{
	//		mRLViewPager.setVisibility(View.VISIBLE);
	//		view1 = flater.inflate(R.layout.loading, null);
	//		view1.setBackgroundResource(R.mipmap.loading_1);
	//		view2 = flater.inflate(R.layout.loading, null);
	//		view2.setBackgroundResource(R.mipmap.loading_2);
	//		view3 = flater.inflate(R.layout.loading2, null);
	//		view3.setBackgroundResource(R.mipmap.loading_3);
	//		mTvGo = (TextView) view3.findViewById(R.id.vTvNow);
	//		list.add(view1);
	//		list.add(view2);
	//		list.add(view3);
	//		mViewPager.setAdapter(pager);
	//		mViewPager.setOnPageChangeListener(this);
	//		mPageControlView.setCount(list.size());
	//		mPageControlView.generatePageControl(0);
	//
	//		view3.setOnClickListener(new View.OnClickListener()
	//		{
	//			@Override public void onClick(View v)
	//			{
	//				if(isFastClick())
	//				{
	//					return;
	//				}
	//				getHandler().postDelayed(new Runnable()
	//				{
	//					public void run()
	//					{
	//						SpUtil.setFirst(false);
	//						ViewAnimator
	//							.animate(mRLViewPager)
	//							.alpha(1, 0)
	//							.duration(1000)
	//							.onStop(new AnimationListener.Stop()
	//							{
	//								@Override public void onStop()
	//								{
	//									mRLViewPager.setVisibility(View.GONE);
	//									startWelcomeAnim();
	//								}
	//							})
	//							.start();
	//					}
	//				}, 0);
	//			}
	//		});
	//	}
	//
	//	PagerAdapter pager = new PagerAdapter()
	//	{
	//
	//		@Override public boolean isViewFromObject(View arg0, Object arg1)
	//		{
	//			return arg0 == arg1;
	//		}
	//
	//		@Override public int getCount()
	//		{
	//			return list.size();
	//		}
	//
	//		@Override public void destroyItem(ViewGroup container, int position, Object object)
	//		{
	//			container.removeView(list.get(position));
	//		}
	//
	//		@Override public Object instantiateItem(ViewGroup container, int position)
	//		{
	//			container.addView(list.get(position));
	//
	//			return list.get(position);
	//		}
	//	};
	//
	//	@Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
	//	{
	//
	//	}
	//
	//	@Override public void onPageSelected(int position)
	//	{
	//		mPageControlView.generatePageControl(position);
	//	}
	//
	//	@Override public void onPageScrollStateChanged(int state)
	//	{
	//
	//	}
}
