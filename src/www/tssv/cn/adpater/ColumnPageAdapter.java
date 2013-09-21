package www.tssv.cn.adpater;

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import www.tssv.cn.R;
import www.tssv.cn.type.TypeColumn;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ColumnPageAdapter extends BaseAdapter {

	protected ImageLoader imageLoader;
	private DisplayImageOptions options;
	private LayoutInflater inflater;
	private List<TypeColumn> listItems;

	public ColumnPageAdapter(Context context, ImageLoader imageLoader) {
		inflater = LayoutInflater.from(context);
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.img_logo)
				.showImageForEmptyUri(R.drawable.img_logo)
				.showImageOnFail(R.drawable.img_logo).cacheInMemory(true)
				.cacheOnDisc(true).displayer(new RoundedBitmapDisplayer(20))
				.build();
		this.imageLoader = imageLoader;
	}

	public void setList(List<TypeColumn> listItems) {
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
			convertView = inflater.inflate(R.layout.gridview_item, null);
			viewHolder.column_img = (ImageView) convertView
					.findViewById(R.id.item_imageview);
			viewHolder.column_title = (TextView) convertView
					.findViewById(R.id.item_title);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		TypeColumn typeColumn = listItems.get(position);
		viewHolder.column_title.setText(typeColumn.getColumn_title());
		imageLoader.displayImage(typeColumn.getColumn_img(),
				viewHolder.column_img, options);
		return convertView;
	}

	class ViewHolder {
		private ImageView column_img;
		private TextView column_title;
	}

}
