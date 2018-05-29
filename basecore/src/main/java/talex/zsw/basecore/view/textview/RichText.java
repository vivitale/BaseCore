package talex.zsw.basecore.view.textview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import talex.zsw.basecore.R;
import talex.zsw.basecore.util.RegTool;
import talex.zsw.basecore.util.glide.GlideApp;


/**
 * Created by zzhoujay on 2015/7/21 0021.
 * 富文本显示TextView
 */
@SuppressLint("AppCompatCustomView")
public class RichText extends TextView
{

	private Drawable placeHolder, errorImage;//占位图，错误图
	private OnImageClickListener onImageClickListener;//图片点击回调
	private HashSet<Target> targets;
	private int d_w = 200;
	private int d_h = 200;

	public RichText(Context context)
	{
		this(context, null);
	}

	public RichText(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public RichText(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);

		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs)
	{
		targets = new HashSet<>();
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RichText);
		placeHolder = typedArray.getDrawable(R.styleable.RichText_RT_placeHolder);
		errorImage = typedArray.getDrawable(R.styleable.RichText_RT_errorImage);

		d_w = typedArray.getDimensionPixelSize(R.styleable.RichText_RT_default_width, d_w);
		d_h = typedArray.getDimensionPixelSize(R.styleable.RichText_RT_default_height, d_h);

		if(placeHolder == null)
		{
			placeHolder = new ColorDrawable(Color.GRAY);
		}
		placeHolder.setBounds(0, 0, d_w, d_h);
		if(errorImage == null)
		{
			errorImage = new ColorDrawable(Color.GRAY);
		}
		errorImage.setBounds(0, 0, d_w, d_h);
		typedArray.recycle();
	}


	/**
	 * 设置富文本
	 *
	 * @param text 富文本
	 */
	public void setRichText(String text)
	{
		if(RegTool.isNullString(text))
		{
			return;
		}
		targets.clear();
		Spanned spanned = Html.fromHtml(text, asyncImageGetter, null);
		SpannableStringBuilder spannableStringBuilder;
		if(spanned instanceof SpannableStringBuilder)
		{
			spannableStringBuilder = (SpannableStringBuilder) spanned;
		}
		else
		{
			spannableStringBuilder = new SpannableStringBuilder(spanned);
		}

		ImageSpan[] imageSpans = spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), ImageSpan.class);
		final List<String> imageUrls = new ArrayList<>();

		for(int i = 0, size = imageSpans.length; i < size; i++)
		{
			ImageSpan imageSpan = imageSpans[i];
			String imageUrl = imageSpan.getSource();
			int start = spannableStringBuilder.getSpanStart(imageSpan);
			int end = spannableStringBuilder.getSpanEnd(imageSpan);
			imageUrls.add(imageUrl);

			final int finalI = i;
			ClickableSpan clickableSpan = new ClickableSpan()
			{
				@Override public void onClick(View widget)
				{
					if(onImageClickListener != null)
					{
						onImageClickListener.imageClicked(imageUrls, finalI);
					}
				}
			};
			ClickableSpan[] clickableSpans = spannableStringBuilder.getSpans(start, end, ClickableSpan.class);
			if(clickableSpans != null && clickableSpans.length != 0)
			{
				for(ClickableSpan cs : clickableSpans)
				{
					spannableStringBuilder.removeSpan(cs);
				}
			}
			spannableStringBuilder.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		super.setText(spanned);
		setMovementMethod(LinkMovementMethod.getInstance());
	}

	private void addTarget(Target target)
	{
		targets.add(target);
	}

	/**
	 * 异步加载图片（依赖于Picasso）
	 */
	private Html.ImageGetter asyncImageGetter = new Html.ImageGetter()
	{
		@Override public Drawable getDrawable(String source)
		{
			final URLDrawable urlDrawable = new URLDrawable();

			SimpleTarget<Bitmap> target = new SimpleTarget<Bitmap>()
			{
				@Override
				public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition)
				{
					Drawable drawable = new BitmapDrawable(getContext().getResources(), bitmap);
					drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
					urlDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
					urlDrawable.setDrawable(drawable);
					RichText.this.setText(getText());
				}

				@Override public void onLoadStarted(@Nullable Drawable placeholder)
				{
					urlDrawable.setBounds(placeholder.getBounds());
					urlDrawable.setDrawable(placeholder);
				}

				@Override public void onLoadFailed(@Nullable Drawable errorDrawable)
				{
					urlDrawable.setBounds(errorDrawable.getBounds());
					urlDrawable.setDrawable(errorDrawable);
				}
			};

			addTarget(target);
			GlideApp
				.with(getContext())
				.asBitmap()
				.load(source)
				.placeholder(new ColorDrawable(0xffF0F0F0))
				.error(new ColorDrawable(0xffF0F0F0))
				.fallback(new ColorDrawable(0xffF0F0F0))
				.transition(BitmapTransitionOptions.withCrossFade())
				.into(target);
			return urlDrawable;
		}
	};

	private static final class URLDrawable extends BitmapDrawable
	{
		private Drawable drawable;

		@SuppressWarnings("deprecation") public URLDrawable()
		{
		}

		@Override public void draw(Canvas canvas)
		{
			if(drawable != null)
			{
				drawable.draw(canvas);
			}
		}

		public void setDrawable(Drawable drawable)
		{
			this.drawable = drawable;
		}
	}

	public void setPlaceHolder(Drawable placeHolder)
	{
		this.placeHolder = placeHolder;
		this.placeHolder.setBounds(0, 0, d_w, d_h);
	}

	public void setErrorImage(Drawable errorImage)
	{
		this.errorImage = errorImage;
		this.errorImage.setBounds(0, 0, d_w, d_h);
	}

	public void setOnImageClickListener(OnImageClickListener onImageClickListener)
	{
		this.onImageClickListener = onImageClickListener;
	}

	public interface OnImageClickListener
	{
		/**
		 * 图片被点击后的回调方法
		 *
		 * @param imageUrls 本篇富文本内容里的全部图片
		 * @param position  点击处图片在imageUrls中的位置
		 */
		void imageClicked(List<String> imageUrls, int position);
	}
}