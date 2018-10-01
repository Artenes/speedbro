package io.github.artenes.speedbro.views;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.artenes.speedbro.R;
import io.github.artenes.speedbro.models.LatestRunsState;
import io.github.artenes.speedbro.models.LatestRunsViewModel;
import io.github.artenes.speedbro.models.State;
import io.github.artenes.speedbro.utils.Dependencies;

/**
 * Displays a list with the latest runs
 */
public class LatestRunsFragment extends BaseFragment implements RunsAdapter.OnRunClickListener {

    private RecyclerView mLatestRuns;
    private RunsAdapter mAdapter;
    private LatestRunsViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.latest_runs, container, false);
        initializeBaseView(view);
        mLatestRuns = view.findViewById(R.id.container);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new RunsAdapter(Dependencies.getImageLoader(), this);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.latest_runs_grid_columns));
        mLatestRuns.setLayoutManager(layoutManager);
        mLatestRuns.setAdapter(mAdapter);
        mViewModel = ViewModelProviders.of(this).get(LatestRunsViewModel.class);
        //the only thing we observe for its for changes in the state
        mViewModel.getState().observe(this, this::render);
    }

    @Override
    public void render(State state) {
        LatestRunsState latestRunsState = (LatestRunsState) state;

        if (latestRunsState.isLoading()) {
            load();
            return;
        }

        if (latestRunsState.hasError()) {
            showError();
            return;
        }

        showContent();
        mAdapter.setData(latestRunsState.getRuns());
    }

    @Override
    public void onRunClick(String gameId, String runId) {
        RunActivity.start(getActivity(), gameId, runId);
    }

    @Override
    public void onRunnerClick(String id) {
        RunnerActivity.start(getActivity(), id);
    }

    @Override
    protected void onTryAgain() {
        mViewModel.load();
    }

}