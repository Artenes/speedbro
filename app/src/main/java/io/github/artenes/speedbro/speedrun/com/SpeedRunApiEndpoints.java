package io.github.artenes.speedbro.speedrun.com;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SpeedRunApiEndpoints {

    public String BASE_URL = "https://www.speedrun.com/api/v1/";

    @GET("runs?status=verified&orderby=date&direction=desc&embed=game,players,category")
    Call<PagedResponse<Run>> latestRuns();

}
