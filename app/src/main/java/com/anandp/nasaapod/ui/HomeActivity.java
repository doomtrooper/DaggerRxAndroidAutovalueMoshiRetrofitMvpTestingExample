package com.anandp.nasaapod.ui;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.anandp.nasaapod.R;
import com.anandp.nasaapod.ui.mainscreen.GalleryFragment;

public class HomeActivity extends AppCompatActivity implements GalleryFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getFragmentManager();
        GalleryFragment fragment = (GalleryFragment) fm.findFragmentByTag(GalleryFragment.TAG);
        if (fragment==null){
            fragment = GalleryFragment.newInstance();
            fm.beginTransaction().add(R.id.container, fragment, GalleryFragment.TAG).commit();
        }
    }

    @Override
    public void onFragmentInteraction() {

    }
}
