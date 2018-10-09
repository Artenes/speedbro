package io.github.artenes.speedbro.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

@Dao
public interface CachedRequestDAO {

    @Query("SELECT * FROM CachedResponse WHERE url = :url")
    CachedResponse getCachedRequest(String url);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CachedResponse request);

    @Delete
    void remove(CachedResponse request);

}