package io.github.artenes.speedbro.speedrun.com.models;

/**
 * A platform or game console
 */
public class Platform {

    private final String name;
    private final String region;
    private final String flag;

    public Platform(String name, String region, String flag) {
        this.name = name;
        this.region = region;
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public String getFlag() {
        return flag;
    }

}