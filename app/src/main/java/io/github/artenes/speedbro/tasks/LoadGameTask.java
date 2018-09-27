package io.github.artenes.speedbro.tasks;

import java.io.IOException;

import io.github.artenes.speedbro.models.DataState;
import io.github.artenes.speedbro.speedrun.com.models.Game;

/**
 * Task to load the data of a game
 */
public class LoadGameTask extends LoadDataTask<Game> {

    public LoadGameTask(DataState<Game> state) {
        super(state);
    }

    @Override
    protected void loadData(String... ids) {
        if (!hasArguments(ids)) {
            mState.setHasError(true);
            return;
        }

        String gameId = ids[0];

        try {
            Game game = mRepository.getGameWithtouLeaderboards(gameId);
            mState.setData(game).setHasError(false);
        } catch (IOException exception) {
            mState.setHasError(true);
        }
    }

}