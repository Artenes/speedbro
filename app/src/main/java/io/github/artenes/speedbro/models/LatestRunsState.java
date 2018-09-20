package io.github.artenes.speedbro.models;

import java.util.ArrayList;
import java.util.List;

import io.github.artenes.speedbro.speedrun.com.models.LatestRun;

/**
 * The state of the latest runs view.
 * The static methods indicates the possible changes in the view.
 * Since this is an immutable object, the state can only be changed
 * by calling the static methods.
 */
public class LatestRunsState implements State {

    private final boolean isLoading;
    private final boolean hasError;
    private final List<LatestRun> runs;

    public static LatestRunsState displayError() {
        return new LatestRunsState(false, true, new ArrayList<LatestRun>());
    }

    public static LatestRunsState displayLoading() {
        return new LatestRunsState(true, false, new ArrayList<LatestRun>());
    }

    public static LatestRunsState displayRuns(List<LatestRun> runs) {
        return new LatestRunsState(false, false, runs);
    }

    public LatestRunsState(boolean isLoading, boolean hasError, List<LatestRun> runs) {
        this.isLoading = isLoading;
        this.hasError = hasError;
        this.runs = runs;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public boolean hasError() {
        return hasError;
    }

    public List<LatestRun> getRuns() {
        return runs;
    }

}