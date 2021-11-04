package io.github.artenes.speedbro.speedrun.com.api;

import io.github.artenes.speedbro.speedrun.com.api.models.CategoryData;
import io.github.artenes.speedbro.speedrun.com.api.models.GameInfo;
import io.github.artenes.speedbro.speedrun.com.api.models.PlayerRunsData;
import io.github.artenes.speedbro.speedrun.com.api.models.RunsData;
import io.github.artenes.speedbro.speedrun.com.api.models.LeaderboardData;
import io.github.artenes.speedbro.speedrun.com.api.models.PlayerData;
import io.github.artenes.speedbro.speedrun.com.api.models.RunResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEndpoints {

    @GET("runs?status=new&orderby=submitted&direction=desc&embed=game,platform,category,players")
    Call<RunsData> getLatestRuns();

    @GET("runs/{run}?embed=game,category,players")
    Call<RunResponse> getRun(@Path("run") String run);

    @GET("games/{game}?embed=platforms")
    Call<GameInfo> getGame(@Path("game") String game);

    @GET("games/{game}/categories")
    Call<CategoryData> getCategories(@Path("game") String game);

    @GET("leaderboards/{game}/category/{category}?embed=platform,players")
    Call<LeaderboardData> getLeaderboards(@Path("game") String game, @Path("category") String category);

    @GET("users/{runner}")
    Call<PlayerData> getRunner(@Path("runner") String runner);

    @GET("runs?embed=game,category")
    Call<PlayerRunsData> getRunnerRuns(@Query("user") String runner);

}
