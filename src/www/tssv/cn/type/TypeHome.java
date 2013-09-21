package www.tssv.cn.type;

import android.os.Parcel;
import android.os.Parcelable;

public class TypeHome implements Parcelable {

	private int home_id;
	private String home_img;
	private String home_title;
	private String home_content;
	private String home_url;

	public TypeHome() {

	}

	private TypeHome(Parcel in) {
		readFromParcel(in);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public void readFromParcel(Parcel in) {
		home_id = in.readInt();
		home_img = in.readString();
		home_title = in.readString();
		home_content = in.readString();
		home_url = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(home_id);
		dest.writeString(home_img);
		dest.writeString(home_title);
		dest.writeString(home_content);
		dest.writeString(home_url);
	}

	public static Creator<TypeHome> CREATOR = new Creator<TypeHome>() {
		@Override
		public TypeHome createFromParcel(Parcel source) {
			return new TypeHome(source);
		}

		@Override
		public TypeHome[] newArray(int size) {
			return new TypeHome[size];
		}
	};

	public int getHome_id() {
		return home_id;
	}

	public void setHome_id(int home_id) {
		this.home_id = home_id;
	}

	public String getHome_img() {
		return home_img;
	}

	public void setHome_img(String home_img) {
		this.home_img = home_img;
	}

	public String getHome_title() {
		return home_title;
	}

	public void setHome_title(String home_title) {
		this.home_title = home_title;
	}

	public String getHome_content() {
		return home_content;
	}

	public void setHome_content(String home_content) {
		this.home_content = home_content;
	}

	public String getHome_url() {
		return home_url;
	}

	public void setHome_url(String home_url) {
		this.home_url = home_url;
	}

}
