package io.github.artenes.speedbro.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * DAO to fetch favorite runs from the database
 */
@Dao
public interface FavoriteRunDao {

    @Query("SELECT * FROM favoriterun")
    List<FavoriteRun> getAll();

    @Query("SELECT * FROM favoriterun WHERE id = :id")
    FavoriteRun getRun(String id);

    @Insert
    void insert(FavoriteRun favoriteRun);

    @Delete
    void delete(FavoriteRun favoriteRun);

}