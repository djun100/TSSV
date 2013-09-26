package www.tssv.cn.type;

import android.os.Parcel;
import android.os.Parcelable;

public class TypePragrams implements Parcelable {

	private String date;
	private String time;
	private String title;

	public TypePragrams() {

	}

	private TypePragrams(Parcel in) {
		readFromParcel(in);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public void readFromParcel(Parcel in) {
		date = in.readString();
		time = in.readString();
		title = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(date);
		dest.writeString(time);
		dest.writeString(title);
	}

	public static Creator<TypePragrams> CREATOR = new Creator<TypePragrams>() {

		@Override
		public TypePragrams createFromParcel(Parcel source) {
			return new TypePragrams(source);
		}

		@Override
		public TypePragrams[] newArray(int size) {
			return new TypePragrams[size];
		}
	};

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
