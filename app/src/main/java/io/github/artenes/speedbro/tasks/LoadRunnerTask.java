package io.github.artenes.speedbro.tasks;

import java.io.IOException;

import io.github.artenes.speedbro.models.DataState;
import io.github.artenes.speedbro.speedrun.com.models.Runner;

/**
 * Task to load the data of a runner
 */
public class LoadRunnerTask extends LoadDataTask<Runner> {

    public LoadRunnerTask(DataState<Runner> state) {
        super(state);
    }

    @Override
    protected void loadData(String... ids) {
        if (!hasArguments(ids)) {
            mState.setHasError(true);
            return;
        }

        String runnerId = ids[0];

        try {
            Runner runner = mRepository.getRunner(runnerId);
            mState.setData(runner).setHasError(false);
        } catch (IOException exception) {
            mState.setHasError(true);
        }
    }

}