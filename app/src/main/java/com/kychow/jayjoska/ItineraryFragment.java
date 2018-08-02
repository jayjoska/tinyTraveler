package com.kychow.jayjoska;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kychow.jayjoska.models.Place;
import com.loopj.android.http.AsyncHttpClient;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link ItineraryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItineraryFragment extends Fragment implements ItineraryAdapter.OnUpdateTimeListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecsFragment.OnItemAddedListener mItemAddedListener;

    private RecyclerView mRecyclerView;
    private ItineraryAdapter mAdapter;
    private ArrayList<Place> mItinerary;
    private AsyncHttpClient client;
    private TextView mTextView;

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

        if (mItinerary == null) {
            mItinerary = new ArrayList<>();
        }
        if (mAdapter == null) {
            mAdapter = new ItineraryAdapter(mItinerary);
        }
        client = new AsyncHttpClient();
        client.addHeader("Authorization", "Bearer + " + getString(R.string.yelp_api_key));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("ItineraryFragment", "itinerary fragment loaded");
        return inflater.inflate(R.layout.fragment_itinerary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.rvItinerary);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mTextView = view.findViewById(R.id.tvTotalTime);
        mTextView.setText("Total time: " + String.valueOf(mAdapter.grabTime()) + " minutes");

        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(mAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRecyclerView);
    }

    public void addToItinerary(Place itineraryPlace) {
        if (mItinerary == null) {
            mItinerary = new ArrayList<>();
        }
        mItinerary.add(itineraryPlace);
        if (mAdapter == null) {
           mAdapter = new ItineraryAdapter(mItinerary);
        }
        mAdapter.notifyItemInserted(mItinerary.size() - 1);
        Log.d("ItineraryFragment", "something has been inserted!");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RecsFragment.OnItemAddedListener) {
            mItemAddedListener = (RecsFragment.OnItemAddedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnItemAddedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void updateTime(int i) {
        mTextView.setText("Total time: " + i + " minutes");
    }

    public void clearItinerary() {
        mAdapter.clear();
    }
}
