package io.github.artenes.speedbro.models;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.github.artenes.speedbro.speedrun.com.models.MapCluster;
import io.github.artenes.speedbro.speedrun.com.models.Run;

/**
 * A list of runs with a map
 */
@SuppressWarnings("UnusedReturnValue")
public class RunsListWithMapState extends DataState<List<Run>> {

    private boolean isShowingMap;
    private List<MapCluster> mapClusters = new ArrayList<>();
    private int latestScrollPosition;
    private int visibleClusterIndex = -1;

    public boolean isShowingMap() {
        return isShowingMap;
    }

    public RunsListWithMapState setShowingMap(boolean showingMap) {
        isShowingMap = showingMap;
        return this;
    }

    public List<MapCluster> getMapClusters() {
        return mapClusters;
    }

    public RunsListWithMapState setMapClusters(@NonNull List<MapCluster> mapClusters) {
        this.mapClusters = mapClusters;
        return this;
    }

    public int getLatestScrollPosition() {
        return latestScrollPosition;
    }

    public RunsListWithMapState setLatestScrollPosition(int latestScrollPosition) {
        this.latestScrollPosition = latestScrollPosition;
        return this;
    }

    public boolean isShowingCluster() {
        return visibleClusterIndex != -1;
    }

    public RunsListWithMapState setVisibleClusterIndex(int visibleClusterIndex) {
        this.visibleClusterIndex = visibleClusterIndex;
        return this;
    }

    public int getVisibleClusterIndex() {
        return visibleClusterIndex;
    }
}