package com.anandp.nasaapod.data.model;

import android.content.ContentValues;
import android.database.Cursor;
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
    @Nullable
    @Json(name = "media_type")
    public abstract String mediaType();
    @Nullable
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

    public static Builder builder() {
        return new AutoValue_GalleryItem.Builder();
    }

    public static GalleryItem getGalleryItem(Cursor cursor){
        return GalleryItem.builder().setDate(cursor.getString(cursor.getColumnIndex(GalleryItem.DATE)))
                .setTitle(cursor.getString(cursor.getColumnIndex(GalleryItem.TITLE)))
                .setExplanation(cursor.getString(cursor.getColumnIndex(GalleryItem.EXPLANATION)))
                .setHdurl(cursor.getString(cursor.getColumnIndex(GalleryItem.HD_URL)))
                .setUrl(cursor.getString(cursor.getColumnIndex(GalleryItem.URL)))
                .setMediaType(cursor.getString(cursor.getColumnIndex(GalleryItem.MEDIA_TYPE)))
                .setServiceVersion(cursor.getString(cursor.getColumnIndex(GalleryItem.SERVICE_VERSION)))
                .build();
    }

    public static ContentValues getContentValues(GalleryItem galleryItem){
        return new GalleryItem.ContentBuilder()
                .date(galleryItem.date())
                .title(galleryItem.title())
                .explanation(galleryItem.explanation())
                .hdUrl(galleryItem.hdurl())
                .url(galleryItem.url())
                .mediaType(galleryItem.mediaType())
                .serviceVersion(galleryItem.serviceVersion())
                .build();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setDate(String value);
        public abstract Builder setExplanation(String value);
        public abstract Builder setHdurl(String value);
        public abstract Builder setMediaType(String value);
        public abstract Builder setServiceVersion(String value);
        public abstract Builder setTitle(String value);
        public abstract Builder setUrl(String value);
        public abstract GalleryItem build();
    }

    public static final class ContentBuilder{
        private final ContentValues values = new ContentValues();

        public ContentBuilder date(String date){
            values.put(DATE, date);
            return this;
        }

        public ContentBuilder explanation(String explanantion){
            values.put(EXPLANATION, explanantion);
            return this;
        }

        public ContentBuilder hdUrl(String hdUrl){
            values.put(HD_URL, hdUrl);
            return this;
        }

        public ContentBuilder mediaType(String mediaType){
            values.put(MEDIA_TYPE, mediaType);
            return this;
        }

        public ContentBuilder serviceVersion(String serviceVersion){
            values.put(SERVICE_VERSION, serviceVersion);
            return this;
        }

        public ContentBuilder title(String title){
            values.put(TITLE, title);
            return this;
        }

        public ContentBuilder url(String url){
            values.put(URL, url);
            return this;
        }

        public ContentValues build(){
            return values;
        }
    }

    public static GalleryItem dummyObject(){
        return GalleryItem.builder().setDate("0").setTitle("asdf").build();
    }

}
