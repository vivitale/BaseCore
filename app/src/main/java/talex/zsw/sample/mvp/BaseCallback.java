package talex.zsw.sample.mvp;

import android.os.Handler;
import android.os.Message;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.base.Request;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Response;
import talex.zsw.basecore.util.LogTool;
import talex.zsw.sample.entitys.BaseResponse;

/**
 * 作用：通用请求返回
 * 作者：赵小白 email:vvtale@gmail.com  
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class BaseCallback extends AbsCallback<String>
{
	private RequestCallback<BaseResponse> requestCallback;
	private HttpDto httpDto;
	private Handler handler;

	public BaseCallback(RequestCallback<BaseResponse> callback, HttpDto http)
	{
		this.requestCallback = callback;
		this.httpDto = http;
		this.handler = new Handler()
		{
			@Override public void handleMessage(Message msg)
			{
				super.handleMessage(msg);
				handler.removeMessages(0);
				requestCallback.requestError("网络请求失败", httpDto);
			}
		};
	}

	/**
	 * 请求网络开始前，UI线程
	 */
	@Override public void onStart(Request request)
	{
		LogTool.v("OkGo", "onStart");
		if(!httpDto.isSilence() && requestCallback.isViewExist())
		{
			requestCallback.beforeRequest();
		}
		handler.removeMessages(0);
		handler.sendEmptyMessageDelayed(0, 40*1000);
		super.onStart(request);
	}

	/** 对返回数据进行操作的回调， UI线程 */
	@Override public void onSuccess(com.lzy.okgo.model.Response<String> response)
	{
		LogTool.v("OkGo", "onSuccess");
		handler.removeMessages(0);
	}

	/** 缓存成功的回调,UI线程 */
	@Override public void onCacheSuccess(com.lzy.okgo.model.Response<String> response)
	{
		LogTool.v("OkGo", "onCacheSuccess");
		onSuccess(response);
		handler.removeMessages(0);
		super.onCacheSuccess(response);
	}

	/** 请求失败，响应错误，数据解析错误等，都会回调该方法， UI线程 */
	@Override public void onError(com.lzy.okgo.model.Response<String> response)
	{
		LogTool.v("OkGo", "onError");
		Observable.just(response.getException())// IO 线程，由 subscribeOn() 指定
		          .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Throwable>()
		{
			@Override public void accept(Throwable e) throws Exception
			{
				requestCallback.requestError(e.getLocalizedMessage()+"网络请求失败"+e, httpDto);
			}
		});// Android 主线程，由 observeOn() 指定
		handler.removeMessages(0);
		super.onError(response);
	}

	/** 请求网络结束后，UI线程 */
	@Override public void onFinish()
	{
		LogTool.v("OkGo", "onFinish");
		requestCallback.requestComplete();
		handler.removeMessages(0);
		super.onFinish();
	}

	/** 上传过程中的进度回调，get请求不回调，UI线程 */
	@Override public void uploadProgress(Progress progress)
	{
		LogTool.v("OkGo", "uploadProgress");
		super.uploadProgress(progress);
	}

	/** 下载过程中的进度回调，UI线程 */
	@Override public void downloadProgress(Progress progress)
	{
		LogTool.v("OkGo", "downloadProgress");
		handler.removeMessages(0);
		super.downloadProgress(progress);
	}

	/** 拿到响应后，将数据转换成需要的格式，子线程中执行，可以是耗时操作 */
	@Override public String convertResponse(Response response) throws Throwable
	{
		LogTool.v("OkGo", "convertResponse");
		handler.removeMessages(0);
		return response.body().string();
	}
}
