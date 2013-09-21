package www.tssv.cn.activity;

import www.tssv.cn.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class News_Page extends Fragment{

	private View newsView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		newsView = inflater.inflate(R.layout.news_page, null);
		return newsView;
	}
}
