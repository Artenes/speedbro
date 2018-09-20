package io.github.artenes.speedbro.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import io.github.artenes.speedbro.R;
import io.github.artenes.speedbro.speedrun.com.models.Run;
import io.github.artenes.speedbro.utils.ImageLoader;

/**
 * Section with the game data
 */
public class RunDetailGameSection implements ScreenSection {

    private final ImageLoader imageLoader;

    RunDetailGameSection(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    @Override
    public RecyclerView.ViewHolder makeViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.run_detail_game, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void bind(RecyclerView.ViewHolder viewHolder, Object data) {
        Run run = (Run) data;
        ((GameViewHolder) viewHolder).bind(run);
    }

    public class GameViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mGameCover;
        private final TextView mGameTitle;

        GameViewHolder(View itemView) {
            super(itemView);
            mGameCover = itemView.findViewById(R.id.game_cover);
            mGameTitle = itemView.findViewById(R.id.game_title);
        }

        void bind(Run run) {
            imageLoader.load(run.getGame().getCover(), R.drawable.placeholder, mGameCover);
            mGameTitle.setText(run.getGame().getTitle());
        }

    }

}