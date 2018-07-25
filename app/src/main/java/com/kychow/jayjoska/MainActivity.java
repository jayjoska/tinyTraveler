package com.kychow.jayjoska;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.kychow.jayjoska.models.Place;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
 * @brief MainActivity activity that holds a RecyclerView of all the categories
 *
 * The contents of this class will probably be migrated at some point to the final activity/fragments
 */
public class MainActivity extends AppCompatActivity
        implements CategoriesFragment.OnNextButtonClicked, RecommendationsFragment.OnPlacesPopulatedListener{

    private static final String TAG = "MainActivity";
    // Hardcoded aliases for the main 22 categories.
    private static final String[] CATEGORY_ALIASES =
            {"active", "arts", "auto", "beautysvc", "bicycles", "education", "eventservices", "financialservices",
                    "food", "health", "homeservices", "hotelstravel", "localflavor",
                    "localservices", "massmedia", "nightlife", "pets", "professional",
                    "publicservicesgovt", "religiousorgs", "restaurants",
                    "shopping"};


    @BindView(R.id.bottom_navigation) BottomNavigationView bottomNavigationView;
    final FragmentManager fragmentManager = getSupportFragmentManager();
    // TODO: Should fragments be Final onCreate?
    CategoriesFragment categoriesFragment;
    MapsRecsFragment mapsRecsFragment;
    ItineraryFragment itineraryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        categoriesFragment = new CategoriesFragment();
        mapsRecsFragment = new MapsRecsFragment();
        itineraryFragment = new ItineraryFragment();

        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, categoriesFragment)
                .commit();

        // handle navigation selection
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        FragmentTransaction fragmentTransaction;
                        switch (item.getItemId()) {
                            case R.id.action_categories:
                                fragmentTransaction = fragmentManager.beginTransaction();
                                // TODO refactor fragment transaction logic for separate map/rec container
                                //fragmentTransaction.show(categoriesFragment).commit();
                                fragmentTransaction.replace(R.id.fragmentContainer, categoriesFragment)
                                        .commit();
                                return true;
                            case R.id.action_map:
                                fragmentTransaction = fragmentManager.beginTransaction();
                                //fragmentTransaction.hide(categoriesFragment);
                                fragmentTransaction.replace(R.id.fragmentContainer, mapsRecsFragment)
                                        .commit();
                                return true;
                            case R.id.action_itinerary:
                                fragmentTransaction = fragmentManager.beginTransaction();
                                //fragmentTransaction.show(itineraryFragment);
                                fragmentTransaction.replace(R.id.fragmentContainer, itineraryFragment)
                                        .commit();
                                return true;
                            default:
                                return true;
                        }
                    }
                });
    }

    // TODO migrate category aliases
    public static String[] getCategoryAliases() {
        return CATEGORY_ALIASES;
    }

    /**
     *
     * @param categories An arraylist
     */
    @Override
    public void sendCategories(ArrayList<String> categories) {
        if (categories != null) {
            mapsRecsFragment.setCategories(categories);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction
                    .replace(R.id.fragmentContainer, mapsRecsFragment, "mapsRecsFrag")
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void sendRecs(ArrayList<Place> places) {
        mapsRecsFragment.sendRecs(places);
    }
}
