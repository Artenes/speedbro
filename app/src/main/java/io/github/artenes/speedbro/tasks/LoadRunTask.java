package io.github.artenes.speedbro.tasks;

import android.os.AsyncTask;

import java.io.IOException;

import io.github.artenes.speedbro.db.Database;
import io.github.artenes.speedbro.db.FavoriteRunDao;
import io.github.artenes.speedbro.models.DataState;
import io.github.artenes.speedbro.speedrun.com.RunsRepository;
import io.github.artenes.speedbro.speedrun.com.models.FavoriteRun;
import io.github.artenes.speedbro.speedrun.com.models.Run;

/**
 * Loads a run in the background
 * and notify the LiveData object by creating a new state object.
 */
public class LoadRunTask extends AsyncTask<String, Void, Void> {

    private final DataState<FavoriteRun> mState;
    private final RunsRepository mRepository;
    private final FavoriteRunDao mFavoriteDao;

    public LoadRunTask(Database database, DataState<FavoriteRun> state) {
        mState = state;
        mRepository = new RunsRepository();
        mFavoriteDao = database.favoriteRunDao();
    }

    @Override
    protected void onPreExecute() {
        //whenever the update method is called
        //any listener of this object (usually an Activity)
        //will have its UI updated
        //so always call it from the main thread
        mState.setLoading(true).update();
    }

    @Override
    protected Void doInBackground(String... ids) {
        if (ids == null || ids.length < 2) {
            mState.setHasError(true);
            return null;
        }
        try {
            String gameId = ids[0];
            String runId = ids[1];

            Run run = mRepository.getRun(gameId, runId);
            //checks in the database if the run is favorite or not
            io.github.artenes.speedbro.db.FavoriteRun favoriteRunFromDb = mFavoriteDao.getRun(runId);
            FavoriteRun favoriteRun = new FavoriteRun(run, favoriteRunFromDb != null);

            mState.setData(favoriteRun).setHasError(false);
        } catch (IOException exception) {
            mState.setHasError(true);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void noValue) {
        mState.setLoading(false).update();
    }

}