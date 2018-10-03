package io.github.artenes.speedbro.models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.artenes.speedbro.db.Database;
import io.github.artenes.speedbro.db.FavoriteRun;
import io.github.artenes.speedbro.speedrun.com.models.Game;
import io.github.artenes.speedbro.speedrun.com.models.Placement;
import io.github.artenes.speedbro.speedrun.com.models.Run;
import io.github.artenes.speedbro.speedrun.com.models.Runner;

/**
 * View model for favorite runs
 */
public class FavoritesViewModel extends AndroidViewModel {

    private final DataState<List<Run>> mState = new DataState<>();
    private final Database mDatabase;
    private final LiveData<List<FavoriteRun>> mDatabaseList;

    public FavoritesViewModel(@NonNull Application application) {
        super(application);
        mDatabase = Database.getDatabase(application);

        //this model is different from others because it has to watch for changes in database
        mState.setLoading(true).update();
        mDatabaseList = mDatabase.favoriteRunDao().getAllAsync();
        mDatabaseList.observeForever(mObserver);
    }

    public LiveData<State> getState() {
        return mState;
    }

    @Override
    protected void onCleared() {
        mDatabaseList.removeObserver(mObserver);
    }

    /**
     * Parse a favorite run from the db to a run model
     *
     * @param favoriteRun the favorite run to parse
     * @return the parsed run
     */
    private Run parseFavoriteToRun(FavoriteRun favoriteRun) {

        Game game = Game.build()
                .withId(favoriteRun.getGame_id())
                .withTitle(favoriteRun.getGame_title())
                .withCover(favoriteRun.getGame_cover())
                .build();

        Runner runner = Runner.build()
                .withId(favoriteRun.getRunner_id())
                .withName(favoriteRun.getRunner_name())
                .withIcon(favoriteRun.getRunner_icon())
                .withFlag(favoriteRun.getRunner_flag())
                .build();

        Placement placement = new Placement(favoriteRun.getPlace(), favoriteRun.getPlace_icon());

        //noinspection ArraysAsListWithZeroOrOneArgument
        return Run.build()
                .withId(favoriteRun.getId())
                .withCategory(favoriteRun.getCategory())
                .withTime(favoriteRun.getTime())
                .withGame(game)
                .withRunners(Arrays.asList(runner))
                .withPlacement(placement)
                .build();

    }

    /**
     * Observer that will notify the state of changes in the list of favorite runs
     */
    private final Observer<List<FavoriteRun>> mObserver = new Observer<List<FavoriteRun>>() {

        @Override
        public void onChanged(@Nullable List<FavoriteRun> favoriteRuns) {
            mState.setLoading(true).update();
            List<Run> runs = new ArrayList<>(favoriteRuns.size());

            for (FavoriteRun favoriteRun : favoriteRuns) {
                runs.add(parseFavoriteToRun(favoriteRun));
            }

            mState.setData(runs).setHasError(false);
            mState.setLoading(false).update();
        }

    };

}