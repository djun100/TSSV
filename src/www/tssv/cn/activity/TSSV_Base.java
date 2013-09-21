package www.tssv.cn.activity;

import www.tssv.cn.R;
import android.app.Activity;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

public class TSSV_Base extends FragmentActivity {

	
	public static Toast infoToast = null;
	public ConnectivityManager con = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}
	
	public void showInfo(int info) {
		if (infoToast == null) {
			infoToast = Toast.makeText(this, info, Toast.LENGTH_SHORT);
		} else {
			infoToast.setText(info);
		}
		infoToast.show();
	}

	public void showInfo(String info) {
		if (infoToast == null) {
			infoToast = Toast.makeText(this, info, Toast.LENGTH_SHORT);
		} else {
			infoToast.setText(info);
		}
		infoToast.show();
	}

	/**
	 * 添加网络检查
	 * 
	 * @return
	 */
	public boolean checkNetwork() {
		if (con == null) {
			con = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
		}
		boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.isConnectedOrConnecting();
		boolean internet = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.isConnectedOrConnecting();
		if (!wifi && !internet) {
			showInfo(R.string.network_check_error);
			return false;
		} else {
			return true;
		}
	}

}
