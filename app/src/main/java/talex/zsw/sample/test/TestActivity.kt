package talex.zsw.sample.test

import android.content.Intent
import talex.zsw.sample.R
import talex.zsw.sample.base.BaseMVPActivity
import talex.zsw.sample.entitys.BaseResponse
import talex.zsw.sample.mvp.CommonPresenter
import talex.zsw.sample.mvp.CommonView

/**
 * 作用：测试用
 * 作者：赵小白 email:vvtale@gmail.com  
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class TestActivity : BaseMVPActivity<CommonView.Presenter>(), CommonView.View
{
    override fun initArgs(intent: Intent)
    {
    }

    override fun initView()
    {
        setContentView(R.layout.activity_test)
        mPresenter = CommonPresenter(this)
    }

    override fun initData()
    {
    }

    override fun bindData(response: BaseResponse)
    {
    }
}
