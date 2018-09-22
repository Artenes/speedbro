package io.github.artenes.speedbro.speedrun.com.models;

/**
 * A social media (not the bad ones)
 * This one does not use a Builder because it is very simple
 */
public class SocialMedia {

    private final String icon;
    private final String link;

    public SocialMedia(String icon, String link) {
        this.icon = icon;
        this.link = link;
    }

    public String getIcon() {
        return icon;
    }

    public String getLink() {
        return link;
    }

}