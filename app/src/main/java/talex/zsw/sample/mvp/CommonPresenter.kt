package talex.zsw.sample.mvp

import talex.zsw.sample.entitys.BaseResponse

/**
 * 作用: 通用的P层实现
 * 作者: 赵小白 email:edisonzsw@icloud.com 
 * 日期: 2016/11/18 15:13 
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class CommonPresenter(view: CommonView.View) : _PresenterImpl<CommonView.View, BaseResponse>(
        view), CommonView.Presenter
{
    override fun requestSuccess(response: BaseResponse, http: HttpDto)
    {
        mView.bindData(response)
    }
}
