package www.tssv.cn.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import www.tssv.cn.AppLog;
import www.tssv.cn.db.DBHelper;
import www.tssv.cn.type.TypeNews;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBUtils implements Serializable {

	private static final long serialVersionUID = -6756346192227931446L;
	private static DBHelper mDBHelper;

	public DBUtils(Context paramContext) {
		mDBHelper = new DBHelper(paramContext);
	}

	public void close() {
		if (mDBHelper != null) {
			mDBHelper.close();
		}
	}

	public void insertNews(TypeNews news) {
		SQLiteDatabase db = null;
		try {
			db = mDBHelper.getWritableDatabase();
			ContentValues contentValues = new ContentValues();
			contentValues.put("news_img", news.getNews_img());
			contentValues.put("news_title", news.getNews_title());
			contentValues.put("news_content", news.getNews_content());
			contentValues.put("news_url", news.getNews_url());
			db.insertOrThrow("news", null, contentValues);
			AppLog.e(news.getNews_title() + "  :插入");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取所有的news的視頻的个数
	 * 
	 * @return
	 */
	public int getNewsCount() {
		SQLiteDatabase db = mDBHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT count(*) FROM news", null);
		cursor.moveToNext();
		int coutn = cursor.getInt(0);
		cursor.close();
		db.close();
		return coutn;
	}
	
	/**
	 * 获取所有的新闻
	 * 
	 * @return
	 */
	public List<TypeNews> getAllNews() {
		List<TypeNews> typeNews = new ArrayList<TypeNews>();
		SQLiteDatabase db = mDBHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM news", null);
		while (cursor.moveToNext()) {
			TypeNews news = new TypeNews();
			news.setNews_id(cursor.getInt(0));
			news.setNews_img(cursor.getString(1));
			news.setNews_title(cursor.getString(2));
			news.setNews_content(cursor.getString(3));
			news.setNews_url(cursor.getString(4));
			typeNews.add(news);
		}
		return typeNews;
	}

}
