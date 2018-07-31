package com.kychow.jayjoska.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * @brief Place model utilizes Yelp Fusion API to parse corresponding data for initial recommendations
 * */
@Parcel
public class Place {
    public String id;
    public String name;
    public String category;
    public String imgURL;
    public String businessURL;
    public String price;
    public int reviewCount;
    public long rating;
    public double distance;
    public double latitude;
    public double longitude;
    public int timeSpent;

    /* Takes in a JSONObject and parses for the corresponding data*/
    public static Place fromJSON(JSONObject jsonObject) throws JSONException {
        Place place = new Place();

        //extracting values from JSON
        place.id = jsonObject.getString("id");
        place.name = jsonObject.getString("name");
        //access Category and grab title string
        place.category = "";
        place.imgURL = jsonObject.getString("image_url");
        place.businessURL = jsonObject.getString("url"); //use with /businesses/{id}
        if (jsonObject.has("price")) {
            place.price = jsonObject.getString("price"); //use with /businesses/{id}
        } else {
            place.price = "";
        }
        place.reviewCount = jsonObject.getInt("review_count");
        place.rating = jsonObject.getLong("rating");
        place.distance = jsonObject.getLong("distance") * 0.00062137;
        //access Coordinates and grab latitude and longitude
        JSONObject coord = jsonObject.getJSONObject("coordinates");
        place.latitude = coord.getDouble("latitude");
        place.longitude = coord.getDouble("longitude");

        return place;
    }

    public Place() {
        //empty constructor needed by the Parceler library
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

    public String getBusinessURL() { return businessURL; }

    public String getPrice() {
        return price;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public long getRating() {
        return rating;
    }

    public double getDistance() { return distance; }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setCategory(String s) {
        category = s;
    }

    public void setTimeSpent(int i) {
        timeSpent = i;
    }

    public int getTimeSpent() {
        return timeSpent;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Place) {
            return ((Place) obj).getId().equals(getId());
        } else {
            return false;
        }
    }
}

