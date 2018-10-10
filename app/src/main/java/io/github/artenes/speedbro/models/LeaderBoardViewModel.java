package io.github.artenes.speedbro.models;

import java.util.List;

import io.github.artenes.speedbro.speedrun.com.models.Run;
import io.github.artenes.speedbro.tasks.LoadLeaderBoardTask;

/**
 * ViewModel of a leader board
 */
public class LeaderBoardViewModel extends DataViewModel<List<Run>> {

    private final String mLeaderBoardUrl;

    public LeaderBoardViewModel(String leaderBoardUrl) {
        mLeaderBoardUrl = leaderBoardUrl;
    }

    @Override
    protected void runLoadTask() {
        new LoadLeaderBoardTask(mData).execute(mLeaderBoardUrl);
    }

}