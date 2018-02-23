package com.anandp.nasaapod.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.anandp.nasaapod.ApiService;
import com.anandp.nasaapod.data.model.GalleryItem;
import com.anandp.nasaapod.utils.Constants;
import com.squareup.sqlbrite3.BriteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

/**
 * Created by Anand Parshuramka on 23/01/18.
 */

public class RepositoryImpl implements Repository {


    private ApiService apiService;
    private BriteDatabase db;

    @Inject
    public RepositoryImpl(ApiService apiService, BriteDatabase db) {
        this.apiService = apiService;
        this.db = db;
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
            int[] count = new int[1];
            for (int i = 0; i < 15; i++) {
                getSingle(sdf.format(calendar.getTime()))
                        .subscribe(galleryItem -> {
                            emitter.onNext(galleryItem);
                        }, throwable -> {
                            count[0]++;
                            if (count[0]==15) emitter.onError(throwable);
                        });
                calendar.add(Calendar.DATE, -1);
            }
        });
    }

    private Single<GalleryItem> getSingle(@NonNull String date) {
        Observable<GalleryItem> localObs = Observable.create(emmiter -> getLocalItem(date)
                .subscribe(galleryItem -> {
                    if (galleryItem.date().equalsIgnoreCase("0")) emmiter.onComplete();
                    else {
                        emmiter.onNext(galleryItem);
                        emmiter.onComplete();
                    }
                }, throwable -> {
                }));
        Observable<GalleryItem> remoteObs = Observable.create(emmiter -> getRemoteitem(date)
                .subscribe(galleryItem -> emmiter.onNext(galleryItem), throwable -> emmiter.onError(throwable)));
        return Observable.concat(localObs, remoteObs).firstOrError();
    }

    private Observable<GalleryItem> getLocalItem(@NonNull String date) {
        return db.createQuery(GalleryItem.TABLE, "Select * from " + GalleryItem.TABLE + " where date='" + date + "'")
                .map(query -> {
                    Cursor cursor = query.run();
                    if (cursor != null && cursor.moveToFirst()) {
                        return GalleryItem.getGalleryItem(cursor);
                    }
                    return GalleryItem.dummyObject();
                });
    }

    private Observable<GalleryItem> getRemoteitem(@NonNull String date) {
        return apiService.getApodObservable(Constants.API_KEY, date)
                .doOnNext(galleryItem -> db.insert(GalleryItem.TABLE, SQLiteDatabase.CONFLICT_REPLACE, GalleryItem.getContentValues(galleryItem)));
    }

}
