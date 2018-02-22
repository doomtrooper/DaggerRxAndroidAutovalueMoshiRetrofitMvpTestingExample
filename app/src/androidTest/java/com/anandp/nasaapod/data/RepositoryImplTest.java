package com.anandp.nasaapod.data;

import com.anandp.nasaapod.ApiService;
import com.anandp.nasaapod.data.model.GalleryItem;
import com.anandp.nasaapod.utils.Constants;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.when;

/**
 * Created by Anand Parshuramka on 29/01/18.
 */
public class RepositoryImplTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @Mock
    ApiService apiService;
    @InjectMocks
    RepositoryImpl repository;

    @Test
    public void testGetApod(){
//        GalleryItem galleryItem = GalleryItem.create("2018-01-29", "For scale, the more compact NGC 1931 (Fly) is about 10 light-years across.","https://apod.nasa.gov/apod/image/1801/SpiderandFly_Morris_1000.jpg", "image", "v1","Spider ","https://apod.nasa.gov/apod/image/1801/SpiderandFly_Morris_960.jpg");
//        when(apiService.getApod(Constants.API_KEY, "2018-01-29")).thenReturn(Observable.just(galleryItem));
//        TestObserver<GalleryItem> testObserver = repository.getSingle("2018-01-29").test();
//        testObserver.awaitTerminalEvent();
//        testObserver.assertNoErrors();
//        testObserver.assertValue(galleryItem);
    }
}
