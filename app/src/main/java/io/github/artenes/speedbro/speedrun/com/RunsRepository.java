package io.github.artenes.speedbro.speedrun.com;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.github.artenes.speedbro.speedrun.com.Contract;
import io.github.artenes.speedbro.speedrun.com.api.SpeedRunApiEndpoints;
import io.github.artenes.speedbro.speedrun.com.models.LatestRun;
import io.github.artenes.speedbro.speedrun.com.website.LatestRunsHtmlParser;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RunsRepository {

    private final OkHttpClient httpClient;
    private final Retrofit retrofit;
    private final SpeedRunApiEndpoints endpoints;

    public RunsRepository() {

        httpClient = new OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Contract.AUTHORITY)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        endpoints = retrofit.create(SpeedRunApiEndpoints.class);

    }

    public List<LatestRun> getLatestRuns() throws IOException {
        Document document = Jsoup.connect(Contract.LATEST_RUNS).get();
        LatestRunsHtmlParser parser = new LatestRunsHtmlParser();

        return parser.parseLatestRuns(document);
    }

}