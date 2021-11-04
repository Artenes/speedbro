package io.github.artenes.speedbro.utils;

import android.app.Application;

import io.github.artenes.speedbro.db.Database;
import io.github.artenes.speedbro.models.CachedDocumentFetcher;
import io.github.artenes.speedbro.speedrun.com.HtmlRunsRepository;
import io.github.artenes.speedbro.speedrun.com.RunsRepository;

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
        return new HtmlRunsRepository(new CachedDocumentFetcher(mDatabaseInstance));
    }

}