package www.tssv.cn.activity;

import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import www.tssv.cn.R;
import www.tssv.cn.adpater.NewsPageAdapter;
import www.tssv.cn.type.TypeNews;
import www.tssv.cn.utils.DBUtils;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class News_Page extends Fragment {

	private Activity activity;
	private View newsView;
	private NewsPageAdapter adapter;
	private PullToRefreshListView pullToRefreshListView;
	private ListView listView;
	private List<TypeNews> lists;
	private ProgressDialog progressDialog;
	private boolean isFirst = true;
	private DBUtils dbUtils = null;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		pullToRefreshListView = (PullToRefreshListView) newsView
				.findViewById(R.id.listview);
		pullToRefreshListView.setMode(Mode.PULL_FROM_END);
		listView = pullToRefreshListView.getRefreshableView();
		pullToRefreshListView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						new InitData().execute();
					}
				});
		listView.setAdapter(adapter);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
		progressDialog = new ProgressDialog(activity);
		progressDialog.setMessage("加载中...");
		adapter = new NewsPageAdapter(activity);
		dbUtils = new DBUtils(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		newsView = inflater.inflate(R.layout.news_page, null);
		return newsView;
	}

	@Override
	public void onResume() {
		super.onResume();
		new InitData().execute();
	}

	class InitData extends AsyncTask<Void, Void, Void> {
		InitData() {
		}

		@Override
		protected Void doInBackground(Void... paramArrayOfVoid) {
			try {
				lists = dbUtils.getAllNews();
				adapter.setList(lists);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (isFirst)
				progressDialog.show();
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			adapter.notifyDataSetChanged();
			if (isFirst) {
				progressDialog.cancel();
				isFirst = false;
			} else {
				pullToRefreshListView.onRefreshComplete();
			}
		}
	}
}
