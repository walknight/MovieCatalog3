package com.dtaoa.moviecatalog3.ViewModel;

import android.os.Parcel;
import android.os.Parcelable;

public class DataModel implements Parcelable {
    private String title;
    private String sinopsis;
    private String genre;
    private String year;
    private String ratings;
    private String imagePoster;
    private String imageThumbnail;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getImagePoster() {
        return imagePoster;
    }

    public void setImagePoster(String imagePoster) {
        this.imagePoster = imagePoster;
    }

    public String getImageThumbnail() {
        return imageThumbnail;
    }

    public void setImageThumbnail(String imageThumbnail) {
        this.imageThumbnail = imageThumbnail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.sinopsis);
        dest.writeString(this.genre);
        dest.writeString(this.year);
        dest.writeString(this.ratings);
        dest.writeString(this.imagePoster);
        dest.writeString(this.imageThumbnail);
    }

    public DataModel() {
    }

    protected DataModel(Parcel in) {
        this.title = in.readString();
        this.sinopsis = in.readString();
        this.genre = in.readString();
        this.year = in.readString();
        this.ratings = in.readString();
        this.imagePoster = in.readString();
        this.imageThumbnail = in.readString();
    }

    public static final Parcelable.Creator<DataModel> CREATOR = new Parcelable.Creator<DataModel>() {
        @Override
        public DataModel createFromParcel(Parcel source) {
            return new DataModel(source);
        }

        @Override
        public DataModel[] newArray(int size) {
            return new DataModel[size];
        }
    };
}
