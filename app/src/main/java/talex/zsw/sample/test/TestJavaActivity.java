package talex.zsw.sample.test;

import android.content.Intent;

import org.jetbrains.annotations.NotNull;

import talex.zsw.sample.R;
import talex.zsw.sample.base.BaseMVPActivity;
import talex.zsw.sample.entitys.BaseResponse;
import talex.zsw.sample.mvp.CommonPresenter;
import talex.zsw.sample.mvp.CommonView;

/**
 * 作用：测试用
 * 作者：赵小白 email:vvtale@gmail.com  
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class TestJavaActivity extends BaseMVPActivity<CommonView.Presenter> implements CommonView.View
{
	@Override protected void initArgs(Intent intent)
	{

	}

	@Override protected void initView()
	{
		setContentView(R.layout.activity_testjava);
		mPresenter = new CommonPresenter(this);
	}

	@Override protected void initData()
	{
	}

	@Override public void bindData(@NotNull BaseResponse response)
	{

	}
}
