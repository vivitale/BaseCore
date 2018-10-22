package talex.zsw.sample.module.main.presenter

import talex.zsw.sample.entitys.BaseResponse
import talex.zsw.sample.module.main.contract.MainContract
import talex.zsw.sample.mvp.HttpDto
import talex.zsw.sample.mvp._PresenterImpl

/**
 * 作用：首页
 * 作者：赵小白 email:vvtale@gmail.com  
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class MainPresenter(view: MainContract.View) : _PresenterImpl<MainContract.View, BaseResponse>(view), MainContract.Presenter
{
    override fun requestSuccess(response: BaseResponse, http: HttpDto)
    {
        when (http.url)
        {
        }
    }
}