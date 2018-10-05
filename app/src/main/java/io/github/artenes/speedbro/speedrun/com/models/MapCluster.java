package io.github.artenes.speedbro.speedrun.com.models;

import java.util.List;

/**
 * A cluster of runs to display in a map
 */
public class MapCluster {

    private String country;
    private long latitude;
    private long longitude;
    private List<Run> runs;

    public MapCluster(String country, long latitude, long longitude, List<Run> runs) {
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.runs = runs;
    }

    public String getCountry() {
        return country;
    }

    public long getLatitude() {
        return latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public List<Run> getRuns() {
        return runs;
    }

}