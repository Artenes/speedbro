package io.github.artenes.speedbro.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.github.artenes.speedbro.R;
import io.github.artenes.speedbro.speedrun.com.RunsRepository;
import io.github.artenes.speedbro.speedrun.com.models.Run;
import io.github.artenes.speedbro.utils.SpeedBroApplication;
import io.github.artenes.speedbro.utils.ImageLoader;

/**
 * Factory that generates the views to display in the widget
 */
class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = StackRemoteViewsFactory.class.getSimpleName();
    private final List<Run> mRuns = new ArrayList<>();
    private final Context mContext;
    private final RunsRepository mRepository;
    private final ImageLoader mImageLoader;

    StackRemoteViewsFactory(Context context) {
        mRepository = SpeedBroApplication.getRunsRepository();
        mContext = context;
        mImageLoader = SpeedBroApplication.getImageLoader();
    }

    @Override
    public void onCreate() {
        //nothing here
    }

    public void onDestroy() {
        mRuns.clear();
    }

    public int getCount() {
        return mRuns.size();
    }

    public RemoteViews getViewAt(int position) {
        Run run = mRuns.get(position);

        //relying on a big title to make width of the widget item match the width of the widget
        //by putting match_parent in the view of the item, it does not matches the parent
        String runTitle = mContext.getResources().getString(R.string.run_title_with_game,
                run.getGame().getTitle(), run.getCategory(), run.getTime());

        String gameCover = run.getGame().getCover();

        // We construct a remote views item based on our widget item xml file, and set the
        // text based on the position.
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        rv.setTextViewText(R.id.run_title, runTitle);
        mImageLoader.load(gameCover, R.drawable.placeholder, R.id.game_cover, rv);

        // Next, we set a fill-intent which will be used to fill-in the pending intent template
        // which is set on the collection view.
        Bundle extras = new Bundle();
        extras.putString(LatestRunsWidgetProvider.EXTRA_GAME_ID, run.getGame().getId());
        extras.putString(LatestRunsWidgetProvider.EXTRA_RUN_ID, run.getId());

        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.widget_item, fillInIntent);

        // Return the remote views object.
        return rv;
    }

    public RemoteViews getLoadingView() {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        rv.setTextViewText(R.id.run_title, mContext.getResources().getString(R.string.loading));
        rv.setImageViewResource(R.id.game_cover, R.drawable.placeholder);
        return rv;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public long getItemId(int position) {
        return position;
    }

    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onDataSetChanged() {
        try {
            List<Run> runs = mRepository.getLatestRuns();
            mRuns.clear();
            mRuns.addAll(runs);
        } catch (IOException exception) {
            Log.e(TAG, exception.getMessage());
        }
    }

}