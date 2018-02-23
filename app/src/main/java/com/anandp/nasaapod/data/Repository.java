package com.anandp.nasaapod.data;

import com.anandp.nasaapod.data.model.GalleryItem;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.Nullable;

/**
 * Created by Anand Parshuramka on 23/01/18.
 */

public interface Repository {
    Observable<GalleryItem> getApodForMonth(@Nullable String date);
    Single<GalleryItem> getApodForDate(@Nullable String date);
}
