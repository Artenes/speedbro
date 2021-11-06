package io.github.artenes.speedbro.speedrun.com;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.github.artenes.speedbro.speedrun.com.api.ApiEndpoints;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRunsRepositoryTest {

    private ApiEndpoints endpoints;
    private ApiRunsRepository apiRunsRepository;

    @Before
    public void setUp() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Contract.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        endpoints = retrofit.create(ApiEndpoints.class);

        apiRunsRepository = new ApiRunsRepository(endpoints);
    }

    @Test
    public void getLatestRuns_returnLatestRuns() throws IOException {

//        String time = "123";
//        String part = time.split("\\.")[0];
//        assertEquals("123", part);

    }

}