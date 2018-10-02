package io.github.artenes.speedbro.speedrun.com.models;

/**
 * A run that can be favorite
 */
public class FavoriteRun {

    private final Run run;
    private boolean isFavorite;

    public FavoriteRun(Run run, boolean isFavorite) {
        this.run = run;
        this.isFavorite = isFavorite;
    }

    public Run getRun() {
        return run;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

}