package talex.zsw.sample.mvp

import talex.zsw.sample.entitys.BaseResponse

/**
 * 作者: 赵小白 email:edisonzsw@icloud.com 
 * 日期: 16/4/7 08:58 
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
interface CommonView : _View
{
    interface View : _View
    {
        fun bindData(response: BaseResponse)
    }

    interface Presenter : _Presenter
    {
    }
}