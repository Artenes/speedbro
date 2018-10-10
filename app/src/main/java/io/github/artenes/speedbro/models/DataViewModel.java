package io.github.artenes.speedbro.models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

/**
 * A ViewModel to load a piece of data
 *
 * @param <T> the type of the data to load
 */
public abstract class DataViewModel<T> extends ViewModel {

    DataState<T> mData = new DataState<>();

    protected abstract void runLoadTask();

    public LiveData<State> getState() {
        return mData;
    }

    public void load() {
        if (mData.getData() != null) {
            return;
        }
        runLoadTask();
    }

}