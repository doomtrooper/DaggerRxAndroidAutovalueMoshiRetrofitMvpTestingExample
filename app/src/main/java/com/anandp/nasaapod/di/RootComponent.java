package com.anandp.nasaapod.di;

import com.anandp.nasaapod.NasaApodApp;
import com.anandp.nasaapod.data.RepositoryImpl;
import com.anandp.nasaapod.ui.HomeActivity;
import com.anandp.nasaapod.ui.mainscreen.GalleryPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Anand Parshuramka on 23/01/18.
 */

@Singleton
@Component(modules = {RootModule.class, AppModule.class})
public interface RootComponent {
    void inject(NasaApodApp app);
    void inject(HomeActivity activity);
    void inject(GalleryPresenter presenter);
    void inject(RepositoryImpl repository);
}
