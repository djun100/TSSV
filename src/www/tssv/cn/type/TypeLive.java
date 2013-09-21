package www.tssv.cn.type;

import android.os.Parcel;
import android.os.Parcelable;

public class TypeLive implements Parcelable {

	private int live_id;
	private String live_img;
	private String live_title;
	private String live_content;
	private String live_url;
	private String live_pragram_url;

	public TypeLive() {

	}

	private TypeLive(Parcel in) {
		readFromParcel(in);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public void readFromParcel(Parcel in) {
		live_id = in.readInt();
		live_img = in.readString();
		live_title = in.readString();
		live_content = in.readString();
		live_url = in.readString();
		live_pragram_url = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(live_id);
		dest.writeString(live_img);
		dest.writeString(live_title);
		dest.writeString(live_content);
		dest.writeString(live_url);
		dest.writeString(live_pragram_url);
	}

	public static Creator<TypeLive> CREATOR = new Creator<TypeLive>() {

		@Override
		public TypeLive createFromParcel(Parcel source) {
			return new TypeLive(source);
		}

		@Override
		public TypeLive[] newArray(int size) {
			return new TypeLive[size];
		}
	};

	public int getLive_id() {
		return live_id;
	}

	public void setLive_id(int live_id) {
		this.live_id = live_id;
	}

	public String getLive_img() {
		return live_img;
	}

	public void setLive_img(String live_img) {
		this.live_img = live_img;
	}

	public String getLive_title() {
		return live_title;
	}

	public void setLive_title(String live_title) {
		this.live_title = live_title;
	}

	public String getLive_content() {
		return live_content;
	}

	public void setLive_content(String live_content) {
		this.live_content = live_content;
	}

	public String getLive_url() {
		return live_url;
	}

	public void setLive_url(String live_url) {
		this.live_url = live_url;
	}

	public String getLive_pragram_url() {
		return live_pragram_url;
	}

	public void setLive_pragram_url(String live_pragram_url) {
		this.live_pragram_url = live_pragram_url;
	}

}
