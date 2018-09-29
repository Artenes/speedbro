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
import io.github.artenes.speedbro.models.LeaderBoardViewModel;
import io.github.artenes.speedbro.models.LeaderBoardViewModelFactory;
import io.github.artenes.speedbro.models.State;
import io.github.artenes.speedbro.speedrun.com.models.Run;
import io.github.artenes.speedbro.utils.Dependencies;

/**
 * Fragment that displays a list of runs
 */
public class LeaderBoardFragment extends BaseFragment implements LeaderBoardRunsAdapter.OnRunClickListener {

    private static final String KEY_CATEGORY_URL = "CATEGORY_URL";
    private static final String KEY_CATEGORY_NAME = "CATEGORY_NAME";

    private RecyclerView mRecyclerView;
    private LeaderBoardRunsAdapter mAdapter;
    private LeaderBoardViewModel mViewModel;

    public static LeaderBoardFragment newInstance(String categoryName, String categoryUrl) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_CATEGORY_URL, categoryUrl);
        bundle.putString(KEY_CATEGORY_NAME, categoryName);
        LeaderBoardFragment fragment = new LeaderBoardFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.leaderboard, container, false);
        mRecyclerView = view.findViewById(R.id.container);
        initializeBaseView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), getActivity().getResources().getInteger(R.integer.category_runs_grid_columns));
        mAdapter = new LeaderBoardRunsAdapter(Dependencies.getImageLoader(), this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        LeaderBoardViewModelFactory factory = new LeaderBoardViewModelFactory(getArgument(KEY_CATEGORY_URL));
        mViewModel = ViewModelProviders.of(this, factory).get(LeaderBoardViewModel.class);

        mViewModel.getState().observe(this, this::render);
        mViewModel.load();
    }

    public String getTitle() {
        return getArgument(KEY_CATEGORY_NAME);
    }

    @Override
    public void render(State state) {
        //noinspection unchecked
        DataState<List<Run>> runsState = (DataState<List<Run>>) state;

        if (runsState.isLoading()) {
            load();
            return;
        }

        if (runsState.hasError()) {
            showError();
            return;
        }

        List<Run> runs = runsState.getData();

        if (runs.isEmpty()) {
            showEmpty();
            return;
        }

        mAdapter.setData(runsState.getData());
        showContent();
    }

    @Override
    public void onRunClick(String gameId, String runId) {
        if (getActivity() == null) {
            return;
        }
        RunActivity.start(getActivity(), gameId, runId);
    }

    @Override
    protected void onTryAgain() {
        mViewModel.load();
    }

}