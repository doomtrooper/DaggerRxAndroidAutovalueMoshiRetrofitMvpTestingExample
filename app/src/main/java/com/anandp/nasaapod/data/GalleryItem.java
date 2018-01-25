package com.anandp.nasaapod.data;

import android.support.annotation.Nullable;

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
    public abstract String date();
    @Nullable
    @Json(name = "explanation")
    public abstract String explanation();
    @Nullable
    @Json(name = "hdurl")
    public abstract String hdurl();
    @Json(name = "media_type")
    public abstract String mediaType();
    @Json(name = "service_version")
    public abstract String serviceVersion();
    @Json(name = "title")
    public abstract String title();
    @Nullable
    @Json(name = "url")
    public abstract String url();

    public static GalleryItem create(String date, String explanation, String hdurl, String mediaType, String serviceVersion, String title, String url) {
        return new AutoValue_GalleryItem(date, explanation, hdurl, mediaType, serviceVersion, title, url);
    }

    public static JsonAdapter<GalleryItem> jsonAdapter(Moshi moshi) {
        return new AutoValue_GalleryItem.MoshiJsonAdapter(moshi);
    }

    @Override
    public final boolean equals(Object obj) {
        GalleryItem galleryItem = (GalleryItem) obj;
        return date().equalsIgnoreCase(galleryItem.date());
    }
}
