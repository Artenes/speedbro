package io.github.artenes.speedbro.tasks;

import android.os.AsyncTask;

import java.io.IOException;

import io.github.artenes.speedbro.models.RunState;
import io.github.artenes.speedbro.speedrun.com.RunsRepository;
import io.github.artenes.speedbro.speedrun.com.models.Run;

/**
 * Loads a run in the background
 * and notify the LiveData object by creating a new state object.
 */
public class LoadRunTask extends AsyncTask<String, Void, Void> {

    private final RunState mState;
    private final RunsRepository mRepository;

    public LoadRunTask(RunState state) {
        mState = state;
        mRepository = new RunsRepository();
    }

    @Override
    protected void onPreExecute() {
        //whenever the update method is called
        //any listener of this object (usually an Activity)
        //will have its UI updated
        //so always call it from the main thread
        mState.setLoadingRun(true).update();
    }

    @Override
    protected Void doInBackground(String... ids) {
        if (ids == null || ids.length < 2) {
            mState.setErrorOnRun(true);
            return null;
        }
        try {
            String gameId = ids[0];
            String runId = ids[1];
            Run run = mRepository.getRun(gameId, runId);
            mState.setRun(run);
        } catch (IOException exception) {
            mState.setErrorOnRun(true);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void noValue) {
        mState.setLoadingRun(false).update();
    }

}