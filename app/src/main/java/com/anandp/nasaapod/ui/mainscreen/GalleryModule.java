package com.anandp.nasaapod.ui.mainscreen;

import com.anandp.nasaapod.data.RepositoryImpl;
import com.anandp.nasaapod.di.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Anand Parshuramka on 29/01/18.
 */

@Module
public class GalleryModule {

    @Provides
    @FragmentScope
    RepositoryImpl providesRepository(){
        return new RepositoryImpl();
    }

    @FragmentScope
    @Provides
    GalleryPresenter providesGalleryPresenter(RepositoryImpl repository){
        return new GalleryPresenter(repository);
    }
}
