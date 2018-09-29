package io.github.artenes.speedbro.speedrun.com;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * The contract of the website: authority, endpoints, urls, etc.
 */
public class Contract {

    private static final String AUTHORITY = "https://www.speedrun.com/";
    public static final String BASE_API_URL = AUTHORITY + "api/v1/";
    public static final String LATEST_RUNS = AUTHORITY + "ajax_latestleaderboard.php?amount=40";

    /**
     * Get a relative uri as an absolute path to the website
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
     * Get the absolute url to a runner
     *
     * @param id the id of the runner (its username)
     * @return the absolute path to the runner
     */
    public static String runnerUrl(@NonNull String id) {
        if (id.isEmpty()) {
            return id;
        }
        return AUTHORITY + "user/" + id;
    }

    /**
     * Get the url to fetch a runner's runs
     * The id can be found in the runner's page
     *
     * @param integerId the id of the runner (not its username)
     * @return the url to fetch the runner's runs
     */
    public static String runnerRunsUrl(@NonNull String integerId) {
        if (integerId.isEmpty()) {
            return integerId;
        }
        return AUTHORITY + "ajax_userleaderboard.php?user=" + integerId;
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

    /**
     * Get the url for a leader board
     *
     * @param gameId     the id of the game
     * @param categoryId the id of the category
     * @param arguments  additional optional arguments for the url (e.g. arg1=val1&arg2=val2)
     * @return the absolute path for the leader board
     */
    public static String leaderBoardUrl(@NonNull String gameId, @NonNull String categoryId, @Nullable String arguments) {
        if (gameId.isEmpty() || categoryId.isEmpty()) {
            return "";
        }
        String url = AUTHORITY + "ajax_leaderboard.php?game=" + gameId + "&category=" + categoryId;
        if (arguments != null && !arguments.isEmpty()) {
            url += "&" + arguments;
        }
        return url;
    }

    /**
     * Get the url for a game
     *
     * @param id the id of the game (its abbreviation)
     * @return the absolute path for the game
     */
    public static String gameUrl(@NonNull String id) {
        if (id.isEmpty()) {
            return id;
        }
        return AUTHORITY + id + "/full_game";
    }

    /**
     * Get the absolute path to the game background image
     * This always returns a path regardless if the game
     * really has or not a background image
     *
     * @param id the id of the game (its abbreviation)
     * @return the absolute path for the game background
     */
    public static String gameBackgroundImage(@NonNull String id) {
        if (id.isEmpty()) {
            return id;
        }
        return AUTHORITY + "themes/" + id + "/background.png";
    }

}