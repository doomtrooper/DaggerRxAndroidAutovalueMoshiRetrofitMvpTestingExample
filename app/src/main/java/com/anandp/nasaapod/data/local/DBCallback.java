package com.anandp.nasaapod.data.local;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;

import com.anandp.nasaapod.data.model.GalleryItem;

/**
 * Created by Anand Parshuramka on 15/02/18.
 */

public class DBCallback extends SupportSQLiteOpenHelper.Callback {

    public static final int VERSION = 1;

    public DBCallback() {
        this(VERSION);
    }

    /**
     * Creates a new Callback to get database lifecycle events.
     *
     * @param version The version for the database instance. See {@link #version}.
     */
    public DBCallback(int version) {
        super(version);
    }


    @Override
    public void onCreate(SupportSQLiteDatabase db) {
        db.execSQL(GalleryItem.CREATE_GALLERY_ITEM);
    }

    @Override
    public void onUpgrade(SupportSQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
