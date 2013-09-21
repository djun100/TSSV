package www.tssv.cn.activity;

import www.tssv.cn.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Setting_Page extends Fragment{

	private View settingView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		settingView = inflater.inflate(R.layout.setting_page, null);
		return settingView;
	}
}
