package com.kychow.jayjoska;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.kychow.jayjoska.models.Place;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
 * @brief MainActivity activity that holds a RecyclerView of all the categories
 *
 * The contents of this class will probably be migrated at some point to the final activity/fragments
 */
public class MainActivity extends AppCompatActivity implements CategoriesFragment.OnNextButtonClicked,
        RecsFragment.OnSelectedListener, RecsFragment.OnPlacesPopulatedListener,
        RecsFragment.OnItemAddedListener, ItineraryAdapter.OnUpdateTimeListener, MapFragment.OnNewAddressListener,
        MapsRecsFragment.OnAddressChangedListener, MapFragment.OnMapListener, MapFragment.OnMarkerClickedListener, ItineraryFragment.OnItemViewClickedListener,
        MapFragment.OnLocationUpdateListener, MapFragment.OnTravelTimeUpdatedListener, MapFragment.OnItineraryMarkerClicked {



    private static final String TAG = "MainActivity";
    // Hardcoded aliases for the main 22 categories.
    private static final String[] CATEGORY_ALIASES =
            {"active", "arts", "auto", "beautysvc", "education", "eventservices", "financialservices",
                    "food", "health", "homeservices", "hotelstravel", "localflavor",
                    "localservices", "massmedia", "nightlife", "pets", "professional",
                    "publicservicesgovt", "religiousorgs", "restaurants",
                    "shopping"};


    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;
    CategoriesFragment categoriesFragment;
    MapsRecsFragment mapsRecsFragment;
    ItineraryMapsFragment itineraryMapsFragment;
    DetailsFragment detailsFragment;
    Bundle bundle;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        categoriesFragment = new CategoriesFragment();
        mapsRecsFragment = new MapsRecsFragment();
        itineraryMapsFragment = new ItineraryMapsFragment();
        detailsFragment = new DetailsFragment();

        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, categoriesFragment)
                .commit();

        if (categoriesFragment.isFirstTime()) {
            bottomNavigationView.setVisibility(View.GONE);
        } else {
            bottomNavigationView.setVisibility(View.VISIBLE);
        }

        // handle navigation selection
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_categories:
                                replaceFragment(categoriesFragment);
                                return true;
                            case R.id.action_map:
                                replaceFragment(mapsRecsFragment);
                                return true;
                            case R.id.action_itinerary:
                                replaceFragment(itineraryMapsFragment);
                                return true;
                            default:
                                return true;
                        }
                    }
                });
    }

    @Override
    public void addToItinerary(Place itineraryPlace) {
        itineraryMapsFragment.addToItinerary(itineraryPlace);
        Log.d("MainActivity", "something has been added!! " + itineraryPlace.getName());
       // Bundle newBundle = new Bundle();
        //newBundle.putParcelable("place", Parcels.wrap(itineraryPlace));
        //bundle = newBundle;
    }

    // TODO migrate category aliases
    public static String[] getCategoryAliases() {
        return CATEGORY_ALIASES;
    }

    /**
     * @param categories An arraylist
     */
    @Override
    public void sendCategories(ArrayList<String> categories) {
        if (categories != null) {
            mapsRecsFragment.setCategories(categories);
            replaceFragment(mapsRecsFragment);
        }
    }

    public void sendRecs(ArrayList<Place> places) {
        mapsRecsFragment.sendRecs(places);
    }

    public void inflateDetails(Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        detailsFragment.setArguments(bundle);
        fragmentTransaction
                .replace(R.id.fragmentContainer, detailsFragment)
                .addToBackStack(null)
                .commit();
    }

    private void replaceFragment(Fragment fragment) {
        //fragment.setArguments(bundle);
        String backStateName = fragment.getClass().getName();
        String fragmentTag = backStateName;

        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null) { //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.fragmentContainer, fragment);
            ft.addToBackStack(backStateName);
            ft.commit();
        }

        //TODO: Add a listener from mapsFrag and recsFrag, that loads this once once the rest is loaded
        bottomNavigationView.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateTime(int i) {
        itineraryMapsFragment.updateTime(i);
    }

    @Override
    public void sendItinerary() {
        Log.d("MainActivity", "itinerary sent successfully");
        itineraryMapsFragment.sendItinerary();
    }

    @Override
    public ArrayList<Place> getItinerary() {
        return itineraryMapsFragment.getItinerary();
    }

    public void requestRecs(String s) {
        mapsRecsFragment.requestRecs(s);
    }

    @Override
    public void onBackPressed() {
        if (mapsRecsFragment.shouldAllowBack()) {
            super.onBackPressed();
        }
    }

    @Override
    public void clearItinerary() {
        if (itineraryMapsFragment != null) {
            itineraryMapsFragment.clearItinerary();
        }
    }

    @Override
    public void scrollToItem(String s) {
        mapsRecsFragment.scrollToItem(s);
    }

    @Override
    public void setLocation(Location location) {
        itineraryMapsFragment.setLocation(location);
    }

    @Override
    public void updateTravelTime(int i) {
        itineraryMapsFragment.updateTravelTime(i);
    }

    @Override
    public void scrollItinerary(String s) {
        itineraryMapsFragment.scrollItinerary(s);
    }
}


