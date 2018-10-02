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
import io.github.artenes.speedbro.utils.ImageLoader;

/**
 * Section with the game data
 */
public class RunDetailGameSection implements ScreenSection {

    private final ImageLoader imageLoader;
    private final OnGameClickedListener gameClickedListener;

    RunDetailGameSection(ImageLoader imageLoader, OnGameClickedListener gameClickedListener) {
        this.imageLoader = imageLoader;
        this.gameClickedListener = gameClickedListener;
    }

    @Override
    public RecyclerView.ViewHolder makeViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.run_detail_game, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void bind(RecyclerView.ViewHolder viewHolder, Object data) {
        FavoriteRun run = (FavoriteRun) data;
        ((GameViewHolder) viewHolder).bind(run.getRun());
    }

    public class GameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
            mGameCover.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            gameClickedListener.onGameClicked(itemView.getContext());
        }

    }

    public interface OnGameClickedListener {

        void onGameClicked(Context context);

    }

}