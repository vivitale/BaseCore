package talex.zsw.sample.entitys

import talex.zsw.sample.mvp.HttpDto
import java.io.Serializable

/**
 * 作用: 接口返回的基类
 * 作者: 赵小白 email:edisonzsw@icloud.com 
 * 日期: 16/5/18 00:55 
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class BaseResponse : Serializable
{
    var msg: String? = null
    var flag: String? = null
    var data: Any? = null
    var result: Any? = null
    var retCode:Int = 0
    var httpDto: HttpDto? = null
}
