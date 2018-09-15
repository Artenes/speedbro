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

import io.github.artenes.speedbro.speedrun.com.models.LatestRun;

/**
 * Displays a list of latest runs
 */
public class LatestRunsAdapter extends RecyclerView.Adapter<LatestRunsAdapter.LatestRunViewHolder> {

    private final ImageLoader mImageLoader;
    private List<LatestRun> mRuns = new ArrayList<>(0);

    public LatestRunsAdapter(ImageLoader imageLoader) {
        mImageLoader = imageLoader;
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
    public class LatestRunViewHolder extends RecyclerView.ViewHolder {

        private final ImageView runnerIcon;
        private final TextView runner;
        private final ImageView countryIcon;
        private final ImageView gameCover;
        private final TextView gameTitle;
        private final TextView category;
        private final ImageView positionIcon;
        private final TextView position;
        private final TextView time;

        public LatestRunViewHolder(View itemView) {
            super(itemView);
            runnerIcon = itemView.findViewById(R.id.runner_icon);
            runner = itemView.findViewById(R.id.runner);
            countryIcon = itemView.findViewById(R.id.country_icon);
            gameCover = itemView.findViewById(R.id.cover);
            gameTitle = itemView.findViewById(R.id.game_title);
            category = itemView.findViewById(R.id.category);
            positionIcon = itemView.findViewById(R.id.position_icon);
            position = itemView.findViewById(R.id.position);
            time = itemView.findViewById(R.id.time);
        }

        public void bind(LatestRun run) {
            gameTitle.setText(run.getGameTitle());
            category.setText(run.getCategory());
            runner.setText(run.getRunnerName());
            time.setText(run.getTime());
            position.setText(run.getPosition());

            //load the game cover
            mImageLoader.load(run.getGameCover(), R.drawable.placeholder, gameCover);

            //load the runner icon
            mImageLoader.load(run.getRunnerIcon(), R.drawable.default_runner, runnerIcon);

            //load the country icon if available
            mImageLoader.load(run.getCountryIcon(), countryIcon);

            //load the position icon if available
            mImageLoader.load(run.getPositionIcon(), positionIcon);
        }

    }

}