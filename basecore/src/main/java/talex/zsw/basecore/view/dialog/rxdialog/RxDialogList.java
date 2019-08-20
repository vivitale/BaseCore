package talex.zsw.basecore.view.dialog.rxdialog;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import talex.zsw.basecore.R;
import talex.zsw.basecore.util.RegTool;
import talex.zsw.basecore.util.TextTool;
import talex.zsw.basecore.view.recyleview.DividerItemDecoration;

/**
 * 带列表的 弹出框
 */
public class RxDialogList extends RxDialog
{
	private View rootView;
	private TextView mTvTitle;
	private TextView mTvContent;
	private TextView mTvSure;
	private TextView mTvCancel;
	private RecyclerView mRecyclerView;

	public RxDialogList(Context context, int themeResId)
	{
		super(context, themeResId);
		initView();
	}

	public RxDialogList(Context context, boolean cancelable, OnCancelListener cancelListener)
	{
		super(context, cancelable, cancelListener);
		initView();
	}

	public RxDialogList(Context context)
	{
		super(context);
		initView();
	}

	public void setTitle(String title)
	{
		mTvTitle.setText(title);
	}

	public void setSure(String content)
	{
		mTvSure.setText(content);
	}

	public void setSureListener(View.OnClickListener listener)
	{
		mTvSure.setOnClickListener(listener);
	}

	public void setCancel(String content)
	{
		mTvCancel.setText(content);
	}

	public void setCancelListener(View.OnClickListener listener)
	{
		mTvCancel.setOnClickListener(listener);
	}

	public void setContent(String str)
	{
		mTvContent.setVisibility(View.VISIBLE);
		if(RegTool.isURL(str))
		{
			// 响应点击事件的话必须设置以下属性
			mTvContent.setMovementMethod(LinkMovementMethod.getInstance());
			mTvContent.setText(TextTool.getBuilder("").setBold().append(str).setUrl(str).create());//当内容为网址的时候，内容变为可点击
		}
		else
		{
			mTvContent.setText(str);
		}
	}

	public void setAdapter(RecyclerView.Adapter adapter)
	{
		mRecyclerView.setAdapter(adapter);
	}

	private void initView()
	{
		View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_list, null);

		setFullScreenWidth();
		WindowManager windowManager = getWindow().getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 1f;
		lp.height = (int) (display.getHeight() * 0.5f); // 设置高度
		getWindow().setAttributes(lp);
		getWindow().setGravity(Gravity.BOTTOM);

		mTvTitle = dialogView.findViewById(R.id.tv_title);
		mTvTitle.setTextIsSelectable(true);
		mTvContent = dialogView.findViewById(R.id.tv_content);
		mTvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
		mTvContent.setTextIsSelectable(true);
		mTvSure = dialogView.findViewById(R.id.tv_sure);
		mTvCancel = dialogView.findViewById(R.id.tv_cancel);
		mTvCancel.setOnClickListener(new View.OnClickListener(){
			@Override public void onClick(View v)
			{
				dismiss();
			}
		});
		mRecyclerView = dialogView.findViewById(R.id.dRecyclerView);
		mRecyclerView.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST, R.color.transparent));
		mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

		setContentView(dialogView);
	}

	/**
	 * 设置高度为
	 */
	public void setDialogHeight(int height)
	{
		WindowManager windowManager = getWindow().getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 1f;
		lp.width = display.getWidth(); // 设置宽度
		lp.height = height; // 设置高度
		getWindow().setAttributes(lp);
		getWindow().setGravity(Gravity.BOTTOM);
	}

	public TextView getmTvTitle()
	{
		return mTvTitle;
	}

	public TextView getmTvContent()
	{
		return mTvContent;
	}

	public TextView getmTvSure()
	{
		return mTvSure;
	}

	public TextView getmTvCancel()
	{
		return mTvCancel;
	}

	public RecyclerView getRecyclerView()
	{
		return mRecyclerView;
	}
}
