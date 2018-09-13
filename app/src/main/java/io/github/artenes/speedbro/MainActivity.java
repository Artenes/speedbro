package io.github.artenes.speedbro;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.github.artenes.speedbro.speedrun.com.LatestRun;
import io.github.artenes.speedbro.speedrun.com.SpeedRunApi;

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
        mLayoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.latest_runs_grid_columns));

        mLatestRuns.setLayoutManager(mLayoutManager);
        mLatestRuns.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new LoadLatestRuns().execute();
    }

    private class LoadLatestRuns extends AsyncTask<Void, Void, List<LatestRun>> {

        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<LatestRun> doInBackground(Void... voids) {
            SpeedRunApi api = new SpeedRunApi();
            try {
                return api.getLatestRuns();
            } catch (IOException exception) {
                exception.printStackTrace();
                return new ArrayList<>();
            }

        }

        @Override
        protected void onPostExecute(List<LatestRun> runs) {
            mProgressBar.setVisibility(View.GONE);
            mAdapter.setData(runs);
        }

    }

}
