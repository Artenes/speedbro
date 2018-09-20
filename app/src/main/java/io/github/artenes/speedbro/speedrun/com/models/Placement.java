package io.github.artenes.speedbro.speedrun.com.models;

import android.support.annotation.NonNull;

/**
 * A placement in a leader board
 */
public class Placement {

    private final String place;
    private final String icon;

    private Placement(Builder builder) {
        this.place = builder.place;
        this.icon = builder.icon;
    }

    public String getPlace() {
        return place;
    }

    public String getIcon() {
        return icon;
    }

    public static final class Builder {
        private String place = "";
        private String icon = "";

        private Builder() {
        }

        public static Builder aPlacement() {
            return new Builder();
        }

        public Builder withPlace(@NonNull String place) {
            this.place = place;
            return this;
        }

        public Builder withIcon(@NonNull String icon) {
            this.icon = icon;
            return this;
        }

        public Placement build() {
            return new Placement(this);
        }
    }

}