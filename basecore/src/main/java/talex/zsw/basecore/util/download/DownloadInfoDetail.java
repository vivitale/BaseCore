package talex.zsw.basecore.util.download;

import java.io.Serializable;

/**
 * 作用: 自定义的一个记载下载器详细信息的类
 * 作者: 赵小白 email:edisonzsw@icloud.com 
 * 日期: 2017/2/14 09:10 
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
@SuppressWarnings("WeakerAccess")
public class DownloadInfoDetail implements Serializable
{
	private int fileSize;// 文件大小
	private int complete;// 完成度
	private String urlstring;// 下载器标识

	DownloadInfoDetail(int fileSize, int complete, String urlstring)
	{
		this.fileSize = fileSize;
		this.complete = complete;
		this.urlstring = urlstring;
	}

	public DownloadInfoDetail()
	{
	}

	public int getFileSize()
	{
		return fileSize;
	}

	public void setFileSize(int fileSize)
	{
		this.fileSize = fileSize;
	}

	public int getComplete()
	{
		return complete;
	}

	public void setComplete(int complete)
	{
		this.complete = complete;
	}

	public String getUrlstring()
	{
		return urlstring;
	}

	public void setUrlstring(String urlstring)
	{
		this.urlstring = urlstring;
	}

	@Override
	public String toString()
	{
		return "DownloadInfoDetail [fileSize=" + fileSize + ", complete=" + complete
				+ ", urlstring=" + urlstring + "]";
	}
}