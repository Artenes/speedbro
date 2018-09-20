package io.github.artenes.speedbro.models;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

/**
 * Factory to create instance of RunViewModel
 */
public class RunViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final String mGameId;
    private final String mRunId;

    public RunViewModelFactory(String gameId, String runId) {
        mGameId = gameId;
        mRunId = runId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new RunViewModel(mGameId, mRunId);
    }

}