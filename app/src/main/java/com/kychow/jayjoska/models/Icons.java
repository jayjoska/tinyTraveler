package com.kychow.jayjoska.models;

import com.kychow.jayjoska.R;

import java.util.HashMap;

/**
 * @brief Helper class that matches drawables to categories from main landing page
 */

public class Icons {
    HashMap<String, Object> drawables;

    public Icons() {
        drawables = new HashMap<String, Object>() {{
            put("Active Life", R.drawable.active);
            put("Arts & Entertainment", R.drawable.art);
            put("Automotive", R.drawable.automotive);
            put("Beauty & Spas", R.drawable.beauty);
            put("Education", R.drawable.education);
            put("Event Planning & Services", R.drawable.event);
            put("Financial Services", R.drawable.financial);
            put("Food", R.drawable.food);
            put("Health & Medical", R.drawable.health);
            put("Home Services", R.drawable.home_services);
            put("Hotels & Travel", R.drawable.hotel);
            put("Local Flavor", R.drawable.local_flavor);
            put("Local Services", R.drawable.local_services);
            put("Mass Media", R.drawable.mass_media);
            put("Nightlife", R.drawable.nightlife);
            put("Pets", R.drawable.pet);
            put("Professional Services", R.drawable.professional_services);
            put("Public Services & Government", R.drawable.government);
            put("Real Estate", R.drawable.real_estate);
            put("Religious Organizations", R.drawable.religion);
            put("Restaurants", R.drawable.restaurant);
            put("Shopping", R.drawable.shopping);
        }};
    }

    /**
     * @use match drawable to category
     * @input takes in category string
     * @output returns corresponding drawable
     */
    public Object matchDrawable(String mName) {
        if (drawables.containsKey(mName)) {
            return drawables.get(mName);
        }
        return 0;
    }
}
