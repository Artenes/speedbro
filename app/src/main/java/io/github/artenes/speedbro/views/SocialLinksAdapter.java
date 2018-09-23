package io.github.artenes.speedbro.views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import io.github.artenes.speedbro.R;
import io.github.artenes.speedbro.speedrun.com.models.SocialMedia;
import io.github.artenes.speedbro.utils.ImageLoader;
import io.github.artenes.speedbro.utils.Utils;

/**
 * Adapter to display a list of social newtork links
 */
public class SocialLinksAdapter extends RecyclerView.Adapter<SocialLinksAdapter.SocialLinkViewHolder> {

    private final ImageLoader mImageLoader;
    private List<SocialMedia> mLinks;

    public SocialLinksAdapter(ImageLoader imageLoader) {
        mImageLoader = imageLoader;
    }

    public void setData(@NonNull List<SocialMedia> links){
        mLinks = links;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SocialLinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.social_line, parent, false);
        return new SocialLinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SocialLinkViewHolder holder, int position) {
        SocialMedia socialMedia = mLinks.get(position);
        holder.bind(socialMedia);
    }

    @Override
    public int getItemCount() {
        return mLinks.size();
    }

    public class SocialLinkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mIcon;

        public SocialLinkViewHolder(View itemView) {
            super(itemView);
            mIcon = itemView.findViewById(R.id.social_icon);
        }

        public void bind(SocialMedia socialMedia) {
            mImageLoader.load(socialMedia.getIcon(), mIcon);
            mIcon.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            SocialMedia socialMedia = mLinks.get(getAdapterPosition());
            Utils.startViewIntent(socialMedia.getLink(), itemView.getContext());
        }

    }

}