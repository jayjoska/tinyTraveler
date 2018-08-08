package com.kychow.jayjoska;

import java.util.HashMap;

/** @brief Helper class created to hold all hard-coded values necessary for categories
 * @use wherever a category alias or drawable is needed
 * */
public class Categories {
    // manually populate hashmaps with aliases, titles, and drawables
    private static HashMap<String, String> aliasAndTitle = new HashMap<String, String>() {{
        put("active","Active Life");
        put("arts","Arts & Entertainment");
        put("auto","Automotive");
        put("beautysvc","Beauty & Spas");
        put("education","Education");
        put("eventservices","Event Services");
        put("financialservices","Financial Services");
        put("food","Food");
        put("health","Health & Medical");
        put("homeservices","Home Services");
        put("hotelstravel","Hotels & Travel");
        put("localflavor","Local Flavor");
        put("localservices","Local Services");
        put("massmedia","Mass Media");
        put("nightlife","Nightlife");
        put("pets","Pets");
        put("professional","Professional Services");
        put("publicservicesgovt","Public Services & Government");
        put("religiousorgs","Religious Organizations");
        put("restaurants","Restaurants");
        put("shopping","Shopping");
    }};

    private static HashMap<String, Object> drawables = new HashMap<String, Object>() {{
        put("active", R.drawable.active);
        put("arts", R.drawable.art);
        put("auto", R.drawable.automotive);
        put("beautysvc", R.drawable.beauty);
        put("education", R.drawable.education);
        put("eventservices", R.drawable.event);
        put("financialservices", R.drawable.financial);
        put("food", R.drawable.food);
        put("health", R.drawable.health);
        put("homeservices", R.drawable.home_services);
        put("hotelstravel", R.drawable.hotel);
        put("localflavor", R.drawable.local_flavor);
        put("localservices", R.drawable.local_services);
        put("massmedia", R.drawable.mass_media);
        put("nightlife", R.drawable.nightlife);
        put("pets", R.drawable.pet);
        put("professional", R.drawable.professional_services);
        put("publicservicesgovt", R.drawable.government);
        put("religiousorgs", R.drawable.religion);
        put("restaurants", R.drawable.restaurant);
        put("shopping", R.drawable.shopping);
    }};

    public static HashMap<String, String> getAliasAndTitle() {
        return aliasAndTitle;
    }

    public static HashMap<String, Object> getDrawables() {
        return drawables;
    }
}