package io.github.artenes.speedbro.views;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import io.github.artenes.speedbro.R;
import io.github.artenes.speedbro.models.LatestRunsState;
import io.github.artenes.speedbro.models.LatestRunsViewModel;
import io.github.artenes.speedbro.models.State;
import io.github.artenes.speedbro.utils.Dependencies;

public class MainActivity extends BaseActivity implements RunsAdapter.OnRunClickListener {

    private RecyclerView mLatestRuns;
    private RunsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar mProgressBar;
    private LinearLayout mErrorMessage;

    private LatestRunsViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar(findViewById(R.id.toolbar));

        mLatestRuns = findViewById(R.id.latest_runs);
        mProgressBar = findViewById(R.id.progressBar);
        mErrorMessage = findViewById(R.id.error_message);

        mAdapter = new RunsAdapter(Dependencies.getImageLoader(), this);
        mLayoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.latest_runs_grid_columns));

        mLatestRuns.setLayoutManager(mLayoutManager);
        mLatestRuns.setAdapter(mAdapter);

        mViewModel = ViewModelProviders.of(this).get(LatestRunsViewModel.class);
        //the only thing we observe for its for changes in the state
        mViewModel.getState().observe(this, this::render);
    }

    /**
     * Receives a state and render it on the view
     *
     * @param state the state to render
     */
    @Override
    public void render(State state) {
        LatestRunsState latestRunsState = (LatestRunsState) state;

        if (latestRunsState.isLoading()) {
            mProgressBar.setVisibility(View.VISIBLE);
            mErrorMessage.setVisibility(View.GONE);
            return;
        }

        if (latestRunsState.hasError()) {
            mErrorMessage.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
            mLatestRuns.setVisibility(View.GONE);
            return;
        }

        mLatestRuns.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mErrorMessage.setVisibility(View.GONE);
        mAdapter.setData(latestRunsState.getRuns());
    }

    /**
     * This is called when the "try again" button
     * is clicked after the error message is shown
     *
     * @param view the clicked button
     */
    public void onTryAgainAfterError(View view) {
        //this starts the load, which will trigger
        //the rendering of the view when finished
        mViewModel.load();
    }

    @Override
    public void onRunClick(String gameId, String runId) {
        RunActivity.start(this, gameId, runId);
    }

    @Override
    public void onRunnerClick(String id) {
        RunnerActivity.start(this, id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        setupSearchAction(menu);
        return true;
    }

}