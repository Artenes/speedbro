package io.github.artenes.speedbro.tasks;

import java.io.IOException;
import java.util.List;

import io.github.artenes.speedbro.models.DataState;
import io.github.artenes.speedbro.speedrun.com.models.Run;

/**
 * Task to load the data of a runner
 */
public class LoadLeaderBoardTask extends LoadDataTask<List<Run>> {

    public LoadLeaderBoardTask(DataState<List<Run>> state) {
        super(state);
    }

    @Override
    protected void loadData(String... ids) {
        if (!hasArguments(ids)) {
            mState.setHasError(true);
            return;
        }

        String gameId = ids[0];
        String categoryId = ids[1];

        try {
            List<Run> runs = mRepository.getLeaderBoard(gameId, categoryId);
            mState.setData(runs).setHasError(false);
        } catch (IOException exception) {
            mState.setHasError(true);
        }
    }

}