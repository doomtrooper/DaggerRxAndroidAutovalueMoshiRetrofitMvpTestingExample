package com.anandp.nasaapod.data;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Anand Parshuramka on 29/01/18.
 */

public class GalleryitemTest {

    @Test
    public void verifyJsonToModelConversion() throws Exception{
        String sampleJson = "{ \"copyright\": \"Joe Morris\", \"date\": \"2018-01-29\", \"explanation\": \"For scale, the more compact NGC 1931 (Fly) is about 10 light-years across.\", \"hdurl\": \"https://apod.nasa.gov/apod/image/1801/SpiderandFly_Morris_1000.jpg\", \"media_type\": \"image\", \"service_version\": \"v1\", \"title\": \"The Spider and The Fly\", \"url\": \"https://apod.nasa.gov/apod/image/1801/SpiderandFly_Morris_960.jpg\" }";
        GalleryItem galleryItem = GalleryItem.create("2018-01-29", "For scale, the more compact NGC 1931 (Fly) is about 10 light-years across.","https://apod.nasa.gov/apod/image/1801/SpiderandFly_Morris_1000.jpg", "image", "v1","The Spider and The Fly","https://apod.nasa.gov/apod/image/1801/SpiderandFly_Morris_960.jpg");
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<GalleryItem> adapter = GalleryItem.jsonAdapter(moshi);
        assertEquals(adapter.fromJson(sampleJson), galleryItem);
    }

    @Test
    public void verifyJsonToModelConversion2() throws Exception{
        String sampleJson = "{ \"copyright\": \"Joe Morris\", \"date\": \"2018-01-29\", \"explanation\": \"For scale, the more compact NGC 1931 (Fly) is about 10 light-years across.\", \"hdurl\": \"https://apod.nasa.gov/apod/image/1801/SpiderandFly_Morris_1000.jpg\", \"media_type\": \"image\", \"service_version\": \"v1\", \"title\": \"The Spider and The Fly\", \"url\": \"https://apod.nasa.gov/apod/image/1801/SpiderandFly_Morris_960.jpg\" }";
        GalleryItem galleryItem = GalleryItem.create("2018-01-29", "For scale, the more compact NGC 1931 (Fly) is about 10 light-years across.","https://apod.nasa.gov/apod/image/1801/SpiderandFly_Morris_1000.jpg", "image", "v1","The Spider and The Fly","https://apod.nasa.gov/apod/image/1801/SpiderandFly_Morris_960.jpg");
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<GalleryItem> adapter = GalleryItem.jsonAdapter(moshi);
        GalleryItem galleryItemTest = adapter.fromJson(sampleJson);
        assertEquals(galleryItemTest.date(), galleryItem.date());
        assertEquals(galleryItemTest.explanation(), galleryItem.explanation());
        assertEquals(galleryItemTest.hdurl(), galleryItem.hdurl());
        assertEquals(galleryItemTest.serviceVersion(), galleryItem.serviceVersion());
        assertEquals(galleryItemTest.url(), galleryItem.url());
        assertEquals(galleryItemTest.mediaType(), galleryItem.mediaType());
    }

    @Test
    public void verifyJsonToModelComapre() throws Exception{
        GalleryItem galleryItem1 = GalleryItem.create("2018-01-27", "For scale, the more compact NGC 1931 (Fly) is about 10 light-years across.","https://apod.nasa.gov/apod/image/1801/SpiderandFly_Morris_1000.jpg", "image", "v1","The Spider and The Fly","https://apod.nasa.gov/apod/image/1801/SpiderandFly_Morris_960.jpg");
        GalleryItem galleryItem2 = GalleryItem.create("2018-01-29", "For scale, the more compact NGC 1931 (Fly) is about 10 light-years across.","https://apod.nasa.gov/apod/image/1801/SpiderandFly_Morris_1000.jpg", "image", "v1","The Spider and The Fly","https://apod.nasa.gov/apod/image/1801/SpiderandFly_Morris_960.jpg");
        assertFalse(galleryItem1.equals(galleryItem2));
    }

    @Test
    public void verifyJsonToModelComapre2() throws Exception{
        GalleryItem galleryItem1 = GalleryItem.create("2018-01-29", "For scale, the more compact NGC 1931 (Fly) is about 10 light-years across.","https://apod.nasa.gov/apod/image/1801/SpiderandFly_Morris_1000.jpg", "image", "v1","The Spider and The Fly","https://apod.nasa.gov/apod/image/1801/SpiderandFly_Morris_960.jpg");
        GalleryItem galleryItem2 = GalleryItem.create("2018-01-29", "For scale, the more compact NGC 1931 (Fly) is about 10 light-years across.","https://apod.nasa.gov/apod/image/1801/SpiderandFly_Morris_1000.jpg", "image", "v1","The Spider and The Fly","https://apod.nasa.gov/apod/image/1801/SpiderandFly_Morris_960.jpg");
        assertTrue(galleryItem1.equals(galleryItem2));
    }
}
