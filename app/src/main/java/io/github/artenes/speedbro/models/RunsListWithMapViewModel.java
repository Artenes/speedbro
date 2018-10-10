package io.github.artenes.speedbro.models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import io.github.artenes.speedbro.tasks.LoadLatestRunsWithMapTask;

/**
 * ViewModel to display list of runs with map
 * This does not inherit from DataViewModel because
 * the type of mData is different
 */
public class RunsListWithMapViewModel extends ViewModel {

    private final RunsListWithMapState mData = new RunsListWithMapState();

    public LiveData<State> getState() {
        return mData;
    }

    public void load() {
        if (mData.getData() != null) {
            return;
        }
        runLoadTask();
    }

    public void setScrollPosition(int scrollPosition) {
        mData.setLatestScrollPosition(scrollPosition);
    }

    public void swapViews() {
        boolean showMap = !mData.isShowingMap();
        mData.setShowingMap(showMap).update();
    }

    private void runLoadTask() {
        new LoadLatestRunsWithMapTask(mData).execute();
    }

    public void setVisibleCluster(int index) {
        mData.setVisibleClusterIndex(index);
    }

    public int getVisibleClusterIndex() {
        return mData.getVisibleClusterIndex();
    }

    public boolean isShowingCluster() {
        return mData.isShowingCluster();
    }

}