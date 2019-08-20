package talex.zsw.sample.module.main.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import talex.zsw.basecore.util.glide.GlideTool
import talex.zsw.sample.R
import talex.zsw.sample.test.TestDto

/**
 * 作用: 公告
 * 作者: 赵小白 email:edisonzsw@icloud.com 
 */
class TestAdapter : BaseQuickAdapter<TestDto, BaseViewHolder>(R.layout.item_pop_listview)
{
    override fun convert(helper: BaseViewHolder, item: TestDto)
    { //iv_itpop tv_itpop
        helper.setText(R.id.tv_itpop, item.title)
        GlideTool.loadImg(helper.getView(R.id.iv_itpop), item.image)
    }
}