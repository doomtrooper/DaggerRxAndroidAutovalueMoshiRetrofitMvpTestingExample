package com.anandp.nasaapod.di;

import android.app.Application;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.framework.FrameworkSQLiteOpenHelperFactory;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.anandp.nasaapod.ApiService;
import com.anandp.nasaapod.data.local.DBCallback;
import com.anandp.nasaapod.utils.Constants;
import com.anandp.nasaapod.utils.MyAdapterFactory;
import com.squareup.moshi.Moshi;
import com.squareup.picasso.Picasso;
import com.squareup.sqlbrite3.BriteDatabase;
import com.squareup.sqlbrite3.SqlBrite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by Anand Parshuramka on 23/01/18.
 */
@Module
public class RootModule {

    // Dagger will only look for methods annotated with @Provides
    @Singleton
    @Provides
    Picasso providePicasso(Application application){
        return Picasso.with(application);
    }

    
    @Provides
    @Singleton
    // Application reference must come from AppModule.class
    SharedPreferences providesSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    Cache provideOkHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    Moshi provideMoshi() {
        return new Moshi.Builder()
                .add(MyAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(logging);
        client.cache(cache);
        return client.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Moshi moshi, OkHttpClient okHttpClient, String baseUrl) {
        return new Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    ApiService provideApiService(Retrofit retrofit){
        return retrofit.create(ApiService.class);
    }

    @Provides
    @Singleton
    SqlBrite provideSqlBrite() {
        return new SqlBrite.Builder()
                .logger(message -> Log.v("Database",message))
                .build();
    }

    @Provides
    @Singleton
    BriteDatabase provideDatabase(SqlBrite sqlBrite, Application application) {
        SupportSQLiteOpenHelper.Configuration configuration = SupportSQLiteOpenHelper.Configuration.builder(application)
                .name(Constants.DB_NAME)
                .callback(new DBCallback())
                .build();
        SupportSQLiteOpenHelper.Factory factory = new FrameworkSQLiteOpenHelperFactory();
        SupportSQLiteOpenHelper helper = factory.create(configuration);
        BriteDatabase db = sqlBrite.wrapDatabaseHelper(helper, Schedulers.io());
        db.setLoggingEnabled(true);
        return db;
    }

}
