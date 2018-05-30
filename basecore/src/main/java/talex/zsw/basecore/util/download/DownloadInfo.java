package talex.zsw.basecore.util.download;

import java.io.Serializable;

/**
 * 作用: 创建一个下载信息的实体类
 * 作者: 赵小白 email:edisonzsw@icloud.com 
 * 日期: 2017/2/14 09:10 
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
@SuppressWarnings("WeakerAccess")
public class DownloadInfo implements Serializable
{
	private int threadId;// 下载器id
	private int startPos;// 开始点
	private int endPos;// 结束点
	private int compeleteSize;// 完成度
	private String url;// 下载器网络标识

	public DownloadInfo(int threadId, int startPos, int endPos, int compeleteSize, String url)
	{
		this.threadId = threadId;
		this.startPos = startPos;
		this.endPos = endPos;
		this.compeleteSize = compeleteSize;
		this.url = url;
	}

	public DownloadInfo()
	{
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public int getThreadId()
	{
		return threadId;
	}

	public void setThreadId(int threadId)
	{
		this.threadId = threadId;
	}

	public int getStartPos()
	{
		return startPos;
	}

	public void setStartPos(int startPos)
	{
		this.startPos = startPos;
	}

	public int getEndPos()
	{
		return endPos;
	}

	public void setEndPos(int endPos)
	{
		this.endPos = endPos;
	}

	public int getCompeleteSize()
	{
		return compeleteSize;
	}

	public void setCompeleteSize(int compeleteSize)
	{
		this.compeleteSize = compeleteSize;
	}

	@Override public String toString()
	{
		return "DownloadInfo [threadId="+threadId+", startPos="+startPos+", endPos="+endPos+", compeleteSize="+
			compeleteSize+"]";
	}
}