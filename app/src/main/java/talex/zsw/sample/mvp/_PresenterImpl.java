package talex.zsw.sample.mvp;

import android.support.annotation.NonNull;

import com.lzy.okgo.OkGo;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import talex.zsw.basecore.util.LogTool;
import talex.zsw.basecore.view.dialog.sweetalertdialog.SweetAlertDialog;
import talex.zsw.sample.entitys.BaseResponse;

/**
 * 作用: 基于MVP架构的Presenter 代理的基类的实现
 * 作者: 赵小白 email:edisonzsw@icloud.com
 * 日期: 2016 16/3/3 10:46 
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class _PresenterImpl<T extends _View, V> implements _Presenter, RequestCallback<V>
{
	protected T mView;
	private _Model model = new _ModelImpl();
	private Disposable mDisposable;

	public _PresenterImpl(T view)
	{
		mView = view;
	}

	@Override public void onResume()
	{
	}

	@Override public void onPause()
	{

	}

	@Override public void onStart()
	{

	}

	@Override public void onStop()
	{

	}

	@Override public void onDestroy()
	{
		OkGo.getInstance().cancelTag(this);
		mView = null;
	}

	@Override public void getData(HttpDto http)
	{
		if(isViewExist())
		{
			http.setTag(this);
			model.getData((RequestCallback<BaseResponse>) http.getTag(), http);
		}
	}
	//================================== 接口请求 =======================================

	@Override public void beforeRequest()
	{
		if(isViewExist())
		{
			mView.showDialog();
		}
	}

	@Override public void requestError(String msg, final HttpDto httpDto)
	{
		if(isViewExist())
		{
			mView.disLoading();
			LogTool.e(msg);
			if(httpDto.isShowError())
			{
				if(httpDto.isFinish())
				{
					if(msg.contains("Timeout"))
					{
						mView.showDialog(SweetAlertDialog.ERROR_TYPE, "连接服务器超时", "数据加载失败，请重试！", "确定", null, mView.getFinishListener(), null);
					}
					else if(msg.contains("504"))
					{
						mView.showDialog(SweetAlertDialog.ERROR_TYPE, "无网络服务", "请检查网络后重试", "确定", null, mView.getFinishListener(), null);
					}
					else if(msg.contains("Failed to connect"))
					{
						mView.showDialog(SweetAlertDialog.ERROR_TYPE, "服务器异常", "请稍后重试", "确定", null, mView.getFinishListener(), null);
					}
					else if(msg.contains("网络请求失败"))
					{
						mView.showDialog(SweetAlertDialog.ERROR_TYPE, "网络请求失败", "请稍后重试", "确定", null, mView.getFinishListener(), null);
					}
					else
					{
						mView.showDialog(SweetAlertDialog.ERROR_TYPE, "对不起，访问出错了", "", "确定", null, mView.getFinishListener(), null);
					}
				}
				else if(httpDto.isTryAgain())
				{
					if(msg.contains("Timeout"))
					{
						mView.showDialog(SweetAlertDialog.ERROR_TYPE, "连接服务器超时", "数据加载失败，是否立即重试？", "重试", "取消", new SweetAlertDialog.OnSweetClickListener()
						{
							@Override public void onClick(SweetAlertDialog sweetAlertDialog)
							{
								getData(httpDto);
							}
						}, null);
					}
					else if(msg.contains("504"))
					{
						mView.showDialog(SweetAlertDialog.ERROR_TYPE, "无网络服务", "是否立即重试？", "重试", "取消", new SweetAlertDialog.OnSweetClickListener()
						{
							@Override public void onClick(SweetAlertDialog sweetAlertDialog)
							{
								getData(httpDto);
							}
						}, null);
					}
					else if(msg.contains("Failed to connect"))
					{
						mView.showDialog(SweetAlertDialog.ERROR_TYPE, "服务器异常", "是否立即重试？", "确定", "取消", new SweetAlertDialog.OnSweetClickListener()
						{
							@Override public void onClick(SweetAlertDialog sweetAlertDialog)
							{
								getData(httpDto);
							}
						}, null);
					}
					else if(msg.contains("网络请求失败"))
					{
						mView.showDialog(SweetAlertDialog.ERROR_TYPE, "网络请求失败", "是否立即重试？", "重试", "取消", new SweetAlertDialog.OnSweetClickListener()
						{
							@Override public void onClick(SweetAlertDialog sweetAlertDialog)
							{
								getData(httpDto);
							}
						}, null);
					}
					else
					{
						mView.showDialog(SweetAlertDialog.ERROR_TYPE, "对不起，访问出错了", "是否立即重试？", "重试", "取消", new SweetAlertDialog.OnSweetClickListener()
						{
							@Override public void onClick(SweetAlertDialog sweetAlertDialog)
							{
								getData(httpDto);
							}
						}, null);
					}
				}
				else
				{
					if(msg.contains("Timeout"))
					{
						mView.showDialog(SweetAlertDialog.ERROR_TYPE, "连接服务器超时", "数据加载失败，请重试！");
					}
					else if(msg.contains("504"))
					{
						mView.showDialog(SweetAlertDialog.ERROR_TYPE, "无网络服务", "请检查网络后重试");
					}
					else if(msg.contains("Failed to connect"))
					{
						mView.showDialog(SweetAlertDialog.ERROR_TYPE, "服务器异常", "请稍后重试");
					}
					else if(msg.contains("网络请求失败"))
					{
						mView.showDialog(SweetAlertDialog.ERROR_TYPE, "网络请求失败", "请稍后重试");
					}
					else
					{
						mView.showDialog(SweetAlertDialog.ERROR_TYPE, "对不起，访问出错了", "");
					}
				}
			}
		}
	}

	@Override public void requestComplete()
	{
	}

	@Override public void requestTryAgain(@NonNull HttpDto httpDto)
	{
		getData(httpDto);
	}

	@Override public boolean isViewExist()
	{
		return mView != null;
	}

	@Override public void requestSuccess(@NotNull V response, @NotNull HttpDto httpDto)
	{

	}

	@Override public void requestSuccess(@NotNull Observable<V> observable, @NotNull final HttpDto httpDto)
	{
		observable.compose(mView.bindLifecycle()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Object>()
		{
			@Override public void onSubscribe(Disposable disposable)
			{
				mDisposable = disposable;
			}

			@Override public void onNext(Object o)
			{
				requestSuccess((V) o, httpDto);
			}

			@Override public void onError(Throwable e)
			{
				requestError(e.getLocalizedMessage()+"\n"+e, httpDto);
			}

			@Override public void onComplete()
			{

			}
		});
	}

	@Override public void requestCancel()
	{
		mDisposable.dispose();
	}
}
