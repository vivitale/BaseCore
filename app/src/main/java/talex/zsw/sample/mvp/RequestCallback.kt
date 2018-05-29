package talex.zsw.sample.mvp

import io.reactivex.Observable

/**
 * 作用: 网络请求监听基类
 * 作者: 赵小白 email:edisonzsw@icloud.com
 * 日期: 2016 16/3/3 10:46 
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
interface RequestCallback<T>
{
    /**
     * 请求之前调用
     */
    fun beforeRequest()

    /**
     * 请求错误调用

     * @param msg 错误信息
     */
    fun requestError(msg: String?, http: HttpDto)

    /**
     * 请求完成调用
     */
    fun requestComplete()

    /**
     * 请求成功调用
     * @param response 返回数据
     */
    fun requestSuccess(response: T, http: HttpDto)

    /**
     * 返回成功调用
     * @param observable 返回数据
     */
    fun requestSuccess(observable: Observable<T>, http: HttpDto)

    /**
     * 重新请求

     * @param http 请求参数
     */
    fun requestTryAgain(http: HttpDto)

    /**
     * 判断View层是否存在
     */
    val isViewExist: Boolean

    fun requestCancel()
}
