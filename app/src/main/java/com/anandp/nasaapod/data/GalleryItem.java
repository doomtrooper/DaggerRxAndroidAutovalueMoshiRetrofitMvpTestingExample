package com.anandp.nasaapod.data;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Created by Anand Parshuramka on 23/01/18.
 */

@AutoValue
public abstract class GalleryItem {
    @Json(name = "date")
    abstract String date();
    @Json(name = "explanation")
    abstract String explanation();
    @Json(name = "hdurl")
    abstract String hdurl();
    @Json(name = "media_type")
    abstract String mediaType();
    @Json(name = "service_version")
    abstract String serviceVersion();
    @Json(name = "title")
    abstract String title();
    @Json(name = "url")
    abstract String url();

    public static GalleryItem create(String date, String explanation, String hdurl, String mediaType, String serviceVersion, String title, String url) {
        return new AutoValue_GalleryItem(date, explanation, hdurl, mediaType, serviceVersion, title, url);
    }

    public static JsonAdapter<GalleryItem> jsonAdapter(Moshi moshi) {
        return new AutoValue_GalleryItem.MoshiJsonAdapter(moshi);
    }
}
