package www.tssv.cn.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;

import www.tssv.cn.AppLog;
import www.tssv.cn.R;
import www.tssv.cn.TSSV_Exit;
import www.tssv.cn.TSetting;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Environment;

public class Splash extends Activity {

	private Timer timer;
	private boolean isDie = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		TSSV_Exit.getInstance().addActivity(this);
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (!isDie) {
					Intent intent = new Intent();
					intent.setClass(Splash.this, TSSV_Main.class);
					startActivity(intent);
				}
			}
		}, 2000);
		checkDB();
	}

	@Override
	protected void onPause() {
		super.onPause();
		isDie = true;
		finish();
	}

	private String dbPath = Environment.getExternalStorageDirectory()
			+ "/TSSV/tssv.sqlite";
	private String dbDir = Environment.getExternalStorageDirectory()
			+ "/TSSV";
	
	public void checkDB() {
		AppLog.e(Environment.getExternalStorageDirectory()+"");
		File fileDir = new File(dbDir);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
			AppLog.e("创建目录");
		}else {
			AppLog.e("目录已经存在");
		}
		File file = new File(dbPath);
		if (isFirstIn()) {
			if (file.exists()) {
				file.delete();
				AppLog.e("删除文件");
			}else {
				AppLog.e("第一次启动");
			}
			writeFirstParm();
			copyDataBase(dbPath);
		}else if(!file.exists()){
			copyDataBase(dbPath);
		}
	}
	
	public boolean isFirstIn() {
		SharedPreferences preferences = getSharedPreferences(
				TSetting.ISFIRSTLOAD, MODE_PRIVATE);
		boolean isFirst = preferences.getBoolean("isFirstIn", true);
		return isFirst;
	}

	public void writeFirstParm() {
		SharedPreferences preferences = getSharedPreferences(
				TSetting.ISFIRSTLOAD, MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putBoolean("isFirstIn", false);
		editor.commit();
	}

	private void copyDataBase(String outpath) {
		try {
			InputStream myInput = getAssets().open("tssv.sqlite");
			// Path to the just created empty db
			// Open the empty db as the output stream
			OutputStream myOutput = new FileOutputStream(outpath);
			// transfer bytes from the inputfile to the outputfile
			byte[] buffer = new byte[1024];
			int length;
			while ((length = myInput.read(buffer)) > 0) {
				myOutput.write(buffer, 0, length);
			}
			// Close the streams
			myOutput.flush();
			myOutput.close();
			myInput.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
