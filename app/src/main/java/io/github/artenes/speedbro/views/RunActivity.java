package io.github.artenes.speedbro.views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import io.github.artenes.speedbro.BuildConfig;
import io.github.artenes.speedbro.R;
import io.github.artenes.speedbro.models.DataState;
import io.github.artenes.speedbro.models.RunViewModel;
import io.github.artenes.speedbro.models.RunViewModelFactory;
import io.github.artenes.speedbro.models.State;
import io.github.artenes.speedbro.speedrun.com.models.FavoriteRun;
import io.github.artenes.speedbro.speedrun.com.models.Run;
import io.github.artenes.speedbro.speedrun.com.models.Video;
import io.github.artenes.speedbro.utils.Dependencies;

/**
 * Display a run
 */
public class RunActivity extends BaseActivity implements YouTubePlayer.OnInitializedListener, View.OnClickListener, RunDetailTitleSection.OnFavoriteClickedListener {

    private static final String EXTRA_RUN_ID = "run_id";
    private static final String EXTRA_GAME_ID = "game_id";

    /**
     * Start this activity
     *
     * @param context the context to start the activity
     * @param gameId  the id of the game
     * @param runId   the id of the run
     */
    public static void start(Context context, String gameId, String runId) {
        Intent intent = new Intent(context, RunActivity.class);
        intent.putExtra(EXTRA_RUN_ID, runId);
        intent.putExtra(EXTRA_GAME_ID, gameId);
        context.startActivity(intent);
    }

    private RunDetailsAdapter mAdapter;
    private TextView mTextVideoStatus;
    private YouTubePlayerSupportFragment mYoutubePlayerFragment;

    private RunViewModel mRunViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        String runId = (String) getExtra(EXTRA_RUN_ID, "");
        String gameId = (String) getExtra(EXTRA_GAME_ID, "");

        if (runId.isEmpty() || gameId.isEmpty()) {
            finish();
        }

        initializeBaseView();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RunDetailsAdapter(Dependencies.getImageLoader(), this);
        RecyclerView runDetails = findViewById(R.id.run_details);
        runDetails.setLayoutManager(mLayoutManager);
        runDetails.setAdapter(mAdapter);
        mTextVideoStatus = findViewById(R.id.video_status);
        mYoutubePlayerFragment = (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtube_player);

        RunViewModelFactory factory = new RunViewModelFactory(gameId, runId);
        mRunViewModel = ViewModelProviders.of(this, factory).get(RunViewModel.class);
        mRunViewModel.getState().observe(this, this::render);
        mRunViewModel.loadRun();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void render(State state) {
        //noinspection unchecked
        DataState<FavoriteRun> runState = (DataState<FavoriteRun>) state;

        if (runState.isLoading()) {
            load();
            return;
        }

        if (runState.hasError()) {
            showError();
            return;
        }

        Run run = runState.getData().getRun();
        mAdapter.setData(runState.getData());
        showContent();

        Video video = run.getVideo();
        if (video.isFromYoutube()) {
            mYoutubePlayerFragment.initialize(BuildConfig.SPEEDBRO_YOUTUBE_API_KEY, this);
            return;
        }

        //if it is not from youtube, hide the player and show the text message
        getSupportFragmentManager().beginTransaction().hide(mYoutubePlayerFragment).commit();
        mTextVideoStatus.setVisibility(View.VISIBLE);

        //show the message accordingly if the video is from twitch or not
        if (video.isFromTwitch()) {
            mTextVideoStatus.setText(getString(R.string.tap_to_watch_video));
            mTextVideoStatus.setOnClickListener(this);
        } else {
            mTextVideoStatus.setText(getString(R.string.no_video_available));
            mTextVideoStatus.setOnClickListener(null);
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        mRunViewModel.loadYoutubeVideo(youTubePlayer);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        //@TODO display error message
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.video_status) {
            mRunViewModel.loadTwitchVideo(this);
        }
    }

    @Override
    protected void onTryAgain() {
        mRunViewModel.loadRun();
    }

    @Override
    public void onFavoriteClicked() {
        mRunViewModel.toggleFavorite();
    }

}