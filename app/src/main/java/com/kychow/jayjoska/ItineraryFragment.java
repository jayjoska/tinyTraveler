package com.kychow.jayjoska;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.kychow.jayjoska.helpers.SimpleItemTouchHelperCallback;
import com.kychow.jayjoska.models.Place;
import com.loopj.android.http.AsyncHttpClient;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link ItineraryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItineraryFragment extends Fragment implements ItineraryAdapter.ItineraryAdapterCommunication {

    private RecsFragment.OnItemAddedListener mItemAddedListener;

    private RecyclerView mRecyclerView;
    private ItineraryAdapter mAdapter;
    private ArrayList<Place> mItinerary;
    private AsyncHttpClient client;
    private TextView mTotalTime;
    private TextView mTravel;
    private ImageView ivShare;
    private int mTime;
    private int mTravelTime = 0;

    private ShareActionProvider mShareActionProvider;

    public ItineraryFragment() {
    }

    public static ItineraryFragment newInstance() {
        ItineraryFragment fragment = new ItineraryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        Log.d("ItineraryFragment", "itinerary fragment loaded");
        return inflater.inflate(R.layout.fragment_itinerary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.rvItinerary);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mTotalTime = view.findViewById(R.id.tvTotalTime);
        mTravel = view.findViewById(R.id.tvTravelTime);
        mTime = mAdapter.grabTime();
        setTimeText(mTime + mTravelTime, mTotalTime);
        setTimeText(mTravelTime, mTravel);
        ivShare = view.findViewById(R.id.ivShare);

        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(mAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRecyclerView);

        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivShare.setImageDrawable(getResources().getDrawable(R.drawable.share_click));

                Intent myIntent = new Intent(Intent.ACTION_SEND);
                //specifies what's being shared, but since it's a dummy it doesn't matter too much what's put inside setType.
                myIntent.setType("Text/plain");
                startActivity(Intent.createChooser(myIntent, "Share Itinerary"));
            }
        });
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
        try {
            mItemAddedListener = (RecsFragment.OnItemAddedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnItemAddedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void updateTime(int i) {
        mTime = i;
        setTimeText(mTime + mTravelTime, mTotalTime);
    }

    @Override
    public void snackbarRemovedItem() {
        Snackbar.make(getActivity().findViewById(R.id.fragmentContainer),
                getString(R.string.snackbar_remove), 500).show();
    }

    public void clearItinerary() {
        mAdapter.clear();
    }

    public ArrayList<Place> getmItinerary() {
        return mItinerary;
    }

    public interface OnItemViewClickedListener {
        void inflateDetails(Bundle bundle);
    }

    public void updateTravelTime(int i) {
        mTravelTime = i;
        setTimeText(mTravelTime, mTravel);
        setTimeText(mTime + mTravelTime, mTotalTime);
    }

    public void setTimeText(int time, TextView tv) {
        int hours = time / 60;
        int minutes = time % 60;
        String s = "";
        if (tv.equals(mTotalTime)) {
            s = "Total time out: ";
        } else if (tv.equals(mTravel)) {
            s = "Travel time: ";
        }

        if (hours != 0) {
            if (hours == 1) {
                s += String.valueOf(hours) + " hour ";
            } else {
                s += String.valueOf(hours) + " hours ";
            }
        }
        if (minutes != 0) {
            if (minutes == 1) {
                s += String.valueOf(minutes) + " minute";
            } else {
                s += String.valueOf(minutes) + " minutes";
            }
        }
        tv.setText(s);
    }

    public void scrollToItem(String s) {
        int position = RecyclerView.NO_POSITION;
        for (int i = 0; i < mItinerary.size(); i++) {
            if (mItinerary.get(i).getName().equals(s)) {
                position = i;
                break;
            }
        }
        if (position != RecyclerView.NO_POSITION) {
            mRecyclerView.scrollToPosition(position);
        }
    }

}
