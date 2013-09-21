package www.tssv.cn.activity;

import www.tssv.cn.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Column_Page extends Fragment{

	private View columnView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		columnView = inflater.inflate(R.layout.column_page, null);
		return columnView;
	}

	

}
