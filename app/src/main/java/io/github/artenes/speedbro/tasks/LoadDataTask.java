package io.github.artenes.speedbro.tasks;

import android.os.AsyncTask;

import io.github.artenes.speedbro.models.DataState;
import io.github.artenes.speedbro.speedrun.com.RunsRepository;
import io.github.artenes.speedbro.utils.SpeedBroApplication;

/**
 * An AsyncTask to load a piece of data
 * This will already use a given state to change it in or out of loading state
 *
 * @param <T> the type of the data to load
 */
abstract class LoadDataTask<T> extends AsyncTask<String, Void, Void> {

    final RunsRepository mRepository;
    final DataState<T> mState;

    LoadDataTask(DataState<T> state) {
        mRepository = SpeedBroApplication.getRunsRepository();
        mState = state;
    }

    protected abstract void loadData(String... args);

    @Override
    protected void onPreExecute() {
        mState.setLoading(true).update();
    }

    @Override
    protected Void doInBackground(String... args) {
        loadData(args);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        mState.setLoading(false).update();
    }

    boolean hasArguments(String[] arguments) {
        return arguments != null && arguments.length > 0;
    }

}