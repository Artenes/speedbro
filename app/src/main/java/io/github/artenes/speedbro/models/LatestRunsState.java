package io.github.artenes.speedbro.models;

import java.util.ArrayList;
import java.util.List;

import io.github.artenes.speedbro.speedrun.com.models.Run;

/**
 * The state of the latest runs view.
 * The static methods indicates the possible changes in the view.
 * Since this is an immutable object, the state can only be changed
 * by calling the static methods.
 */
public class LatestRunsState extends State {

    private final boolean isLoading;
    private final boolean hasError;
    private final List<Run> runs;

    public static LatestRunsState displayError() {
        return new LatestRunsState(false, true, new ArrayList<>());
    }

    public static LatestRunsState displayLoading() {
        return new LatestRunsState(true, false, new ArrayList<>());
    }

    public static LatestRunsState displayRuns(List<Run> runs) {
        return new LatestRunsState(false, false, runs);
    }

    private LatestRunsState(boolean isLoading, boolean hasError, List<Run> runs) {
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

    public List<Run> getRuns() {
        return runs;
    }

}