package com.anandp.nasaapod.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.anandp.nasaapod.R;
import com.anandp.nasaapod.ui.mainscreen.GalleryFragment;

public class HomeActivity extends AppCompatActivity implements GalleryFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFragmentManager().beginTransaction().add(R.id.container, GalleryFragment.newInstance(), GalleryFragment.class.getSimpleName()).commit();
    }

    @Override
    public void onFragmentInteraction() {

    }
}
