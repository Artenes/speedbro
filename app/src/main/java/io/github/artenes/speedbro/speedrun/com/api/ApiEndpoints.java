package io.github.artenes.speedbro.speedrun.com.api;

import io.github.artenes.speedbro.speedrun.com.api.models.LatestRunsResponse;
import io.github.artenes.speedbro.speedrun.com.api.models.RunResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiEndpoints {

    @GET("runs?status=new&orderby=submitted&direction=desc&embed=game,platform,category,players")
    Call<LatestRunsResponse> getLatestRuns();

    @GET("runs/{run}?embed=game,category,players")
    Call<RunResponse> getRun(@Path("run") String run);

}
