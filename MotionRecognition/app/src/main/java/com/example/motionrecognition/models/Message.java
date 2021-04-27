package adroidtown.org.graduateproject.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "memoTable")
public class Message implements Parcelable {

  //Room에서 자동으로 id를 할당
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "user_id")
  private int id;
  @ColumnInfo(name = "user_title")
  private String title;

  public Message(String title) {
    this.title = title;
  }

  protected Message(Parcel in) {
    id = in.readInt();
    title = in.readString();
  }

  public static final Creator<Message> CREATOR = new Creator<Message>() {
    @Override
    public Message createFromParcel(Parcel in) {
      return new Message(in);
    }

    @Override
    public Message[] newArray(int size) {
      return new Message[size];
    }
  };

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {

    dest.writeInt(id);
    dest.writeString(title);
  }
}
