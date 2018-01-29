package com.anandp.nasaapod.ui.mainscreen;

import com.anandp.nasaapod.data.RepositoryImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Anand Parshuramka on 29/01/18.
 */

@Module
public class GalleryModule {

    @Provides
    RepositoryImpl providesRepository(){
        return new RepositoryImpl();
    }

    @Provides
    GalleryPresenter providesGalleryPresenter(RepositoryImpl repository){
        return new GalleryPresenter(repository);
    }
}
