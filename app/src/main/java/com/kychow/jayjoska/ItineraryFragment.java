package com.kychow.jayjoska;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
 * {@link ItineraryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ItineraryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItineraryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private RecyclerView mRecyclerView;
    private ItineraryAdapter mAdapter;
    private ArrayList<Place> mItinerary;
    private AsyncHttpClient client;

    public ItineraryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ItineraryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ItineraryFragment newInstance(String param1, String param2) {
        ItineraryFragment fragment = new ItineraryFragment();
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

        mItinerary = new ArrayList<>();
        mAdapter = new ItineraryAdapter(mItinerary);
        client = new AsyncHttpClient();
        client.addHeader("Authorization", "Bearer + " + getString(R.string.yelp_api_key));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_itinerary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.rvItinerary);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        getItinerary();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        */
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /*
     * @brief getRecs fetches a list of businesses for a particular category
     *
     * #################
     * Current status: hardcoded the "food" category
     * #################
     *
     * @input -
     * @output void
     */
    private void getItinerary() {
        final double TEMP_LATITUDE = 37.484377;
        final double TEMP_LONGITUDE = -122.148304;
        String url = getString(R.string.base_url) + getString(R.string.search);

        RequestParams params = new RequestParams();

        // params.put("location", "san+francisco");
        params.put("categories", "food");
        params.put("latitude", TEMP_LATITUDE);
        params.put("longitude", TEMP_LONGITUDE);

        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray businesses = response.getJSONArray("businesses");
                    Place place = new Place();
                    for (int i = 0; i < 20; i++) {
                        place = Place.fromJSON(businesses.getJSONObject(i));
                        mItinerary.add(place);
                        mAdapter.notifyItemInserted(mItinerary.size() - 1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    Log.i("ItineraryFragment", errorResponse.getJSONObject("error").getString("code"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
