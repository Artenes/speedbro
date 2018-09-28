package io.github.artenes.speedbro.views;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.github.artenes.speedbro.R;
import io.github.artenes.speedbro.speedrun.com.models.Run;
import io.github.artenes.speedbro.speedrun.com.models.Runner;
import io.github.artenes.speedbro.utils.ImageLoader;

/**
 * Adapter to display a list of runs from a leader board
 */
public class LeaderBoardRunsAdapter extends RecyclerView.Adapter<LeaderBoardRunsAdapter.RunViewHolder> {

    private final ImageLoader mImageLoader;
    private final LeaderBoardRunsAdapter.OnRunClickListener mOnRunClickListener;
    private List<Run> mRuns = new ArrayList<>(0);

    LeaderBoardRunsAdapter(ImageLoader imageLoader, LeaderBoardRunsAdapter.OnRunClickListener runClickListener) {
        mImageLoader = imageLoader;
        mOnRunClickListener = runClickListener;
    }

    /**
     * Sets the list of latest runs
     *
     * @param runs the list of latest runs
     */
    public void setData(@NonNull List<Run> runs) {
        mRuns = runs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RunViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_run_line, parent, false);
        return new LeaderBoardRunsAdapter.RunViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RunViewHolder holder, int position) {
        Run latestRun = mRuns.get(position);
        holder.bind(latestRun);
    }

    @Override
    public int getItemCount() {
        return mRuns.size();
    }

    public class RunViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView mRunner;
        private final ImageView mCountryIcon;
        private final ImageView mPositionIcon;
        private final TextView mRankPosition;
        private final TextView mTime;
        private final CardView mCardView;

        RunViewHolder(View itemView) {
            super(itemView);
            mRunner = itemView.findViewById(R.id.runner);
            mCountryIcon = itemView.findViewById(R.id.country_icon);
            mPositionIcon = itemView.findViewById(R.id.position_icon);
            mRankPosition = itemView.findViewById(R.id.position);
            mTime = itemView.findViewById(R.id.time);
            mCardView = itemView.findViewById(R.id.cv_contents);

            mCardView.setOnClickListener(this);
            mRunner.setOnClickListener(this);
            mCountryIcon.setOnClickListener(this);
            mPositionIcon.setOnClickListener(this);
            mRankPosition.setOnClickListener(this);
            mTime.setOnClickListener(this);
        }

        void bind(Run run) {
            Runner runner = run.getFirstRunner();

            mRunner.setText(runner.getName());
            mTime.setText(run.getTime().isEmpty() ? run.getInGameTime() : run.getTime());
            mRankPosition.setText(run.getPlacement().getPlace());

            //load the country icon if available
            mImageLoader.load(runner.getFlag(), mCountryIcon);

            //load the position icon if available
            mImageLoader.load(run.getPlacement().getIcon(), mPositionIcon);

            if (!runner.getId().isEmpty()) {
                mRunner.setVisibility(View.VISIBLE);
            } else {
                mRunner.setVisibility(View.GONE);
            }

        }

        @Override
        public void onClick(View view) {
            Run run = mRuns.get(getAdapterPosition());
            mOnRunClickListener.onRunClick(run.getGame().getId(), run.getId());
        }

    }

    public interface OnRunClickListener {
        void onRunClick(String gameId, String runId);
    }

}