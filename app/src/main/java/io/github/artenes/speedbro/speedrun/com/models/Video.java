package io.github.artenes.speedbro.speedrun.com.models;

import android.support.annotation.NonNull;

import okhttp3.HttpUrl;

/**
 * A video of a run
 */
public class Video {

    private final String id;
    private final boolean isFromYoutube;
    private final boolean isFromTwitch;

    /**
     * Create a video with the given video url. The following accepted formats are:
     * <p>
     * - https://www.youtube.com/embed/vvywoNUGS94
     * - https://player.twitch.tv/?video=v311439528
     * <p>
     * Anything but these will result in an empty video
     *
     * @param videoUrl the video url
     */
    public Video(@NonNull String videoUrl) {
        HttpUrl url = HttpUrl.parse(videoUrl);
        if (url == null) {
            id = "";
            isFromYoutube = false;
            isFromTwitch = false;
            return;
        }

        if (url.host().contains("youtube")) {
            id = url.pathSegments().get(url.pathSegments().size() - 1);
            isFromYoutube = true;
            isFromTwitch = false;
        } else if (url.host().contains("twitch")) {
            String videoId = url.queryParameter("video");
            id = videoId == null || videoId.isEmpty() ? "" : videoId.substring(1);
            isFromTwitch = true;
            isFromYoutube = false;
        } else {
            id = "";
            isFromYoutube = false;
            isFromTwitch = false;
        }
    }

    public String getId() {
        return id;
    }

    public boolean isFromYoutube() {
        return isFromYoutube;
    }

    public boolean isFromTwitch() {
        return isFromTwitch;
    }

    /**
     * This is the uri to use in the browser
     *
     * @return the full uri to the video
     */
    public String getTwitchHttpUri() {
        if (id.isEmpty()) {
            return id;
        }
        return "https://www.twitch.tv/videos/" + id;
    }

    /**
     * This is the uri to use between applications
     *
     * @return the full uri to the video
     */
    public String getTwitchUrl() {
        if (id.isEmpty()) {
            return id;
        }
        return "twitch://video/" + id;
    }

}