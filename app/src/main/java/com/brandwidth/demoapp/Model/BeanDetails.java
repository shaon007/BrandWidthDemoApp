package com.brandwidth.demoapp.Model;

import android.os.Parcel;
import android.os.Parcelable;


public class BeanDetails implements Parcelable
{
    private String id;
    private String img_path;
    private String rating;
    private String name;
    private String release_date;
    private String overview;
    private String production_place;
    private String extraString;

    public BeanDetails()
    {  }

    public BeanDetails(String _id, String _imgPath, String _rating, String _name,String _releaseDate, String _overview, String _productionPlace, String _extraString)
    {
        this.id = _id;
        this.img_path = _imgPath;
        this.rating = _rating;
        this.name = _name;
        this.release_date = _releaseDate;
        this.overview = _overview;
        this.production_place = _productionPlace;
        this.extraString = _extraString;
    }
    public String functoString()
    { return this.id + "\n" +
        this.img_path + "\n" +
        this.rating + "\n" +
        this.name + "\n" +
        this.release_date + "\n" +
        this.overview + "\n" +
        this.production_place+ "\n\n" +
        this.extraString ;

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getProduction_place() {
        return production_place;
    }

    public void setProduction_place(String production_place) {
        this.production_place = production_place;
    }

    public String getExtraString() {
        return extraString;
    }

    public void setExtraString(String extraString) {
        this.extraString = extraString;
    }


    public BeanDetails(Parcel in)
    {
        readFromParcel(in);
    }

    @Override
    public int describeContents()
    {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.id);
        dest.writeString(this.img_path);
        dest.writeString(this.rating);
        dest.writeString(this.name);
        dest.writeString(this.release_date);
        dest.writeString(this.overview);
        dest.writeString(this.production_place);
        dest.writeString(this.extraString);
    }

    public void readFromParcel(Parcel in)
    {
        this.id = in.readString();
        this.img_path = in.readString();
        this.rating = in.readString();
        this.name = in.readString();
        this.release_date = in.readString();
        this.overview = in.readString();
        this.production_place = in.readString();
        this.extraString = in.readString();
    }

    @SuppressWarnings("unchecked")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
    {
        @Override
        public BeanDetails createFromParcel(Parcel in)
        {
            return new BeanDetails(in);
        }

        @Override
        public Object[] newArray(int size)
        {
            return new BeanDetails[size];
        }
    };

}
