package io.github.artenes.speedbro.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.artenes.speedbro.models.RunsListWithMapState;
import io.github.artenes.speedbro.speedrun.com.RunsRepository;
import io.github.artenes.speedbro.speedrun.com.models.MapCluster;
import io.github.artenes.speedbro.speedrun.com.models.Run;
import io.github.artenes.speedbro.utils.CountriesCenters;

/**
 * Loads a list of latest runs in the background
 * and notify the LiveData object by creating a new state object.
 */
public class LoadLatestRunsWithMapTask extends AsyncTask<Void, Void, Void> {

    private final static String TAG = LoadLatestRunsWithMapTask.class.getSimpleName();
    private final RunsListWithMapState mState;
    private final RunsRepository mRepository;

    public LoadLatestRunsWithMapTask(RunsListWithMapState state) {
        mState = state;
        mRepository = new RunsRepository();
    }

    @Override
    protected void onPreExecute() {
        mState.setLoading(true).update();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            List<Run> latestRuns = mRepository.getLatestRuns();
            //if setMapClusters is move below the other method calls
            //this cause an error due to the return type of the other methods
            mState
                    .setMapClusters(groupRunsByLocation(latestRuns))
                    .setData(latestRuns)
                    .setHasError(false);
        } catch (IOException exception) {
            mState.setHasError(true);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void nothing) {
        mState.setLoading(false).update();
    }

    private List<MapCluster> groupRunsByLocation(List<Run> runs) {
        List<MapCluster> clusters = new ArrayList<>();
        Map<String, List<Run>> countries = new HashMap<>();

        for (Run run : runs) {
            String country = run.getFirstRunner().getCountry();
            //ignores empty countries
            if (country.isEmpty()) {
                continue;
            }

            //checks if the country already exists in the mapping
            List<Run> runsInCountry = countries.get(country);

            //if it does not exists, map the location to a new empty list
            if (runsInCountry == null) {
                runsInCountry = new ArrayList<>();
                countries.put(country, runsInCountry);
            }

            //and then add the current run to this location
            runsInCountry.add(run);
        }

        //after the grouping, start the conversion to the MapCluster type
        for (String country : countries.keySet()) {
            CountriesCenters.Coordinates coordinates = CountriesCenters.get(country);
            Log.d(TAG, String.format("Country: %s, location: %s", country, coordinates));
            if (coordinates.isEmpty()) {
                continue;
            }
            clusters.add(new MapCluster(country, coordinates.latitude, coordinates.longitude, countries.get(country)));
        }

        return clusters;
    }

}