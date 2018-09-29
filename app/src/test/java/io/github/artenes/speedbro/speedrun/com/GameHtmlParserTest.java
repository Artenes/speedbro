package io.github.artenes.speedbro.speedrun.com;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import io.github.artenes.speedbro.TestUtils;
import io.github.artenes.speedbro.speedrun.com.models.Game;
import io.github.artenes.speedbro.speedrun.com.website.GameHtmlParser;

import static org.junit.Assert.assertEquals;

/**
 * Test for GameHtmlParser class
 */
public class GameHtmlParserTest {

    private GameHtmlParser parser;
    private Game fullGame;

    @Before
    public void setUp() throws IOException {
        File gameHtmlPage = TestUtils.getFile(this, "game.html");
        Document document = Jsoup.parse(gameHtmlPage, "UTF-8");
        parser = new GameHtmlParser();
        fullGame = parser.parse(document);
    }

    @Test
    public void extractGameDetails() {
        assertEquals("sm64", fullGame.getId());
        assertEquals("Super Mario 64", fullGame.getTitle());
        assertEquals("https://www.speedrun.com/themes/sm64/cover-256.png", fullGame.getCover());
        assertEquals("1996", fullGame.getYear());
        assertEquals("N64, WiiVC, WiiUVC", fullGame.getPlatforms());
        assertEquals("https://www.speedrun.com/themes/sm64/background.png", fullGame.getBackground());
    }

    @Test
    public void extractGameCategories() {
        assertEquals("120 Star", fullGame.getCategories().get(0).getName());
        assertEquals("https://www.speedrun.com/ajax_leaderboard.php?game=sm64&category=2409&verified=1&variable5138=16457&loadtimes=0&emulator=2", fullGame.getCategories().get(0).getUrl());

        assertEquals("70 Star", fullGame.getCategories().get(1).getName());
        assertEquals("https://www.speedrun.com/ajax_leaderboard.php?game=sm64&category=2410&verified=1&variable5138=16457&loadtimes=0&emulator=2", fullGame.getCategories().get(1).getUrl());

        assertEquals("16 Star", fullGame.getCategories().get(2).getName());
        assertEquals("https://www.speedrun.com/ajax_leaderboard.php?game=sm64&category=2414&verified=1&variable5138=16457&loadtimes=0&emulator=2", fullGame.getCategories().get(2).getUrl());

        assertEquals("1 Star", fullGame.getCategories().get(3).getName());
        assertEquals("https://www.speedrun.com/ajax_leaderboard.php?game=sm64&category=2415&verified=1&variable5138=16457&loadtimes=0&emulator=2", fullGame.getCategories().get(3).getUrl());

        assertEquals("0 Star", fullGame.getCategories().get(4).getName());
        assertEquals("https://www.speedrun.com/ajax_leaderboard.php?game=sm64&category=2416&verified=1&variable5138=16457&loadtimes=0&emulator=2", fullGame.getCategories().get(4).getUrl());
    }

    @Test
    public void extractNothingFromEmptyDocument() {
        Game emptyGame = parser.parse(Jsoup.parse(""));
        assertEquals("", emptyGame.getTitle());
        assertEquals("", emptyGame.getCover());
        assertEquals(0, emptyGame.getCategories().size());
    }

    @Test
    public void extractGameDetailsFromGameWithoutSeries() throws IOException {
        File file = TestUtils.getFile(this, "game_without_series.html");
        Document document = Jsoup.parse(file, "UTF-8");
        Game game = parser.parse(document);
        assertEquals("Super Mario 64", game.getTitle());
        assertEquals("https://www.speedrun.com/themes/sm64/cover-256.png", game.getCover());
        assertEquals("1996", game.getYear());
        assertEquals("N64, WiiVC, WiiUVC", game.getPlatforms());
    }

    @Test
    public void ignoreEmptyCategories() throws IOException {
        File file = TestUtils.getFile(this, "game_with_miscellaneous_categories.html");
        Document document = Jsoup.parse(file, "UTF-8");
        Game game = parser.parse(document);
        assertEquals(5, game.getCategories().size());
        assertEquals("Any%", game.getCategories().get(0).getName());
        assertEquals("All Levels", game.getCategories().get(1).getName());
        assertEquals("All Spirit Birds", game.getCategories().get(2).getName());
        assertEquals("The Forest Kingdom", game.getCategories().get(3).getName());
        assertEquals("The Lake Kingdom", game.getCategories().get(4).getName());
    }

}