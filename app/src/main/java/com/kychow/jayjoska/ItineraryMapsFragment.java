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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ItineraryMapsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ItineraryMapsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItineraryMapsFragment extends Fragment implements RecsFragment.OnItemAddedListener,
        ItineraryAdapter.OnUpdateTimeListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecsFragment.OnItemAddedListener mListener;

    private MapFragment mapFragment;
    private ItineraryFragment itineraryFragment;

    public ItineraryMapsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ItineraryMapsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ItineraryMapsFragment newInstance(String param1, String param2) {
        ItineraryMapsFragment fragment = new ItineraryMapsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        // Inflate the layout for this fragment
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
}
