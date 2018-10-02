package io.github.artenes.speedbro.tasks;

import android.os.AsyncTask;

import io.github.artenes.speedbro.db.Database;
import io.github.artenes.speedbro.db.FavoriteRun;
import io.github.artenes.speedbro.db.FavoriteRunDao;
import io.github.artenes.speedbro.speedrun.com.models.Run;

/**
 * Remove or add a run in the favorite table in the local database
 */
public class FavoriteRunTask extends AsyncTask<Run, Void, Void> {

    private final FavoriteRunDao mFavoriteDao;

    public FavoriteRunTask(Database database) {
        mFavoriteDao = database.favoriteRunDao();
    }

    @Override
    protected Void doInBackground(Run... runs) {
        if (runs == null || runs.length == 0) {
            return null;
        }
        Run run = runs[0];
        FavoriteRun favoriteRun = mFavoriteDao.getRun(run.getId());
        //if the run does not exists, insert it
        if (favoriteRun == null) {
            mFavoriteDao.insert(parseToFavorite(run));
        } else {
            //otherwise remove id
            mFavoriteDao.delete(favoriteRun);
        }
        return null;
    }

    /**
     * Parse the run from the website model to the local database model
     *
     * @param run the run to parse
     * @return the instance to insert in the database
     */
    private FavoriteRun parseToFavorite(Run run) {
        FavoriteRun favoriteRun = new FavoriteRun();
        favoriteRun.setId(run.getId());
        favoriteRun.setGame_id(run.getGame().getId());
        favoriteRun.setRunner_icon(run.getFirstRunner().getIcon());
        favoriteRun.setRunner_name(run.getFirstRunner().getName());
        favoriteRun.setRunner_flag(run.getFirstRunner().getFlag());
        favoriteRun.setGame_cover(run.getGame().getCover());
        favoriteRun.setGame_title(run.getGame().getTitle());
        favoriteRun.setCategory(run.getCategory());
        favoriteRun.setPlace_icon(run.getPlacement().getIcon());
        favoriteRun.setPlace(run.getPlacement().getPlace());
        favoriteRun.setTime(run.getTime());
        return favoriteRun;
    }

}