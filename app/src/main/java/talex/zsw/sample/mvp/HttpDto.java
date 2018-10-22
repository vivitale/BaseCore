package talex.zsw.sample.mvp;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.request.base.BodyRequest;
import com.lzy.okgo.request.base.Request;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import talex.zsw.basecore.util.JsonTool;
import talex.zsw.basecore.util.LogTool;
import talex.zsw.basecore.util.RegTool;
import talex.zsw.sample.entitys.BaseModel;

/**
 * 作用：网络请求参数状态
 * 作者：赵小白 email:vvtale@gmail.com  
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class HttpDto implements Serializable
{
	public final static int GET = 0x100000;
	public final static int POST = 0x100001;
	public final static int PUT = 0x100002;
	public final static int DELETE = 0x100003;
	public final static int HEAD = 0x100004;
	public final static int OPTIONS = 0x100005;

	private int type = POST;// get post
	private boolean silence = false;//是否静默加载
	private boolean finish = false;//加载失败,是否关闭
	private boolean tryAgain = false;//显示重试
	private boolean showError = true;//是否显示错误信息
	private String url;//请求的标志,用来唯一指定请求
	private String end;//请求尾巴
	private String fullUrl;//完整链接地址
	private CacheMode cacheMode = CacheMode.DEFAULT;

	private Map<String, String> params;// 请求参数
	private Map<String, String> heads;// 请求头
	private Object model;
	private String bodyStr;
	private String bodyJson;
	private Object tag;

	// -------------- 基础组 --------------
	public HttpDto(String url)
	{
		this(url, new BaseModel(), false);
	}

	public HttpDto(String url, Object model)
	{
		this(url, model, false);
	}

	// -------------- 终极实体 --------------
	public HttpDto(String url, Object model, boolean silence)
	{
		this.url = url;
		this.fullUrl = url;
		this.model = model;
		this.silence = silence;

		params = JsonTool.getMapFromObj(model);
		heads = new HashMap<>();
	}

	public Request getRequest()
	{
		Request request;
		if(type == GET)
		{
			request = OkGo.get(fullUrl);
		}
		else if(type == POST)
		{
			request = OkGo.post(fullUrl);
		}
		else if(type == PUT)
		{
			request = OkGo.put(fullUrl);
		}
		else if(type == DELETE)
		{
			request = OkGo.delete(fullUrl);
		}
		else if(type == HEAD)
		{
			request = OkGo.head(fullUrl);
		}
		else
		{
			request = OkGo.options(fullUrl);
		}

		if(params != null)
		{
			request.params(params);
		}
		if(heads != null)
		{
			for(Map.Entry<String, String> entry : heads.entrySet())
			{
				request.headers(entry.getKey(), entry.getValue());
			}
		}
		if(!RegTool.isNullString(bodyStr))
		{
			((BodyRequest) request).upString(bodyStr);
		}
		if(!RegTool.isNullString(bodyJson))
		{
			((BodyRequest) request).upJson(bodyJson);
		}
		request.tag(tag);
		request.cacheMode(cacheMode);
		return request;
	}

	public boolean isShowError()
	{
		return showError;
	}

	public HttpDto setShowError(boolean showError)
	{
		this.showError = showError;
		return this;
	}

	public String getBodyStr()
	{
		return bodyStr;
	}

	public HttpDto setBodyStr(String bodyStr)
	{
		this.bodyStr = bodyStr;
		return this;
	}

	public String getBodyJson()
	{
		return bodyJson;
	}

	public HttpDto setBodyJson(String bodyJson)
	{
		this.bodyJson = bodyJson;
		return this;
	}

	public boolean isSilence()
	{
		return silence;
	}

	public HttpDto setSilence(boolean silence)
	{
		this.silence = silence;
		return this;
	}

	public boolean isFinish()
	{
		return finish;
	}

	public HttpDto setFinish(boolean finish)
	{
		this.finish = finish;
		return this;
	}

	public boolean isTryAgain()
	{
		return tryAgain;
	}

	public HttpDto setTryAgain(boolean tryAgain)
	{
		this.tryAgain = tryAgain;
		return this;
	}

	public String getUrl()
	{
		return url;
	}

	public HttpDto setUrl(String url)
	{
		this.url = url;
		return this;
	}

	public String getFullUrl()
	{
		return fullUrl;
	}

	public HttpDto setFullUrl(String fullUrl)
	{
		this.fullUrl = fullUrl;
		return this;
	}

	public String getEnd()
	{
		return end;
	}

	public HttpDto setEnd(String end)
	{
		this.end = end;
		this.fullUrl = this.url+end;
		return this;
	}

	public Map<String, String> getParams()
	{
		return params;
	}

	public HttpDto setParams(Map<String, String> params)
	{
		this.params = params;
		return this;
	}

	public Map<String, String> getHeads()
	{
		return heads;
	}

	public HttpDto setHeads(Map<String, String> heads)
	{
		this.heads = heads;
		return this;
	}

	public HttpDto setCacheMode(CacheMode cacheMode)
	{
		this.cacheMode = cacheMode;
		return this;
	}

	public CacheMode getCacheMode()
	{
		return cacheMode;
	}

	public Object getModel()
	{
		return model;
	}

	public HttpDto setModel(Object model)
	{
		this.model = model;
		params = JsonTool.getMapFromObj(model);
		return this;
	}

	public void addParams(String key, String value)
	{
		if(params == null)
		{
			params = new HashMap<>();
		}
		params.put(key, value);
	}

	public int getType()
	{
		return type;
	}

	public HttpDto setType(int type)
	{
		this.type = type;
		return this;
	}

	public void setTag(Object tag)
	{
		this.tag = tag;
	}

	public Object getTag()
	{
		return tag;
	}

	public void print()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(getStringType()).append(":").append(fullUrl).append("\n");
		if(params != null)
		{
			builder.append("\n请求参数为:");
			for(Map.Entry<String, String> entry : params.entrySet())
			{
				builder.append("\n").append(entry.getKey()).append(" = ").append(entry.getValue());
			}
		}

		builder.append("\n");

		if(!RegTool.isNullString(bodyStr))
		{
			builder.append("\n请求 body 为:");

			builder.append("\n").append(bodyStr);
		}

		if(!RegTool.isNullString(bodyJson))
		{
			builder.append("\n请求 bodyJson 为:");

			builder.append("\n").append(bodyJson);
		}

		builder.append("\n");

		if(heads != null)
		{
			builder.append("\n请求头为:");
			for(Map.Entry<String, String> entry : heads.entrySet())
			{
				builder.append("\n").append(entry.getKey()).append(" = ").append(entry.getValue());
			}
		}

		builder.append("\n");
		LogTool.d(builder.toString());
	}

	private String getStringType()
	{
		if(type == GET)
		{
			return "GET";
		}
		else if(type == POST)
		{
			return "POST";
		}
		else if(type == PUT)
		{
			return "PUT";
		}
		else if(type == DELETE)
		{
			return "DELETE";
		}
		else if(type == HEAD)
		{
			return "HEAD";
		}
		else
		{
			return "OPTIONS";
		}
	}
}
