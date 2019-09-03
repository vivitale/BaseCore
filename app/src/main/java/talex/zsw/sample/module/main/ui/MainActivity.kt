package talex.zsw.sample.module.main.ui

import android.content.Intent
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_main.*
import talex.zsw.basecore.model.ActionItem
import talex.zsw.basecore.util.LogTool
import talex.zsw.basecore.util.TimeTool
import talex.zsw.basecore.util.glide.GlideTool
import talex.zsw.basecore.view.dialog.rxdialog.RxDialogList
import talex.zsw.basecore.view.other.slidedatetimepicker.SlideDateTimeListener
import talex.zsw.basecore.view.other.slidedatetimepicker.SlideDateTimePicker
import talex.zsw.basecore.view.popupwindow.PopLayout
import talex.zsw.basecore.view.popupwindow.PopListView
import talex.zsw.sample.R
import talex.zsw.sample.base.BaseMVPActivity
import talex.zsw.sample.module.main.adapter.TestAdapter
import talex.zsw.sample.module.main.contract.MainContract
import talex.zsw.sample.module.main.presenter.MainPresenter
import talex.zsw.sample.test.TestData
import talex.zsw.sample.util.LogUtils
import java.io.File
import java.util.*

/**
 * 作者: 赵小白 email:vvtale@gmail.com  
 * 日期: 2018/5/24 15:46 
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class MainActivity : BaseMVPActivity<MainContract.Presenter>(), MainContract.View
{
    private var popListView: PopListView? = null
    private var popLayout: PopLayout? = null

    override fun initArgs(intent: Intent?)
    {
    }

    override fun initView()
    {
        setContentView(R.layout.activity_main)
        mPresenter = MainPresenter(this)
    }

    override fun initData()
    {
        GlideTool.loadImgCircleCrop(mImageView,
                                    "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1182022639,405039723&fm=27&gp=0.jpg")
        LogTool.nv("initData")
        LogUtils.listLogs()
                .forEach {
                    LogTool.nv(it.name)
                    uploadFile(it)
                }
        val list: ArrayList<String> = arrayListOf()
        list.add("1111")
        list.add("2222")
        list.add("3333")
        list.add("4444")
        list.add("4444")
        list.add("4444")
        list.add("4444")
        list.add("4444")
        mNiceSpinner.attachDataSource(list)
    }

    @OnClick(R.id.mBtn1, R.id.mBtn2, R.id.mBtn3, R.id.mBtn4, R.id.mBtn5, R.id.mBtn6)
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
                    popLayout =
                            PopLayout(this@MainActivity,
                                      ViewGroup.LayoutParams.WRAP_CONTENT,
                                      ViewGroup.LayoutParams.WRAP_CONTENT,
                                      R.layout.activity_main)
                }
                popLayout?.show(mBtn1)
            }
            R.id.mBtn2 ->
            {
                initPopupView()
                popListView?.show(mBtn2, 0)
            }
            R.id.mBtn3 ->
            {
                SlideDateTimePicker.Builder(supportFragmentManager)
                        .setListener(object : SlideDateTimeListener()
                                     {
                                         override fun onDateTimeSet(date: Date?)
                                         {
                                         }
                                     })
                        .setInitialDate(Date())
                        .setMinDate(Date())
                        .setMaxDate(Date())
                        .setIndicatorColor(Color.parseColor("#0000FF"))
                        .setThemeColor(Color.parseColor("#00FF00"))
                        .setTitleColor(Color.parseColor("#FF0000"))
                        .setShowTime(true)
                        .setShowDay(true)
                        .build()
                        .show()
            }
            R.id.mBtn4 ->
            {
                //                val body = BaseModel()
                //                body.key = "26802ee608152"
                //                body.city = "杭州"
                //                mPresenter.getData(HttpDto(Constant.WEATHER, body).setType(HttpDto.GET))
                uploadLog()
            }
            R.id.mBtn5 ->
            {
                Integer.parseInt("abc")
            }
            R.id.mBtn6 ->
            {
                listDialog
                        ?: let {
                            listDialog = RxDialogList(this@MainActivity)
                            listDialog?.setAdapter(adapter)
                            adapter.replaceData(TestData.getGoods(20))
                        }
                listDialog?.show()
            }
        }
    }

    private var listDialog: RxDialogList? = null
    private val adapter = TestAdapter()

    private fun initPopupView()
    {
        popListView =
                PopListView(this@MainActivity,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT)
        popListView?.addAction(ActionItem("标清"))
        popListView?.addAction(ActionItem("高清"))
        popListView?.addAction(ActionItem("超清"))
        popListView?.setItemOnClickListener { item, position ->
            LogTool.d(popListView?.getAction(position).toString())
        }
    }

    override fun bindWeatherStr(json: String)
    {
        mTvInfo.text = TimeTool.getCurTimeString()
        mTvInfo.append("\n$json")
    }

    fun uploadLog()
    {

    }

    fun uploadFile(file: File)
    {
    }
}