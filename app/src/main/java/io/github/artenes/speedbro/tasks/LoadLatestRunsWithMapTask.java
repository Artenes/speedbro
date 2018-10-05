package io.github.artenes.speedbro.tasks;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;

import io.github.artenes.speedbro.models.RunsListWithMapState;
import io.github.artenes.speedbro.speedrun.com.RunsRepository;
import io.github.artenes.speedbro.speedrun.com.models.Run;

/**
 * Loads a list of latest runs in the background
 * and notify the LiveData object by creating a new state object.
 */
public class LoadLatestRunsWithMapTask extends AsyncTask<Void, Void, Void> {

    private final RunsListWithMapState mState;
    private final RunsRepository mRepository;

    public LoadLatestRunsWithMapTask(RunsListWithMapState state) {
        mState = state;
        mRepository = new RunsRepository();
    }

    @Override
    protected void onPreExecute() {
        mState.setLoading(true).update();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            List<Run> latestRuns = mRepository.getLatestRuns();
            mState.setData(latestRuns).setHasError(false);
        } catch (IOException exception) {
            mState.setHasError(true);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void nothing) {
        mState.setLoading(false).update();
    }

}