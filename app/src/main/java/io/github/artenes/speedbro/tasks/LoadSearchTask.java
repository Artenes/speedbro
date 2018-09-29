package io.github.artenes.speedbro.tasks;

import java.io.IOException;
import java.util.List;

import io.github.artenes.speedbro.models.DataState;
import io.github.artenes.speedbro.speedrun.com.models.SearchItem;

/**
 * Task to load the data of a search
 */
public class LoadSearchTask extends LoadDataTask<List<SearchItem>> {

    public LoadSearchTask(DataState<List<SearchItem>> state) {
        super(state);
    }

    @Override
    protected void loadData(String... queries) {
        if (!hasArguments(queries)) {
            mState.setHasError(true).setData(null);
            return;
        }

        String query = queries[0];

        try {
            List<SearchItem> results = mRepository.search(query);
            mState.setData(results).setHasError(false);
        } catch (IOException exception) {
            mState.setHasError(true).setData(null);
        }
    }

}