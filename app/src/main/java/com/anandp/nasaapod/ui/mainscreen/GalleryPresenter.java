package com.anandp.nasaapod.ui.mainscreen;

import com.anandp.nasaapod.data.GalleryItem;
import com.anandp.nasaapod.data.Repository;
import com.anandp.nasaapod.data.RepositoryImpl;

import io.reactivex.disposables.Disposable;

/**
 * Created by Anand Parshuramka on 23/01/18.
 */

public class GalleryPresenter implements GalleryContract.Presenter, Repository.LoadApodCallback {

    private GalleryContract.View mView;
    private Repository mRepository;
    private Disposable request;

    GalleryPresenter() {
        mRepository = new RepositoryImpl();
    }

    @Override
    public void loadGalleryItems() {
        request = mRepository.getApodForDate(null, this);
    }

    @Override
    public void takeView(GalleryContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        if (request!=null && !request.isDisposed()) request.dispose();
        mView = null;
    }

    @Override
    public void onGalleryItemsLoaded(GalleryItem item) {
        mView.addGalleryItem(item);
    }

    @Override
    public void onError(String error) {

    }
}
