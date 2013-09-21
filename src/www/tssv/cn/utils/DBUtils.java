package www.tssv.cn.utils;

import java.io.Serializable;

import www.tssv.cn.db.DBHelper;

import android.content.Context;

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
	
	

}
