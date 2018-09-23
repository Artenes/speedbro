package io.github.artenes.speedbro.tasks;

import android.os.AsyncTask;

import io.github.artenes.speedbro.models.DataState;
import io.github.artenes.speedbro.speedrun.com.RunsRepository;

/**
 * An AsyncTask to load a piece of data
 * This will already use a given state to change it in or out of loading state
 *
 * @param <T> the type of the data to load
 */
public abstract class LoadDataTask<T> extends AsyncTask<String, Void, Void> {

    protected final RunsRepository mRepository;
    protected final DataState<T> mState;

    public LoadDataTask(DataState<T> state) {
        mRepository = new RunsRepository();
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

    protected boolean hasArguments(String[] arguments) {
        return arguments != null && arguments.length > 0;
    }

}