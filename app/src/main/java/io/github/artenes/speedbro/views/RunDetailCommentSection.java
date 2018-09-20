package io.github.artenes.speedbro.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.github.artenes.speedbro.R;
import io.github.artenes.speedbro.speedrun.com.models.Run;

/**
 * Section with the run commentary
 */
public class RunDetailCommentSection implements ScreenSection {

    @Override
    public RecyclerView.ViewHolder makeViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.run_detail_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void bind(RecyclerView.ViewHolder viewHolder, Object data) {
        Run run = (Run) data;
        ((CommentViewHolder) viewHolder).bind(run);
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        private final TextView mComment;

        CommentViewHolder(View itemView) {
            super(itemView);
            mComment = itemView.findViewById(R.id.comment);
        }

        void bind(Run run) {
            mComment.setText(run.getCommentary());
        }

    }

}