package io.github.artenes.speedbro.models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.youtube.player.YouTubePlayer;

import io.github.artenes.speedbro.db.Database;
import io.github.artenes.speedbro.speedrun.com.models.FavoriteRun;
import io.github.artenes.speedbro.speedrun.com.models.Video;
import io.github.artenes.speedbro.tasks.FavoriteRunTask;
import io.github.artenes.speedbro.tasks.LoadRunTask;
import io.github.artenes.speedbro.utils.Utils;

/**
 * View model for a run
 */
public class RunViewModel extends ViewModel {

    private static final String TAG = "RunViewModel";

    private final String mGameId;
    private final String mRunId;
    private final Database mDatabase;
    private final DataState<FavoriteRun> mState = new DataState<>();

    RunViewModel(String gameId, String runId, Database database) {
        mRunId = runId;
        mGameId = gameId;
        mDatabase = database;
    }

    public LiveData<State> getState() {
        return mState;
    }

    public void loadRun() {
        if (mState.getData() != null) {
            return;
        }
        new LoadRunTask(mDatabase, mState).execute(mGameId, mRunId);
    }

    public void loadYoutubeVideo(@NonNull YouTubePlayer player) {
        String videoId = mState.getData().getRun().getVideo().getId();
        player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI);
        Log.i(TAG, "Loading youtube video: " + videoId);
        player.cueVideo(videoId);
    }

    public void loadTwitchVideo(@NonNull Context context) {
        boolean isTwitchInstalled = Utils.isTwitchInstalled(context);

        Uri videoUri;
        Video video = mState.getData().getRun().getVideo();
        if (isTwitchInstalled) {
            videoUri = Uri.parse(video.getTwitchUrl());
        } else {
            videoUri = Uri.parse(video.getTwitchHttpUri());
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, videoUri);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            Log.e(TAG, "Was not possible to play twitch video from: " + videoUri.toString());
        }
    }

    public void toggleFavorite() {
        FavoriteRun run = mState.getData();
        run.setFavorite(!run.isFavorite());
        mState.update();
        new FavoriteRunTask(mDatabase).execute(run.getRun());
    }

    public Bundle getBundleForAnalytics() {
        Bundle bundle = new Bundle();
        bundle.putString("GAME_ID", mGameId);
        bundle.putString("RUN_ID", mRunId);
        bundle.putBoolean("IS_FAVORITE", mState.getData().isFavorite());
        return bundle;
    }

}