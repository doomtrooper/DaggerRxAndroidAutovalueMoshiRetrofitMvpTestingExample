package com.anandp.nasaapod.ui.mainscreen;

import com.anandp.nasaapod.data.RepositoryImpl;
import com.anandp.nasaapod.di.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Anand Parshuramka on 29/01/18.
 */

@FragmentScope
@Subcomponent(modules = GalleryModule.class)
public interface GalleryComponent {
    void inject(GalleryFragment fragment);
    void inject(RepositoryImpl repository);
    void inject(GalleryHolder holder);

    @Subcomponent.Builder
    interface Builder{
        GalleryComponent build();
    }
}
