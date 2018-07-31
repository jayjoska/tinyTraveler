package com.kychow.jayjoska;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kychow.jayjoska.models.Place;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapsRecsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapsRecsFragment extends Fragment implements RecsFragment.OnPlacesPopulatedListener,
        RecsFragment.OnSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecsFragment.OnSelectedListener mOnSelectedListener;
    private RecsFragment.OnItemAddedListener mOnItemAddedListener;

    private MapFragment mapFragment;
    private RecsFragment recsFragment;

    public MapsRecsFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    // TODO: Rename and change types and number of parameters
    public static MapsRecsFragment newInstance(String param1, String param2) {
        MapsRecsFragment fragment = new MapsRecsFragment();
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
        if (mapFragment == null) {
            mapFragment = new MapFragment();
        }
        if (recsFragment == null) {
            recsFragment = new RecsFragment();
        }
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.flMap, mapFragment).add(R.id.flRecs, recsFragment).commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_maps_recs, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnSelectedListener = (RecsFragment.OnSelectedListener) context;
            Log.d("MapsRec", "created listener in MapsRec");
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnRecSelectedListener");
        }
        try {
            mOnItemAddedListener = (RecsFragment.OnItemAddedListener) context;
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
    public void sendRecs(ArrayList<Place> places) {
        if (mapFragment == null) {
            mapFragment = new MapFragment();
        }
        mapFragment.setPlaces(places);
        mapFragment.addMarkers();
    }

    @Override
    public void inflateDetails(Bundle bundle) {
        mOnSelectedListener.inflateDetails(bundle);
    }

    public void setCategories(ArrayList<String> categories) {
        if (recsFragment == null) {
            recsFragment = new RecsFragment();
        }
        recsFragment.setCategories(categories);
    }
}
