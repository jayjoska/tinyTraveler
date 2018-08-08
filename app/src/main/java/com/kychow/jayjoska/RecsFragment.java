package com.kychow.jayjoska;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kychow.jayjoska.models.Place;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecsFragment.OnPlacesPopulatedListener} interface
 * to handle interaction events.
 * Use the {@link RecsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecsFragment extends Fragment {

    private OnPlacesPopulatedListener mListener;
    private OnItemAddedListener mAddedListener;

    // Added by Jose (most of the code in this file is auto-generated
    private RecyclerView mRecyclerView;
    private ProgressBar mLoading;
    private RecsAdapter mAdapter;
    private ArrayList<Place> mRecs;
    private ArrayList<String> mCategories;
    private ArrayList<String> mOldCategories;
    private ArrayList<Place> mItinerary;
    private AsyncHttpClient client;
    private Location mLocation;
    private Location mOldLocation;
    private double lat;
    private double lng;
    private Bundle savedState;
    private String mAddress;
    private boolean allowBack;

    // Max distance between two points (in meters) so that you can consider them the same location
    private static final int MAX_DISTANCE = 50;

    // Bad style (I think). We need to figure out how to make this better
    // This is used to iterate through the categories
    private int iteratorCounter;

    public RecsFragment() {
    }

    public static RecsFragment newInstance() {
        RecsFragment fragment = new RecsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mItinerary = new ArrayList<>();
        mRecs = new ArrayList<>();
        mAdapter = new RecsAdapter(mRecs);
        client = new AsyncHttpClient();
        mOldCategories = new ArrayList<>();
        client.addHeader("Authorization", "Bearer " + getString(R.string.yelp_api_key));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_recommendations, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.rvRecs);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        if (savedState == null) {
            savedState = new Bundle();
        }

        mLoading = view.findViewById(R.id.pbLoadingRecs);
        for (String s : mCategories) {
            if (!mOldCategories.contains(s)) {
                mLoading.setVisibility(View.VISIBLE);
                break;
            }
        }
        // Syntax taken from https://stackoverflow.com/questions/19722712/get-user-current-location-android
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Criteria criteria = new Criteria();
            LocationManager locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
            String provider = locationManager.getBestProvider(criteria, false);
            mLocation = locationManager.getLastKnownLocation(provider);
            lat =  mLocation.getLatitude();
            lng = mLocation.getLongitude();
        } else {
            mLocation = new Location("");
            lat = 37.484377;
            lng = -122.148304;
            mLocation.setLatitude(lat);
            mLocation.setLongitude(lng);
        }

        getRecs(mCategories);

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition(); //swiped position
                Place place = mRecs.get(position);
                if(direction == ItemTouchHelper.RIGHT) {
                    mAddedListener.addToItinerary(mRecs.get(position));

                    Log.d("RecFragment", "something has been swiped");
                    mRecs.remove(position);
                    mAdapter.notifyItemRemoved(position);
                    Toast.makeText(getContext(),place.getName() + " was added to itinerary!",Toast.LENGTH_SHORT).show();
                } else if (direction == ItemTouchHelper.LEFT) {
                    mRecs.remove(position);
                    mAdapter.notifyItemRemoved(position);
                    Toast.makeText(getContext(), place.getName() +  " was removed from recommendations.", Toast.LENGTH_LONG).show();
                }

            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnPlacesPopulatedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnPlacesPopulatedListener");
        }
        try {
            mAddedListener = (OnItemAddedListener) context;
            Log.d("RecsFragment", "mAddedListener has been assigned");
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnItemAddedListener");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mOldCategories.clear();
        mOldCategories.addAll(mCategories);
        mOldLocation = new Location(mLocation);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnPlacesPopulatedListener {
        void sendRecs(ArrayList<Place> places);
    }

    // fetches a list of businesses for a particular category
    private void getRecs(ArrayList<String> categories) {
        final int catSize = categories.size();

        String url = getString(R.string.base_url) + getString(R.string.search);

        RequestParams params = new RequestParams();

        // params.put("location", "san+francisco");
        if (mAddress == null || mAddress.length() == 0) {
            params.put("latitude", lat);
            params.put("longitude", lng);
        } else {
            params.put("location", mAddress);
        }

        removeClearedRecs(categories);

        for (iteratorCounter = 0; iteratorCounter < catSize; iteratorCounter++) {
            final String category = categories.get(iteratorCounter);
            params.put("categories", category);
            params.put("limit", 20);
            allowBack = false;
            client.get(url, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        int recsPerCategory = 10;
                        for (int i = 0; i < recsPerCategory; i++) {
                            if (!mOldCategories.contains(category)) {
                                JSONArray businesses = response.getJSONArray("businesses");
                                if (businesses.length() <= i) {
                                    break;
                                }
                                Place place = Place.fromJSON(businesses.getJSONObject(i));
                                if (mRecs.contains(place)) {
                                    recsPerCategory++;
                                    continue;
                                } else {
                                    place.setCategory(category);
                                    mRecs.add(place);
                                    mAdapter.notifyItemInserted(mRecs.size() - 1);
                                }
                            }
                            if (i == recsPerCategory - 1 && iteratorCounter == catSize) {
                                mListener.sendRecs(mRecs);
                                mLoading.setVisibility(View.GONE);
                            }
                            allowBack = true;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
            params.remove("categories");
        }
    }

     // checks which elements of mRecs must be removed and removes them
    private void removeClearedRecs(ArrayList<String> categories) {
        ArrayList<Place> toRemove = new ArrayList<>();
        if (mOldLocation != null  && mLocation != null) {
            if (mLocation.distanceTo(mOldLocation ) > MAX_DISTANCE) {
                toRemove.addAll(mRecs);
                mAdapter.remove(toRemove);
                return;
            }
        }
        for (String s : mOldCategories) {
            if (!categories.contains(s)) {
                for (Place place : mRecs) {
                    if (place.getCategory().equals(s)) {
                        toRemove.add(place);
                    }
                }
            }
        }
        mAdapter.remove(toRemove);
    }

    public void setCategories(ArrayList<String> categories) {
        mCategories = categories;
    }

    /*@brief custom listener to send data from Recommendations to Details*/
    public interface OnSelectedListener {
        void inflateDetails(Bundle bundle);
    }

    public interface OnItemAddedListener {
        void addToItinerary(Place itineraryPlaces);
    }

    public void setAddress(String s) {
        mAddress = s;
        // TODO: Next diff, implement a way to get a location from the address
        // Location loc = new Location("");
        // AsyncHttpClient client = new AsyncHttpClient();
    }

    public void getRecsFromOutside() {
        ArrayList<Place> recsCopy = new ArrayList<>();
        for (Place p : mRecs) {
            recsCopy.add(p);
        }
        mAdapter.remove(recsCopy);
        mOldCategories.clear();
        getRecs(mCategories);
    }

    public boolean shouldAllowBack() {
        return allowBack;
    }

    public void scrollToItem(String s) {
        int position = RecyclerView.NO_POSITION;
        for (int i = 0; i < mRecs.size(); i++) {
            if (mRecs.get(i).getName().equals(s)) {
                position = i;
            }
        }
        if (position != RecyclerView.NO_POSITION) {
            mRecyclerView.scrollToPosition(position);
        }
    }
}
