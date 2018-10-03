package io.github.artenes.speedbro.speedrun.com.models;

/**
 * A placement in a leader board
 */
public class Placement {

    private final String place;
    private final String icon;

    public Placement(String place, String icon) {
        this.place = place;
        this.icon = icon;
    }

    public String getPlace() {
        return place;
    }

    public String getIcon() {
        return icon;
    }

}