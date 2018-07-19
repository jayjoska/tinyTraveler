package com.kychow.jayjoska;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

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

    @BindView(R.id.rvCategories)
    RecyclerView mRecyclerView;

    private CategoryAdapter mAdapter;
    private ArrayList<String> mCategories;
    private AsyncHttpClient client;

    @BindView(R.id.bottom_navigation) BottomNavigationView bottomNavigationView;
    @BindView(R.id.next_btn) FloatingActionButton next_btn;
    final FragmentManager fragmentManager = getSupportFragmentManager();
    android.support.v4.app.Fragment categoriesFragment;
    android.support.v4.app.Fragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mapFragment = getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        categoriesFragment = getSupportFragmentManager().findFragmentById(R.id.rvCategories);

        client = new AsyncHttpClient();
        // Provide API with API key
        client.addHeader("Authorization", "Bearer " + getString(R.string.yelp_api_key));
        mCategories = new ArrayList<>(); // change to array of categories
        mAdapter = new CategoryAdapter(mCategories);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setAdapter(mAdapter);
        getCategories();

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCategories.size() == 5) {
                    Intent mapIntent = new Intent(MainActivity.this, MapActivity.class);
                    mapIntent.putExtra("categories", mCategories);
                    startActivity(mapIntent);
                }
            }
        });

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
                                */
                                return true;
                            default:
                                return true;
                        }
                    }
                });
    }

    public static String[] getCategoryAliases() {
        return CATEGORY_ALIASES;
    }

    /*
     * @brief getCategories fetches the categories from the Yelp api and populates the recycler view.
     * In order to get the full category name, the alias must be placed directly into the url, and not
     * passed as an argument.
     *
     * @input  -
     * @output void
     */
    public void getCategories() {
        String url = "";

        for (int i = 0; i < CATEGORY_ALIASES.length; i++) {
            url = getString(R.string.base_url) + getString(R.string.categories) + CATEGORY_ALIASES[i];

            client.get(url, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    String title = null;
                    try {
                        JSONObject category = response.getJSONObject("category");
                        title = category.getString("title");
                        mCategories.add(title);
                        mAdapter.notifyItemInserted(mCategories.size() - 1);
                        Log.i(TAG, title);
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
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

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
