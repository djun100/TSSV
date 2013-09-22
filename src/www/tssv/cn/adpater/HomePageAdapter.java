package www.tssv.cn.adpater;

import java.util.List;

import www.tssv.cn.R;
import www.tssv.cn.type.TypeHome;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HomePageAdapter extends BaseAdapter {

	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private LayoutInflater inflater;
	private DisplayImageOptions options;
	private List<TypeHome> listItems;

	public HomePageAdapter(Context convert) {
		inflater = LayoutInflater.from(convert);
		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.img_logo)
				.showImageOnFail(R.drawable.img_logo).cacheInMemory(true)
				.cacheOnDisc(true).displayer(new RoundedBitmapDisplayer(20))
				.build();
	}

	public void setList(List<TypeHome> listItems) {
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
			convertView = inflater.inflate(R.layout.home_item, null);
			viewHolder = new ViewHolder();
			viewHolder.home_img = (ImageView) convertView
					.findViewById(R.id.home_item_imageview);
			viewHolder.home_title = (TextView) convertView
					.findViewById(R.id.home_item_title);
			viewHolder.home_content = (TextView) convertView
					.findViewById(R.id.home_item_conten);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		TypeHome typeHome = listItems.get(position);
		viewHolder.home_title.setText(typeHome.getHome_title());
		viewHolder.home_content.setText(typeHome.getHome_content());
		imageLoader.displayImage(typeHome.getHome_img(), viewHolder.home_img, options);
		return convertView;
	}

	class ViewHolder {
		public ImageView home_img;
		public TextView home_title;
		public TextView home_content;
		public ImageView home_run;
	}

}
