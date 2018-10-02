package io.github.artenes.speedbro.views;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import io.github.artenes.speedbro.R;
import io.github.artenes.speedbro.speedrun.com.models.FavoriteRun;
import io.github.artenes.speedbro.speedrun.com.models.Run;

/**
 * Section with the title of the run
 */
public class RunDetailTitleSection implements ScreenSection {

    private final OnFavoriteClickedListener favoriteClickedListener;

    public RunDetailTitleSection(OnFavoriteClickedListener favoriteClickedListener) {
        this.favoriteClickedListener = favoriteClickedListener;
    }

    @Override
    public RecyclerView.ViewHolder makeViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.run_detail_title, parent, false);
        return new TitleViewHolder(view);
    }

    @Override
    public void bind(RecyclerView.ViewHolder viewHolder, Object data) {
        FavoriteRun run = (FavoriteRun) data;
        ((TitleViewHolder) viewHolder).bind(run);
    }

    public class TitleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView mTitle;
        private final ImageView mFavorite;

        TitleViewHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title);
            mFavorite = itemView.findViewById(R.id.favorite);
            mFavorite.setOnClickListener(this);
        }

        void bind(FavoriteRun run) {
            Resources resources = itemView.getContext().getResources();
            mTitle.setText(resources.getString(R.string.run_title, run.getRun().getCategory(), run.getRun().getTime()));
            if (run.isFavorite()) {
                mFavorite.setImageDrawable(itemView.getResources().getDrawable(android.R.drawable.star_big_on));
            } else {
                mFavorite.setImageDrawable(itemView.getResources().getDrawable(android.R.drawable.star_big_off));
            }
        }

        @Override
        public void onClick(View v) {
            favoriteClickedListener.onFavoriteClicked();
        }

    }

    public interface OnFavoriteClickedListener {

        void onFavoriteClicked();

    }

}