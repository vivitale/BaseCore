package talex.zsw.sample.mvp;

import talex.zsw.sample.entitys.BaseResponse;

/**
 * 作用：基本的Model类，简单的用来获取一个网络数据
 * 作者：赵小白 email:vvtale@gmail.com  
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public interface _Model
{
	void getData(RequestCallback<BaseResponse> callback, HttpDto http);
}