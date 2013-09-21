package www.tssv.cn.server;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;

public class Server extends Activity {

	private ServerNews serverNews;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		File dbFile = new File("/mnt/sdcard/tssv.sqlite");
		if (dbFile.exists()) {
			dbFile.delete();
		}
		serverNews = new ServerNews(this);
	}

}
