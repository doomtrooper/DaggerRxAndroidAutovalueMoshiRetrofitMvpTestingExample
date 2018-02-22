package com.anandp.nasaapod.GalleryFragmentTest;

import android.app.Application;

import com.anandp.nasaapod.NasaApodApp;
import com.anandp.nasaapod.data.Repository;
import com.anandp.nasaapod.di.RootComponent;
import com.anandp.nasaapod.di.RootModule;
import com.anandp.nasaapod.ui.mainscreen.GalleryComponent;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Created by @iamBedant on 20/02/18.
 */

@Singleton
@Component(modules = {TestRootModule.class})
public interface TestRootComponent extends RootComponent {


}
