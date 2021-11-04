package io.github.artenes.speedbro.speedrun.com.api;

import io.github.artenes.speedbro.speedrun.com.api.models.latestruns.LatestRunsResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiEndpoints {

    @GET("runs?status=new&orderby=submitted&direction=desc&embed=game,platform,category,players")
    Call<LatestRunsResponse> getLatestRuns();

}
