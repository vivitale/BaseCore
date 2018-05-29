package talex.zsw.basecore.util.download;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 作用: 建立一个数据库帮助类,用来保存下载信息
 * 作者: 赵小白 email:edisonzsw@icloud.com 
 * 日期: 2017/2/14 09:10 
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
@SuppressWarnings("WeakerAccess")
public class DownloadDBHelper extends SQLiteOpenHelper
{
	// download.db-->数据库名
	DownloadDBHelper(Context context)
	{
		super(context, "download.db", null, 1);
	}

	/** 在download.db数据库下创建一个download_info表存储下载信息 */
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(
			"create table download_info(_id integer PRIMARY KEY AUTOINCREMENT, thread_id integer, "
				+ "start_pos integer, end_pos integer, compelete_size integer,url char)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{

	}
}