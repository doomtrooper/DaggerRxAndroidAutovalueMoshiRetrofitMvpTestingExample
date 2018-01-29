package com.anandp.nasaapod.ui.mainscreen;

import com.anandp.nasaapod.BaseView;
import com.anandp.nasaapod.data.GalleryItem;

/**
 * Created by Anand Parshuramka on 23/01/18.
 */

public interface GalleryContract {
    interface View extends BaseView<Presenter>{
        void setLoadingIndicator(boolean indicator);
        void addGalleryItem(GalleryItem item);
        void showError(String error);
        void showItems(boolean bool);
        void showErrorView(boolean bool);
    }

    interface Presenter extends BaseView<View>{
        void loadGalleryItems();
        void takeView(GalleryContract.View view);
        void dropView();
    }
}
