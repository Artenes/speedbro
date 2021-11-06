package io.github.artenes.speedbro.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.github.artenes.speedbro.speedrun.com.models.FavoriteRun;
import io.github.artenes.speedbro.speedrun.com.models.Runner;
import io.github.artenes.speedbro.utils.ImageLoader;

/**
 * Adapter to display the sections of the run screen
 * This approach was adopted to better separate each section,
 * so it becomes easier to manage each one individually or to add
 * new ones in the future
 */
public class RunDetailsAdapter extends RecyclerView.Adapter implements RunDetailRunnerSection.OnRunnerClickListener, RunDetailGameSection.OnGameClickedListener, RunDetailTitleSection.OnFavoriteClickedListener {

    private final ImageLoader mImageLoader;
    private FavoriteRun mRun;
    private final List<ScreenSection> mSections = new ArrayList<>();
    private final RunDetailTitleSection.OnFavoriteClickedListener mOnFavoriteClickListener;

    RunDetailsAdapter(ImageLoader imageLoader, RunDetailTitleSection.OnFavoriteClickedListener favoriteClickedListener) {
        mImageLoader = imageLoader;
        mOnFavoriteClickListener = favoriteClickedListener;
    }

    public void setData(@NonNull FavoriteRun run) {
        mRun = run;

        mSections.clear();
        mSections.add(new RunDetailTitleSection(this));

        Runner firstRunner = run.getRun().getFirstRunner();
        if (firstRunner != null && firstRunner.isUser()) {
            mSections.add(new RunDetailRunnerSection(mImageLoader, this));
        }

        if (run.getRun().hasCommentary()) {
            mSections.add(new RunDetailCommentSection());
        }

        mSections.add(new RunDetailGameSection(mImageLoader, this));

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

    @Override
    public void onRunnerClick(Context context) {
        RunnerActivity.start(context, mRun.getRun().getFirstRunner().getId());
    }

    @Override
    public void onGameClicked(Context context) {
        GameActivity.start(context, mRun.getRun().getGame().getId());
    }

    @Override
    public void onFavoriteClicked() {
        mOnFavoriteClickListener.onFavoriteClicked();
    }

}