package com.anandp.nasaapod.data;

import com.anandp.nasaapod.ApiService;
import com.anandp.nasaapod.data.model.GalleryItem;
import com.anandp.nasaapod.utils.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.Nullable;

/**
 * Created by Anand Parshuramka on 23/01/18.
 */

public class RepositoryImpl implements Repository {


    ApiService apiService;

    @Inject
    RepositoryImpl(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Single<GalleryItem> getApodForDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date fromDate;
        try {
            fromDate = sdf.parse(date);
        } catch (ParseException | NullPointerException e) {
            fromDate = new Date();
        }
        return getSingle(sdf.format(fromDate));

    }

    @Override
    public Observable<GalleryItem> getApodForMonth(@Nullable String date) {
        return getImageObservable(date);
    }

    private Single<GalleryItem> getSingle(@Nullable String date) {
        return apiService.getApod(Constants.API_KEY, date);
    }

    private Observable<GalleryItem> getImageObservable(@Nullable String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date fromDate;
        try {
            fromDate = sdf.parse(date);
        } catch (ParseException | NullPointerException e) {
            fromDate = new Date();
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(fromDate);
        return Observable.create(emitter -> {
            for (int i = 0; i < 15; i++) {
                getSingle(sdf.format(calendar.getTime()))
                        .subscribe(galleryItem -> emitter.onNext(galleryItem), throwable -> {});
                calendar.add(Calendar.DATE, -1);
            }
        });
    }


}
