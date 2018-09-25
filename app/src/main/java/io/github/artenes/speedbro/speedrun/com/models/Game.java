package io.github.artenes.speedbro.speedrun.com.models;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A game
 */
public class Game {

    private final String id;
    private final String title;
    private final String cover;
    private final String year;
    private final String platforms;
    private final List<Category> categories;

    private Game(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.cover = builder.cover;
        this.year = builder.year;
        this.platforms = builder.platforms;
        this.categories = builder.categories;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCover() {
        return cover;
    }

    public String getYear() {
        return year;
    }

    public String getPlatforms() {
        return platforms;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public static Builder build() {
        return Game.Builder.aGame();
    }

    public static final class Builder {
        private String id = "";
        private String title = "";
        private String cover = "";
        private String year = "";
        private String platforms = "";
        private List<Category> categories = new ArrayList<>();

        private Builder() {
        }

        public static Builder aGame() {
            return new Builder();
        }

        public Builder withId(@NonNull String id) {
            this.id = id;
            return this;
        }

        public Builder withTitle(@NonNull String title) {
            this.title = title;
            return this;
        }

        public Builder withCover(@NonNull String cover) {
            this.cover = cover;
            return this;
        }

        public Builder withYear(@NonNull String year) {
            this.year = year;
            return this;
        }

        public Builder withPlatforms(@NonNull String platforms) {
            this.platforms = platforms;
            return this;
        }

        public Builder withCategories(@NonNull List<Category> categories) {
            this.categories = categories;
            return this;
        }

        public Game build() {
            return new Game(this);
        }

    }
}