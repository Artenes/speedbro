package io.github.artenes.speedbro.tasks;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import java.io.IOException;

import io.github.artenes.speedbro.models.RunState;
import io.github.artenes.speedbro.models.State;
import io.github.artenes.speedbro.speedrun.com.RunsRepository;
import io.github.artenes.speedbro.speedrun.com.models.Run;

/**
 * Loads a run in the background
 * and notify the LiveData object by creating a new state object.
 */
public class LoadRunTask extends AsyncTask<String, Void, RunState> {

    private final MutableLiveData<State> mState;
    private final RunsRepository mRepository;

    public LoadRunTask(MutableLiveData<State> state) {
        mState = state;
        mRepository = new RunsRepository();
    }

    @Override
    protected void onPreExecute() {
        mState.setValue(RunState.displayLoading());
    }

    @Override
    protected RunState doInBackground(String... ids) {
        if (ids == null || ids.length < 2) {
            return RunState.displayError();
        }
        try {
            String gameId = ids[0];
            String runId = ids[1];
            Run run = mRepository.getRun(gameId, runId);
            return RunState.displayRun(run);
        } catch (IOException exception) {
            return RunState.displayError();
        }
    }

    @Override
    protected void onPostExecute(RunState state) {
        mState.setValue(state);
    }

}