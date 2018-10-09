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

import java.util.List;

import io.github.artenes.speedbro.R;
import io.github.artenes.speedbro.models.DataState;
import io.github.artenes.speedbro.models.FavoritesViewModel;
import io.github.artenes.speedbro.models.State;
import io.github.artenes.speedbro.speedrun.com.models.Run;
import io.github.artenes.speedbro.utils.SpeedBroApplication;

/**
 * Displays a list of favorite runs
 */
public class FavoritesFragment extends BaseFragment implements RunsAdapter.OnRunClickListener {

    private RecyclerView mFavoriteList;
    private RunsAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorites, container, false);
        initializeBaseView(view);
        mFavoriteList = view.findViewById(R.id.container);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.latest_runs_grid_columns));
        mAdapter = new RunsAdapter(SpeedBroApplication.getImageLoader(), this);
        mFavoriteList.setLayoutManager(gridLayoutManager);
        mFavoriteList.setAdapter(mAdapter);

        FavoritesViewModel mViewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);
        mViewModel.getState().observe(this, this::render);
    }

    @Override
    public void render(State state) {
        //noinspection unchecked
        DataState<List<Run>> favoriteState = (DataState<List<Run>>) state;

        if (favoriteState.isLoading()) {
            load();
            return;
        }

        if (favoriteState.hasError()) {
            showError();
            return;
        }

        List<Run> runs = favoriteState.getData();

        if (runs.isEmpty()) {
            showEmpty();
            return;
        }

        showContent();
        mAdapter.setData(runs);
    }

    @Override
    public void onRunClick(String gameId, String runId) {
        RunActivity.start(getActivity(), gameId, runId);
    }

    @Override
    public void onRunnerClick(String id) {
        RunnerActivity.start(getActivity(), id);
    }

}