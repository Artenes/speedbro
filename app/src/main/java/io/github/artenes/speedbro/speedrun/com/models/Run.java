package io.github.artenes.speedbro.speedrun.com.models;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A run (gotta go fast, gotta go fast, gotta go faster, faster, faster, faster!)
 */
public class Run {

    private final Game game;
    private final Video video;
    private final String category;
    private final String time;
    private final List<Runner> runners;
    private final Placement placement;
    private final String commentary;

    private Run(Builder builder) {
        this.game = builder.game;
        this.video = builder.video;
        this.category = builder.category;
        this.time = builder.time;
        this.runners = builder.runners;
        this.placement = builder.placement;
        this.commentary = builder.commentary;
    }

    public Game getGame() {
        return game;
    }

    public Video getVideo() {
        return video;
    }

    public String getTime() {
        return time;
    }

    public String getCategory() {
        return category;
    }

    public List<Runner> getRunners() {
        return runners;
    }

    public Placement getPlacement() {
        return placement;
    }

    public boolean hasCommentary() {
        return commentary != null && !commentary.isEmpty();
    }

    public String getCommentary() {
        return commentary;
    }

    public static final class Builder {
        private Game game = Game.Builder.aGame().build();
        private Video video = new Video("");
        private String category = "";
        private String time = "";
        private List<Runner> runners = new ArrayList<>();
        private Placement placement = Placement.Builder.aPlacement().build();
        private String commentary = "";

        private Builder() {
        }

        public static Builder aRun() {
            return new Builder();
        }

        public Builder withGame(@NonNull Game game) {
            this.game = game;
            return this;
        }

        public Builder withVideo(@NonNull Video video) {
            this.video = video;
            return this;
        }

        public Builder withCategory(@NonNull String category) {
            this.category = category;
            return this;
        }

        public Builder withTime(@NonNull String time) {
            this.time = time;
            return this;
        }

        public Builder withRunners(@NonNull List<Runner> runners) {
            this.runners = runners;
            return this;
        }

        public Builder withPlacement(@NonNull Placement placement) {
            this.placement = placement;
            return this;
        }

        public Builder withCommentary(@NonNull String commentary) {
            this.commentary = commentary;
            return this;
        }

        public Run build() {
            return new Run(this);
        }
    }

}