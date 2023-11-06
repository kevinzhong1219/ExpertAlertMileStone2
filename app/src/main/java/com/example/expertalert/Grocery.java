package com.example.expertalert;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Grocery implements Parcelable {
    private String name;
    private String date;
    private String description;
    private String imageId;

    public Grocery() {
    }

    protected Grocery(Parcel in) {
        name = in.readString();
        date = in.readString();
        description = in.readString();
        imageId = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId() {
        this.imageId = this.name + System.currentTimeMillis() + ".jpeg";
    }

    @Override
    public String toString() {
        return "Grocery{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                ", imageId='" + imageId + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0; // Return 0 if you don't have any special flags to describe
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(date);
        dest.writeString(description);
        dest.writeString(imageId);
    }

    public static final Parcelable.Creator<Grocery> CREATOR = new Parcelable.Creator<Grocery>() {
        @Override
        public Grocery createFromParcel(Parcel in) {
            return new Grocery(in);
        }

        @Override
        public Grocery[] newArray(int size) {
            return new Grocery[size];
        }
    };

    public String toJsonFormat(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("name", name);
            obj.put("date", date);
            obj.put("description", description);
            obj.put("imageId", imageId);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return obj.toString();
    }
}
