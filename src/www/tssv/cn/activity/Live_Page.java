package www.tssv.cn.activity;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;

import www.tssv.cn.R;
import www.tssv.cn.adpater.LivePageAdapter;
import www.tssv.cn.dom.DomLive;
import www.tssv.cn.type.TypeLive;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
	private Activity activity;
	public static final int FRESH_FINISH = 8888;
	@SuppressLint("HandlerLeak")
	public Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case FRESH_FINISH:
				pullToRefreshListView.onRefreshComplete();
				break;
			}
			super.handleMessage(msg);
		}
	};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
		domLive = new DomLive();
		progressDialog = new ProgressDialog(activity);
		progressDialog.setMessage("加载中...");
		adapter = new LivePageAdapter(activity);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		pullToRefreshListView = (PullToRefreshListView) liveView
				.findViewById(R.id.listview_live);
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
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TypeLive typeLive = lists.get(position-1);
				startLiveMedia(typeLive.getLive_url(), typeLive.getLive_title());
			}
		});
	}

	/**
	 * 启动播放器界面
	 * 
	 * @param liveUrl
	 * @param title
	 */
	private void startLiveMedia(String liveUrl, String title) {
		Intent intent = new Intent(getActivity(), PlayerActivity.class);

		ArrayList<String> playlist = new ArrayList<String>();
		playlist.add(liveUrl);
		intent.putExtra("selected", 0);
		intent.putExtra("playlist", playlist);
		intent.putExtra("title", title);

		startActivity(intent);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		liveView = inflater.inflate(R.layout.live_page, null);
		liveView.findViewById(R.id.home_top_bg).setBackgroundResource(
				R.drawable.live_logo);
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
				if (lists == null) {
					lists = domLive.parseXml(getActivity());
					adapter.setList(lists);
				}else {
					adapter.updatePragram();
				}
				
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
				handler.sendEmptyMessageDelayed(FRESH_FINISH, 5000);
			}
		}
	}
}
