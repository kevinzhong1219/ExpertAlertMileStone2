package com.example.expertalert;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Grocery{
    private String name;
    private String date;
    private String description;
    private String imageId;

    public Grocery() {
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
