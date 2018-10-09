package io.github.artenes.speedbro.db;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Local database to store favorite runs and cached data
 */
@android.arch.persistence.room.Database(entities = {FavoriteRun.class, CachedResponse.class}, version = 3, exportSchema = false)
public abstract class Database extends RoomDatabase {

    private static final String DATABASE_NAME = "speedbro.db";
    private static Database database;

    public static Database getDatabase(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context, Database.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
        }
        return database;
    }

    public abstract FavoriteRunDao favoriteRunDao();

    public abstract CachedRequestDAO cachedRequestDAO();

}