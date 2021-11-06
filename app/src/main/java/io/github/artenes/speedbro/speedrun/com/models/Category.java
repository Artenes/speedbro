package io.github.artenes.speedbro.speedrun.com.models;

/**
 * A run category
 */
public class Category {

    private final String name;
    private final String id;


    public Category(String id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

}