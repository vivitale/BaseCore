package talex.zsw.sample.test

import android.os.Bundle
import talex.zsw.sample.base.BaseMVPFragment
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
class TestFragment : BaseMVPFragment<CommonView.Presenter>(), CommonView.View
{
    override fun initArgs(bundle: Bundle)
    {
    }

    override fun initView(bundle: Bundle)
    {
        mPresenter = CommonPresenter(this)
    }

    override fun initData()
    {
    }

    override fun bindData(response: BaseResponse)
    {
    }
}