package io.github.artenes.speedbro.api;

import android.support.annotation.NonNull;

import java.util.List;

import io.github.artenes.speedbro.speedrun.com.models.SearchItem;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Endpoints of the website or API
 */
public interface Endpoints {

    /**
     * Search for games or runners.
     * Between the found items there will be items
     * labeled "More..." or "No results"
     *
     * @param query the query search
     * @return a list of search items
     */
    @GET("ajax_search.php")
    Call<List<SearchItem>> search(@NonNull @Query("term") String query);

}