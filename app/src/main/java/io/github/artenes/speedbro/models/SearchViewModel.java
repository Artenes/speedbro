package io.github.artenes.speedbro.models;

import android.support.annotation.NonNull;

import java.util.List;

import io.github.artenes.speedbro.speedrun.com.models.SearchItem;
import io.github.artenes.speedbro.tasks.LoadSearchTask;

/**
 * ViewModel of a search
 */
public class SearchViewModel extends DataViewModel<List<SearchItem>> {

    private String mQuery = "";

    public SearchViewModel() {
        mData = new SearchState();
    }

    @Override
    protected void runLoadTask() {
        ((SearchState) mData).setQuery(mQuery);
        new LoadSearchTask(mData).execute(mQuery);
    }

    /**
     * Used for the firs search when the activity is created
     *
     * @param query the query string
     */
    public void searchIfEmpty(@NonNull String query) {
        if (!mQuery.isEmpty()) {
            return;
        }
        mQuery = query;
        runLoadTask();
    }

    /**
     * Used to search after the first creation of the activity
     *
     * @param query the query string
     */
    public void search(@NonNull String query) {
        if (query.isEmpty() || mQuery.equalsIgnoreCase(query)) {
            return;
        }
        mQuery = query;
        runLoadTask();
    }

}