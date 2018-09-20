package io.github.artenes.speedbro.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import io.github.artenes.speedbro.R;
import io.github.artenes.speedbro.speedrun.com.models.Run;
import io.github.artenes.speedbro.speedrun.com.models.Runner;
import io.github.artenes.speedbro.utils.ImageLoader;

/**
 * Section with information about runner
 */
public class RunDetailRunnerSection implements ScreenSection {

    private final ImageLoader imageLoader;

    RunDetailRunnerSection(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    @Override
    public RecyclerView.ViewHolder makeViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.run_detail_runner, parent, false);
        return new RunnerViewHolder(view);
    }

    @Override
    public void bind(RecyclerView.ViewHolder viewHolder, Object data) {
        Run run = (Run) data;
        ((RunnerViewHolder) viewHolder).bind(run);
    }

    public class RunnerViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mRunnerIcon;
        private final TextView mRunnerName;
        private final ImageView mFlag;
        private final TextView mPlacement;
        private final ImageView mPlacementIcon;

        RunnerViewHolder(View itemView) {
            super(itemView);
            mRunnerIcon = itemView.findViewById(R.id.runner_icon);
            mRunnerName = itemView.findViewById(R.id.runner_name);
            mFlag = itemView.findViewById(R.id.country_icon);
            mPlacement = itemView.findViewById(R.id.position);
            mPlacementIcon = itemView.findViewById(R.id.placement_icon);
        }

        void bind(Run run) {
            Runner runner = run.getRunners().get(0);
            imageLoader.load(runner.getIcon(), R.drawable.default_runner, mRunnerIcon);
            mRunnerName.setText(runner.getName());
            imageLoader.load(runner.getFlag(), mFlag);
            mPlacement.setText(run.getPlacement().getPlace());
            imageLoader.load(run.getPlacement().getIcon(), mPlacementIcon);
        }

    }

}