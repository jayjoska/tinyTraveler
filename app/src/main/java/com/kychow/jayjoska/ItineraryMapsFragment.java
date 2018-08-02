package com.kychow.jayjoska;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kychow.jayjoska.models.Place;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecsFragment.OnItemAddedListener} interface
 * to handle interaction events.
 * Use the {@link ItineraryMapsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItineraryMapsFragment extends Fragment implements RecsFragment.OnItemAddedListener,
        ItineraryAdapter.OnUpdateTimeListener{

    private RecsFragment.OnItemAddedListener mListener;

    private MapFragment mapFragment;
    private ItineraryFragment itineraryFragment;

    public ItineraryMapsFragment() {
    }

    // new instance of ItineraryMapsFragment
    public static ItineraryMapsFragment newInstance() {
        ItineraryMapsFragment fragment = new ItineraryMapsFragment();
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

        if (itineraryFragment == null) {
            itineraryFragment = new ItineraryFragment();
        }

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.flMapContainer, mapFragment).add(R.id.flItineraryContainer, itineraryFragment).commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_itinerary_maps, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RecsFragment.OnItemAddedListener) {
            mListener = (RecsFragment.OnItemAddedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void addToItinerary(Place itineraryPlaces) {
        if (itineraryFragment == null) {
            itineraryFragment = new ItineraryFragment();
        }
        itineraryFragment.addToItinerary(itineraryPlaces);
    }

    @Override
    public void updateTime(int i) {
        itineraryFragment.updateTime(i);
    }
}
