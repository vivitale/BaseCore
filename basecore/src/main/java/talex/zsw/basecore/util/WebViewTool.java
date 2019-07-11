package talex.zsw.basecore.util;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import talex.zsw.basecore.view.textview.RichText;

/**
 * 作用: WebView工具类
 * 作者: 赵小白 email:vvtale@gmail.com  
 * 日期: 2018/5/21 16:29 
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class WebViewTool
{
	public static void setWebData(String content, final WebView mWebView, RichText mRichText, final ProgressBar mProgressBar)
	{
		// 设置WebView的属性，此时可以去执行JavaScript脚本`
		mWebView.getSettings().setJavaScriptEnabled(true); // 设置支持javascript脚本
		mWebView.getSettings().setAllowFileAccess(true); // 允许访问文件
		mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		mWebView.getSettings().setDefaultTextEncodingName("UTF-8");//设置默认为utf-8
		//		mWebView.getSettings().setDefaultFontSize( (int) (46 / scale) );
		//		mWebView.getSettings().setMinimumFontSize( (int) (38 / scale) );
		mWebView.getSettings().setSupportZoom(false);// 支持缩放
		mWebView.getSettings().setBuiltInZoomControls(false); // 设置显示缩放按钮
		//		mWebView.getSettings().setTextZoom(80); // 设置字体大小
		mWebView.setBackgroundColor(0);
		//		mWebView.getSettings().setUseWideViewPort(true);
		//		mWebView.getSettings().setLoadWithOverviewMode(true);
		//		mWebView.setInitialScale(960 * 100 / getScrnHeight());

		mWebView.getSettings().setUseWideViewPort(true);//设置此属性，可任意比例缩放
		mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

		mWebView.getSettings().setDomStorageEnabled(true);
		//priority high
		mWebView.getSettings().setAppCacheEnabled(true);
		mWebView.getSettings().setDatabaseEnabled(true);
		mWebView
			.getSettings()
			.setDatabasePath(Tool.getContext().getCacheDir().getAbsolutePath());
		//add by wjj end
		//解决图片不显示
		mWebView.getSettings().setBlockNetworkImage(false);
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
		{
			mWebView.getSettings().setMixedContentMode(0);
		}
		String ua = mWebView.getSettings().getUserAgentString();
		mWebView.getSettings().setUseWideViewPort(true);
		mWebView.getSettings().setLoadWithOverviewMode(true);

		if(content.startsWith("www"))
		{
			content = "http://"+content;
		}
		if(RegTool.isNullString(content))
		{
			if(mRichText != null)
			{
				mWebView.setVisibility(View.GONE);
				mRichText.setVisibility(View.VISIBLE);
				mRichText.setText("no data");
			}
		}
		else if(content.startsWith("http://") || content.startsWith("https://"))
		{
			mWebView.getSettings().setUseWideViewPort(true);
			mWebView.getSettings().setLoadWithOverviewMode(true);

			if(mProgressBar != null)
			{
				mProgressBar.setVisibility(View.VISIBLE);
				mProgressBar.setMax(100);
			}

			// 当webview里面能点击是 在当前页面上显示！
			mWebView.setWebViewClient(new WebViewClient()
			{
				public boolean shouldOverrideUrlLoading(WebView view, String url)
				{
					if(url == null)
					{
						return false;
					}

					try
					{
						if(url.startsWith("http:") || url.startsWith("https:"))
						{
							view.loadUrl(url);
							return true;
						}
						else
						{
							Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
							view.getContext().startActivity(intent);
							return true;
						}
					}
					catch(Exception e)
					{ //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
						return false;
					}
				}

				/** 网页页面开始加载的时候，执行的回调方法 */
				@Override public void onPageStarted(WebView view, String url, Bitmap favicon)
				{//网页页面开始加载的时候
					mWebView.setEnabled(false);// 当加载网页的时候将网页进行隐藏
					super.onPageStarted(view, url, favicon);
				}
			});

			mWebView.setWebChromeClient(new WebChromeClient()
			{
				/** 当WebView加载之后，返回 HTML 页面的标题 Title */
				@Override public void onReceivedTitle(WebView view, String title)
				{
					//判断标题 title 中是否包含有“error”字段，如果包含“error”字段，则设置加载失败，显示加载失败的视图
					if(!TextUtils.isEmpty(title) && title.toLowerCase().contains("error"))
					{
					}
				}

				@Override public void onProgressChanged(WebView view, int newProgress)
				{
					if(mProgressBar != null)
					{
						mProgressBar.setProgress(newProgress);
						if(newProgress == 100)
						{
							mProgressBar.setVisibility(View.GONE);
						}
						else
						{
							if(mProgressBar.getVisibility() == View.GONE)
							{
								mProgressBar.setVisibility(View.VISIBLE);
								// pb.setProgress(newProgress);
							}
						}
					}
					super.onProgressChanged(view, newProgress);
				}

//				@Override
//				public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams)
//				{
//					mUploadCallbackAboveL = filePathCallback;
//					take();
//					return true;
//				}
//
//				public void openFileChooser(ValueCallback<Uri> uploadMsg)
//				{
//					mUploadMessage = uploadMsg;
//					take();
//				}
//
//				public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType)
//				{
//					mUploadMessage = uploadMsg;
//					take();
//				}
//
//				public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture)
//				{
//					mUploadMessage = uploadMsg;
//					take();
//				}
			});

			mWebView.loadUrl(content);
		}
		else
		{
			content = "<style>\n"+"    img {\n"+"        max-width: 100%;\n"+"        width: 100%;\n"+
				"        height: auto\n"+"    }\n"+"    \n"+"    div {\n"+"        width: 100%;\n"+
				"        max-width: 100%;\n"+"        height: auto\n"+"    }\n"+"    \n"+"    p {\n"+
				"        width: 100%;\n"+"        max-width: 100%;\n"+"        height: auto\n"+"    }\n"+"</style>\n"+
				content+"<script type=\"text/javascript\">"+"var imgs = document.getElementsByTagName('img');"+
				"for(var i = 0; i<tables.length; i++){"+  // 逐个改变
				"imgs[i].style.width = '100%';"+  // 宽度改为100%
				"imgs[i].style.height = 'auto';"+"}"+"var ps = document.getElementsByTagName('p');"+
				"for(var i = 0; i<tables.length; i++){"+  // 逐个改变
				"ps[i].style.width = '100%';"+  // 宽度改为100%
				"ps[i].style.height = 'auto';"+"}"+"</script>";
			String regEx = "</?[^>]+>";
			Pattern pat = Pattern.compile(regEx);
			Matcher mat = pat.matcher(content);
			boolean rs = mat.find();
			if(rs)
			{
				if(content.contains("https"))//如果含有包括https
				{
					WebViewClient mWebviewclient = new WebViewClient()
					{
						public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error)
						{
							handler.proceed();
							//handler.cancel(); 默认的处理方式，WebView变成空白页
							//handler.process();接受证书
							//handleMessage(Message msg); 其他处理
						}
					};
					mWebView.setWebViewClient(mWebviewclient);
				}
				else// 当webview里面能点击是 在当前页面上显示！
				{
					mWebView.setWebViewClient(new WebViewClient()
					{
						@Override public boolean shouldOverrideUrlLoading(WebView view, String url)
						{
							view.loadUrl(url);
							return true;
						}
					});
				}
				mWebView.loadData(content, "text/html; charset=UTF-8", null);
				// mWebView.loadData(fmtString(content), "text/html", "utf-8");
			}
			else
			{
				if(mRichText != null)
				{
					mWebView.setVisibility(View.GONE);
					mRichText.setVisibility(View.VISIBLE);
					mRichText.setRichText(content);
				}
			}
		}
		if(Build.VERSION.SDK_INT > 10 && Build.VERSION.SDK_INT < 17)
		{
			fixWebView(mWebView);
		}
	}

	@TargetApi(11) private static void fixWebView(WebView mWebView)
	{
		mWebView.removeJavascriptInterface("searchBoxJavaBridge_");
	}
}
