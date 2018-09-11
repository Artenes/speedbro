package io.github.artenes.speedbro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import io.github.artenes.speedbro.speedrun.com.PagedResponse;
import io.github.artenes.speedbro.speedrun.com.Run;
import io.github.artenes.speedbro.speedrun.com.SpeedRunApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mLatestRuns;
    private LatestRunsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLatestRuns = findViewById(R.id.latest_runs);
        mProgressBar = findViewById(R.id.progressBar);

        mAdapter = new LatestRunsAdapter();
        mLayoutManager = new LinearLayoutManager(this);


        mLatestRuns.setLayoutManager(mLayoutManager);
        mLatestRuns.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SpeedRunApi api = new SpeedRunApi();
        api.latestRuns().enqueue(new Callback<PagedResponse<Run>>() {
            @Override
            public void onResponse(Call<PagedResponse<Run>> call, Response<PagedResponse<Run>> response) {
                Log.d(TAG, response.raw().toString());
                mAdapter.setData(response.body().getData());
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<PagedResponse<Run>> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });

    }

}
