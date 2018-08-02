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
        RecsFragment.OnSelectedListener, MapFragment.OnNewAddressListener {

    private RecsFragment.OnSelectedListener mOnSelectedListener;
    private RecsFragment.OnItemAddedListener mOnItemAddedListener;

    private MapFragment mapFragment;
    private RecsFragment recsFragment;

    public MapsRecsFragment() {
    }

    public static MapsRecsFragment newInstance() {
        MapsRecsFragment fragment = new MapsRecsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @Override
    public void requestRecs(String s) {
        recsFragment.setAddress(s);
        recsFragment.getRecsFromOutside();
    }
}
