package talex.zsw.sample.mvp;


import org.jetbrains.annotations.NotNull;

import talex.zsw.sample.entitys.BaseResponse;

/**
 * 作用：通用P层
 * 作者：赵小白 email:vvtale@gmail.com  
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class CommonPresenter extends _PresenterImpl<CommonView.View, BaseResponse> implements CommonView.Presenter
{
	public CommonPresenter(CommonView.View view)
	{
		super(view);
	}

	@Override public void requestSuccess(@NotNull BaseResponse response, @NotNull HttpDto httpDto)
	{
		mView.bindData(response);
	}
}
