package talex.zsw.basecore.util.download;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * 作用: 一个业务类,封装有各种SQL语句,用于下载时的各种操作
 * 作者: 赵小白 email:edisonzsw@icloud.com 
 * 日期: 2017/2/14 09:10 
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
@SuppressWarnings("WeakerAccess")
public class DownloadDao
{
	private static DownloadDao dao = null;
	private Context context;

	private DownloadDao(Context context)
	{
		this.context = context;
	}

	public static DownloadDao getInstance(Context context)
	{
		if(dao == null)
		{
			dao = new DownloadDao(context);
		}
		return dao;
	}

	private SQLiteDatabase getConnection()
	{
		SQLiteDatabase sqliteDatabase = null;
		try
		{
			sqliteDatabase = new DownloadDBHelper(context).getReadableDatabase();
		}
		catch(Exception ignored)
		{
		}
		return sqliteDatabase;
	}

	/**
	 * 查看数据库中是否有数据
	 */
	public synchronized boolean isHasInfors(String urlstr)
	{
		SQLiteDatabase database = getConnection();
		int count = -1;
		Cursor cursor = null;
		try
		{
			String sql = "select count(*)  from download_info where url=?";
			cursor = database.rawQuery(sql, new String[]{urlstr});
			if(cursor.moveToFirst())
			{
				count = cursor.getInt(0);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(null != database)
			{
				database.close();
			}
			if(null != cursor)
			{
				cursor.close();
			}
		}
		return count == 0;
	}

	/**
	 * 保存 下载的具体信息
	 */
	public synchronized void saveInfos(List<DownloadInfo> infos)
	{
		SQLiteDatabase database = getConnection();
		try
		{
			for(DownloadInfo info : infos)
			{
				String sql
					= "insert into download_info(thread_id,start_pos, end_pos,compelete_size,url) values (?,?,?,?,?)";
				Object[] bindArgs = {info.getThreadId(), info.getStartPos(), info.getEndPos(), info.getCompeleteSize(),
					info.getUrl()};
				database.execSQL(sql, bindArgs);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(null != database)
			{
				database.close();
			}
		}
	}

	/**
	 * 得到下载具体信息
	 */
	public synchronized List<DownloadInfo> getInfos(String urlstr)
	{
		List<DownloadInfo> list = new ArrayList<DownloadInfo>();
		SQLiteDatabase database = getConnection();
		Cursor cursor = null;
		try
		{
			String sql = "select thread_id, start_pos, end_pos,compelete_size,url from download_info where url=?";
			cursor = database.rawQuery(sql, new String[]{urlstr});
			while(cursor.moveToNext())
			{
				DownloadInfo info
					= new DownloadInfo(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), cursor.getString(4));
				list.add(info);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(null != database)
			{
				database.close();
			}
			if(null != cursor)
			{
				cursor.close();
			}
		}
		return list;
	}

	/**
	 * 更新数据库中的下载信息
	 */
	public synchronized void updataInfos(int threadId, int compeleteSize, String urlstr)
	{
		SQLiteDatabase database = getConnection();
		try
		{
			String sql = "update download_info set compelete_size=? where thread_id=? and url=?";
			Object[] bindArgs = {compeleteSize, threadId, urlstr};
			database.execSQL(sql, bindArgs);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(null != database)
			{
				database.close();
			}
		}
	}

	/**
	 * 下载完成后删除数据库中的数据
	 */
	public synchronized void delete(String url)
	{
		SQLiteDatabase database = getConnection();
		try
		{
			database.delete("download_info", "url=?", new String[]{url});
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(null != database)
			{
				database.close();
			}
		}
	}

	/**
	 * 下载完成后删除数据库中的数据
	 */
	public synchronized void delete()
	{
		SQLiteDatabase database = getConnection();
	}
}