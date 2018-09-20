package io.github.artenes.speedbro.views;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import io.github.artenes.speedbro.R;
import io.github.artenes.speedbro.speedrun.com.models.Run;

/**
 * Section with the title of the run
 */
public class RunDetailTitleSection implements ScreenSection {

    @Override
    public RecyclerView.ViewHolder makeViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.run_detail_title, parent, false);
        return new TitleViewHolder(view);
    }

    @Override
    public void bind(RecyclerView.ViewHolder viewHolder, Object data) {
        Run run = (Run) data;
        ((TitleViewHolder) viewHolder).bind(run);
    }

    public class TitleViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTitle;
        private final ImageView mFavorite;

        TitleViewHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title);
            mFavorite = itemView.findViewById(R.id.favorite);
        }

        void bind(Run run) {
            Resources resources = itemView.getContext().getResources();
            mTitle.setText(resources.getString(R.string.run_title, run.getCategory(), run.getTime()));
        }

    }

}