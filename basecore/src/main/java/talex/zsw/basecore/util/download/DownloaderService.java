package talex.zsw.basecore.util.download;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import talex.zsw.basecore.util.FileTool;

/**
 * 作用: 下载器
 * 作者: 赵小白 email:edisonzsw@icloud.com 
 * 日期: 2017/2/14 09:10 
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
@SuppressWarnings("WeakerAccess")
public class DownloaderService
{
	private String urlstr;// 下载的地址
	private String localfile;// 保存路径
	private int threadcount;// 线程数
	private Handler mHandler;// 消息处理器
	private int fileSize;// 所要下载的文件的大小
	private Context context;
	private List<DownloadInfo> infos;// 存放下载信息类的集合
	private static final int INIT = 1;// 初始化状态
	private static final int DOWNLOADING = 2;// 正在下载状态
	private static final int PAUSE = 3;// 暂停状态
	private int state = INIT;
	public static final String path = "/mnt/sdcard/Sendinfo/Download/";// 保存到SD卡

	public DownloaderService(String urlstr, String localfile, int threadcount,
							 Context context, Handler mHandler)
	{
		this.urlstr = urlstr;
		this.localfile = localfile;
		this.threadcount = threadcount;
		this.mHandler = mHandler;
		this.context = context;
	}

	/**
	 * 判断是否正在下载
	 */
	public boolean isdownloading()
	{
		return state == DOWNLOADING;
	}

	/**
	 * 得到downloader里的信息
	 * 首先进行判断是否是第一次下载，如果是第一次就要进行初始化，并将下载器的信息保存到数据库中
	 * 如果不是第一次下载，那就要从数据库中读出之前下载的信息（起始位置，结束为止，文件大小等），
	 * 并将下载信息返回给下载器
	 */
	public DownloadInfoDetail getDownloaderInfors()
	{
		if (isFirst(urlstr))
		{
			FileTool.delAllFile(path);
			Log.v("TAG", "isFirst");
			init();
			int range = fileSize / threadcount;
			infos = new ArrayList<DownloadInfo>();
			for (int i = 0; i < threadcount - 1; i++)
			{
				DownloadInfo info = new DownloadInfo(i, i * range, (i + 1)
					* range - 1, 0, urlstr);
				infos.add(info);
			}
			DownloadInfo info = new DownloadInfo(threadcount - 1,
				(threadcount - 1) * range, fileSize - 1, 0, urlstr);
			infos.add(info);
			// 保存infos中的数据到数据库
			DownloadDao.getInstance(context).saveInfos(infos);
			// 创建一个LoadInfo对象记载下载器的具体信息
			return new DownloadInfoDetail(fileSize, 0, urlstr);
		}
		else
		{
			// 得到数据库中已有的urlstr的下载器的具体信息
			infos = DownloadDao.getInstance(context).getInfos(urlstr);
			Log.v("TAG", "not isFirst size=" + infos.size());
			int size = 0;
			int compeleteSize = 0;
			for (DownloadInfo info : infos)
			{
				compeleteSize += info.getCompeleteSize();
				size += info.getEndPos() - info.getStartPos() + 1;
			}
			return new DownloadInfoDetail(size, compeleteSize, urlstr);
		}
	}

	/** 初始化 */
	private void init()
	{
		try
		{
			URL url = new URL(urlstr);
			HttpURLConnection connection = (HttpURLConnection) url
				.openConnection();
			connection.setConnectTimeout(5000);
			connection.setRequestMethod("GET");
			fileSize = connection.getContentLength();

			File destDir = new File(path);
			if (!destDir.exists())
			{
				destDir.mkdirs();
			}
			File file = new File(localfile);
			if (!file.exists())
			{
				file.createNewFile();
				System.out.println("新建文件成功");
			}
			// 本地访问文件
			RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
			accessFile.setLength(fileSize);
			accessFile.close();
			connection.disconnect();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/** 判断是否是第一次 下载 */
	private boolean isFirst(String urlstr)
	{
		return DownloadDao.getInstance(context).isHasInfors(urlstr);
	}

	/** 利用线程开始下载数据 */
	public void download()
	{
		if (infos != null)
		{
			if (state == DOWNLOADING)
			{
				return;
			}
			state = DOWNLOADING;
			for (DownloadInfo info : infos)
			{
				new MyThread(info.getThreadId(), info.getStartPos(),
					info.getEndPos(), info.getCompeleteSize(),
					info.getUrl()).start();
			}
		}
	}

	private class MyThread extends Thread
	{
		private int threadId;
		private int startPos;
		private int endPos;
		private int compeleteSize;
		private String urlstr;

		public MyThread(int threadId, int startPos, int endPos,
						int compeleteSize, String urlstr)
		{
			this.threadId = threadId;
			this.startPos = startPos;
			this.endPos = endPos;
			this.compeleteSize = compeleteSize;
			this.urlstr = urlstr;
		}

		@Override
		public void run()
		{
			HttpURLConnection connection = null;
			RandomAccessFile randomAccessFile = null;
			InputStream is = null;
			try
			{
				URL url = new URL(urlstr);
				connection = (HttpURLConnection) url.openConnection();
				connection.setConnectTimeout(5000);
				connection.setRequestMethod("GET");
				// 设置范围，格式为Range：bytes x-y;
				connection.setRequestProperty("Range", "bytes="
					+ (startPos + compeleteSize) + "-" + endPos);

				randomAccessFile = new RandomAccessFile(localfile, "rwd");
				randomAccessFile.seek(startPos + compeleteSize);
				// 将要下载的文件写到保存在保存路径下的文件中
				is = connection.getInputStream();
				byte[] buffer = new byte[4096];
				int length = -1;
				while ((length = is.read(buffer)) != -1)
				{
					randomAccessFile.write(buffer, 0, length);
					compeleteSize += length;
					// 更新数据库中的下载信息
					DownloadDao.getInstance(context).updataInfos(threadId,
						compeleteSize, urlstr);
					// 用消息将下载信息传给进度条，对进度条进行更新
					Message message = Message.obtain();
					message.what = 5478;
					message.obj = urlstr;
					message.arg1 = length;
					mHandler.sendMessage(message);
					if (state == PAUSE)
					{
						return;
					}
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	// 删除数据库中urlstr对应的下载器信息
	public void delete(String urlstr)
	{
		DownloadDao.getInstance(context).delete(urlstr);
	}

	// 设置暂停
	public void pause()
	{
		state = PAUSE;
	}

	// 重置下载状态
	public void reset()
	{
		state = INIT;
	}
}