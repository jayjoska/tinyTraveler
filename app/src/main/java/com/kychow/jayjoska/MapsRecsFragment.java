package com.kychow.jayjoska;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kychow.jayjoska.models.Place;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapsRecsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapsRecsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapsRecsFragment extends Fragment implements RecommendationsFragment.OnPlacesPopulatedListener, RecommendationsFragment.OnSelectedListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private RecommendationsFragment.OnSelectedListener mOnSelectedListener;

    private MapFragment mapFragment;
    private RecommendationsFragment recommendationsFragment;

    public MapsRecsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapsRecsFragment.
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

        if (recommendationsFragment == null) {
            recommendationsFragment = new RecommendationsFragment();
        }

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.flMap, mapFragment).add(R.id.flRecs, recommendationsFragment).commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_maps_recs, container, false);
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
        try {
            mOnSelectedListener = (RecommendationsFragment.OnSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnRecSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public void inflateDetails() {
        mOnSelectedListener.inflateDetails();
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

    public void setCategories(ArrayList<String> categories) {
        if (recommendationsFragment == null) {
            recommendationsFragment = new RecommendationsFragment();
        }
        recommendationsFragment.setCategories(categories);
    }
}
