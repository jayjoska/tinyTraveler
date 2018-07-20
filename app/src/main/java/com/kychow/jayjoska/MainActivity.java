package com.kychow.jayjoska;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


import butterknife.BindView;
import butterknife.ButterKnife;

/*
 * @brief MainActivity activity that holds a RecyclerView of all the categories
 *
 * The contents of this class will probably be migrated at some point to the final activity/fragments
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    // Hardcoded aliases for the main 22 categories.
    private static final String[] CATEGORY_ALIASES =
            {"active", "arts", "auto", "beautysvc", "bicycles", "education", "eventservices", "financialservices",
                    "food", "health", "homeservices", "hotelstravel", "localflavor",
                    "localservices", "massmedia", "nightlife", "pets", "professional",
                    "publicservicesgovt", "religiousorgs", "restaurants",
                    "shopping"};

    // Temporal variables for testing
    // private static final double TEMP_LATITUDE = 37.484377;
    // private static final double TEMP_LONGITUDE = -122.148304;


    @BindView(R.id.bottom_navigation) BottomNavigationView bottomNavigationView;
    final FragmentManager fragmentManager = getSupportFragmentManager();
    android.support.v4.app.Fragment categoriesFragment;
    android.support.v4.app.Fragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        CategoriesFragment categoriesFragment = new CategoriesFragment();
        fragmentTransaction.add(R.id.flContainerCategories, categoriesFragment);
        fragmentTransaction.commit();

        /* TODO in later diff
        // handle navigation selection
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        FragmentTransaction fragmentTransaction;
                        switch (item.getItemId()) {
                            case R.id.action_categories:
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.rvCategories, categoriesFragment).commit();
                                return true;
                            case R.id.action_map:
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.map_fragment, mapFragment).commit();
                                return true;
                            case R.id.action_itinerary:
                                /*
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.flContainer, fragment3).commit();
                                return true;
                            default:
                                return true;
                        }
                    }
                });
                */
    }

    // TODO migrate category aliases
    public static String[] getCategoryAliases() {
        return CATEGORY_ALIASES;
    }

    /*
     * @brief getRecs takes in a category and provides recomendations
     *
     * ############################
     * NOT YET IMPLEMENTED
     * ############################
     *
     * @input  String      The category to be searched
     * @output void
     */
    /*
    private void getRecs(String category) {
        String url = getString(R.string.base_url) + getString(R.string.search);
        RequestParams params = new RequestParams();
        params.put("latitude", TEMP_LATITUDE);
        params.put("longitude", TEMP_LONGITUDE);
        params.put("categories", category);
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray results = null;
                try {
                    results = response.getJSONArray("businesses");
                    String name = "";
                    for (int i = 0; i < results.length(); i++) {
                        name = results.getJSONObject(i).getString("name");
                        mCategories.add(name);
                        mAdapter.notifyItemInserted(mCategories.size() - 1);
                        Log.i(TAG, name);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    Log.i(TAG, errorResponse.getJSONObject("error").getString("code"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    */
}
