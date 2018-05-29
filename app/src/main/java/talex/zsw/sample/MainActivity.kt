package talex.zsw.sample

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_main.*
import talex.zsw.basecore.model.ActionItem
import talex.zsw.basecore.util.LogTool
import talex.zsw.basecore.util.glide.GlideTool
import talex.zsw.basecore.view.popupwindow.PopLayout
import talex.zsw.basecore.view.popupwindow.PopListView
import talex.zsw.sample.base.BaseMVPActivity
import talex.zsw.sample.entitys.BaseResponse
import talex.zsw.sample.mvp.CommonPresenter
import talex.zsw.sample.mvp.CommonView

/**
 * 作者: 赵小白 email:vvtale@gmail.com  
 * 日期: 2018/5/24 15:46 
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class MainActivity : BaseMVPActivity<CommonView.Presenter>(), CommonView.View
{
    private var popListView: PopListView? = null
    private var popLayout: PopLayout? = null

    override fun initArgs(intent: Intent?)
    {
    }

    override fun initView()
    {
        setContentView(R.layout.activity_main)
        mPresenter = CommonPresenter(this)
    }

    override fun initData()
    {
        GlideTool.loadImgCircleCrop(mImageView,"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1182022639,405039723&fm=27&gp=0.jpg")
    }

    override fun bindData(response: BaseResponse)
    {
    }

    @OnClick(R.id.mBtn1, R.id.mBtn2)
    fun onViewClicked(view: View)
    {
        when (view.id)
        {
            R.id.mBtn1 ->
            {
                if (popLayout == null)
                {
                    popLayout = PopLayout(this@MainActivity, "厉害了")
                }
                else
                {
                    popLayout = PopLayout(this@MainActivity,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            R.layout.activity_main)
                }
                popLayout?.show(mBtn1)
                showDialog()
            }
            R.id.mBtn2 ->
            {
                initPopupView()
                popListView?.show(mBtn2, 0)
            }
        }
    }

    private fun initPopupView()
    {
        popListView = PopListView(this@MainActivity,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        popListView?.addAction(ActionItem("标清"))
        popListView?.addAction(ActionItem("高清"))
        popListView?.addAction(ActionItem("超清"))
        popListView?.setItemOnClickListener { item, position ->
            LogTool.d(popListView?.getAction(position).toString())
        }
    }
}