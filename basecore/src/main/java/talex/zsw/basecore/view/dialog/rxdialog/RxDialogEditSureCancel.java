package talex.zsw.basecore.view.dialog.rxdialog;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import talex.zsw.basecore.R;

/**
 * 带有确认取消按钮的 输入框Dialog
 */
public class RxDialogEditSureCancel extends RxDialog
{
	private ImageView mIvLogo;
	private TextView mTvSure;
	private TextView mTvCancel;
	private EditText editText;
	private TextView mTvTitle;

	private View.OnClickListener sureListener = new View.OnClickListener()
	{
		@Override public void onClick(View v)
		{
			dismiss();
		}
	};
	private View.OnClickListener cancelListener = new View.OnClickListener()
	{
		@Override public void onClick(View v)
		{
			dismiss();
		}
	};

	public RxDialogEditSureCancel(Context context, int themeResId) {
		super(context, themeResId);
		initView();
	}

	public RxDialogEditSureCancel(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		initView();
	}

	public RxDialogEditSureCancel(Context context) {
		super(context);
		initView();
	}

	public RxDialogEditSureCancel(Activity context) {
		super(context);
		initView();
	}

	public RxDialogEditSureCancel(Context context, float alpha, int gravity) {
		super(context, alpha, gravity);
		initView();
	}
	public RxDialogEditSureCancel(Context context, View.OnClickListener sureListener, View.OnClickListener cancelListener)
	{
		super(context);
		this.sureListener = sureListener;
		this.cancelListener = cancelListener;
		initView();
	}

	public RxDialogEditSureCancel(Activity context, View.OnClickListener sureListener, View.OnClickListener cancelListener)
	{
		super(context);
		this.sureListener = sureListener;
		this.cancelListener = cancelListener;
		initView();
	}

	public ImageView getLogoView()
	{
		return mIvLogo;
	}

	public void setTitle(String title)
	{
		mTvTitle.setText(title);
	}

	public TextView getTitleView()
	{
		return mTvTitle;
	}

	public EditText getEditText()
	{
		return editText;
	}

	public TextView getSureView()
	{
		return mTvSure;
	}

	public void setSure(String strSure)
	{
		this.mTvSure.setText(strSure);
	}

	public void setSureListener(View.OnClickListener sureListener)
	{
		this.mTvSure.setOnClickListener(sureListener);
	}

	public TextView getCancelView()
	{
		return mTvCancel;
	}

	public void setCancel(String strCancel)
	{
		this.mTvCancel.setText(strCancel);
	}

	public void setCancelListener(View.OnClickListener cancelListener)
	{
		this.mTvCancel.setOnClickListener(cancelListener);
	}

	private void initView()
	{
		View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edittext_sure_false, null);
		mIvLogo = (ImageView) dialogView.findViewById(R.id.iv_logo);
		mTvTitle = (TextView) dialogView.findViewById(R.id.tv_title);
		mTvSure = (TextView) dialogView.findViewById(R.id.tv_sure);
		mTvCancel = (TextView) dialogView.findViewById(R.id.tv_cancle);
		editText = (EditText) dialogView.findViewById(R.id.editText);

		mTvSure.setOnClickListener(sureListener);
		mTvCancel.setOnClickListener(cancelListener);
		setContentView(dialogView);
	}
}
