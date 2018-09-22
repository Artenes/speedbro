package io.github.artenes.speedbro.models;

import io.github.artenes.speedbro.speedrun.com.models.Run;

/**
 * The state of a run.
 * The static methods indicates the possible changes in the view.
 * Since this is an immutable object, the state can only be changed
 * by calling the static methods.
 */
public class RunState extends State {

    private boolean isLoadingRun = false;
    private boolean hasErrorOnRun = false;
    private Run run = null;

    public boolean isLoadingRun() {
        return isLoadingRun;
    }

    public RunState setLoadingRun(boolean loadingRun) {
        isLoadingRun = loadingRun;
        return this;
    }

    public boolean hasErrorOnRun() {
        return hasErrorOnRun;
    }

    public RunState setErrorOnRun(boolean hasErrorOnRun) {
        this.hasErrorOnRun = hasErrorOnRun;
        return this;
    }

    public Run getRun() {
        return run;
    }

    public RunState setRun(Run run) {
        this.run = run;
        return this;
    }

}