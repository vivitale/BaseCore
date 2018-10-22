package talex.zsw.sample.mvp;

/**
 * 作用：基于MVP架构的Presenter 代理的基类
 * 作者：赵小白 email:vvtale@gmail.com  
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public interface _Presenter
{
	void onStart();

	void onResume();

	void onPause();

	void onStop();

	void onRestart();

	void onDestroy();

	void getData(HttpDto http);
}
