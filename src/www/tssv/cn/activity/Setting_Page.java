package www.tssv.cn.activity;

import www.tssv.cn.AppLog;
import www.tssv.cn.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class Setting_Page extends Fragment implements OnClickListener{

	private View settingView;
	private TextView shareSoft, clearCache, feedback, contack_me, qulity_apps;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		settingView = inflater.inflate(R.layout.setting_page, null);
		settingView.findViewById(R.id.home_top_bg).setBackgroundResource(
				R.drawable.setting_logo);
		return settingView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		shareSoft = (TextView)settingView.findViewById(R.id.share_tssv);
		shareSoft.setOnClickListener(this);
		clearCache = (TextView)settingView.findViewById(R.id.clear_cache);
		clearCache.setOnClickListener(this);
		feedback = (TextView)settingView.findViewById(R.id.soft_feedback);
		feedback.setOnClickListener(this);
		contack_me = (TextView)settingView.findViewById(R.id.contack_me);
		contack_me.setOnClickListener(this);
		qulity_apps = (TextView)settingView.findViewById(R.id.quality_application);
		qulity_apps.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.share_tssv:
			AppLog.e("Share");
			break;
		case R.id.clear_cache:
			AppLog.e("clear_cache");
			break;
		case R.id.soft_feedback:
			AppLog.e("soft_feedback");
			break;
		case R.id.contack_me:
			AppLog.e("contack_me");
			break;
		case R.id.quality_application:
			AppLog.e("quality_application");
			break;
		}
	}
}