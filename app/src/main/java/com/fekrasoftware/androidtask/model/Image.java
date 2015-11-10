package com.fekrasoftware.androidtask.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by mohamedzakaria on 11/7/15.
 */
@Table(name = "images")
public class Image extends Model implements Parcelable {
    @Column
    private int width;
    @Column
    private int height;
    @Column(unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private String url;

    public Image() {
        super();
    }

    public Image(Parcel parcel) {
        super();
        width = parcel.readInt();
        height = parcel.readInt();
        url = parcel.readString();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeString(url);
    }
}
