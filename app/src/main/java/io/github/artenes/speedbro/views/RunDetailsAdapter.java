package io.github.artenes.speedbro.views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.github.artenes.speedbro.speedrun.com.models.Run;
import io.github.artenes.speedbro.utils.ImageLoader;

/**
 * Adapter to display the sections of the run screen
 * This approach was adopted to better separate each section,
 * so it becomes easier to manage each one individually or to add
 * new ones in the future
 */
public class RunDetailsAdapter extends RecyclerView.Adapter {

    private final ImageLoader mImageLoader;
    private Run mRun;
    private List<ScreenSection> mSections = new ArrayList<>();

    RunDetailsAdapter(ImageLoader imageLoader) {
        mImageLoader = imageLoader;
    }

    public void setData(@NonNull Run run) {
        mRun = run;

        mSections.clear();
        mSections.add(new RunDetailTitleSection());
        mSections.add(new RunDetailRunnerSection(mImageLoader));

        if (run.hasCommentary()) {
            mSections.add(new RunDetailCommentSection());
        }

        mSections.add(new RunDetailGameSection(mImageLoader));

        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        //this is a little trick
        //go to onCreateViewHolder to see more
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //this is a little trick
        //since each section can create its own ViewHolder
        //we make the getItemViewType return just the position
        //so here we can get the section instance to call its makeViewHolder method
        ScreenSection screenSection = mSections.get(viewType);
        return screenSection.makeViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ScreenSection screenSection = mSections.get(position);
        screenSection.bind(holder, mRun);
    }

    @Override
    public int getItemCount() {
        return mSections.size();
    }

}