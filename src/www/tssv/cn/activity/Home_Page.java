package www.tssv.cn.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import www.tssv.cn.R;
import www.tssv.cn.adpater.HomePageAdapter;
import www.tssv.cn.type.TypeHome;
import www.tssv.cn.utils.DBUtils;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

public class Home_Page extends Fragment {

	private Activity activity;
	private View homeView;
	private View listViewHeader;
	private HomePageAdapter adapter;
	private List<TypeHome> lists;
	private ViewPager viewPager; // android-support-v4中的滑动组件
	private List<ImageView> imageViews; // 滑动的图片集合
	private ListView listView;
	private String[] titles; // 图片标题
	private int[] imageResId; // 图片ID
	private List<View> dots; // 图片标题正文的那些点
	private DBUtils dbUtils = null;
	private ProgressDialog progressDialog;

	private TextView tv_title;
	private int currentItem = 0; // 当前图片的索引号
	// An ExecutorService that can schedule commands to run after a given delay,
	// or to execute periodically.
	private ScheduledExecutorService scheduledExecutorService;

	// 切换当前显示的图片
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(currentItem);// 切换当前显示的图片
		};
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
		adapter = new HomePageAdapter(activity);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		progressDialog = new ProgressDialog(activity);
		progressDialog.setMessage("加载中...");
		listView = (ListView) homeView.findViewById(R.id.listview_home);
		listView.addHeaderView(listViewHeader, null, true);
		listView.setAdapter(adapter);
		imageResId = new int[] { R.drawable.dot0, R.drawable.dot1,
				R.drawable.dot2, R.drawable.dot3, R.drawable.dot4 };
		titles = new String[imageResId.length];
		titles[0] = "献出你的爱心，为贫困山区的孩子";
		titles[1] = "<<古豆啥呢>> 第二十期 跟我学唐山话2";
		titles[2] = "评剧交响--《盛世华章》访主创郝立轩、罗慧琴";
		titles[3] = "平民艺术憾人心--访著名评剧表演艺术家谷文月";
		titles[4] = "访评剧艺术家--崔连润";

		imageViews = new ArrayList<ImageView>();

		// 初始化图片资源
		for (int i = 0; i < imageResId.length; i++) {
			ImageView imageView = new ImageView(activity);
			imageView.setImageResource(imageResId[i]);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			imageViews.add(imageView);
		}

		dots = new ArrayList<View>();
		dots.add(listViewHeader.findViewById(R.id.v_dot0));
		dots.add(listViewHeader.findViewById(R.id.v_dot1));
		dots.add(listViewHeader.findViewById(R.id.v_dot2));
		dots.add(listViewHeader.findViewById(R.id.v_dot3));
		dots.add(listViewHeader.findViewById(R.id.v_dot4));

		tv_title = (TextView) listViewHeader.findViewById(R.id.tv_title);
		tv_title.setText(titles[0]);

		viewPager = (ViewPager) listViewHeader.findViewById(R.id.vp);
		viewPager.setAdapter(new MyAdapter());// 设置填充ViewPager页面的适配器
		// 设置一个监听器，当ViewPager中的页面改变时调用
		viewPager.setOnPageChangeListener(new MyPageChangeListener());
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TypeHome typeHome = lists.get(position - 1);
				//=================================
				// 2013-09-25 intent to player
				// just a test
				startLiveMedia(typeHome.getHome_url(), typeHome.getHome_title());
				//=================================
			}
		});
		new InitData().execute();
	}

	/**
	 * 启动播放器界面
	 * 
	 * @param liveUrl
	 * @param title
	 */
	private void startLiveMedia(String liveUrl, String title) {
		Intent intent = new Intent(activity, PlayerActivity.class);

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
		homeView = inflater.inflate(R.layout.home_page, null);
		listViewHeader = inflater.inflate(R.layout.imagechange, null);
		homeView.findViewById(R.id.home_top_bg).setBackgroundResource(
				R.drawable.home_logo);
		return homeView;
	}

	@Override
	public void onStart() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		// 当Activity显示出来后，每两秒钟切换一次图片显示
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2,
				TimeUnit.SECONDS);
		super.onStart();
	}

	@Override
	public void onStop() {
		// 当Activity不可见的时候停止切换
		scheduledExecutorService.shutdown();
		super.onStop();
	}

	class InitData extends AsyncTask<Void, Void, Void> {
		InitData() {
			dbUtils = new DBUtils(activity);
		}

		@Override
		protected Void doInBackground(Void... paramArrayOfVoid) {
			try {
				lists = dbUtils.getAllHomes();
				adapter.setList(lists);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			progressDialog.cancel();
			dbUtils.close();
			// adapter.notifyDataSetChanged();
		}
	}

	/**
	 * 当ViewPager中页面的状态发生改变时调用
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyPageChangeListener implements OnPageChangeListener {
		private int oldPosition = 0;

		/**
		 * This method will be invoked when a new page becomes selected.
		 * position: Position index of the new selected page.
		 */
		public void onPageSelected(int position) {
			currentItem = position;
			tv_title.setText(titles[position]);
			dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
			dots.get(position).setBackgroundResource(R.drawable.dot_focused);
			oldPosition = position;
		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}
	}

	/**
	 * 填充ViewPager页面的适配器
	 */
	private class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return imageResId.length;
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(imageViews.get(arg1));
			return imageViews.get(arg1);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView((View) arg2);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}

		@Override
		public void finishUpdate(View arg0) {

		}
	}

	/**
	 * 换行切换任务
	 * 
	 * @author jie
	 * 
	 */
	private class ScrollTask implements Runnable {
		public void run() {
			synchronized (viewPager) {
				currentItem = (currentItem + 1) % imageViews.size();
				handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
			}
		}
	}
}
