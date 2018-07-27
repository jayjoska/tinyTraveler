package com.kychow.jayjoska.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @brief Place model utilizes Yelp Fusion API to parse corresponding data for initial recommendations
 * */
public class Place {
    private String id;
    private String name;
    private String category;
    private String imgURL;
    private int reviewCount;
    private long rating;
    private double distance;
    private double latitude;
    private double longitude;

    /* Takes in a JSONObject and parses for the corresponding data*/
    public static Place fromJSON(JSONObject jsonObject) throws JSONException {
        Place place = new Place();

        //extracting values from JSON
        place.id = jsonObject.getString("id");
        place.name = jsonObject.getString("name");
        //access Category and grab title string
        JSONArray categories = jsonObject.getJSONArray("categories");
        //TODO: figure out how to grab single category (initially returns JSONArray)
        place.category = "";
        place.imgURL = jsonObject.getString("image_url");
        place.reviewCount = jsonObject.getInt("review_count");
        place.rating = jsonObject.getLong("rating");
        place.distance = jsonObject.getLong("distance") * 0.00062137;
        //access Coordinates and grab latitude and longitude
        JSONObject coord = jsonObject.getJSONObject("coordinates");
        place.latitude = coord.getDouble("latitude");
        place.longitude = coord.getDouble("longitude");

        return place;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getImgURL() {
        return imgURL;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public long getRating() {
        return rating;
    }

    public double getDistance() {
        return distance;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setCategory(String s) {
        category = s;
    }
}

