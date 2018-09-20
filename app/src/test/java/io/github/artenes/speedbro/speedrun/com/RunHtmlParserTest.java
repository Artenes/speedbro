package io.github.artenes.speedbro.speedrun.com;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import io.github.artenes.speedbro.TestUtils;
import io.github.artenes.speedbro.speedrun.com.models.Run;
import io.github.artenes.speedbro.speedrun.com.website.RunHtmlParser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test for the RunHtmlParser
 */
public class RunHtmlParserTest {

    private RunHtmlParser parser;
    private Run fullRun;

    @Before
    public void setUp() throws IOException {
        parser = new RunHtmlParser();
        File file = TestUtils.getFile(this, "run.html");
        Document fullDocument = Jsoup.parse(file, "UTF-8");
        fullRun = parser.parse(fullDocument);
    }

    @Test
    public void extractGameDetails() {
        assertEquals("To_The_Top", fullRun.getGame().getId());
        assertEquals("To The Top", fullRun.getGame().getTitle());
        assertEquals("https://www.speedrun.com/themes/To_The_Top/cover-256.png", fullRun.getGame().getCover());
        assertEquals("2017", fullRun.getGame().getYear());
        assertEquals("PS4, Vive, Rift, PS4Pro", fullRun.getGame().getPlatforms());
    }

    @Test
    public void extractVideoDetails() {
        assertTrue(fullRun.getVideo().isFromYoutube());
        assertEquals("vvywoNUGS94", fullRun.getVideo().getId());
    }

    @Test
    public void extractRunDetails() {
        assertEquals("Honey: PC - Version 2.0", fullRun.getCategory());
        assertEquals("2m 30s 941ms", fullRun.getTime());
        assertEquals("1st", fullRun.getPlacement().getPlace());
        assertEquals("https://www.speedrun.com/themes/To_The_Top/1st.png", fullRun.getPlacement().getIcon());
        assertEquals("Savegame: https://www.speedrun.com/saves/3_25_23_8rafj.zip", fullRun.getCommentary());
    }

    @Test
    public void extractRunners() {
        assertEquals(3, fullRun.getRunners().size());
        assertEquals("trickster00", fullRun.getRunners().get(0).getId());
        assertEquals("trickster00", fullRun.getRunners().get(0).getName());
        assertEquals("https://www.speedrun.com/images/flags/ee.png", fullRun.getRunners().get(0).getFlag());
        assertEquals("https://www.speedrun.com/themes/user/trickster00/image.png", fullRun.getRunners().get(0).getIcon());

        assertEquals("Bilka", fullRun.getRunners().get(1).getId());
        assertEquals("Bilka", fullRun.getRunners().get(1).getName());
        assertEquals("https://www.speedrun.com/images/flags/de.png", fullRun.getRunners().get(1).getFlag());
        assertEquals("https://www.speedrun.com/themes/user/Bilka/image.png", fullRun.getRunners().get(1).getIcon());

        assertEquals("peet1993", fullRun.getRunners().get(2).getId());
        assertEquals("peet1993", fullRun.getRunners().get(2).getName());
        assertEquals("https://www.speedrun.com/images/flags/de.png", fullRun.getRunners().get(2).getFlag());
        assertEquals("https://www.speedrun.com/themes/user/peet1993/image.png", fullRun.getRunners().get(2).getIcon());
    }

    @Test
    public void extractNothingFromEmptyDocument() {
        Run run = parser.parse(Jsoup.parse(""));
        assertEquals("", run.getCategory());
    }

    @Test
    public void extractNoRunnerFromRunWithoutRunners() throws IOException {
        Run run = parser.parse(Jsoup.parse(TestUtils.getFile(this, "run_without_runners.html"), "UTF-8"));
        assertEquals(0, run.getRunners().size());
    }

    @Test
    public void extractGameCoverRegardlessOfImageClassName() throws IOException {
        Run run = parser.parse(Jsoup.parse(TestUtils.getFile(this, "run_with_wide_game_cover.html"), "UTF-8"));
        assertEquals("https://www.speedrun.com/themes/To_The_Top/cover-256.png", run.getGame().getCover());
    }

    @Test
    public void extractVideoFromTwitch() throws IOException {
        Run run = parser.parse(Jsoup.parse(TestUtils.getFile(this, "run_with_twitch_video.html"), "UTF-8"));
        assertTrue(run.getVideo().isFromTwitch());
        assertEquals("v312235878", run.getVideo().getId());
    }

}