package talex.zsw.basecore.view.recyleview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import java.util.concurrent.atomic.AtomicBoolean;


/**
 * 作用: 自动跑马灯的RecyclerView
 * 作者: 赵小白 email:edisonzsw@icloud.com 
 * 日期: 16/5/21 01:04 
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MarqueeRecyclerView extends RecyclerView
{
	Thread thread = null;
	AtomicBoolean shouldContinue = new AtomicBoolean(false);
	Handler mHandler;

	public MarqueeRecyclerView(Context context)
	{
		super(context);
	}

	public MarqueeRecyclerView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public MarqueeRecyclerView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	private void init()
	{
		//主线程的handler，用于执行Marquee的滚动消息
		mHandler = new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				super.handleMessage(msg);
				switch (msg.what)
				{
					case 1://不论是竖直滚动还是水平滚动，都是偏移5个像素
						MarqueeRecyclerView.this.scrollBy(5, 5);
						break;
				}
			}
		};
		if (thread == null)
		{
			thread = new Thread()
			{
				public void run()
				{
					while (shouldContinue.get())
					{
						try
						{
							Thread.sleep(100);
						} catch (InterruptedException e)
						{
							e.printStackTrace();
						}
						Message msg = mHandler.obtainMessage();
						msg.what = 1;
						msg.sendToTarget();
					}
					//退出循环时清理handler
					mHandler = null;
				}
			};
		}
	}

	@Override
	/**
	 * 在附到窗口的时候开始滚动
	 */
	protected void onAttachedToWindow()
	{
		super.onAttachedToWindow();
		shouldContinue.set(true);
		init();
		thread.start();
	}

	@Override
	/**
	 * 在脱离窗口时处理相关内容
	 */
	protected void onDetachedFromWindow()
	{
		super.onDetachedFromWindow();
		stopMarquee();
	}

	/**
	 * 停止滚动
	 */
	public void stopMarquee()
	{
		shouldContinue.set(false);
		thread = null;
	}

//	/**
//	 * Adapter类
//	 */
//	public static class InnerAdapter extends Adapter<InnerAdapter.MarqueHolder>
//	{
//		private List<TestDto> mData;
//		private int size;
//		private LayoutInflater mLayoutInflater;
//
//		public InnerAdapter(List<TestDto> data, Context context)
//		{
//			mData = data;
//			size = mData.size();
//			mLayoutInflater = LayoutInflater.from(context);
//		}
//
//		@Override
//		public MarqueHolder onCreateViewHolder(ViewGroup parent, int viewType)
//		{
//
//			View view = mLayoutInflater.inflate(R.layout.item_main_limit, parent, false);
//
//			return new MarqueHolder(view);
//		}
//
//		@Override
//		public void onBindViewHolder(MarqueHolder holder, int position)
//		{
//			TestDto dto = mData.get(position%size);
//			holder.iTvName.setText(dto.getTitle());
//			ImageUtil.loadImg(holder.iImageView,dto.getImage());
//		}
//
//		@Override
//		public int getItemCount()
//		{
//			return Integer.MAX_VALUE;
//		}
//
//		/**
//		 * * ViewHolder类
//		 **/
//		static class MarqueHolder extends ViewHolder
//		{
//			TextView iTvName,iTvPrice,iTvOld;
//			ImageView iImageView;
//
//			public MarqueHolder(View view)
//			{
//				super(view);
//				iTvName = (TextView) view.findViewById(R.id.iTvName);
//				iTvPrice = (TextView) view.findViewById(R.id.iTvPrice);
//				iTvOld = (TextView) view.findViewById(R.id.iTvOld);
//				iImageView = (ImageView) view.findViewById(R.id.iImageView);
//			}
//		}
//	}
}