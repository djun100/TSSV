package www.tssv.cn.activity;

import java.util.Timer;
import java.util.TimerTask;

import www.tssv.cn.R;
import www.tssv.cn.TSSV_Exit;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

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
	}

	@Override
	protected void onPause() {
		super.onPause();
		isDie = true;
		finish();
	}
}
