package bryan.mobilepaindiary.ObjectClass;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bryanyhy on 25/4/16.
 */

// record class for creating data in SQLite
public class Record implements Parcelable {

    private long _id;
    private int userid;
    private String address;
    private String latitude;
    private String longitude;
    private String dateTime;

    public Record(long id, int userid, String address, String latitude, String longitude, String dateTime) {
        this._id = id;
        this.userid = userid;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dateTime = dateTime;
    }

    public Record(int userid, String address, String latitude, String longitude, String dateTime) {
        this.userid = userid;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dateTime = dateTime;
    }

    public long getId() {
        return _id;
    }

    public void setId(long id) {
        this._id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {this.userid = userid; }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    protected Record(Parcel in) {
        _id = in.readLong();
        userid = in.readInt();
        address = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        dateTime = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(_id);
        dest.writeInt(userid);
        dest.writeString(address);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(dateTime);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Record> CREATOR = new Parcelable.Creator<Record>() {
        @Override
        public Record createFromParcel(Parcel in) {
            return new Record(in);
        }

        @Override
        public Record[] newArray(int size) {
            return new Record[size];
        }
    };

}