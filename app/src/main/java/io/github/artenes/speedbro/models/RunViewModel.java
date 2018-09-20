package io.github.artenes.speedbro.models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import io.github.artenes.speedbro.tasks.LoadRunTask;

/**
 * View model for a run
 */
public class RunViewModel extends ViewModel {

    private final String mGameId;
    private final String mRunId;
    private final MutableLiveData<State> mState = new MutableLiveData<>();

    RunViewModel(String gameId, String runId) {
        mRunId = runId;
        mGameId = gameId;
        load();
    }

    public LiveData<State> getState() {
        return mState;
    }

    private void load() {
        new LoadRunTask(mState).execute(mGameId, mRunId);
    }

}