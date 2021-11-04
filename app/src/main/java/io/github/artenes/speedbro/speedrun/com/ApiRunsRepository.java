package io.github.artenes.speedbro.speedrun.com;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.artenes.speedbro.speedrun.com.api.ApiEndpoints;
import io.github.artenes.speedbro.speedrun.com.api.models.latestruns.Datum;
import io.github.artenes.speedbro.speedrun.com.api.models.latestruns.Datum__1;
import io.github.artenes.speedbro.speedrun.com.api.models.latestruns.LatestRunsResponse;
import io.github.artenes.speedbro.speedrun.com.models.Game;
import io.github.artenes.speedbro.speedrun.com.models.Run;
import io.github.artenes.speedbro.speedrun.com.models.Runner;
import io.github.artenes.speedbro.speedrun.com.models.SearchItem;

public class ApiRunsRepository implements RunsRepository {

    private final ApiEndpoints endpoints;

    public ApiRunsRepository(ApiEndpoints endpoints) {
        this.endpoints = endpoints;
    }

    @Override
    public List<Run> getLatestRuns() throws IOException {
        List<Run> runs = new ArrayList<>();
        LatestRunsResponse response = endpoints.getLatestRuns().execute().body();

        if (response == null) {
            return runs;
        }

        for (Datum runData : response.data) {

            Game game = Game.build()
                    .withId(runData.game.data.id)
                    .withCover(runData.game.data.assets.coverMedium.uri)
                    .withTitle(runData.game.data.names.international)
                    .build();

            String time = Converters.toReadableTime(runData.times.primary_t);

            Run.Builder runBuilder = Run.build()
                    .withId(runData.id)
                    .withGame(game)
                    .withCategory(runData.category.data.name)
                    .withTime(time);

            if (!runData.players.data.isEmpty()) {

                Datum__1 runnerData = runData.players.data.get(0);

                Runner.Builder runnerBuilder = Runner.build()
                        .withId(runnerData.id)
                        .withIcon(runnerData.assets.image.uri != null ? runnerData.assets.image.uri : "")
                        .withName(runnerData.names.international);

                if (runnerData.location != null) {

                    String flagUrl = Contract.flagIcon(runnerData.location.country.code);
                    runnerBuilder.withFlag(flagUrl);

                }

                runBuilder.withRunners(Collections.singletonList(runnerBuilder.build()));

            }

            runs.add(runBuilder.build());

        }

        return runs;
    }

    @Override
    public Run getRun(String gameId, String runId) throws IOException {
        return null;
    }

    @Override
    public Runner getRunner(String id) throws IOException {
        return null;
    }

    @Override
    public Game getGameWithoutLeaderBoards(String id) throws IOException {
        return null;
    }

    @Override
    public List<Run> getLeaderBoardDirectly(String url) throws IOException {
        return null;
    }

    @Override
    public List<SearchItem> search(String query) throws IOException {
        return null;
    }
}
