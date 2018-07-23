package com.kychow.jayjoska.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @brief Place model utilizes Yelp Fusion API to parse corresponding data for initial recommendations
 * */
public class Place {
    private String name;
    private String category;
    private String imgURL;
    private String businessURL;
    private long rating;
    private long distance;
    private long latitude;
    private long longitude;
    
    /* Takes in a JSONObject and parses for the corresponding data*/
    public static Place fromJSON(JSONObject jsonObject) throws JSONException {
        Place place = new Place();

        //extracting values from JSON
        place.name = jsonObject.getString("name");
        //access Category and grab title string
        JSONArray categories = jsonObject.getJSONArray("categories");
        //TODO: figure out how to grab single category (initially returns JSONArray)
        // place.category = categories.getString("title");
        place.businessURL = jsonObject.getString("url");
        place.imgURL = jsonObject.getString("image_url");
        place.rating = jsonObject.getLong("rating");
        place.distance = jsonObject.getLong("distance");
        //access Coordinates and grab latitude and longitude
        JSONObject coord = jsonObject.getJSONObject("coordinates");
        place.latitude = coord.getLong("latitude");
        place.longitude = coord.getLong("longitude");

        return place;
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

    public String getBusinessURL() {
        return businessURL;
    }

    public long getRating() {
        return rating;
    }

    public long getDistance() {
        return distance;
    }

    public long getLatitude() {
        return latitude;
    }

    public long getLongitude() {
        return longitude;
    }
}

