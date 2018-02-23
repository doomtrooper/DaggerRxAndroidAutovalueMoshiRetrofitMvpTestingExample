package com.anandp.nasaapod.ui.mainscreen;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anandp.nasaapod.R;
import com.anandp.nasaapod.data.model.GalleryItem;
import com.anandp.nasaapod.exceptions.SameElementInsertedException;
import com.anandp.nasaapod.utils.Utility;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Anand Parshuramka on 24/01/18.
 */

public class GalleryRecyclerAdapter extends RecyclerView.Adapter<GalleryHolder>{

    public static String TAG = GalleryRecyclerAdapter.class.getSimpleName();

    //list should be sorted at all times
    private List<GalleryItem> mGalleryItems;

    GalleryRecyclerAdapter(@NonNull List<GalleryItem> mGalleryItems) {
        Collections.sort(mGalleryItems, (item1, item2) -> Utility.comapreDates(item1.date(), item2.date()));
        this.mGalleryItems = mGalleryItems;
    }

    @Override
    public GalleryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext().getApplicationContext()).inflate(R.layout.grid_item_layout, parent, false);
        return new GalleryHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryHolder holder, int position) {
        holder.setImageView(mGalleryItems.get(position).url());
    }

    @Override
    public int getItemCount() {
        return mGalleryItems.size();
    }

    void addGalleryItems(GalleryItem galleryItem){
        synchronized (this){
            Observable.create((ObservableOnSubscribe<Integer>) e -> {
                if (mGalleryItems == null) e.onError(new IllegalStateException("List is NULL"));
                if (mGalleryItems.size() == 0) e.onNext(0);
                else {
                    int i;
                    for (i = 0; i < mGalleryItems.size(); i++) {
                        if (mGalleryItems.get(i).equals(galleryItem)) e.onError(new SameElementInsertedException("Element Already present!!!"));
                        if (Utility.comapreDates(mGalleryItems.get(i).date(), galleryItem.date()) < 0)
                            break;
                    }
                    e.onNext(i);
                }
                e.onComplete();
            })
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(position -> {
                        mGalleryItems.add(position, galleryItem);
                        notifyItemInserted(position);
                    }, e -> Log.e(TAG, e.getMessage()));
        }
    }

    void addGalleryItems(List<GalleryItem> items) {
        for (GalleryItem item:items){
            mGalleryItems.add(item);
            notifyItemInserted(mGalleryItems.size()-1);
        }
    }
}
