package com.kychow.jayjoska;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private static final String CLIENT_KEY = "client_id";
    private static final String API_KEY = "api_key";
    private static final String BASE_URL = "base_url";
    private static final String ENDPOINT_SEARCH = "search";
    private static final String TAG = "MainActivity";
    private static final String[] CATEGORY_ALIASES =
            {"active", "arts", "auto", "beautysvc", "bicycles", "education", "eventservices", "financialservices",
                    "food", "health", "homeservices", "hotelstravel", "localflavor",
                    "localservices", "massmedia", "nightlife", "pets", "professional",
                    "publicservicesgovt", "religiousorgs", "restaurants",
                    "shopping"};

    // private static final double TEMP_LATITUDE = 37.484377;
    // private static final double TEMP_LONGITUDE = -122.148304;


    @BindView(R.id.rvCategories)
    RecyclerView mRecyclerView;

    private CategoryAdapter mAdapter;
    private ArrayList<String> mCategories;
    private AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Log.i(TAG, "in onCreate");

        client = new AsyncHttpClient();
        // Provide API with API key
        client.addHeader("Authorization", "Bearer " + getString(R.string.maps_api_key));
        mCategories = new ArrayList<>(); // change to array of categories
        mAdapter = new CategoryAdapter(mCategories);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setAdapter(mAdapter);
        Log.i(TAG, "before getCategories");
        getCategories();
        Log.i(TAG, "after getCategories");
    }

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
