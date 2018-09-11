package io.github.artenes.speedbro.speedrun.com;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpeedRunApi {

    private final OkHttpClient httpClient;
    private final Retrofit retrofit;
    private final SpeedRunApiEndpoints endpoints;

    public SpeedRunApi() {

        httpClient = new OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(SpeedRunApiEndpoints.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        endpoints = retrofit.create(SpeedRunApiEndpoints.class);

    }

    public Call<PagedResponse<Run>> latestRuns() {

        return endpoints.latestRuns();

    }

}
