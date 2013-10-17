package www.tssv.cn.activity;

import java.io.File;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import www.tssv.cn.AppLog;
import www.tssv.cn.R;
import www.tssv.cn.utils.FileSize;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Setting_Page extends Fragment implements OnClickListener, OnTouchListener{

	private ImageLoader imageLoader = ImageLoader.getInstance();
	private View settingView;
	private TextView shareSoft, clearCache, feedback, contack_me, qulity_apps, cache_size;
	private RelativeLayout shaeSoftLayout, clearCacheLayout, feedbackLayout, contack_meLayout, qulity_appsLayout;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

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
		calcuCacheSize(getActivity());
		super.onResume();
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