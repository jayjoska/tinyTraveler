package com.kychow.jayjoska;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private static final String CLIENT_KEY = "client_id";
    private static final String API_KEY = "api_key";
    private static final String BASE_URL = "base_url";
    private static final String ENDPOINT_SEARCH = "search";
    private static final String[] CATEGORY_ALIASES =
            {"active", "arts", "auto", "beautysvc", "bicycles", "education", "eventservices", "financialservices",
                    "food", "health", "homeservices", "hotelstravel", "localflavor",
                    "localservices", "massmedia", "nightlife", "pets", "professional",
                    "publicservicesgovt", "religiousorgs", "restaurants",
                    "shopping"};

    private static final double TEMP_LATITUDE = 37.484377;
    private static final double TEMP_LONGITUDE = -122.148304;


    @BindView(R.id.gvCategories)
    GridView mGridView;

    // private CategoryAdapter mAdapter;
    // private ArrayList<String> mCategories;
    private AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        client = new AsyncHttpClient();
        // mCategories = new ArrayList<>();
        // mAdapter = new CategoryAdapter(mCategories);

        // mGridView.setAdapter(mAdapter);
        getCategories();
    }

    public void getCategories() {
        String url = getString(R.string.base_url) + getString(R.string.search);
        RequestParams params = new RequestParams();
        client.addHeader("Authorization HTTP", "Bearer " + getString(R.string.api_key));
        params.put("categories", CATEGORY_ALIASES[8]);
        params.put("location", "san+francisco,+ca");
        // params.put("latitude", TEMP_LATITUDE);
        //params.put("longitude", TEMP_LONGITUDE);
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray results = null;
                try {
                    results = response.getJSONArray("businesses");
                    String name = results.getJSONObject(0).getString("name");
                    Log.i("MainActivity", name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}
