package io.github.artenes.speedbro.views;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        private final TextView mPlatform;
        private final ImageView mPlatformFlag;
        private final TextView mDate;
        private final CardView mCardView;
        private final TextView mTimeLabel;

        RunViewHolder(View itemView) {
            super(itemView);
            mRunner = itemView.findViewById(R.id.runner);
            mCountryIcon = itemView.findViewById(R.id.country_icon);
            mPositionIcon = itemView.findViewById(R.id.position_icon);
            mRankPosition = itemView.findViewById(R.id.position);
            mTime = itemView.findViewById(R.id.time);
            mPlatform = itemView.findViewById(R.id.platform);
            mPlatformFlag = itemView.findViewById(R.id.region_flag);
            mDate = itemView.findViewById(R.id.date);
            mTimeLabel = itemView.findViewById(R.id.realtime_label);
            mCardView = itemView.findViewById(R.id.cv_contents);

            mCardView.setOnClickListener(this);
            mRunner.setOnClickListener(this);
            mCountryIcon.setOnClickListener(this);
            mPositionIcon.setOnClickListener(this);
            mRankPosition.setOnClickListener(this);
            mTime.setOnClickListener(this);
        }

        @SuppressLint("SetTextI18n")
        void bind(Run run) {
            Runner runner = run.getFirstRunner();

            mRunner.setText(runner.getName());
            mRankPosition.setText("#" + run.getPlacement().getPlace());
            mPlatform.setText(run.getPlatform().getName());

            mDate.setText(formatDate(run.getDate()));
            mCardView.setCardBackgroundColor(getBackgroundColor(run.getPlacement().getPlace()));

            if (!run.getTime().isEmpty()) {
                mTime.setVisibility(View.VISIBLE);
                mTimeLabel.setVisibility(View.VISIBLE);
                mTime.setText(run.getTime());
            } else {
                mTime.setVisibility(View.GONE);
                mTimeLabel.setVisibility(View.GONE);
            }

            //load the country icon if available
            mImageLoader.load(runner.getFlag(), mCountryIcon);

            //load the position icon if available
            mImageLoader.load(run.getPlacement().getIcon(), mPositionIcon);

            //load the platform flag if available
            mImageLoader.load(run.getPlatform().getFlag(), mPlatformFlag);

            if (runner.getId() != null && !runner.getId().isEmpty()) {
                mRunner.setVisibility(View.VISIBLE);
            } else {
                mRunner.setVisibility(View.GONE);
            }

        }

        private int getBackgroundColor(String place) {
            switch (place) {
                case "1":
                    return Color.parseColor("#f6e58d");
                case "2":
                    return Color.parseColor("#95afc0");
                case "3":
                    return Color.parseColor("#d35400");
                default:
                    return Color.parseColor("#2c3e50");
            }
        }

        private String formatDate(String date) {

            Calendar calendar = Calendar.getInstance();
            DateFormat simpleDateFormat = SimpleDateFormat.getDateInstance();
            String defaultDate = simpleDateFormat.format(calendar.getTime());

            if (date == null) {
                return defaultDate;
            }

            String[] dateParts = date.split("-");

            if (dateParts.length != 3) {
                return defaultDate;
            }

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            try {
                year = Integer.parseInt(dateParts[0]);
                month = Integer.parseInt(dateParts[1]);
                day = Integer.parseInt(dateParts[2]);
            } catch (NumberFormatException exception) {
                //ignore
            }

            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);

            return simpleDateFormat.format(calendar.getTime());
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