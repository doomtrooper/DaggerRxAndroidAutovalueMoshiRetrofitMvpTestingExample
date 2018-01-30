package com.anandp.nasaapod.data;

import android.util.Log;

import com.anandp.nasaapod.ApiService;
import com.anandp.nasaapod.NasaApodApp;
import com.anandp.nasaapod.utils.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Anand Parshuramka on 23/01/18.
 */

public class RepositoryImpl implements Repository {

    public static String TAG = RepositoryImpl.class.getSimpleName();

    @Inject
    ApiService apiService;

    @Override
    public Disposable getApodForDate(String date, LoadApodCallback listener) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date fromDate;
        try {
            fromDate = sdf.parse(date);
        } catch (ParseException | NullPointerException e) {
            fromDate = new Date();
        }
        return getObservable(sdf.format(fromDate))
                .subscribe(item -> {
                    Log.v(TAG, item.toString());
                    listener.onGalleryItemsLoaded(item);
                }, e -> listener.onError(e.getLocalizedMessage()));
    }

    @Override
    public Disposable getApodForMonth(@Nullable String date, final LoadApodCallback listener) {
        NasaApodApp.getAppContext().getRootComponent().inject(this);
        List<Observable<GalleryItem>> list = getObservableList(date);
        Disposable disposable = Observable
                .merge(list)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> {
                    Log.v(TAG, item.toString());
                    listener.onGalleryItemsLoaded(item);
                }, e -> listener.onError(e.getLocalizedMessage()));
        return disposable;
    }

    private List<Observable<GalleryItem>> getObservableList(@Nullable String date) {
        List<Observable<GalleryItem>> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date fromDate;
        try {
            fromDate = sdf.parse(date);
        } catch (ParseException | NullPointerException e) {
            fromDate = new Date();
        }
        fromDate.setMonth(fromDate.getMonth()-1);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(fromDate);
        for (int i = 0; i < 30; i++) {
            list.add(getObservable(sdf.format(calendar.getTime())));
            calendar.add(Calendar.DATE,1);
        }
        return list;
    }

    public Observable<GalleryItem> getObservable(@Nullable String date){
        return apiService.getApod(Constants.API_KEY, date)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread());
    }

}
