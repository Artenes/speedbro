package io.github.artenes.speedbro.models;

import io.github.artenes.speedbro.speedrun.com.models.Run;

/**
 * The state of a run.
 * The static methods indicates the possible changes in the view.
 * Since this is an immutable object, the state can only be changed
 * by calling the static methods.
 */
public class RunState implements State {

    private final boolean isLoading;
    private final boolean hasError;
    private final Run run;

    public static RunState displayError() {
        return new RunState(false, true, Run.Builder.aRun().build());
    }

    public static RunState displayLoading() {
        return new RunState(true, false, Run.Builder.aRun().build());
    }

    public static RunState displayRun(Run run) {
        return new RunState(false, false, run);
    }

    private RunState(boolean isLoading, boolean hasError, Run run) {
        this.isLoading = isLoading;
        this.hasError = hasError;
        this.run = run;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public boolean hasError() {
        return hasError;
    }

    public Run getRun() {
        return run;
    }

}