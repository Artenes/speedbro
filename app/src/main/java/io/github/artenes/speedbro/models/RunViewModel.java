package io.github.artenes.speedbro.models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.youtube.player.YouTubePlayer;

import io.github.artenes.speedbro.speedrun.com.models.Video;
import io.github.artenes.speedbro.tasks.LoadRunTask;
import io.github.artenes.speedbro.utils.Utils;

/**
 * View model for a run
 */
public class RunViewModel extends ViewModel {

    private static final String TAG = "RunViewModel";

    private final String mGameId;
    private final String mRunId;
    private final RunState mState = new RunState();

    RunViewModel(String gameId, String runId) {
        mRunId = runId;
        mGameId = gameId;
    }

    public LiveData<State> getState() {
        return mState;
    }

    public void loadRun() {
        if (mState.getRun() != null) {
            return;
        }
        new LoadRunTask(mState).execute(mGameId, mRunId);
    }

    public void loadYoutubeVideo(@NonNull YouTubePlayer player) {
        player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
        player.cueVideo(mState.getRun().getVideo().getId());
    }

    public void loadTwitchVideo(@NonNull Context context) {
        boolean isTwitchInstalled = Utils.isTwitchInstalled(context);

        Uri videoUri;
        Video video = mState.getRun().getVideo();
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

}