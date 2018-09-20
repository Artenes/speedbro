package io.github.artenes.speedbro.views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import io.github.artenes.speedbro.R;
import io.github.artenes.speedbro.models.RunState;
import io.github.artenes.speedbro.models.RunViewModel;
import io.github.artenes.speedbro.models.RunViewModelFactory;
import io.github.artenes.speedbro.models.State;
import io.github.artenes.speedbro.utils.Dependencies;

/**
 * Display a run
 */
public class RunActivity extends BaseActivity {

    private static final String EXTRA_RUN_ID = "run_id";
    private static final String EXTRA_GAME_ID = "game_id";

    /**
     * Start this activity
     *
     * @param context the context to start the activity
     * @param gameId the id of the game
     * @param runId the id of the run
     */
    public static void start(Context context, String gameId, String runId) {
        Intent intent = new Intent(context, RunActivity.class);
        intent.putExtra(EXTRA_RUN_ID, runId);
        intent.putExtra(EXTRA_GAME_ID, gameId);
        context.startActivity(intent);
    }

    private ProgressBar mProgressBar;
    private RecyclerView mContainer;
    private RunDetailsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        String runId = (String) getExtra(EXTRA_RUN_ID, "");
        String gameId = (String) getExtra(EXTRA_GAME_ID, "");

        if (runId.isEmpty() || gameId.isEmpty()) {
            finish();
        }

        mProgressBar = findViewById(R.id.progressBar);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RunDetailsAdapter(Dependencies.getImageLoader());
        mContainer = findViewById(R.id.container);
        mContainer.setLayoutManager(mLayoutManager);
        mContainer.setAdapter(mAdapter);

        RunViewModelFactory factory = new RunViewModelFactory(gameId, runId);
        RunViewModel mViewModel = ViewModelProviders.of(this, factory).get(RunViewModel.class);
        mViewModel.getState().observe(this, this::render);
    }

    @Override
    public void render(State state) {
        RunState runState = (RunState) state;

        if (runState.isLoading()) {
            mProgressBar.setVisibility(View.VISIBLE);
            mContainer.setVisibility(View.GONE);
            return;
        }

        if (runState.hasError()) {
            mContainer.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.GONE);
            return;
        }

        mAdapter.setData(runState.getRun());
        mContainer.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

}