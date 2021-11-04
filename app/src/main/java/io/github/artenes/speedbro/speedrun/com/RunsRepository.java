package io.github.artenes.speedbro.speedrun.com;

import java.io.IOException;
import java.util.List;

import io.github.artenes.speedbro.speedrun.com.models.Game;
import io.github.artenes.speedbro.speedrun.com.models.Run;
import io.github.artenes.speedbro.speedrun.com.models.Runner;
import io.github.artenes.speedbro.speedrun.com.models.SearchItem;

public interface RunsRepository {
    List<Run> getLatestRuns() throws IOException;

    Run getRun(String gameId, String runId) throws IOException;

    Runner getRunner(String id) throws IOException;

    Game getGameWithoutLeaderBoards(String id) throws IOException;

    List<Run> getLeaderBoard(String gameId, String categoryId) throws IOException;

    List<SearchItem> search(String query) throws IOException;
}
