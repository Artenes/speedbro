package io.github.artenes.speedbro.speedrun.com;

import android.support.annotation.NonNull;

/**
 * The contract of the website: authority, endpoints, urls, etc.
 */
public class Contract {

    private static final String AUTHORITY = "https://www.speedrun.com/";
    public static final String BASE_API_URL = AUTHORITY + "api/v1/";
    public static final String LATEST_RUNS = AUTHORITY + "ajax_latestleaderboard.php?amount=40";

    /**
     * Get a relative uri as an absolute path to eh website
     *
     * @param relativeUri the relative uri
     * @return the absolute path
     */
    public static String asAbsolutePath(@NonNull String relativeUri) {
        if (relativeUri.isEmpty()) {
            return relativeUri;
        }
        return AUTHORITY + Utils.withoutStartingSlash(relativeUri);
    }

    /**
     * Get the avatar of a runner
     *
     * @param runner the runner username
     * @return the absolute path to its avatar. This just assumes the runner
     * has an avatar in this uri, this does not guarantee if the runner
     * has really an avatar or not
     */
    public static String runnerAvatar(@NonNull String runner) {
        if (runner.isEmpty()) {
            return runner;
        }
        return AUTHORITY + "themes/user/" + runner + "/image.png";
    }

    /**
     * Get the flag icon for a country
     *
     * @param countryCode the code for the country (as the website standard
     *                    - ru (Russia), gb (United Kingdom), etc, 2 letters)
     * @return the absolute path for the flag icon
     */
    public static String flagIcon(@NonNull String countryCode) {
        if (countryCode.isEmpty()) {
            return countryCode;
        }
        return AUTHORITY + "images/flags/" + countryCode + ".png";
    }

    /**
     * Get the url for a run
     *
     * @param gameId the game id
     * @param runId  the run id
     * @return the absolute path for the run
     */
    public static String runUrl(@NonNull String gameId, @NonNull String runId) {
        return AUTHORITY + gameId + "/run/" + runId;
    }

}