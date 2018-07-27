package com.example.admin.mymusicplayer.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by TamTT on 7/17/2018.
 */

public class Song implements Parcelable{
    private String name;
    private String path;
    private String singer;

    protected Song(Parcel in) {
        name = in.readString();
        path = in.readString();
        singer = in.readString();
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    @Override
    public String toString() {
        return name;
    }

    public Song(String name, String singer, String path ) {
        this.name = name;
        this.path = path;
        this.singer = singer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(path);
        parcel.writeString(singer);
    }
}
