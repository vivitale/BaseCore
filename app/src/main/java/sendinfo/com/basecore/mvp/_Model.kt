package sendinfo.com.basecore.mvp

import sendinfo.com.basecore.entitys.BaseResponse

/**
 * 作用: 基本的Model类，简单的用来获取一个网络数据
 * 作者: 赵小白 email:edisonzsw@icloud.com 
 * 日期: 16/4/7 09:25 
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
interface _Model
{
    fun getData(callback: RequestCallback<BaseResponse>, http: HttpDto)
}
