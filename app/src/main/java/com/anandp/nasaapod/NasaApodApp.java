package com.anandp.nasaapod;

import android.app.Application;

import com.anandp.nasaapod.di.DaggerRootComponent;
import com.anandp.nasaapod.di.RootComponent;

/**
 * Created by Anand Parshuramka on 22/01/18.
 */

public class NasaApodApp extends Application {
    private RootComponent rootComponent;

    private static NasaApodApp appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        rootComponent = DaggerRootComponent.builder()
                .application(this)
                .baseUrl("https://api.nasa.gov/")
                .build();
        appContext = this;
    }

    public static NasaApodApp getAppContext() {
        return appContext;
    }

    public RootComponent getRootComponent() {
        return rootComponent;
    }
}
