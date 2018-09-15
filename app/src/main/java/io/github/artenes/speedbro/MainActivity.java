package io.github.artenes.speedbro;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import io.github.artenes.speedbro.models.LatestRunsState;
import io.github.artenes.speedbro.models.LatestRunsViewModel;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mLatestRuns;
    private LatestRunsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar mProgressBar;
    private LinearLayout mErrorMessage;

    private LatestRunsViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLatestRuns = findViewById(R.id.latest_runs);
        mProgressBar = findViewById(R.id.progressBar);
        mErrorMessage = findViewById(R.id.error_message);

        mAdapter = new LatestRunsAdapter();
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
    public void render(LatestRunsState state) {
        if (state.isLoading()) {
            mProgressBar.setVisibility(View.VISIBLE);
            mErrorMessage.setVisibility(View.GONE);
            return;
        }

        if (state.hasError()) {
            mErrorMessage.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
            mLatestRuns.setVisibility(View.GONE);
            return;
        }

        mLatestRuns.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mErrorMessage.setVisibility(View.GONE);
        mAdapter.setData(state.getRuns());
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

}