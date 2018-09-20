package io.github.artenes.speedbro.models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import io.github.artenes.speedbro.tasks.LoadLatestRunsTask;

/**
 * View model for latest runs
 */
public class LatestRunsViewModel extends ViewModel {

    private final MutableLiveData<State> mState = new MutableLiveData<>();

    public LatestRunsViewModel() {
        load();
    }

    public LiveData<State> getState() {
        return mState;
    }

    public void load() {
        new LoadLatestRunsTask(mState).execute();
    }

}