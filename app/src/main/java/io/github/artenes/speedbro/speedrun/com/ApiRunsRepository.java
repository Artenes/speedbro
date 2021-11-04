package io.github.artenes.speedbro.speedrun.com;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.artenes.speedbro.speedrun.com.api.ApiEndpoints;
import io.github.artenes.speedbro.speedrun.com.api.models.CategoryData;
import io.github.artenes.speedbro.speedrun.com.api.models.Data__1;
import io.github.artenes.speedbro.speedrun.com.api.models.LeaderboardData;
import io.github.artenes.speedbro.speedrun.com.api.models.LeaderboardRun;
import io.github.artenes.speedbro.speedrun.com.api.models.Run;
import io.github.artenes.speedbro.speedrun.com.api.models.Datum__1;
import io.github.artenes.speedbro.speedrun.com.api.models.GameData;
import io.github.artenes.speedbro.speedrun.com.api.models.GameInfo;
import io.github.artenes.speedbro.speedrun.com.api.models.LatestRunsResponse;
import io.github.artenes.speedbro.speedrun.com.api.models.RunResponse;
import io.github.artenes.speedbro.speedrun.com.models.Category;
import io.github.artenes.speedbro.speedrun.com.models.Game;
import io.github.artenes.speedbro.speedrun.com.models.Placement;
import io.github.artenes.speedbro.speedrun.com.models.Runner;
import io.github.artenes.speedbro.speedrun.com.models.SearchItem;
import io.github.artenes.speedbro.speedrun.com.models.Video;

public class ApiRunsRepository implements RunsRepository {

    private final ApiEndpoints endpoints;

    public ApiRunsRepository(ApiEndpoints endpoints) {
        this.endpoints = endpoints;
    }

    @Override
    public List<io.github.artenes.speedbro.speedrun.com.models.Run> getLatestRuns() throws IOException {
        List<io.github.artenes.speedbro.speedrun.com.models.Run> runs = new ArrayList<>();
        LatestRunsResponse response = endpoints.getLatestRuns().execute().body();

        if (response == null) {
            return runs;
        }

        for (Run runData : response.data) {

            runs.add(convertToRun(runData));

        }

        return runs;
    }

    @Override
    public io.github.artenes.speedbro.speedrun.com.models.Run getRun(String gameId, String runId) throws IOException {

        RunResponse response = endpoints.getRun(runId).execute().body();

        if (response == null) {
            return null;
        }

        return convertToRun(response.data);

    }

    @Override
    public Runner getRunner(String id) throws IOException {
        return null;
    }

    @Override
    public Game getGameWithoutLeaderBoards(String id) throws IOException {

        GameInfo response = endpoints.getGame(id).execute().body();
        CategoryData categoryResponse = endpoints.getCategories(id).execute().body();

        if (response == null || categoryResponse == null) {
            return null;
        }

        GameData gameData = response.data;

        String platforms = Converters.toSimplePlatformList(gameData.platforms.data);
        List<Category> categories = convertToCategories(categoryResponse.data);

        Game.Builder gameBuilder = Game.build()
                .withId(gameData.id)
                .withCover(gameData.assets.coverMedium.uri)
                .withTitle(gameData.names.international)
                .withYear(gameData.released.toString())
                .withPlatforms(platforms)
                .withCategories(categories);

        return gameBuilder.build();

    }

    @Override
    public List<io.github.artenes.speedbro.speedrun.com.models.Run> getLeaderBoard(String gameId, String categoryId) throws IOException {

        LeaderboardData response = endpoints.getLeaderboards(gameId, categoryId).execute().body();

        if (response == null) {
            return null;
        }

        List<LeaderboardRun> runsList = response.data.runs;
        List<io.github.artenes.speedbro.speedrun.com.models.Run> runs = new ArrayList<>();

        for (LeaderboardRun runData : runsList) {

            io.github.artenes.speedbro.speedrun.com.models.Run.Builder runBuilder = io.github.artenes.speedbro.speedrun.com.models.Run.build();

            List<Runner> runners = convertToRunners(runData.run.players, false);
            Placement placement = new Placement(runData.place.toString(), "");
            String realTime = Converters.toReadableTime(runData.run.times.realtime_t);
            String inGameTime = Converters.toReadableTime(runData.run.times.ingame_t);

            runBuilder
                    .withRunners(runners)
                    .withPlacement(placement)
                    .withTime(realTime)
                    .withInGameTime(inGameTime)
                    .withDate(runData.run.date);

            runs.add(runBuilder.build());

        }

        return runs;
    }

    @Override
    public List<SearchItem> search(String query) throws IOException {
        return null;
    }

    private List<Category> convertToCategories(List<Data__1> categoriesList) {

        List<Category> categories = new ArrayList<>();

        if (categoriesList == null || categoriesList.isEmpty()) {
            return categories;
        }

        for (Data__1 categoryData : categoriesList) {

            Category category = new Category(
                    categoryData.id,
                    categoryData.name
            );

            categories.add(category);

        }

        return categories;
    }

    private io.github.artenes.speedbro.speedrun.com.models.Run convertToRun(Run runData) {

        Game game = Game.build()
                .withId(runData.game.data.id)
                .withCover(runData.game.data.assets.coverMedium.uri)
                .withTitle(runData.game.data.names.international)
                .build();

        String time = Converters.toReadableTime(runData.times.primary_t);

        io.github.artenes.speedbro.speedrun.com.models.Run.Builder runBuilder = io.github.artenes.speedbro.speedrun.com.models.Run.build()
                .withId(runData.id)
                .withGame(game)
                .withCommentary(runData.comment)
                .withCategory(runData.category.data.name)
                .withTime(time);

        if (runData.videos != null && runData.videos.links != null && !runData.videos.links.isEmpty()) {
            runBuilder.withVideo(new Video(runData.videos.links.get(0).uri));
        }

        runBuilder.withRunners(convertToRunners(runData.players.data, true));

        return runBuilder.build();

    }

    private List<Runner> convertToRunners(List<Datum__1> runnersList, boolean withDetails) {

        if (runnersList == null || runnersList.isEmpty()) {
            return Collections.emptyList();
        }

        Datum__1 runnerData = runnersList.get(0);

        Runner.Builder runnerBuilder = Runner.build()
                .withId(runnerData.id);

        if (withDetails) {
            runnerBuilder
                    .withIcon(runnerData.assets.image.uri != null ? runnerData.assets.image.uri : "")
                    .withName(runnerData.names.international);
        }


        if (runnerData.location != null) {

            String flagUrl = Contract.flagIcon(runnerData.location.country.code);
            runnerBuilder.withFlag(flagUrl);

        }

        return Collections.singletonList(runnerBuilder.build());

    }

}
