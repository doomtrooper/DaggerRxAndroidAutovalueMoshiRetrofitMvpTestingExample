package com.anandp.nasaapod;

import com.anandp.nasaapod.data.model.GalleryItem;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Anand Parshuramka on 23/01/18.
 */

public interface ApiService {

    @GET("planetary/apod")
    Single<GalleryItem> getApod(@Query("api_key") String apiKey, @Query("date") String date);
}
