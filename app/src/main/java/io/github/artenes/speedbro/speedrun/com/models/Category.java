package io.github.artenes.speedbro.speedrun.com.models;

/**
 * A run category
 */
public class Category {

    private final String name;
    private final String url;

    public Category(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

}