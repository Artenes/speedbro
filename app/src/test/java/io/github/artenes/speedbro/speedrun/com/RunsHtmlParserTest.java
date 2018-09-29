package io.github.artenes.speedbro.speedrun.com;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import io.github.artenes.speedbro.TestUtils;
import io.github.artenes.speedbro.speedrun.com.models.Run;
import io.github.artenes.speedbro.speedrun.com.models.Runner;
import io.github.artenes.speedbro.speedrun.com.website.RunsHtmlParser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test for latest runs parser
 */
public class RunsHtmlParserTest {

    private RunsHtmlParser parser;
    private RunsHtmlParser categoryParser;

    @Before
    public void setUp() {
        parser = new RunsHtmlParser(RunsHtmlParser.Source.HOME_PAGE);
        categoryParser = new RunsHtmlParser(RunsHtmlParser.Source.CATEGORIES);
    }

    @Test
    public void parseARunWithAllItsDataAvailable() {
        Document document = Jsoup.parse("<table><tr class=\"height-minimal\"><td class=\"top center gamecover\" rowspan=\"3\"><a href=\"/sk\"><img class=\"cover-tall-64 border\" src=\"/themes/sk/cover-128.png\" alt=\"\"></a></td><td colspan=\"100%\" class=\"gamename\"><a href=\"/sk\">Spiral Knights</a></td></tr><tr class=\"height-minimal linked\" data-target=\"/sk/run/mk9gw85z\" title=\"Assembly was pretty messy with the Quicksilvers in the last arena, along with one major mistake in Workshop. Twins pulling through to keep the run alive.\"><td class=\"center-sm\"><a href=\"/sk/Ironclaw_Munitions_Factory#Solo\">Ironclaw Munitions Factory: Solo - Elite</a><span class=\"hidden-sm hidden-md hidden-lg\"> &nbsp; 8<small>m </small>09<small>s</small></span></td><td class=\"nobr center hidden-xs\"><img class=\"trophy\" src=\"/themes/sk/1st.png\" alt=\"\">1st</td><td class=\"center hidden-xs\"><a class='nobr nounderline' href='/user/Tenka' ><img class=\"flagicon\" src=\"/images/flags/gb/eng.png\" title=\" England\"><span class='blackoutline username color4' ><span style=\"color:#8ac951\">T</span><span style=\"color:#69c45a\">e</span><span style=\"color:#49c063\">n</span><span style=\"color:#29bc6c\">k</span><span style=\"color:#09b876\">a</span></span></a></td><td class=\"center hidden-xs\">8<small>m </small>09<small>s</small></td></tr><tr><td class='filler'></td></tr></table>");

        List<Run> latestRuns = parser.parse(document);
        Run latestRun = latestRuns.get(0);

        assertEquals(1, latestRuns.size());

        assertEquals("mk9gw85z", latestRun.getId());

        assertEquals("sk", latestRun.getGame().getId());
        assertEquals("Spiral Knights", latestRun.getGame().getTitle());
        assertEquals("https://www.speedrun.com/themes/sk/cover-128.png", latestRun.getGame().getCover());

        assertEquals("Ironclaw Munitions Factory: Solo - Elite", latestRun.getCategory());
        assertEquals("1st", latestRun.getPlacement().getPlace());
        assertEquals("https://www.speedrun.com/themes/sk/1st.png", latestRun.getPlacement().getIcon());
        assertEquals("8m 09s", latestRun.getTime());

        Runner runner = latestRun.getRunners().get(0);
        assertEquals("Tenka", runner.getId());
        assertEquals("Tenka", runner.getName());
        assertEquals("https://www.speedrun.com/themes/user/Tenka/image.png", runner.getIcon());
        assertEquals("England", runner.getCountry());
        assertEquals("https://www.speedrun.com/images/flags/gb/eng.png", runner.getFlag());
    }

    @Test
    public void parseAGameWithMultipleRuns() {
        Document document = Jsoup.parse("<html><head></head><body cz-shortcut-listen=\"true\"><table><tbody><tr class=\"height-minimal\"><td class=\"top center gamecover\" rowspan=\"4\"><a href=\"/mario_sonic_at_the_winter_olympic_games_\"><img class=\"cover-wide-64 border\" src=\"/themes/mario_sonic_at_the_winter_olympic_games_/cover-128.png\" alt=\"\"></a></td><td colspan=\"100%\" class=\"gamename\"><a href=\"/mario_sonic_at_the_winter_olympic_games_\">Mario &amp; Sonic at the Winter Olympic Games (DS)</a></td></tr><tr class=\"height-minimal linked\" data-target=\"/mario_sonic_at_the_winter_olympic_games_/run/z03rko8z\" title=\"\"><td class=\"center-sm\"><a href=\"/mario_sonic_at_the_winter_olympic_games_/Short_Track_500m#Any\">Short Track 500m: Any%</a><span class=\"hidden-sm hidden-md hidden-lg\"> &nbsp; 0<small>m </small>37<small>s </small>452<small>ms</small><span class=\"hidden-xs\"> (in-game time)</span></span></td><td class=\"nobr center hidden-xs\"><img class=\"trophy\" src=\"/themes/Default/1st.png\" alt=\"\">1st</td><td class=\"center hidden-xs\"><a class=\"nobr nounderline\" href=\"/user/Spielpro\"><img class=\"flagicon\" src=\"/images/flags/lu.png\" title=\" Luxembourg\"><span class=\"blackoutline username color3\">Spielpro</span></a></td><td class=\"center hidden-xs\">0<small>m </small>37<small>s </small>452<small>ms</small><span class=\"hidden-xs\"> (in-game time)</span></td></tr><tr class=\"height-minimal linked\" data-target=\"/mario_sonic_at_the_winter_olympic_games_/run/y2qxr0wy\" title=\"\"><td class=\"center-sm\"><a href=\"/mario_sonic_at_the_winter_olympic_games_/Speed_Skating_500m#Any\">Speed Skating 500m: Any%</a><span class=\"hidden-sm hidden-md hidden-lg\"> &nbsp; 0<small>m </small>28<small>s </small>892<small>ms</small><span class=\"hidden-xs\"> (in-game time)</span></span></td><td class=\"nobr center hidden-xs\"><img class=\"trophy\" src=\"/themes/Default/2nd.png\" alt=\"\">2nd</td><td class=\"center hidden-xs\"><a class=\"nobr nounderline\" href=\"/user/Spielpro\"><img class=\"flagicon\" src=\"/images/flags/lu.png\" title=\" Luxembourg\"><span class=\"blackoutline username color3\">Spielpro</span></a></td><td class=\"center hidden-xs\">0<small>m </small>28<small>s </small>892<small>ms</small><span class=\"hidden-xs\"> (in-game time)</span></td></tr><tr><td class=\"filler\"></td></tr></tbody></table></body></html>");
        List<Run> latestRuns = parser.parse(document);

        assertEquals(2, latestRuns.size());

        Run firstRun = latestRuns.get(0);
        Run secondRun = latestRuns.get(1);

        assertEquals("Mario & Sonic at the Winter Olympic Games (DS)", firstRun.getGame().getTitle());
        assertEquals("Mario & Sonic at the Winter Olympic Games (DS)", secondRun.getGame().getTitle());
    }

    @Test
    public void parseARunWithRunnerThatHasNoUserOrCountry() {
        Document document = Jsoup.parse("<table><tr class=\"height-minimal\"><td class=\"top center gamecover\" rowspan=\"3\"><a href=\"/sk\"><img class=\"cover-tall-64 border\" src=\"/themes/sk/cover-128.png\" alt=\"\"></a></td><td colspan=\"100%\" class=\"gamename\"><a href=\"/sk\">Spiral Knights</a></td></tr><tr class=\"height-minimal linked\" data-target=\"/sk/run/mk9gw85z\" title=\"Assembly was pretty messy with the Quicksilvers in the last arena, along with one major mistake in Workshop. Twins pulling through to keep the run alive.\"><td class=\"center-sm\"><a href=\"/sk/Ironclaw_Munitions_Factory#Solo\">Ironclaw Munitions Factory: Solo - Elite</a><span class=\"hidden-sm hidden-md hidden-lg\"> &nbsp; 8<small>m </small>09<small>s</small></span></td><td class=\"nobr center hidden-xs\"><img class=\"trophy\" src=\"/themes/sk/1st.png\" alt=\"\">1st</td><td class=\"center hidden-xs\">Tenka</td><td class=\"center hidden-xs\">8<small>m </small>09<small>s</small></td></tr><tr><td class='filler'></td></tr></table>");

        List<Run> latestRuns = parser.parse(document);
        Run latestRun = latestRuns.get(0);

        Runner runner = latestRun.getRunners().get(0);
        assertEquals("Tenka", runner.getName());
        assertTrue(runner.getId().isEmpty());
        assertTrue(runner.getIcon().isEmpty());
        assertTrue(runner.getCountry().isEmpty());
        assertTrue(runner.getFlag().isEmpty());
    }

    @Test
    public void ignoreHtmlTableThatDoesNotFollowStructure() {
        Document document = Jsoup.parse("<html><body><table><tbody><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr></tbody></table></body></html>");

        List<Run> latestRuns = parser.parse(document);

        assertTrue(latestRuns.isEmpty());
    }

    @Test
    public void extractFromRunThatCameFromCategory() throws IOException {
        Document document = TestUtils.getDocument(this, "run_from_category.html");
        List<Run> runs = categoryParser.parse(document);

        assertEquals(317, runs.size());

        Run cheese05Run = runs.get(0);
        assertEquals("https://www.speedrun.com/themes/sm64/1st.png", cheese05Run.getPlacement().getIcon());
        assertEquals("1st", cheese05Run.getPlacement().getPlace());
        assertEquals("https://www.speedrun.com/images/flags/es.png", cheese05Run.getFirstRunner().getFlag());
        assertEquals("Spain", cheese05Run.getFirstRunner().getCountry());
        assertEquals("cheese05", cheese05Run.getFirstRunner().getName());
        assertEquals("cheese05", cheese05Run.getFirstRunner().getId());
        assertEquals("1h 39m 19s", cheese05Run.getTime());
        assertEquals("https://www.speedrun.com/images/flags/jp.png", cheese05Run.getPlatform().getFlag());
        assertEquals("JPN / NTSC", cheese05Run.getPlatform().getRegion());
        assertEquals("N64", cheese05Run.getPlatform().getName());
        assertEquals("8 Feb 2018", cheese05Run.getDate());
    }

    @Test
    public void extractFromRunThatCameFromCategoryWithInGameTime() throws IOException {
        Document document = TestUtils.getDocument(this, "run_from_category_with_in_game_time.html");
        List<Run> runs = categoryParser.parse(document);

        assertEquals(729, runs.size());

        Run behemoth87Run = runs.get(0);
        assertEquals("https://www.speedrun.com/themes/supermetroid/1st.png", behemoth87Run.getPlacement().getIcon());
        assertEquals("1st", behemoth87Run.getPlacement().getPlace());
        assertEquals("https://www.speedrun.com/images/flags/gb/eng.png", behemoth87Run.getFirstRunner().getFlag());
        assertEquals("England", behemoth87Run.getFirstRunner().getCountry());
        assertEquals("Behemoth87", behemoth87Run.getFirstRunner().getName());
        assertEquals("Behemoth87", behemoth87Run.getFirstRunner().getId());
        assertEquals("41m 18s", behemoth87Run.getTime());
        assertEquals("28m 00s", behemoth87Run.getInGameTime());
        assertEquals("", behemoth87Run.getPlatform().getFlag());
        assertEquals("", behemoth87Run.getPlatform().getRegion());
        assertEquals("SNES", behemoth87Run.getPlatform().getName());
        assertEquals("2 Sep 2018", behemoth87Run.getDate());
    }

    @Test
    public void extractFromRunThatCameFromCategoryWithLoadTimes() throws IOException {
        Document document = TestUtils.getDocument(this, "run_from_category_with_load_times.html");
        List<Run> runs = categoryParser.parse(document);

        assertEquals(95, runs.size());

        Run depCow = runs.get(0);
        assertEquals("50m 57s", depCow.getTime());
        assertEquals("42m 47s", depCow.getInGameTime());
    }

    @Test
    public void extractNothingFromEmptyTable() {
        Document document = Jsoup.parse("<html><body><table><tbody><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr></tbody></table></body></html>");
        List<Run> runs = categoryParser.parse(document);
        assertEquals(0, runs.size());
    }

    @Test
    public void extractTheDateOfTheRunIfItDoesNotHavePlatform() throws IOException {
        Document document = TestUtils.getDocument(this, "run_from_category_with_date_bug.html");
        List<Run> runs = categoryParser.parse(document);

        Run firstRun = runs.get(0);
        assertEquals("6 Jul 2017", firstRun.getDate());
    }

}