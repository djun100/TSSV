package www.tssv.cn.adpater;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import www.tssv.cn.R;
import www.tssv.cn.parser.LivePragram;
import www.tssv.cn.type.TypeLive;
import www.tssv.cn.type.TypePragrams;
import www.tssv.cn.utils.DateUtils;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LivePageAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<TypeLive> listItems;
	private int weekDay = DateUtils.getTodayWeekDay();
	private ArrayList<TypePragrams> list_1 = null, list_2 =null, list_3 = null, list_4 = null;
	
	public LivePageAdapter(Context context) {
		inflater = LayoutInflater.from(context);
	}

	public void setList(List<TypeLive> listItems){
		this.listItems = listItems;
		new InitData().execute();
	}
	@Override
	public int getCount() {
		if (listItems != null) {
			return listItems.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return listItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.listview_item, null);
			viewHolder.live_title = (TextView)convertView.findViewById(R.id.item_title);
			viewHolder.live_content = (TextView)convertView.findViewById(R.id.item_conten);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		TypeLive typeLive = listItems.get(position);
		viewHolder.live_title.setText(typeLive.getLive_title());
		viewHolder.live_content.setText(typeLive.getLive_content());
		return convertView;
	}
	
	class ViewHolder{
		private TextView live_title;
		private TextView live_content;
	}
	
	class InitData extends AsyncTask<Void, Void, Void> {
		InitData() {
		}

		@Override
		protected Void doInBackground(Void... paramArrayOfVoid) {
			try {
				if (list_1 == null) {
					LivePragram livePragram = new LivePragram();
					TypePragrams typePragrams = null;
					for (int i = 0; i < 4; i++) {
						TypeLive live = listItems.get(i);
						String url = live.getLive_pragram_url()+weekDay+".html";
						switch (i) {
						case 0:
							list_1 = livePragram.ParsePragram(url);
							typePragrams = list_1.get(0);
							listItems.get(i).setLive_content(typePragrams.getTitle());
							break;
						case 1:
							list_2 = livePragram.ParsePragram(url);
							typePragrams = list_2.get(0);
							listItems.get(i).setLive_content(typePragrams.getTitle());
							break;
						case 2:
							list_3 = livePragram.ParsePragram(url);
							typePragrams = list_3.get(0);
							listItems.get(i).setLive_content(typePragrams.getTitle());
							break;
						case 3:
							list_4 = livePragram.ParsePragram(url);
							typePragrams = list_4.get(0);
							listItems.get(i).setLive_content(typePragrams.getTitle());
							break;
						}
					}
				}else {
					int cur_time = DateUtils.getDateToInt();
					int count = list_1.size();
					for (int i = 1; i < count; i++) {
						Date curr = DateUtils.getStringToDate(list_1.get(i).getTime().trim());
						int cur = DateUtils.getDateToInt(curr);
						if (cur_time < cur) {
							listItems.get(0).setLive_content(list_1.get(i-1).getTitle());
							break;
						}
					}
					count = list_2.size();
					for (int i = 1; i < count; i++) {
						Date curr = DateUtils.getStringToDate(list_2.get(i).getTime().trim());
						int cur = DateUtils.getDateToInt(curr);
						if (cur_time < cur) {
							listItems.get(1).setLive_content(list_2.get(i-1).getTitle());
							break;
						}
					}
					count = list_3.size();
					for (int i = 1; i < count; i++) {
						Date curr = DateUtils.getStringToDate(list_3.get(i).getTime().trim());
						int cur = DateUtils.getDateToInt(curr);
						if (cur_time < cur) {
							listItems.get(2).setLive_content(list_3.get(i-1).getTitle());
							break;
						}
					}
					count = list_4.size();
					for (int i = 1; i < count; i++) {
						Date curr = DateUtils.getStringToDate(list_4.get(i).getTime().trim());
						int cur = DateUtils.getDateToInt(curr);
						if (cur_time < cur) {
							listItems.get(3).setLive_content(list_4.get(i-1).getTitle());
							break;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			notifyDataSetChanged();
		}
	}

}
