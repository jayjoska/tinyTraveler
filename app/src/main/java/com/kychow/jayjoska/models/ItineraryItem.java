package com.kychow.jayjoska.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by Karena Chow on 7/27/18.
 */
@Parcel
public class ItineraryItem extends Place {

    int time;
    int cost;

    public ItineraryItem() {
        super();
        time = 30;
        cost = 0;
    }

    public ItineraryItem(Place place) {
        super();
        time = 30;
        cost = 0;
    }

    public static ItineraryItem fromJSON(JSONObject jsonObject) throws JSONException {
        ItineraryItem item = new ItineraryItem();
        item.time = 30;
        /* cost midpoint calculated with below info
        $= under $10
        $$= $11-$30
        $$$= $31-$60
        $$$$= above $61
         */
        int[] prices = {0, 5, 20, 45, 60};
        String priceRange = jsonObject.getString("price"); //use with /businesses/{id}
        item.cost = prices[priceRange.length()];

        return item;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
