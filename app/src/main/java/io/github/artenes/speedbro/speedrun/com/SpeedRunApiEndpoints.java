package io.github.artenes.speedbro.speedrun.com;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SpeedRunApiEndpoints {

    public String BASE_URL = "https://www.speedrun.com/";
    public String BASE_API_URL = BASE_URL + "api/v1/";
    public String LATEST_RUNS = BASE_URL + "ajax_latestleaderboard.php?amount=40";

    @GET("runs?status=verified&orderby=date&direction=desc&embed=game,players,category")
    Call<PagedResponse<Run>> latestRuns();

}
