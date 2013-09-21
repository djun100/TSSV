package www.tssv.cn.type;

import android.os.Parcel;
import android.os.Parcelable;

public class TypeColumn implements Parcelable {

	private int column_id;
	private String column_img;
	private String column_title;
	private int column_type;

	public TypeColumn() {

	}

	private TypeColumn(Parcel in) {
		readFromParcel(in);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public void readFromParcel(Parcel in) {
		column_id = in.readInt();
		column_img = in.readString();
		column_title = in.readString();
		column_type = in.readInt();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(column_id);
		dest.writeString(column_img);
		dest.writeString(column_title);
		dest.writeInt(column_type);
	}

	public static Creator<TypeColumn> CREATOR = new Creator<TypeColumn>() {

		@Override
		public TypeColumn createFromParcel(Parcel source) {
			return new TypeColumn(source);
		}

		@Override
		public TypeColumn[] newArray(int size) {
			return new TypeColumn[size];
		}
	};

	public int getColumn_id() {
		return column_id;
	}

	public void setColumn_id(int column_id) {
		this.column_id = column_id;
	}

	public String getColumn_img() {
		return column_img;
	}

	public void setColumn_img(String column_img) {
		this.column_img = column_img;
	}

	public String getColumn_title() {
		return column_title;
	}

	public void setColumn_title(String column_title) {
		this.column_title = column_title;
	}

	public int getColumn_type() {
		return column_type;
	}

	public void setColumn_type(int column_type) {
		this.column_type = column_type;
	}

}
