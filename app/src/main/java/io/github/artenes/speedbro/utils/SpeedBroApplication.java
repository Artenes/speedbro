package io.github.artenes.speedbro.utils;

import android.app.Application;

import java.util.concurrent.TimeUnit;

import io.github.artenes.speedbro.db.Database;
import io.github.artenes.speedbro.models.CachedDocumentFetcher;
import io.github.artenes.speedbro.speedrun.com.ApiRunsRepository;
import io.github.artenes.speedbro.speedrun.com.Contract;
import io.github.artenes.speedbro.speedrun.com.HtmlRunsRepository;
import io.github.artenes.speedbro.speedrun.com.RunsRepository;
import io.github.artenes.speedbro.speedrun.com.api.ApiEndpoints;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Common dependencies used across the application
 */
public class SpeedBroApplication extends Application {

    private static Database mDatabaseInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mDatabaseInstance = Database.getDatabase(this);
    }

    public static ImageLoader getImageLoader() {
        return new ImageLoader(new ImageLoader.PicassoLoader());
    }

    public static RunsRepository getRunsRepository() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Contract.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        ApiEndpoints endpoints = retrofit.create(ApiEndpoints.class);

        return new ApiRunsRepository(endpoints);
    }

}