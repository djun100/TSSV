package www.tssv.cn.activity;

import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;

import www.tssv.cn.R;
import www.tssv.cn.adpater.ColumnPageAdapter;
import www.tssv.cn.type.TypeColumn;
import www.tssv.cn.utils.DBUtils;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class Column_Page extends Fragment {

	protected static final String STATE_PAUSE_ON_SCROLL = "STATE_PAUSE_ON_SCROLL";
	protected static final String STATE_PAUSE_ON_FLING = "STATE_PAUSE_ON_FLING";
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private Activity activity;
	private View columnView;
	private DBUtils dbUtils;
	private List<TypeColumn> lists;
	private ColumnPageAdapter adapter;
	private GridView gridView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
		adapter = new ColumnPageAdapter(activity, imageLoader);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		gridView = (GridView) columnView.findViewById(R.id.gridview);
		gridView.setAdapter(adapter);
		new InitData().execute();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		columnView = inflater.inflate(R.layout.column_page, null);
		columnView.findViewById(R.id.home_top_bg).setBackgroundResource(
				R.drawable.column_logo);
		return columnView;
	}

	protected boolean pauseOnScroll = false;
	protected boolean pauseOnFling = true;

	@Override
	public void onResume() {
		super.onResume();
		applyScrollListener();
	}

	private void applyScrollListener() {
		gridView.setOnScrollListener(new PauseOnScrollListener(imageLoader,
				pauseOnScroll, pauseOnFling));
	}

	class InitData extends AsyncTask<Void, Void, Void> {
		InitData() {
			dbUtils = new DBUtils(activity);
		}

		@Override
		protected Void doInBackground(Void... params) {
			lists = dbUtils.getAllColumns();
			adapter.setList(lists);
			return null;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Void result) {
			adapter.notifyDataSetChanged();
			dbUtils.close();
			super.onPostExecute(result);
		}
	}

}
