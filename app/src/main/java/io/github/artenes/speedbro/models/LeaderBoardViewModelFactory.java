package io.github.artenes.speedbro.models;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

/**
 * Factory to create instance of LeaderBoardViewModel
 */
public class LeaderBoardViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final String mGameId;
    private final String mCategoryId;

    public LeaderBoardViewModelFactory(String gameId, String categoryId) {
        mGameId = gameId;
        mCategoryId = categoryId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new LeaderBoardViewModel(mGameId, mCategoryId);
    }

}