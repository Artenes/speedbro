package io.github.artenes.speedbro.tasks;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;

import io.github.artenes.speedbro.models.LatestRunsState;
import io.github.artenes.speedbro.models.State;
import io.github.artenes.speedbro.speedrun.com.RunsRepository;
import io.github.artenes.speedbro.speedrun.com.models.LatestRun;

/**
 * Loads a list of latest runs in the background
 * and notify the LiveData object by creating a new state object.
 */
public class LoadLatestRunsTask extends AsyncTask<Void, Void, LatestRunsState> {

    private final MutableLiveData<State> mState;
    private final RunsRepository mRepository;

    public LoadLatestRunsTask(MutableLiveData<State> state) {
        mState = state;
        mRepository = new RunsRepository();
    }

    @Override
    protected void onPreExecute() {
        mState.setValue(LatestRunsState.displayLoading());
    }

    @Override
    protected LatestRunsState doInBackground(Void... voids) {
        try {
            List<LatestRun> latestRuns = mRepository.getLatestRuns();
            return LatestRunsState.displayRuns(latestRuns);
        } catch (IOException exception) {
            return LatestRunsState.displayError();
        }
    }

    @Override
    protected void onPostExecute(LatestRunsState latestRunsState) {
        mState.setValue(latestRunsState);
    }

}