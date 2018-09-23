package io.github.artenes.speedbro.models;

import io.github.artenes.speedbro.speedrun.com.models.Runner;
import io.github.artenes.speedbro.tasks.LoadRunnerTask;

/**
 * ViewModel of a runner
 */
public class RunnerViewModel extends DataViewModel<Runner> {

    private final String mRunnerId;

    public RunnerViewModel(String runnerId) {
        mRunnerId = runnerId;
    }

    @Override
    protected void runLoadTask() {
        new LoadRunnerTask(mData).execute(mRunnerId);
    }

}