package io.github.artenes.speedbro.views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import io.github.artenes.speedbro.R;
import io.github.artenes.speedbro.models.DataState;
import io.github.artenes.speedbro.models.RunnerViewModel;
import io.github.artenes.speedbro.models.RunnerViewModelFactory;
import io.github.artenes.speedbro.models.State;
import io.github.artenes.speedbro.speedrun.com.models.Runner;
import io.github.artenes.speedbro.utils.SpeedBroApplication;
import io.github.artenes.speedbro.utils.ImageLoader;

/**
 * The details of a runner
 */
public class RunnerActivity extends BaseActivity implements RunsAdapter.OnRunClickListener {

    private static final String EXTRA_RUNNER_ID = "extra_runner_id";

    public static void start(@NonNull Context context, @NonNull String runnerId) {
        //it is possible for a runner id to be empty
        if (runnerId.isEmpty()) {
            return;
        }
        Intent intent = new Intent(context, RunnerActivity.class);
        intent.putExtra(EXTRA_RUNNER_ID, runnerId);
        context.startActivity(intent);
    }

    private RunnerViewModel mViewModel;
    private ImageLoader mImageLoader;

    private ImageView mRunnerImage;
    private TextView mRunnerName;
    private TextView mRunnerCountry;
    private ImageView mRunnerFlag;
    private SocialLinksAdapter mSocialLinksAdapter;
    private RunsAdapter mRunsAdapter;
    private RecyclerView mRunsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runner);

        String runnerId = (String) getExtra(EXTRA_RUNNER_ID, "");

        if (runnerId.isEmpty()) {
            finish();
        }

        mImageLoader = SpeedBroApplication.getImageLoader();

        initializeBaseView();
        mRunnerImage = findViewById(R.id.runner_image);
        mRunnerName = findViewById(R.id.runner_name);
        mRunnerCountry = findViewById(R.id.runner_country);
        mRunnerFlag = findViewById(R.id.runner_flag);

        RecyclerView socialLinksRecyclerView = findViewById(R.id.runner_social_links);
        GridLayoutManager socialLinksLayoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.runner_social_links_grid_columns));
        mSocialLinksAdapter = new SocialLinksAdapter(mImageLoader);

        mRunsList = findViewById(R.id.runner_runs);
        GridLayoutManager runsLayoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.runner_runs_grid_columns));
        mRunsAdapter = new RunsAdapter(mImageLoader, this);

        socialLinksRecyclerView.setLayoutManager(socialLinksLayoutManager);
        socialLinksRecyclerView.setAdapter(mSocialLinksAdapter);

        mRunsList.setLayoutManager(runsLayoutManager);
        mRunsList.setAdapter(mRunsAdapter);

        RunnerViewModelFactory factory = new RunnerViewModelFactory(runnerId);
        mViewModel = ViewModelProviders.of(this, factory).get(RunnerViewModel.class);
        mViewModel.getState().observe(this, this::render);

        mViewModel.load();
    }

    @Override
    public void render(State state) {

        //noinspection unchecked
        DataState<Runner> runnerState = (DataState<Runner>) state;

        if (runnerState.isLoading()) {
            load();
            return;
        }

        if (runnerState.hasError()) {
            showError();
            return;
        }

        Runner runner = runnerState.getData();

        mImageLoader.load(runner.getIcon(), R.drawable.default_runner, mRunnerImage);
        mRunnerName.setText(runner.getName());
        mRunnerCountry.setText(runner.getCountry());
        mImageLoader.load(runner.getFlag(), mRunnerFlag);
        mSocialLinksAdapter.setData(runner.getSocialMedias());
        showContent();

        if (runner.getRuns().isEmpty()) {
            mRunsList.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.GONE);
            mRunsList.setVisibility(View.VISIBLE);
            mRunsAdapter.setData(runner.getRuns());
        }

    }

    @Override
    protected void onTryAgain() {
        mViewModel.load();
    }

    @Override
    public void onRunClick(String gameId, String runId) {
        RunActivity.start(this, gameId, runId);
    }

    @Override
    public void onRunnerClick(String id) {
        //do nothing since we are already displaying the runner
    }

}