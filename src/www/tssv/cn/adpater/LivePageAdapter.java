package www.tssv.cn.adpater;

import java.util.List;

import www.tssv.cn.R;
import www.tssv.cn.type.TypeLive;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LivePageAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<TypeLive> listItems;
	public LivePageAdapter(Context context) {
		inflater = LayoutInflater.from(context);
	}

	public void setList(List<TypeLive> listItems){
		this.listItems = listItems;
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

}
