package www.tssv.cn.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

public class DBHelper extends SQLiteOpenHelper {

	private static final String NAME = "tssv.sqlite";
	private static final String DB_PATH = Environment
			.getExternalStorageDirectory() + "/";
	private static final int DB_VERSION = 1;
	private static final String LIVE_SQL = "CREATE TABLE live ('live_id' INTEGER PRIMARY KEY  AUTOINCREMENT, 'live_img' VARCHAR, 'live_title' VARCHAR, 'live_content' VARCHAR, 'live_url' VARCHAR, 'live_pragram_url' VARCHAR)";
	private static final String NEWS_SQL = "CREATE TABLE news ('news_id' INTEGER PRIMARY KEY  AUTOINCREMENT, 'news_img' VARCHAR, 'news_title' VARCHAR, 'news_content' VARCHAR, 'news_url' VARCHAR)";
	private static final String COLUMN_SQL = "CREATE TABLE colu ('column_id' INTEGER PRIMARY KEY  AUTOINCREMENT, 'column_img' VARCHAR, 'column_title' VARCHAR, 'column_type' INTEGER)";
	public DBHelper(Context context) {
		super(context, DB_PATH + NAME, null, DB_VERSION);
	}

	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(LIVE_SQL);
		db.execSQL(NEWS_SQL);
		db.execSQL(COLUMN_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS live");
		db.execSQL("DROP TABLE IF EXISTS news");
		db.execSQL("DROP TABLE IF EXISTS column");
	}

}
