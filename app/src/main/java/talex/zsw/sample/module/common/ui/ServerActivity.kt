package talex.zsw.sample.module.common.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import kotlinx.android.synthetic.main.activity_server.*
import talex.zsw.basecore.service.PingService
import talex.zsw.basecore.util.LogTool
import talex.zsw.basecore.util.NetPingTool
import talex.zsw.sample.R
import talex.zsw.sample.base.BaseMVPActivity
import talex.zsw.sample.entitys.BaseResponse
import talex.zsw.sample.mvp.CommonPresenter
import talex.zsw.sample.mvp.CommonView
import talex.zsw.sample.util.LogUtils

/**
 * 作者: 赵小白 email:vvtale@gmail.com  
 * 日期: 2018/5/24 15:46 
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class ServerActivity : BaseMVPActivity<CommonView.Presenter>(), CommonView.View
{
    override fun bindData(response: BaseResponse?)
    {
    }

    override fun initArgs(intent: Intent?)
    {
    }

    override fun initView()
    {
        setContentView(R.layout.activity_server)
        mPresenter = CommonPresenter(this)
    }

    override fun initData()
    {
        bindPingService()
    }

    override fun onDestroy()
    {
        super.onDestroy()
        unBindPingService()
    }

    private fun showInfo()
    {
        runOnUiThread {
            mTv1.text = c1.toString()
            mTv2.text = c2.toString()
            mTv3.text = c3.toString()
            mTv4.text = c4.toString()
            mTv5.text = c5.toString()
            mTv6.text = c6.toString()
        }
    }

    private var c1 = 0 //总
    private var c2 = 0 //500
    private var c3 = 0 //500 - 2000
    private var c4 = 0 //2000 - 5000
    private var c5 = 0 //5000+
    private var c6 = 0 //error
    // ---------------- PingService ----------------
    private var mPingService: PingService? = null
    private val mPingServiceConnection = object : ServiceConnection
    {
        override fun onServiceConnected(name: ComponentName, service: IBinder)
        {
            try
            {
                LogTool.nv("onServiceConnected")
                mPingService = (service as PingService.PingBinder).service
                mPingService!!.startPing("47.97.236.153",
                                         2347,
                                         object : NetPingTool.IOnNetPingListener
                                         {
                                             override fun ontDelay(log: Long)
                                             {
                                                 c1++
                                                 if (log < 500)
                                                 {
                                                     c2++
                                                 }
                                                 else if (log < 2000)
                                                 {
                                                     c3++
                                                 }
                                                 else if (log < 5000)
                                                 {
                                                     c4++
                                                 }
                                                 else
                                                 {
                                                     c5++
                                                 }
                                                 showInfo()
                                                 LogUtils.file("延迟 $log ms  总：$c1  |500：$c2  |2000：$c3  |5000：$c4  |>5000：$c5  |error：$c6")
                                             }

                                             override fun onError()
                                             {
                                                 c6++
                                                 c1++
                                                 showInfo()
                                                 LogTool.ne("错误，网络不通  总：$c1  |500：$c2  |2000：$c3  |5000：$c4  |>5000：$c5  |error：$c6")
                                             }
                                         })
            }
            catch (e: Exception)
            {
                bindPingService()
                e.printStackTrace()
            }
        }

        override fun onServiceDisconnected(name: ComponentName)
        {
            LogTool.nv("onServiceDisconnected")
            mPingService = null
        }
    }

    private fun bindPingService()
    {
        LogTool.nv("bindPingService")
        val intent = Intent(this, PingService::class.java)
        bindService(intent, mPingServiceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun unBindPingService()
    {
        unbindService(mPingServiceConnection)
    }
}