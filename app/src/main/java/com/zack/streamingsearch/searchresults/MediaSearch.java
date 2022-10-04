package com.zack.streamingsearch.searchresults;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class MediaSearch implements Parcelable {

    @SerializedName("Title")
    private String title;

    @SerializedName("Year")
    private String year;

    @SerializedName("imdbID")
    private String imdbID;

    @SerializedName("Type")
    private String type;

    @SerializedName("Poster")
    private String poster;

    public MediaSearch(Parcel source) {
        title = source.readString();
        year = source.readString();
        imdbID = source.readString();
        type = source.readString();
        poster = source.readString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(year);
        parcel.writeString(imdbID);
        parcel.writeString(type);
        parcel.writeString(poster);
    }

    public static final Creator<MediaSearch> CREATOR = new Creator<MediaSearch>() {
        @Override
        public MediaSearch[] newArray(int size) {
            return new MediaSearch[size];
        }

        @Override
        public MediaSearch createFromParcel(Parcel source) {
            return new MediaSearch(source);
        }
    };

}
