package com.anandp.nasaapod.data;

import java.util.List;

import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Anand Parshuramka on 23/01/18.
 */

public interface Repository {
    Disposable getApodForMonth(@Nullable String date, LoadApodCallback listener);
    Disposable getApodForDate(@Nullable String date, LoadApodCallback listener);


    interface LoadApodCallback{
        void onGalleryItemsLoaded(GalleryItem item);
        void onError(String error);
        void onGalleryItemsLoaded(List<GalleryItem> items);
    }
}
