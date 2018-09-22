package io.github.artenes.speedbro.speedrun.com.models;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A runner of a game
 */
public class Runner {

    private final String id;
    private final String name;
    private final String flag;
    private final String country;
    private final String icon;
    private final List<SocialMedia> socialMedias;
    private final List<Run> runs;

    private Runner(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.flag = builder.flag;
        this.country = builder.country;
        this.icon = builder.icon;
        this.socialMedias = builder.socialMedias;
        this.runs = builder.runs;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFlag() {
        return flag;
    }

    public String getCountry() {
        return country;
    }

    public String getIcon() {
        return icon;
    }

    public List<SocialMedia> getSocialMedias() {
        return socialMedias;
    }

    public List<Run> getRuns() {
        return runs;
    }

    public static final class Builder {
        private String id = "";
        private String name = "";
        private String flag = "";
        private String country = "";
        private String icon = "";
        private List<SocialMedia> socialMedias = new ArrayList<>();
        private List<Run> runs = new ArrayList<>();

        private Builder() {
        }

        public static Builder aRunner() {
            return new Builder();
        }

        public Builder withId(@NonNull String id) {
            this.id = id;
            return this;
        }

        public Builder withName(@NonNull String name) {
            this.name = name;
            return this;
        }

        public Builder withFlag(@NonNull String flag) {
            this.flag = flag;
            return this;
        }

        public Builder withCountry(@NonNull String country) {
            this.country = country;
            return this;
        }

        public Builder withIcon(@NonNull String icon) {
            this.icon = icon;
            return this;
        }

        public Builder withSocialMedia(@NonNull List<SocialMedia> socialMedias) {
            this.socialMedias = socialMedias;
            return this;
        }

        public Builder withRuns(@NonNull List<Run> runs) {
            this.runs = runs;
            return this;
        }

        public Runner build() {
            return new Runner(this);
        }

    }

}