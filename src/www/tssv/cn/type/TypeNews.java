package www.tssv.cn.type;

import android.os.Parcel;
import android.os.Parcelable;

public class TypeNews implements Parcelable {

	@Override
	public int describeContents() {
		return 0;
	}

	public void readFromParcel(Parcel in) {

	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

	}

	public static Creator<TypeLive> CREATOR = new Creator<TypeLive>() {

		@Override
		public TypeLive createFromParcel(Parcel source) {
			return null;
		}

		@Override
		public TypeLive[] newArray(int size) {
			return null;
		}
	};
}
