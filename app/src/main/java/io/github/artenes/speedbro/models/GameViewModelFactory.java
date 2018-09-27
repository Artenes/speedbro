package io.github.artenes.speedbro.models;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

/**
 * Factory to create instance of GameViewModel
 */
public class GameViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final String mGameId;

    public GameViewModelFactory(String gameId) {
        mGameId = gameId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new GameViewModel(mGameId);
    }

}