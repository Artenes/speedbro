package io.github.artenes.speedbro.models;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import io.github.artenes.speedbro.db.Database;

/**
 * Factory to create instance of RunViewModel
 */
public class RunViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final String mGameId;
    private final String mRunId;
    private final Database mDatabase;

    public RunViewModelFactory(String gameId, String runId, Database database) {
        mGameId = gameId;
        mRunId = runId;
        mDatabase = database;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new RunViewModel(mGameId, mRunId, mDatabase);
    }

}