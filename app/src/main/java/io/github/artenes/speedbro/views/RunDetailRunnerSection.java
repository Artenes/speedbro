package io.github.artenes.speedbro.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import io.github.artenes.speedbro.R;
import io.github.artenes.speedbro.speedrun.com.models.FavoriteRun;
import io.github.artenes.speedbro.speedrun.com.models.Run;
import io.github.artenes.speedbro.speedrun.com.models.Runner;
import io.github.artenes.speedbro.utils.ImageLoader;

/**
 * Section with information about runner
 */
public class RunDetailRunnerSection implements ScreenSection {

    private final ImageLoader imageLoader;
    private final OnRunnerClickListener runnerClickListener;

    public RunDetailRunnerSection(ImageLoader imageLoader, OnRunnerClickListener runnerClickListener) {
        this.imageLoader = imageLoader;
        this.runnerClickListener = runnerClickListener;
    }

    @Override
    public RecyclerView.ViewHolder makeViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.run_detail_runner, parent, false);
        return new RunnerViewHolder(view);
    }

    @Override
    public void bind(RecyclerView.ViewHolder viewHolder, Object data) {
        FavoriteRun run = (FavoriteRun) data;
        ((RunnerViewHolder) viewHolder).bind(run.getRun());
    }

    class RunnerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView mRunnerIcon;
        private final TextView mRunnerName;
        private final ImageView mFlag;

        RunnerViewHolder(View itemView) {
            super(itemView);
            mRunnerIcon = itemView.findViewById(R.id.runner_icon);
            mRunnerName = itemView.findViewById(R.id.runner_name);
            mFlag = itemView.findViewById(R.id.country_icon);
        }

        void bind(Run run) {
            Runner runner = run.getFirstRunner();

            imageLoader.load(runner.getIcon(), R.drawable.default_runner, mRunnerIcon);
            mRunnerName.setText(runner.getName());
            imageLoader.load(runner.getFlag(), mFlag);

            if (!runner.getId().isEmpty()) {
                mRunnerIcon.setOnClickListener(this);
                mRunnerName.setOnClickListener(this);
                mFlag.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View v) {
            runnerClickListener.onRunnerClick(itemView.getContext());
        }

    }

    public interface OnRunnerClickListener {
        void onRunnerClick(Context context);
    }

}