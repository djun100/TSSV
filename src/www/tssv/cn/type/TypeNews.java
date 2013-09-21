package www.tssv.cn.type;

import android.os.Parcel;
import android.os.Parcelable;

public class TypeNews implements Parcelable {

	private int news_id;
	private String news_img;
	private String news_title;
	private String news_content;
	private String news_url;

	public TypeNews() {

	}

	private TypeNews(Parcel in) {
		readFromParcel(in);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public void readFromParcel(Parcel in) {
		news_id = in.readInt();
		news_img = in.readString();
		news_title = in.readString();
		news_content = in.readString();
		news_url = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(news_id);
		dest.writeString(news_img);
		dest.writeString(news_title);
		dest.writeString(news_content);
		dest.writeString(news_url);
	}

	public static Creator<TypeNews> CREATOR = new Creator<TypeNews>() {

		@Override
		public TypeNews createFromParcel(Parcel source) {
			return new TypeNews(source);
		}

		@Override
		public TypeNews[] newArray(int size) {
			return new TypeNews[size];
		}
	};

	public int getNews_id() {
		return news_id;
	}

	public void setNews_id(int news_id) {
		this.news_id = news_id;
	}

	public String getNews_img() {
		return news_img;
	}

	public void setNews_img(String news_img) {
		this.news_img = news_img;
	}

	public String getNews_title() {
		return news_title;
	}

	public void setNews_title(String news_title) {
		this.news_title = news_title;
	}

	public String getNews_content() {
		return news_content;
	}

	public void setNews_content(String news_content) {
		this.news_content = news_content;
	}

	public String getNews_url() {
		return news_url;
	}

	public void setNews_url(String news_url) {
		this.news_url = news_url;
	}

}
