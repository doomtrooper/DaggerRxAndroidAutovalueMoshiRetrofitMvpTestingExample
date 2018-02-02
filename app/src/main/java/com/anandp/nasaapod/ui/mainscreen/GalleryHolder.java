package com.anandp.nasaapod.ui.mainscreen;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.anandp.nasaapod.NasaApodApp;
import com.anandp.nasaapod.R;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Anand Parshuramka on 25/01/18.
 */

public class GalleryHolder extends RecyclerView.ViewHolder {
    @Inject
    Picasso picasso;
    @BindView(R.id.row_image)
    ImageView imageView;
    public GalleryHolder(View itemView) {
        super(itemView);
        NasaApodApp.getAppContext().getRootComponent().galleryBuilder().build().inject(this);
        ButterKnife.bind(this, itemView);
    }

    void setImageView(String url){
        picasso.load(url).into(imageView);
    }
}
