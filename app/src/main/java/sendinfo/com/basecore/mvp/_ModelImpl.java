package sendinfo.com.basecore.mvp;

import com.lzy.okgo.model.Response;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import sendinfo.com.basecore.entitys.BaseResponse;
import talex.zsw.basecore.util.JsonTool;
import talex.zsw.basecore.util.LogTool;

/**
 * 作用: 首页
 * 作者: 赵小白 email:edisonzsw@icloud.com 
 * 日期: 16/5/18 00:01 
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class _ModelImpl implements _Model
{
	private BaseResponse baseResponse = new BaseResponse();

	@Override public void getData(final RequestCallback<BaseResponse> callback, final HttpDto http)
	{
		http.print();

		http.getRequest().execute(new BaseCallback(callback, http)
		{
			@Override public void onSuccess(Response<String> response)
			{
				Observable<BaseResponse> observable = Observable.just(response.body())// IO 线程，由 subscribeOn() 指定
				                                                .subscribeOn(Schedulers.io()).observeOn(Schedulers.newThread()).map(new Function<String, BaseResponse>()
					{
						@Override public BaseResponse apply(String s) throws Exception
						{
							LogTool.json(s);
							baseResponse = JsonTool.getObject(s, BaseResponse.class);
							if(baseResponse ==null)
							{
								baseResponse = new BaseResponse();
							}
							baseResponse.setHttpDto(http);
							return baseResponse;
						}
					});
				callback.requestSuccess(observable, http);
			}
		});
	}
}
