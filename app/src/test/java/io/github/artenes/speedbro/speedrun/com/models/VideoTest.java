package io.github.artenes.speedbro.speedrun.com.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class VideoTest {

    @Test
    public void parseYoutubeUrl() {
        Video video = new Video("https://www.youtube.com/embed/vvywoNUGS94?autoplay=0&amp;wmode=transparent&start=0");
        assertTrue(video.isFromYoutube());
        assertEquals("vvywoNUGS94", video.getId());
    }

    @Test
    public void parseTwitchUrl() {
        Video video = new Video("https://player.twitch.tv/?video=v311439528&autoplay=false&time=0");
        assertTrue(video.isFromTwitch());
        assertEquals("v311439528", video.getId());
    }

    @Test
    public void parseEmptyString() {
        Video video = new Video("");
        assertFalse(video.isFromTwitch());
        assertFalse(video.isFromYoutube());
        assertEquals("", video.getId());
    }

    @Test
    public void idIsEmptyStringWhenTwitchUrlIsInvalid() {
        //do not has video query parameter
        Video video = new Video("https://player.twitch.tv/?autoplay=false&time=0");
        assertTrue(video.isFromTwitch());
        assertEquals("", video.getId());
    }

}