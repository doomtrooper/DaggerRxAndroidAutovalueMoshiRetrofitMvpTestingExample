package com.anandp.nasaapod.data;

import android.util.Log;

import com.anandp.nasaapod.ApiService;
import com.anandp.nasaapod.NasaApodApp;

import java.util.ArrayList;
import java.util.List;

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
    public Disposable getApodForDate(@Nullable String date, final LoadApodCallback listener) {
        NasaApodApp.getAppContext().getRootComponent().inject(this);
        List<Observable<GalleryItem>> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(apiService.getApod("QDITyi8e1uk6izr89dm7mpyfRG5kNSBCmmrZAqzY",null).subscribeOn(Schedulers.io()));
        }
        Disposable disposable = Observable
                .merge(list)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> Log.i(TAG, item.toString()), e -> Log.e(TAG, e.getLocalizedMessage()));
        return disposable;
    }
}
