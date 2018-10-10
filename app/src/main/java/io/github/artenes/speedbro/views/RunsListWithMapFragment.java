package io.github.artenes.speedbro.views;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.github.artenes.speedbro.R;
import io.github.artenes.speedbro.models.RunsListWithMapState;
import io.github.artenes.speedbro.models.RunsListWithMapViewModel;
import io.github.artenes.speedbro.models.State;
import io.github.artenes.speedbro.speedrun.com.models.Run;

/**
 * List of runs that has a map view
 */
public class RunsListWithMapFragment extends BaseFragment implements View.OnClickListener, LatestRunsFragment.OnScrollPositionSave {

    private final LatestRunsFragment mLatestRunsFragment;
    private final RunsMapFragment mMapFragment;
    private RunsListWithMapViewModel mViewModel;
    private FloatingActionButton mFloatSwapButton;

    public RunsListWithMapFragment() {
        mLatestRunsFragment = new LatestRunsFragment();
        mMapFragment = new RunsMapFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_with_map, container, false);
        mFloatSwapButton = view.findViewById(R.id.swap_view_fab);
        mFloatSwapButton.setOnClickListener(this);
        initializeBaseView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RunsListWithMapViewModel.class);

        mMapFragment.setViewModel(mViewModel);

        mViewModel.getState().observe(this, this::render);
        mViewModel.load();
    }

    @Override
    public void render(State state) {
        RunsListWithMapState viewState = (RunsListWithMapState) state;

        if (viewState.isLoading()) {
            load();
            return;
        }

        if (viewState.hasError()) {
            showError();
            return;
        }

        List<Run> runs = viewState.getData();

        if (runs.isEmpty()) {
            showEmpty();
            return;
        }

        FragmentManager manager = getChildFragmentManager();
        if (viewState.isShowingMap()) {
            mMapFragment.setClusters(viewState.getMapClusters());
            manager.beginTransaction().replace(R.id.container, mMapFragment).commit();
            mFloatSwapButton.setImageDrawable(getActivity().getResources().getDrawable(android.R.drawable.ic_menu_sort_by_size));
        } else {
            mLatestRunsFragment.setState(runs, viewState.getLatestScrollPosition(), this);
            manager.beginTransaction().replace(R.id.container, mLatestRunsFragment).commit();
            mFloatSwapButton.setImageDrawable(getActivity().getResources().getDrawable(android.R.drawable.ic_menu_mapmode));
        }

        showContent();
    }

    @Override
    public void onClick(View v) {
        mViewModel.swapViews();
    }

    @Override
    public void onScrollPositionSave(int position) {
        mViewModel.setScrollPosition(position);
    }

    @Override
    protected void onTryAgain() {
        mViewModel.load();
    }

}