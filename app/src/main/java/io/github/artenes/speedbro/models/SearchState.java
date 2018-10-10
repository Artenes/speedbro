package io.github.artenes.speedbro.models;

import java.util.List;

import io.github.artenes.speedbro.speedrun.com.models.SearchItem;

/**
 * State for the search view
 */
@SuppressWarnings("UnusedReturnValue")
public class SearchState extends DataState<List<SearchItem>> {

    private String query;

    public String getQuery() {
        return query;
    }

    public SearchState setQuery(String query) {
        this.query = query;
        return this;
    }

}