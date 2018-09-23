package io.github.artenes.speedbro.models;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

/**
 * Factory to create instance of RunnerViewModel
 */
public class RunnerViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final String mRunnerId;

    public RunnerViewModelFactory(String runnerId) {
        mRunnerId = runnerId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new RunnerViewModel(mRunnerId);
    }

}