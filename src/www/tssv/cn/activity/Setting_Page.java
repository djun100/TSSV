package www.tssv.cn.activity;

import java.io.File;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.umeng.fb.FeedbackAgent;
import com.umeng.newxp.common.ExchangeConstants;
import com.umeng.newxp.controller.ExchangeDataService;
import com.umeng.newxp.view.ExchangeViewManager;
import com.umeng.update.UmengDialogButtonListener;
import com.umeng.update.UmengDownloadListener;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

import www.tssv.cn.AppLog;
import www.tssv.cn.R;
import www.tssv.cn.utils.FileSize;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Setting_Page extends Fragment implements OnClickListener, OnTouchListener{

	private ImageLoader imageLoader = ImageLoader.getInstance();
	private View settingView;
	private TextView shareSoft, clearCache, feedback, soft_update, contack_me, qulity_apps, cache_size;
	private RelativeLayout shaeSoftLayout, clearCacheLayout, feedbackLayout, soft_updateLayout, contack_meLayout, qulity_appsLayout;
	private static ExchangeDataService exchangeDataService;
	private Activity activity;
	private FeedbackAgent agent;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		settingView = inflater.inflate(R.layout.setting_page, null);
		settingView.findViewById(R.id.home_top_bg).setBackgroundResource(
				R.drawable.setting_logo);
		exchangeDataService = new ExchangeDataService("40174");
		agent = new FeedbackAgent(activity);
		agent.sync();
		return settingView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		shareSoft = (TextView)settingView.findViewById(R.id.share_tssv);
		shaeSoftLayout = (RelativeLayout)settingView.findViewById(R.id.share_tssv_p);
		shareSoft.setOnClickListener(this);
		shareSoft.setOnTouchListener(this);
		clearCache = (TextView)settingView.findViewById(R.id.clear_cache);
		clearCacheLayout = (RelativeLayout)settingView.findViewById(R.id.clear_cache_p);
		clearCache.setOnClickListener(this);
		clearCache.setOnTouchListener(this);
		cache_size = (TextView)settingView.findViewById(R.id.cache_size_show);
		feedback = (TextView)settingView.findViewById(R.id.soft_feedback);
		feedbackLayout = (RelativeLayout)settingView.findViewById(R.id.soft_feedback_p);
		feedback.setOnClickListener(this);
		feedback.setOnTouchListener(this);
		soft_update = (TextView)settingView.findViewById(R.id.soft_update);
		soft_update.setOnClickListener(this);
		soft_update.setOnTouchListener(this);
		soft_updateLayout = (RelativeLayout)settingView.findViewById(R.id.soft_update_p);
		contack_me = (TextView)settingView.findViewById(R.id.contack_me);
		contack_meLayout = (RelativeLayout)settingView.findViewById(R.id.contack_me_p);
		contack_me.setOnClickListener(this);
		contack_me.setOnTouchListener(this);
		qulity_apps = (TextView)settingView.findViewById(R.id.quality_application);
		qulity_appsLayout = (RelativeLayout)settingView.findViewById(R.id.quality_application_p);
		qulity_apps.setOnClickListener(this);
		qulity_apps.setOnTouchListener(this);
	}

	@Override
	public void onResume() {
		calcuCacheSize(activity);
		super.onResume();
	}

	
	@Override
	public void onStop() {
//	 	如果您同时使用了手动更新和自动检查更新，请加上下面这句代码，因为这些配置是全局静态的。
		UmengUpdateAgent.setUpdateOnlyWifi(true);
		UmengUpdateAgent.setUpdateAutoPopup(true);
		UmengUpdateAgent.setUpdateListener(null);
		UmengUpdateAgent.setDownloadListener(null);
		UmengUpdateAgent.setDialogListener(null);
		super.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	public void calcuCacheSize(Context context){
		File path = StorageUtils.getCacheDirectory(context);//获取缓存的目录，如果不存在，则进行创建,获取的是目录
		new GetCacheSizeAsyncTask().execute(path.getAbsolutePath());
	}
	
	public class GetCacheSizeAsyncTask extends AsyncTask<String, Integer, String>{
		@Override
		protected String doInBackground(String... params) {
			FileSize fileSize = new FileSize(new File(params[0]));
			return fileSize.toString();
		}

		@Override
		protected void onPostExecute(String result) {
			cache_size.setText(result);
			super.onPostExecute(result);
		}
	}
	UmengUpdateListener updateListener = new UmengUpdateListener() {
		@Override
		public void onUpdateReturned(int updateStatus,
				UpdateResponse updateInfo) {
			switch (updateStatus) {
			case 0: // has update
				Log.i("--->", "callback result");
				UmengUpdateAgent.showUpdateDialog(activity, updateInfo);
				break;
			case 1: // has no update
				Toast.makeText(activity, "没有更新", Toast.LENGTH_SHORT)
						.show();
				break;
			case 2: // none wifi
				Toast.makeText(activity, "没有wifi连接， 只在wifi下更新", Toast.LENGTH_SHORT)
						.show();
				break;
			case 3: // time out
				Toast.makeText(activity, "超时", Toast.LENGTH_SHORT)
						.show();
				break;
			case 4: // is updating
				/*Toast.makeText(mContext, "正在下载更新...", Toast.LENGTH_SHORT)
						.show();*/
				break;
			}

		}
	};
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.share_tssv:
			ShareTSSV();
			break;
		case R.id.clear_cache:
			imageLoader.clearMemoryCache();
			imageLoader.clearDiscCache();
			cache_size.setText(R.string.cache_size_temp);
			break;
		case R.id.soft_feedback:
			agent.startFeedbackActivity();
			AppLog.e("soft_feedback");
			break;
		case R.id.soft_update:
			// 如果想程序启动时自动检查是否需要更新， 把下面两行代码加在Activity 的onCreate()函数里。
			com.umeng.common.Log.LOG = true;
			
			UmengUpdateAgent.setUpdateOnlyWifi(false); // 目前我们默认在Wi-Fi接入情况下才进行自动提醒。如需要在其他网络环境下进行更新自动提醒，则请添加该行代码
			UmengUpdateAgent.setUpdateAutoPopup(false);
			UmengUpdateAgent.setUpdateListener(updateListener);
			UmengUpdateAgent.setDownloadListener(new UmengDownloadListener(){
			    @Override
			    public void OnDownloadStart() {
			        Toast.makeText(activity, "download start" , Toast.LENGTH_SHORT).show();
			    }
			    @Override
			    public void OnDownloadUpdate(int progress) {
			        Toast.makeText(activity, "download progress : " + progress + "%" , Toast.LENGTH_SHORT).show();
			    }
			    @Override
			    public void OnDownloadEnd(int result, String file) {
			        Toast.makeText(activity, "download file path : " + file , Toast.LENGTH_SHORT).show();
			    }           
			});
			UmengUpdateAgent.setDialogListener(new UmengDialogButtonListener() {
			    @Override
			    public void onClick(int status) {
			        switch (status) {
			        case UpdateStatus.Update:
			            Toast.makeText(activity, "User chooses update." , Toast.LENGTH_SHORT).show();
			            break;
			        case UpdateStatus.Ignore:
			            Toast.makeText(activity, "User chooses ignore." , Toast.LENGTH_SHORT).show();
			            break;
			        case UpdateStatus.NotNow:
			            Toast.makeText(activity, "User chooses cancel." , Toast.LENGTH_SHORT).show();
			            break;
			        }
			    }
			});
			UmengUpdateAgent.forceUpdate(activity);
			break;
		case R.id.contack_me:
			AppLog.e("contack_me");
			break;
		case R.id.quality_application:
			ExchangeViewManager viewManager = new ExchangeViewManager(activity, exchangeDataService);
			viewManager.addView(ExchangeConstants.type_list_curtain, null);
			break;
		}
	}
	
	public void ShareTSSV(){
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.share_subject);
		shareIntent.putExtra(Intent.EXTRA_TEXT, R.string.share_text);
		shareIntent.putExtra(Intent.EXTRA_TITLE, R.string.share_title);
		shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.share_select)));
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
		case R.id.share_tssv:
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				shaeSoftLayout.setBackgroundResource(R.drawable.press_up_concor);
				break;
			case MotionEvent.ACTION_UP:
				shaeSoftLayout.setBackgroundResource(R.drawable.nor_up_concor);
				break;
			}
			break;
		case R.id.clear_cache:
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				clearCacheLayout.setBackgroundColor(getResources().getColor(R.color.papayawhip));
				break;
			case MotionEvent.ACTION_UP:
				clearCacheLayout.setBackgroundColor(getResources().getColor(R.color.turnk));
				break;
			}
			break;
		case R.id.soft_feedback:
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				feedbackLayout.setBackgroundColor(getResources().getColor(R.color.papayawhip));
				break;
			case MotionEvent.ACTION_UP:
				feedbackLayout.setBackgroundColor(getResources().getColor(R.color.turnk));
				break;
			}
			break;
		case R.id.soft_update:
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				soft_updateLayout.setBackgroundColor(getResources().getColor(R.color.papayawhip));
				break;
			case MotionEvent.ACTION_UP:
				soft_updateLayout.setBackgroundColor(getResources().getColor(R.color.turnk));
				break;
			}
			break;
		case R.id.contack_me:
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				contack_meLayout.setBackgroundResource(R.drawable.press_down_concor);
				break;
			case MotionEvent.ACTION_UP:
				contack_meLayout.setBackgroundResource(R.drawable.nor_down_concor);
				break;
			}
			break;
		case R.id.quality_application:
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				qulity_appsLayout.setBackgroundResource(R.drawable.press_concor);
				break;
			case MotionEvent.ACTION_UP:
				qulity_appsLayout.setBackgroundResource(R.drawable.nor_concor);
				break;
			}
			break;
		}
		return false;
	}
}