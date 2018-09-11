package io.github.artenes.speedbro;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import io.github.artenes.speedbro.speedrun.com.Run;

public class LatestRunsAdapter extends RecyclerView.Adapter<LatestRunsAdapter.LatestRunViewHolder>{

    private List<Run> mRuns = new ArrayList<>(0);

    public void setData(List<Run> runs) {
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
        private final TextView gameTitle;
        private final TextView category;
        private final TextView runner;
        private final TextView time;

        public LatestRunViewHolder(View itemView) {
            super(itemView);
            cover = itemView.findViewById(R.id.cover);
            gameTitle = itemView.findViewById(R.id.game_title);
            category = itemView.findViewById(R.id.category);
            runner = itemView.findViewById(R.id.runner);
            time = itemView.findViewById(R.id.time);
        }

        public void bind(Run run) {
            //Glide.with(itemView.getContext()).load(run.getGame().getData().getAssets().getCoverLarge()).into(cover);
            gameTitle.setText(run.getGame().getData().getNames().getInternational());
            category.setText(run.getCategory().getData().getName());
            runner.setText(run.getPlayers().getData().get(0).getNames().getInternational());
            time.setText(run.getTimes().getPrimary());
        }

    }

}
