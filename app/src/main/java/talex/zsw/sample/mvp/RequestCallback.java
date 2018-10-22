package talex.zsw.sample.mvp;

import io.reactivex.Observable;

/**
 * 作用：接口请求相关通用类
 * 作者：赵小白 email:vvtale@gmail.com  
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public interface RequestCallback<T>
{
	/**
	 * 请求之前调用
	 */
	void beforeRequest();

	/**
	 * 请求错误调用
	 *
	 * @param msg 错误信息
	 */
	void requestError(String msg, HttpDto http);

	/**
	 * 请求完成调用
	 */
	void requestComplete();

	/**
	 * 请求成功调用
	 *
	 * @param response 返回数据
	 */
	void requestSuccess(T response, HttpDto http);

	/**
	 * 返回成功调用
	 *
	 * @param observable 返回数据
	 */
	void requestSuccess(Observable<T> observable, HttpDto http);

	/**
	 * 重新请求
	 *
	 * @param http 请求参数
	 */
	void requestTryAgain(HttpDto http);

	/**
	 * 判断View层是否存在
	 */
	boolean isViewExist();

	void requestCancel();
}
