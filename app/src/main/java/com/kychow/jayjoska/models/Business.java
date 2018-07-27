package com.kychow.jayjoska.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**@brief Business model extends Place model
 * @use to be used with DetailsFragment
 * */
public class Business extends Place {
    private JSONArray reviews;
    private String price;
    private String businessURL;


    public static Business fromJSON(JSONObject jsonObject) throws JSONException {
        Business business = new Business();

        business.price = jsonObject.getString("price"); //use with /businesses/{id}
        business.reviews = jsonObject.getJSONArray("reviews"); //use with /businesses/{id}/reviews
        business.businessURL = jsonObject.getString("url"); //use with /businesses/{id}

        return business;
    }

    public JSONArray getReviews() {
        return reviews;
    }

    public String getPrice() {
        return price;
    }

    public String getBusinessURL() {
        return businessURL;
    }
}
