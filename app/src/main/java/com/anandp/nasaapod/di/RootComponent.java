package com.anandp.nasaapod.di;

import android.app.Application;

import com.anandp.nasaapod.NasaApodApp;
import com.anandp.nasaapod.data.Repository;
import com.anandp.nasaapod.ui.mainscreen.GalleryComponent;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Created by Anand Parshuramka on 23/01/18.
 */

@Singleton
@Component(modules = {RootModule.class})
public interface RootComponent {
    void inject(NasaApodApp app);

    GalleryComponent.Builder galleryBuilder();

    @Component.Builder
    interface Builder{
        RootComponent build();
        @BindsInstance
        Builder application(Application application);
        @BindsInstance
        Builder baseUrl(String baseUrl);
    }

    Repository getRepository();

}
