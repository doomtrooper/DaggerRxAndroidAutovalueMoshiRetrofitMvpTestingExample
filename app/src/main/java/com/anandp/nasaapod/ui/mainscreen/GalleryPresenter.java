package com.anandp.nasaapod.ui.mainscreen;

import com.anandp.nasaapod.data.model.GalleryItem;
import com.anandp.nasaapod.data.Repository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Anand Parshuramka on 23/01/18.
 */

public class GalleryPresenter implements GalleryContract.Presenter {

    private GalleryContract.View mView;
    private Repository mRepository;
    private CompositeDisposable compositeDisposable;


    @Inject
    public GalleryPresenter(Repository mRepository) {
        this.mRepository = mRepository;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void loadGalleryItems() {
        compositeDisposable.add(mRepository.getApodForMonth(null)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(galleryItems -> {
                    mView.showErrorView(false);
                    mView.setLoadingIndicator(false);
                    mView.showItems(true);
                    mView.addGalleryItem(galleryItems);
                }, throwable -> mView.showError(throwable.getMessage())));
    }

    @Override
    public void takeView(GalleryContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        compositeDisposable.dispose();
        mView = null;
    }
}
