package io.github.artenes.speedbro.speedrun.com;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.artenes.speedbro.speedrun.com.api.ApiEndpoints;
import io.github.artenes.speedbro.speedrun.com.api.models.CategoryData;
import io.github.artenes.speedbro.speedrun.com.api.models.Data__1;
import io.github.artenes.speedbro.speedrun.com.api.models.Data__2;
import io.github.artenes.speedbro.speedrun.com.api.models.GameSearchData;
import io.github.artenes.speedbro.speedrun.com.api.models.GameSearchResultData;
import io.github.artenes.speedbro.speedrun.com.api.models.LeaderboardData;
import io.github.artenes.speedbro.speedrun.com.api.models.LeaderboardRun;
import io.github.artenes.speedbro.speedrun.com.api.models.PlayerData;
import io.github.artenes.speedbro.speedrun.com.api.models.PlayerRun;
import io.github.artenes.speedbro.speedrun.com.api.models.PlayerRunsData;
import io.github.artenes.speedbro.speedrun.com.api.models.Run;
import io.github.artenes.speedbro.speedrun.com.api.models.Player;
import io.github.artenes.speedbro.speedrun.com.api.models.GameData;
import io.github.artenes.speedbro.speedrun.com.api.models.GameInfo;
import io.github.artenes.speedbro.speedrun.com.api.models.RunsData;
import io.github.artenes.speedbro.speedrun.com.api.models.RunResponse;
import io.github.artenes.speedbro.speedrun.com.models.Category;
import io.github.artenes.speedbro.speedrun.com.models.Game;
import io.github.artenes.speedbro.speedrun.com.models.Placement;
import io.github.artenes.speedbro.speedrun.com.models.Platform;
import io.github.artenes.speedbro.speedrun.com.models.Runner;
import io.github.artenes.speedbro.speedrun.com.models.SearchItem;
import io.github.artenes.speedbro.speedrun.com.models.SocialMedia;
import io.github.artenes.speedbro.speedrun.com.models.Video;

public class ApiRunsRepository implements RunsRepository {

    private final ApiEndpoints endpoints;

    public ApiRunsRepository(ApiEndpoints endpoints) {
        this.endpoints = endpoints;
    }

    @Override
    public List<io.github.artenes.speedbro.speedrun.com.models.Run> getLatestRuns() throws IOException {
        List<io.github.artenes.speedbro.speedrun.com.models.Run> runs = new ArrayList<>();
        RunsData response = endpoints.getLatestRuns().execute().body();

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

        PlayerData response = endpoints.getRunner(id).execute().body();
        PlayerRunsData runsResponse = endpoints.getRunnerRuns(id).execute().body();

        if (response == null || runsResponse == null) {
            return null;
        }

        Player playerData = response.data;
        List<PlayerRun> runsData = runsResponse.data;

        Runner.Builder runnerBuilder = Runner.build();
        List<SocialMedia> socialList = new ArrayList<>();
        List<io.github.artenes.speedbro.speedrun.com.models.Run> runs = new ArrayList<>();

        if (playerData.twitch != null) {
            socialList.add(new SocialMedia(Contract.socialIcon("twitch"), playerData.twitch.uri));
        }

        if (playerData.youtube != null) {
            socialList.add(new SocialMedia(Contract.socialIcon("youtube"), playerData.youtube.uri));
        }

        if (playerData.twitter != null) {
            socialList.add(new SocialMedia(Contract.socialIcon("twitter"), playerData.twitter.uri));
        }

        String icon = playerData.assets.image.uri != null ? playerData.assets.image.uri : "";


        for (PlayerRun runData : runsData) {
            runs.add(convertToRun(runData));
        }

        runnerBuilder
                .withId(playerData.id)
                .withName(playerData.names.international)
                .withSocialMedia(socialList)
                .withIcon(icon)
                .withRuns(runs);

        if (playerData.location != null) {
            String flagIcon = Contract.flagIcon(playerData.location.country.code);
            runnerBuilder.
                    withCountry(playerData.location.country.names.international)
                    .withFlag(flagIcon);
        }

        return runnerBuilder.build();
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

        List<Player> players = response.data.players.data;
        List<Data__2> platforms = response.data.platforms.data;
        List<LeaderboardRun> runsList = response.data.runs;
        List<io.github.artenes.speedbro.speedrun.com.models.Run> runs = new ArrayList<>();

        for (LeaderboardRun runData : runsList) {

            io.github.artenes.speedbro.speedrun.com.models.Run.Builder runBuilder = io.github.artenes.speedbro.speedrun.com.models.Run.build();

            Placement placement = new Placement(runData.place.toString(), "");
            String realTime = Converters.toReadableTime(runData.run.times.realtime_t);
            String inGameTime = Converters.toReadableTime(runData.run.times.ingame_t);

            runBuilder
                    .withId(runData.run.id)
                    .withPlacement(placement)
                    .withTime(realTime)
                    .withInGameTime(inGameTime)
                    .withDate(runData.run.date);

            //get player
            if (runData.run.players != null && !runData.run.players.isEmpty()) {
                String playerId = runData.run.players.get(0).id;
                Player playerData = null;
                for (Player p : players) {
                    if (p.id != null && p.id.equals(playerId)) {
                        playerData = p;
                        break;
                    }
                }

                if (playerData != null) {
                    List<Runner> runners = convertToRunners(Collections.singletonList(playerData), true);
                    runBuilder.withRunners(runners);
                }
            }

            //get platform
            if (runData.run.system != null) {

                String platformId = runData.run.system.platform;
                Data__2 platform = null;
                for (Data__2 p : platforms) {
                    if (p.id != null && p.id.equals(platformId)) {
                        platform = p;
                        break;
                    }
                }

                if (platform != null) {
                    runBuilder.withPlatform(new Platform(platform.name, "", ""));
                }
            }

            runs.add(runBuilder.build());

        }

        return runs;
    }

    @Override
    public List<SearchItem> search(String query) throws IOException {
        List<SearchItem> results = new ArrayList<>();
        GameSearchData response = endpoints.searchGames(query).execute().body();

        if (response == null) {
            return results;
        }

        for (GameSearchResultData item : response.data) {
            SearchItem searchItem = SearchItem.makeGameItem(item.names.international, item.id);
            results.add(searchItem);
        }

        return results;
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

    private io.github.artenes.speedbro.speedrun.com.models.Run convertToRun(PlayerRun runData) {

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

        return runBuilder.build();

    }

    private List<Runner> convertToRunners(List<Player> runnersList, boolean withDetails) {

        if (runnersList == null || runnersList.isEmpty()) {
            return Collections.emptyList();
        }

        Player runnerData = runnersList.get(0);

        Runner.Builder runnerBuilder = Runner.build()
                .withId(runnerData.id);

        if (withDetails) {
            if (runnerData.assets != null && runnerData.assets.image != null) {
                runnerBuilder.withIcon(runnerData.assets.image.uri != null ? runnerData.assets.image.uri : "");
            }
            if (runnerData.names != null) {
                runnerBuilder.withName(runnerData.names.international);
            }
        }

        if (runnerData.location != null) {

            String flagUrl = Contract.flagIcon(runnerData.location.country.code);
            runnerBuilder.withFlag(flagUrl);
            runnerBuilder.withCountry(runnerData.location.country.names.international);

        }

        return Collections.singletonList(runnerBuilder.build());

    }

}
