package com.anandp.nasaapod.ui.mainscreen;

import dagger.Component;

/**
 * Created by Anand Parshuramka on 29/01/18.
 */

@Component(modules = GalleryModule.class)
public interface GalleryComponent {
    void inject(GalleryFragment fragment);
}
