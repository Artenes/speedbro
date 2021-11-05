package io.github.artenes.speedbro.speedrun.com.models;

/**
 * A result from a search
 */
public class SearchItem {

    private static final String CATEGORY_GAME = "Games";
    private static final String CATEGORY_USERS = "Users";
    private static final String CATEGORY_SECTION = "Section";
    private static final String CATEGORY_SERIES = "Series";
    private static final String MORE = "More...";
    private static final String NO_RESULTS = "No results";

    private String label;
    private String url;
    private String category;

    public static SearchItem makeSection(String label) {
        return new SearchItem(label, CATEGORY_SECTION, "");
    }

    public static SearchItem makeGameItem(String name, String id) {
        return new SearchItem(name, CATEGORY_GAME, id);
    }

    private SearchItem(String label, String category, String url) {
        this.label = label;
        this.url = url;
        this.category = category;
    }

    /**
     * Empty constructor used by parsers
     */
    public SearchItem() {
    }

    public String getId() {
        if (isUser()) {
            return url.replace("user/", "");
        }
        return url;
    }

    public String getLabel() {
        return label;
    }

    public String getUrl() {
        return url;
    }

    public String getCategory() {
        return category;
    }

    public boolean isMore() {
        return MORE.equalsIgnoreCase(label);
    }

    public boolean isNoResults() {
        return NO_RESULTS.equalsIgnoreCase(category);
    }

    public boolean isGame() {
        return CATEGORY_GAME.equalsIgnoreCase(category);
    }

    public boolean isUser() {
        return CATEGORY_USERS.equalsIgnoreCase(category);
    }

    public boolean isSection() {
        return CATEGORY_SECTION.equalsIgnoreCase(category);
    }

    public boolean isSeries() {
        return CATEGORY_SERIES.equalsIgnoreCase(category);
    }

}