package com.example.challenge;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelUtils  {
    private String id;
    private String start;
    private String personLastName;
    private String end;
    private  String image;
    private boolean isFavourite;
    private  String price;

    protected ModelUtils(Parcel in) {
        id = in.readString();
        start = in.readString();
        personLastName = in.readString();
        end = in.readString();
        image = in.readString();
        isFavourite = in.readByte() != 0;
        price = in.readString();
    }

    public ModelUtils() {

    }


    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }





    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }






    public String getPersonLastName() {
        return personLastName;
    }

    public void setPersonLastName(String personLastName) {
        this.personLastName = personLastName;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }}


