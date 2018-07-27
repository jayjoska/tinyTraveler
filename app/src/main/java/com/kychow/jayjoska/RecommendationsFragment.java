package com.kychow.jayjoska;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
 * {@link RecommendationsFragment.OnPlacesPopulatedListener} interface
 * to handle interaction events.
 * Use the {@link RecommendationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecommendationsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnPlacesPopulatedListener mListener;


    // Added by Jose (most of the code in this file is auto-generated
    private RecyclerView mRecyclerView;
    private RecommendationsAdapter mAdapter;
    private ArrayList<Place> mRecs;
    private ArrayList<String> mCategories;
    private ArrayList<String> mOldCategories;
    private AsyncHttpClient client;
    private Bundle savedState;

    // Bad style (I think). We need to figure out how to make this better
    // This is used to iterate through the categories
    private int iteratorCounter;

    public RecommendationsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecommendationsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecommendationsFragment newInstance(String param1, String param2) {
        RecommendationsFragment fragment = new RecommendationsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mRecs = new ArrayList<>();
        mAdapter = new RecommendationsAdapter(mRecs);
        client = new AsyncHttpClient();
        mOldCategories = new ArrayList<>();
        client.addHeader("Authorization", "Bearer " + getString(R.string.yelp_api_key));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recommendations, container, false);
        mRecyclerView = view.findViewById(R.id.rvRecs);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        if (savedState == null) {
            savedState = new Bundle();
        }
        getRecs(mCategories);
        return view;
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        for (String s : mCategories) {
            mOldCategories.add(s);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnPlacesPopulatedListener {
        void sendRecs(ArrayList<Place> places);
    }

    /**
     * @brief getRecs fetches a list of businesses for a particular category
     *
     * @input -
     * @output void
     *
     * TODO: GET LOCATION DYNAMICALLY (I.E. GET THE LATITUDE AND LONGITUDE FROM GPS)
     */
    private void getRecs(ArrayList<String> categories) {
        final double TEMP_LATITUDE = 37.484377;
        final double TEMP_LONGITUDE = -122.148304;
        final int catSize = categories.size();
        final int RECS_PER_CATEGORY = 5;

        String url = getString(R.string.base_url) + getString(R.string.search);

        RequestParams params = new RequestParams();

        // params.put("location", "san+francisco");
        params.put("latitude", TEMP_LATITUDE);
        params.put("longitude", TEMP_LONGITUDE);

        removeClearedRecs(categories);
        for (iteratorCounter = 0; iteratorCounter < catSize; iteratorCounter++) {
            final String category = categories.get(iteratorCounter);
            params.put("categories", category);
            client.get(url, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        for (int i = 0; i < RECS_PER_CATEGORY; i++) {
                            if (!mOldCategories.contains(category)) {
                                JSONArray businesses = response.getJSONArray("businesses");
                                Place place = Place.fromJSON(businesses.getJSONObject(i));
                                place.setCategory(category);
                                mRecs.add(place);
                                mAdapter.notifyItemInserted(mRecs.size() - 1);
                            }
                            if (i == RECS_PER_CATEGORY - 1 && iteratorCounter == catSize) {
                                mListener.sendRecs(mRecs);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
            params.remove("categories");
        }
    }

    /**
     * @brief removeClearedRecs checks which elements of mRecs must be removed and removes them
     *
     * @input -
     * @output void
     *
     */
    private void removeClearedRecs(ArrayList<String> categories) {
        ArrayList<Place> toRemove = new ArrayList<>();
        for (String s : mOldCategories) {
            if (!categories.contains(s)) {
                for (Place place : mRecs) {
                    if (place.getCategory().equals(s)) {
                        toRemove.add(place);
                    }
                }
            }
        }
        for (Place place : toRemove) {
            mRecs.remove(place);
        }
    }

    public void setCategories(ArrayList<String> categories) {
        mCategories = categories;
    }
}
