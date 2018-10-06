package io.github.artenes.speedbro.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.List;

import io.github.artenes.speedbro.R;
import io.github.artenes.speedbro.speedrun.com.models.Run;
import io.github.artenes.speedbro.utils.Dependencies;

/**
 * Displays a list with the latest runs
 */
public class DialogRunsFragment extends DialogFragment implements RunsAdapter.OnRunClickListener {

    private RecyclerView mLatestRuns;

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
     */
    public void setState(@NonNull List<Run> runs, int latestScrollPosition) {
        mRuns = runs;
        mLatestScrollPosition = latestScrollPosition;
    }

    public String getCountry() {
        //all runs should be of the same country, so just get the first one
        if (mRuns == null) {
            return "";
        }
        Run firstRun = mRuns.get(0);
        return firstRun.getFirstRunner().getCountry();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.run_dialog, container, false);

        TextView textView = view.findViewById(R.id.dialog_title);
        textView.setText(getResources().getString(R.string.latest_runs_in, getCountry()));

        mLatestRuns = view.findViewById(R.id.myrecycler);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RunsAdapter mAdapter = new RunsAdapter(Dependencies.getImageLoader(), this);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.latest_runs_grid_columns));
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
    public void onResume() {
        // Get existing layout params for the window
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        // Assign window properties to fill the parent
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        // Call super onResume after sizing
        super.onResume();

    }

}