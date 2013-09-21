package www.tssv.cn.adpater;

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import www.tssv.cn.R;
import www.tssv.cn.type.TypeNews;
import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsPageAdapter extends BaseAdapter {

	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;
	private LayoutInflater inflater;
	private List<TypeNews> listItems;

	public NewsPageAdapter(Context context) {
		inflater = LayoutInflater.from(context);
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.img_logo)
				.showImageForEmptyUri(R.drawable.img_logo)
				.showImageOnFail(R.drawable.img_logo).cacheInMemory(true)
				.cacheOnDisc(true).displayer(new RoundedBitmapDisplayer(20))
				.build();
	}

	public void setList(List<TypeNews> listItems) {
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
			viewHolder.news_img = (ImageView) convertView
					.findViewById(R.id.item_imageview);
			viewHolder.news_title = (TextView) convertView
					.findViewById(R.id.item_title);
			viewHolder.news_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
			viewHolder.news_content = (TextView) convertView
					.findViewById(R.id.item_conten);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		TypeNews news = listItems.get(position);
		viewHolder.news_title.setText(news.getNews_title());
		viewHolder.news_content.setText(news.getNews_content());
		imageLoader.displayImage(news.getNews_img(), viewHolder.news_img, options);
		return convertView;
	}

	class ViewHolder {
		private ImageView news_img;
		private TextView news_title;
		private TextView news_content;
	}
}
