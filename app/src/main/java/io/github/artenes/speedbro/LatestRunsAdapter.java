package io.github.artenes.speedbro;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.github.artenes.speedbro.speedrun.com.LatestRun;

public class LatestRunsAdapter extends RecyclerView.Adapter<LatestRunsAdapter.LatestRunViewHolder>{

    private List<LatestRun> mRuns = new ArrayList<>(0);

    public void setData(List<LatestRun> runs) {
        if (runs != null) {
            mRuns = runs;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public LatestRunViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.run_line, parent, false);
        return new LatestRunViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LatestRunViewHolder holder, int position) {
        holder.bind(mRuns.get(position));
    }

    @Override
    public int getItemCount() {
        return mRuns.size();
    }

    public class LatestRunViewHolder extends RecyclerView.ViewHolder {

        private final ImageView cover;
        private final ImageView runnerIcon;
        private final ImageView countryIcon;
        private final ImageView placementIcon;
        private final TextView gameTitle;
        private final TextView category;
        private final TextView runner;
        private final TextView time;
        private final TextView placement;

        public LatestRunViewHolder(View itemView) {
            super(itemView);
            cover = itemView.findViewById(R.id.cover);
            gameTitle = itemView.findViewById(R.id.game_title);
            category = itemView.findViewById(R.id.category);
            runner = itemView.findViewById(R.id.runner);
            time = itemView.findViewById(R.id.time);
            placement = itemView.findViewById(R.id.placement);
            runnerIcon = itemView.findViewById(R.id.runner_icon);
            countryIcon = itemView.findViewById(R.id.country_icon);
            placementIcon = itemView.findViewById(R.id.placement_icon);
        }

        public void bind(LatestRun run) {
            gameTitle.setText(run.getGameTitle());
            category.setText(run.getCategory());
            runner.setText(run.getRunner());
            time.setText(run.getTime());
            placement.setText(run.getPosition());

            Picasso.get().load(run.getGameCover()).into(cover);

            //if there is a runner available (it is not a guest), load its icon and country
            if (run.hasRunnerId()) {
                Picasso.get().load(run.getRunnerIcon()).error(R.drawable.default_runner).into(runnerIcon);
                Picasso.get().load(run.getCountryIcon()).into(countryIcon);
            } else {
                //otherwise just load the placeholder icon
                Picasso.get().load(R.drawable.default_runner).into(runnerIcon);
            }

            //if there is the icon for the position/placement, just load it
            if (run.hasPositionIcon()) {
                Picasso.get().load(run.getPositionIcon()).into(placementIcon);
            } else {
                //otherwise be gone with it
                placementIcon.setVisibility(View.GONE);
            }
        }

    }

}