package io.github.artenes.speedbro.speedrun.com.models;

import android.support.annotation.NonNull;

/**
 * A game
 */
public class Game {

    private final String id;
    private final String title;
    private final String cover;
    private final String year;
    private final String platforms;

    private Game(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.cover = builder.cover;
        this.year = builder.year;
        this.platforms = builder.platforms;
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

    public static final class Builder {
        private String id = "";
        private String title = "";
        private String cover = "";
        private String year = "";
        private String platforms = "";

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

        public Game build() {
            return new Game(this);
        }

    }
}