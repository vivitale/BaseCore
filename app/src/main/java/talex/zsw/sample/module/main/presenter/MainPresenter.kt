package talex.zsw.sample.module.main.presenter

import talex.zsw.basecore.util.JsonTool
import talex.zsw.basecore.view.dialog.sweetalertdialog.SweetAlertDialog
import talex.zsw.sample.entitys.BaseResponse
import talex.zsw.sample.module.main.contract.MainContract
import talex.zsw.sample.mvp.HttpDto
import talex.zsw.sample.mvp._PresenterImpl
import talex.zsw.sample.util.Constant

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
            Constant.WEATHER->{
                if (response.retCode==200)
                {
                    mView?.disDialog()
                    mView?.bindWeatherStr(JsonTool.getJsonString(response.result))
                }
                else
                {
                    mView?.showDialog(SweetAlertDialog.ERROR_TYPE,"请求错误",response.msg)
                }
            }
        }
    }

    override fun requestError(msg: String?, httpDto: HttpDto?)
    {
        super.requestError(msg, httpDto)
    }
}