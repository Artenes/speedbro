package io.github.artenes.speedbro.models;

import io.github.artenes.speedbro.speedrun.com.models.Game;
import io.github.artenes.speedbro.tasks.LoadGameTask;

/**
 * ViewModel of a game
 */
public class GameViewModel extends DataViewModel<Game> {

    private final String mGameId;

    public GameViewModel(String gameId) {
        mGameId = gameId;
    }

    @Override
    protected void runLoadTask() {
        new LoadGameTask(mData).execute(mGameId);
    }

}