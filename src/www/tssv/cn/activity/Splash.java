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
import android.app.Activity;
import android.content.Intent;
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
			+ "/tssv.sqlite";

	public void checkDB() {
		File file = new File("/mnt/sdcard/tssv.sqlite");
		if (!file.exists()) {
			AppLog.e("no exist");
			copyDataBase("/mnt/sdcard/tssv.sqlite");
		}else{
			AppLog.e("exist");
		}
			
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
