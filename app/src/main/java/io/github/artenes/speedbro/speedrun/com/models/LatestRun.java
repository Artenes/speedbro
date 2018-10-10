package io.github.artenes.speedbro.speedrun.com.models;

/**
 * A run that was recently submitted to the website
 * This one has a different structure compared to other
 * POJOs that represent the API response, due to the fact
 * that this one represent a response straight from the
 * website as a HTML page. We had to scrap the website for these
 * because the endpoint to return the latest runs was taking
 * up to 20 seconds to respond (2018-09-11).
 *
 */
class LatestRun {

    private String id;
    private String gameId;
    private String runnerId;
    private String gameCover;
    private String gameTitle;
    private String category;
    private String time;
    private String position;
    private String positionIcon;
    private String country;
    private String countryIcon;
    private String runnerName;
    private String runnerDisplayName;
    private String runnerIcon;

    public boolean hasRunnerId() {
        return runnerId != null && !runnerId.isEmpty();
    }

    public boolean hasRunnerName() {
        return runnerName != null && !runnerName.isEmpty();
    }

    public boolean hasPositionIcon() {
        return positionIcon != null && !positionIcon.isEmpty();
    }

    public boolean hasCountryIcon() {
        return countryIcon != null && !countryIcon.isEmpty();
    }

    public String getId() {
        return id;
    }

    public String getGameId() {
        return gameId;
    }

    public String getRunnerId() {
        return runnerId;
    }

    public String getGameCover() {
        return gameCover;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public String getCategory() {
        return category;
    }

    public String getTime() {
        return time;
    }

    public String getPosition() {
        return position;
    }

    public String getPositionIcon() {
        return positionIcon;
    }

    public String getCountry() {
        return country;
    }

    public String getCountryIcon() {
        return countryIcon;
    }

    public String getRunnerName() {
        return runnerName;
    }

    public String getRunnerDisplayName() {
        return runnerDisplayName;
    }

    public String getRunnerIcon() {
        return runnerIcon;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setRunnerId(String runnerId) {
        this.runnerId = runnerId;
    }

    public void setGameCover(String gameCover) {
        this.gameCover = gameCover;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setPositionIcon(String positionIcon) {
        this.positionIcon = positionIcon;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCountryIcon(String countryIcon) {
        this.countryIcon = countryIcon;
    }

    public void setRunnerName(String runnerName) {
        this.runnerName = runnerName;
    }

    public void setRunnerDisplayName(String runnerDisplayName) {
        this.runnerDisplayName = runnerDisplayName;
    }

    public void setRunnerIcon(String runnerIcon) {
        this.runnerIcon = runnerIcon;
    }

}