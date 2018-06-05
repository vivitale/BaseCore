package talex.zsw.sample.mvp

import android.content.Context
import android.os.Handler
import com.trello.rxlifecycle2.LifecycleTransformer
import talex.zsw.basecore.view.dialog.sweetalertdialog.SweetAlertDialog

/**
 * 作用: 基于MVP架构的View 视图基类
 * 作者: 赵小白 email:edisonzsw@icloud.com
 * 日期: 2016 16/3/3 10:46 
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
interface _View
{
    fun showToast(msg: String)

    fun showDialog()

    fun showDialog(type: Int = SweetAlertDialog.NORMAL_TYPE, title: String?, content: String?, confirmText: String?, cancelText: String?, confirmListener: SweetAlertDialog.OnSweetClickListener?, cancelListener: SweetAlertDialog.OnSweetClickListener?)

    fun showDialog(type: Int = SweetAlertDialog.NORMAL_TYPE, title: String?, content: String?)

    fun disDialog()

    fun getData(silence: Boolean = false)

    fun getHandler(): Handler

    fun getCont(): Context

    fun disLoading()

    fun isFastClick():Boolean

    fun getFinishListener(): SweetAlertDialog.OnSweetClickListener

    fun bindLifecycle(): LifecycleTransformer<Any>

    fun onVisible()

    fun onInvisible()
}
