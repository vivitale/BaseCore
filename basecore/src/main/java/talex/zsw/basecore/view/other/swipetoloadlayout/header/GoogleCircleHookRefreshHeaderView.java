package talex.zsw.basecore.view.other.swipetoloadlayout.header;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import talex.zsw.basecore.R;
import talex.zsw.basecore.view.other.swipetoloadlayout.SwipeRefreshTrigger;
import talex.zsw.basecore.view.other.swipetoloadlayout.SwipeTrigger;
import talex.zsw.basecore.view.other.swipetoloadlayout.drawable.google.GoogleCircleProgressView;

/**
 * Created by aspsine on 15/11/7.
 */
public class GoogleCircleHookRefreshHeaderView extends FrameLayout
	implements SwipeTrigger, SwipeRefreshTrigger
{
	private GoogleCircleProgressView progressView;

	private int mTriggerOffset;

	private int mFinalOffset;

	public GoogleCircleHookRefreshHeaderView(Context context)
	{
		this( context, null );
	}

	public GoogleCircleHookRefreshHeaderView(Context context, AttributeSet attrs)
	{
		this( context, attrs, 0 );
	}

	public GoogleCircleHookRefreshHeaderView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super( context, attrs, defStyleAttr );
		mTriggerOffset =
			context.getResources().getDimensionPixelOffset(R.dimen.refresh_trigger_offset_google );
		mFinalOffset =
			context.getResources().getDimensionPixelOffset( R.dimen.refresh_final_offset_google );
	}


	@Override protected void onFinishInflate()
	{
		super.onFinishInflate();
		progressView = (GoogleCircleProgressView) findViewById( R.id.googleProgress );
		progressView.setColorSchemeResources( R.color.google_blue, R.color.google_red,
			R.color.google_yellow, R.color.google_green );
		progressView.setStartEndTrim( 0, (float) 0.75 );
	}

	@Override public void onRefresh()
	{
		progressView.start();
	}

	@Override public void onPrepare()
	{
		progressView.setStartEndTrim( 0, (float) 0.75 );
	}

	@Override public void onSwipe(int y, boolean isComplete)
	{
		float alpha = y / (float) mTriggerOffset;
		Log.i("onSwipe", "alpha= " + alpha );
		ViewCompat.setAlpha(progressView, alpha );
		progressView.setProgressRotation( y / (float) mFinalOffset );
	}

	@Override public void onRelease()
	{

	}

	@Override public void complete()
	{
		progressView.stop();
	}

	@Override public void onReset()
	{
		ViewCompat.setAlpha(progressView, 1f );
	}
}
