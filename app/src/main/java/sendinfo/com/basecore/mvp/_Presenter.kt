package sendinfo.com.basecore.mvp

/**
 * 作用: 基于MVP架构的Presenter 代理的基类
 * 作者: 赵小白 email:edisonzsw@icloud.com
 * 日期: 2016 16/3/3 10:46 
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
interface _Presenter
{
    fun onResume()

    fun onPause()

    fun onDestroy()

    fun getData(http: HttpDto)
}
