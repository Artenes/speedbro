package io.github.artenes.speedbro.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.github.artenes.speedbro.R;
import io.github.artenes.speedbro.speedrun.com.models.Run;
import io.github.artenes.speedbro.utils.SpeedBroApplication;

/**
 * Displays a list with the latest runs
 */
public class LatestRunsFragment extends Fragment implements RunsAdapter.OnRunClickListener {

    private RecyclerView mLatestRuns;
    private GridLayoutManager mLayoutManager;
    private OnScrollPositionSave mOnScrollPositionSave;

    private List<Run> mRuns;
    private int mLatestScrollPosition;

    /**
     * Set the current state of the fragment.
     * <p>
     * If this is not called before the fragment is initialized
     * a NullPointerException will be thrown
     *
     * @param runs                 the list of runs
     * @param latestScrollPosition the last scroll position of the list
     * @param onScrollPositionSave the listener to watch for changes in the scroll position
     */
    public void setState(@NonNull List<Run> runs, int latestScrollPosition, @NonNull OnScrollPositionSave onScrollPositionSave) {
        mRuns = runs;
        mLatestScrollPosition = latestScrollPosition;
        mOnScrollPositionSave = onScrollPositionSave;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.latest_runs, container, false);
        mLatestRuns = view.findViewById(R.id.container);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RunsAdapter mAdapter = new RunsAdapter(SpeedBroApplication.getImageLoader(), this);
        mLayoutManager = new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.latest_runs_grid_columns));
        mLatestRuns.setLayoutManager(mLayoutManager);
        mLatestRuns.setAdapter(mAdapter);
        mAdapter.setData(mRuns);
        mLatestRuns.scrollToPosition(mLatestScrollPosition);
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
    public void onPause() {
        super.onPause();
        mOnScrollPositionSave.onScrollPositionSave(mLayoutManager.findFirstVisibleItemPosition());
    }

    /**
     * Interface to notify a watcher when this fragment pauses
     * and its scroll position needs to be saved
     */
    public interface OnScrollPositionSave {
        void onScrollPositionSave(int position);
    }

}