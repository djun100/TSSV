package www.tssv.cn.activity;

import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;

import www.tssv.cn.R;
import www.tssv.cn.adpater.LivePageAdapter;
import www.tssv.cn.dom.DomLive;
import www.tssv.cn.type.TypeLive;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class Live_Page extends Fragment {
	private View liveView;
	private LivePageAdapter adapter;
	private PullToRefreshListView pullToRefreshListView;
	private ListView listView;
	private List<TypeLive> lists;
	private DomLive domLive;
	private ProgressDialog progressDialog;
	private boolean isFirst = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		domLive = new DomLive();
		progressDialog = new ProgressDialog(getActivity());
		progressDialog.setMessage("加载中...");
		adapter = new LivePageAdapter(getActivity());
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		pullToRefreshListView = (PullToRefreshListView) liveView
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		liveView = inflater.inflate(R.layout.live_page, null);
		return liveView;
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
				lists = domLive.parseXml(getActivity());
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
