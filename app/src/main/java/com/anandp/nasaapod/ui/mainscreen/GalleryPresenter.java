package com.anandp.nasaapod.ui.mainscreen;

import com.anandp.nasaapod.data.model.GalleryItem;
import com.anandp.nasaapod.data.Repository;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by Anand Parshuramka on 23/01/18.
 */

public class GalleryPresenter implements GalleryContract.Presenter, Repository.LoadApodCallback {

    private GalleryContract.View mView;
    private Repository mRepository;
    private Disposable request;

    public GalleryPresenter(Repository mRepository) {
        this.mRepository = mRepository;
    }

    @Override
    public void loadGalleryItems() {
        request = mRepository.getApodForMonth(null, this);
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
        mView.showErrorView(false);
        mView.setLoadingIndicator(false);
        mView.showItems(true);
        mView.addGalleryItem(item);
    }

    @Override
    public void onGalleryItemsLoaded(List<GalleryItem> items) {
        mView.showErrorView(false);
        mView.setLoadingIndicator(false);
        mView.showItems(true);
        mView.addGalleryItem(items);
    }

    @Override
    public void onError(String error) {
        mView.showError(error);
    }
}
