package com.anandp.nasaapod.data.model;

import android.content.ContentValues;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Created by Anand Parshuramka on 23/01/18.
 */

@AutoValue
public abstract class GalleryItem implements Parcelable{

    public static final String CREATE_GALLERY_ITEM = ""
            + "CREATE TABLE " + GalleryItem.TABLE + "("
            + GalleryItem.DATE + " TEXT NOT NULL PRIMARY KEY,"
            + GalleryItem.EXPLANATION + " TEXT ,"
            + GalleryItem.MEDIA_TYPE + " TEXT ,"
            + GalleryItem.SERVICE_VERSION + " TEXT,"
            + GalleryItem.HD_URL + " TEXT,"
            + GalleryItem.TITLE + " TEXT,"
            + GalleryItem.URL + " TEXT "
            + ")";


    public static final String TABLE = "gallery_item";

    public static final String DATE = "date";
    public static final String EXPLANATION = "explanation";
    public static final String HD_URL= "hd_url";
    public static final String MEDIA_TYPE = "media_type";
    public static final String SERVICE_VERSION = "service_version";
    public static final String TITLE = "title";
    public static final String URL = "url";

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

    public static final class Builder{
        private final ContentValues values = new ContentValues();

        public Builder date(String date){
            values.put(DATE, date);
            return this;
        }

        public Builder explanation(String explanantion){
            values.put(EXPLANATION, explanantion);
            return this;
        }

        public Builder hdUrl(String hdUrl){
            values.put(HD_URL, hdUrl);
            return this;
        }

        public Builder mediaType(String mediaType){
            values.put(MEDIA_TYPE, mediaType);
            return this;
        }

        public Builder serviceVersion(String serviceVersion){
            values.put(SERVICE_VERSION, serviceVersion);
            return this;
        }

        public Builder title(String title){
            values.put(TITLE, title);
            return this;
        }

        public Builder url(String url){
            values.put(URL, url);
            return this;
        }

        public ContentValues build(){
            return values;
        }
    }


}
