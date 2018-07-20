package com.kychow.jayjoska;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/*
 * ###############################
 * TEST CLASS, PLEASE IGNORE
 * ###############################
 */
public class RecsTempActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recs_temp);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        RecommendationsFragment fragment = new RecommendationsFragment();
        fragmentTransaction.add(R.id.flContainer, fragment);
        fragmentTransaction.commit();
    }
}
