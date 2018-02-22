package com.anandp.nasaapod.data;

import com.anandp.nasaapod.ApiService;
import com.anandp.nasaapod.data.model.GalleryItem;
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

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Anand Parshuramka on 23/01/18.
 */

public class RepositoryImpl implements Repository {

    public static String TAG = RepositoryImpl.class.getSimpleName();

    ApiService apiService;

    @Inject
    public RepositoryImpl(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Disposable getApodForDate(String date, LoadApodCallback listener) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date fromDate;
        try {
            fromDate = sdf.parse(date);
        } catch (ParseException | NullPointerException e) {
            fromDate = new Date();
        }
        return getSingle(sdf.format(fromDate))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(erro -> listener.onError(erro.getMessage()))
                .doOnSuccess(item -> listener.onGalleryItemsLoaded(item))
                .subscribe();
    }

    @Override
    public Single<List<GalleryItem>> getApodForMonth(@Nullable String date) {

        List<Single<GalleryItem>> list = getSingleList(date);

        return Single.zip(list, objects -> {
            List<GalleryItem> galleryItemList = new ArrayList<>();
            for (Object object : objects) {
                galleryItemList.add((GalleryItem) object);
            }
            return galleryItemList;
        });
    }

    private List<Single<GalleryItem>> getSingleList(@Nullable String date) {
        List<Single<GalleryItem>> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date fromDate;
        try {
            fromDate = sdf.parse(date);
        } catch (ParseException | NullPointerException e) {
            fromDate = new Date();
        }
        fromDate.setMonth(fromDate.getMonth() - 1);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(fromDate);
        for (int i = 0; i < 15; i++) {
            list.add(getSingle(sdf.format(calendar.getTime())));
            calendar.add(Calendar.DATE, 1);
        }
        //list.add(getSingle("2020-11-11"));
        return list;
    }

    public Single<GalleryItem> getSingle(@Nullable String date) {
        return apiService.getApod(Constants.API_KEY, date);
    }

}
