package io.github.artenes.speedbro.speedrun.com.api;

import io.github.artenes.speedbro.speedrun.com.models.PagedResponse;
import io.github.artenes.speedbro.speedrun.com.models.Run;
import retrofit2.Call;
import retrofit2.http.GET;

public interface SpeedRunApiEndpoints {

    @GET("runs?status=verified&orderby=date&direction=desc&embed=game,players,category")
    Call<PagedResponse<Run>> latestRuns();

}
