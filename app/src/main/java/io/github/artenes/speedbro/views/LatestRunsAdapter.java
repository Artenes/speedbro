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
import io.github.artenes.speedbro.speedrun.com.models.LatestRun;
import io.github.artenes.speedbro.utils.ImageLoader;

/**
 * Displays a list of latest runs
 */
public class LatestRunsAdapter extends RecyclerView.Adapter<LatestRunsAdapter.LatestRunViewHolder> {

    private final ImageLoader mImageLoader;
    private final OnRunClickListener mOnRunClickListener;
    private List<LatestRun> mRuns = new ArrayList<>(0);

    LatestRunsAdapter(ImageLoader imageLoader, OnRunClickListener runClickListener) {
        mImageLoader = imageLoader;
        mOnRunClickListener = runClickListener;
    }

    /**
     * Sets the list of latest runs
     *
     * @param runs the list of latest runs
     */
    public void setData(@NonNull List<LatestRun> runs) {
        mRuns = runs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LatestRunViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.run_line, parent, false);
        return new LatestRunViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LatestRunViewHolder holder, int position) {
        LatestRun latestRun = mRuns.get(position);
        holder.bind(latestRun);
    }

    @Override
    public int getItemCount() {
        return mRuns.size();
    }

    /**
     * View holder for a latest run
     */
    public class LatestRunViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView mRunnerIcon;
        private final TextView mRunner;
        private final ImageView mCountryIcon;
        private final ImageView mGameCover;
        private final TextView mGameTitle;
        private final TextView mCategory;
        private final ImageView mPositionIcon;
        private final TextView mRankPosition;
        private final TextView mTime;
        private final CardView mCardView;

        LatestRunViewHolder(View itemView) {
            super(itemView);
            mRunnerIcon = itemView.findViewById(R.id.runner_icon);
            mRunner = itemView.findViewById(R.id.runner);
            mCountryIcon = itemView.findViewById(R.id.country_icon);
            mGameCover = itemView.findViewById(R.id.cover);
            mGameTitle = itemView.findViewById(R.id.game_title);
            mCategory = itemView.findViewById(R.id.category);
            mPositionIcon = itemView.findViewById(R.id.position_icon);
            mRankPosition = itemView.findViewById(R.id.position);
            mTime = itemView.findViewById(R.id.time);
            mCardView = itemView.findViewById(R.id.cv_contents);

            mCardView.setOnClickListener(this);
            mRunnerIcon.setOnClickListener(this);
            mRunner.setOnClickListener(this);
            mCountryIcon.setOnClickListener(this);
            mGameCover.setOnClickListener(this);
            mGameTitle.setOnClickListener(this);
            mCategory.setOnClickListener(this);
            mPositionIcon.setOnClickListener(this);
            mRankPosition.setOnClickListener(this);
            mTime.setOnClickListener(this);
        }

        void bind(LatestRun run) {
            mGameTitle.setText(run.getGameTitle());
            mCategory.setText(run.getCategory());
            mRunner.setText(run.getRunnerName());
            mTime.setText(run.getTime());
            mRankPosition.setText(run.getPosition());

            //load the game cover
            mImageLoader.load(run.getGameCover(), R.drawable.placeholder, mGameCover);

            //load the runner icon
            mImageLoader.load(run.getRunnerIcon(), R.drawable.default_runner, mRunnerIcon);

            //load the country icon if available
            mImageLoader.load(run.getCountryIcon(), mCountryIcon);

            //load the position icon if available
            mImageLoader.load(run.getPositionIcon(), mPositionIcon);
        }

        @Override
        public void onClick(View view) {
            LatestRun run = mRuns.get(getAdapterPosition());

            if (isRunnerView(view)) {
                mOnRunClickListener.onRunnerClick(run.getRunnerId());
                return;
            }

            mOnRunClickListener.onRunClick(run.getGameId(), run.getId());
        }

        private boolean isRunnerView(View view) {
            int id = view.getId();
            return id == R.id.runner || id == R.id.country_icon || id == R.id.runner_icon;
        }

    }

    public interface OnRunClickListener {
        void onRunClick(String gameId, String runId);

        void onRunnerClick(String id);
    }

}